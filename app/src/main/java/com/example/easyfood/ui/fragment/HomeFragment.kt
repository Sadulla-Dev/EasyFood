package com.example.easyfood.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.easyfood.adapter.MostPopularAdapter
import com.example.easyfood.databinding.FragmentHomeBinding
import com.example.easyfood.model.CategoryMeals
import com.example.easyfood.model.Meal
import com.example.easyfood.ui.activity.MealActivity
import com.example.easyfood.viewModel.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemAdapter: MostPopularAdapter

    companion object{
        const val MEAL_ID = "com.example.easyfood.ui.fragment.idMeal"
        const val MEAL_NAME = "com.example.easyfood.ui.fragment.nameMeal"
        const val MEAL_THUMB = "com.example.easyfood.ui.fragment.thumbMeal"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProviders.of(this)[HomeViewModel::class.java]
        popularItemAdapter = MostPopularAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemRecyclerView()
        homeMvvm.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

        homeMvvm.getPopularItems()
        observePopularItemLiveData()
        onPopularItemClick()
    }

    private fun onPopularItemClick() {
        popularItemAdapter.onItemClick = { meal->
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            startActivity(intent)
        }
    }

    private fun preparePopularItemRecyclerView() {
         binding.recViewMealsPopular.apply {
             layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false )
             adapter = popularItemAdapter
         }
    }

    private fun observePopularItemLiveData() {
        homeMvvm.observePopularItemLiveData().observe(viewLifecycleOwner){ mealList ->
            popularItemAdapter.setMeals(mealsList = mealList as ArrayList<CategoryMeals> )
        }
    }

    private fun onRandomMealClick() {
        binding.randomMeal.setOnClickListener {
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner) { meal ->
            Glide.with(this@HomeFragment).load(meal!!.strMealThumb).into(binding.imgRandomMeal)

            this.randomMeal = meal

        }
    }

}