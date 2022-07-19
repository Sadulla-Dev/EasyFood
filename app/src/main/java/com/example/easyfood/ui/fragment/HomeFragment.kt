package com.example.easyfood.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.easyfood.adapter.CategoriesAdapter
import com.example.easyfood.adapter.MostPopularAdapter
import com.example.easyfood.databinding.FragmentHomeBinding
import com.example.easyfood.model.MealsByCategory
import com.example.easyfood.model.Meal
import com.example.easyfood.ui.activity.CategoryMealsActivity
import com.example.easyfood.ui.activity.MainActivity
import com.example.easyfood.ui.activity.MealActivity
import com.example.easyfood.viewModel.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemAdapter: MostPopularAdapter
    private lateinit var categoryAdapter: CategoriesAdapter

    companion object{
        const val MEAL_ID = "com.example.easyfood.ui.fragment.idMeal"
        const val MEAL_NAME = "com.example.easyfood.ui.fragment.nameMeal"
        const val MEAL_THUMB = "com.example.easyfood.ui.fragment.thumbMeal"
        const val CATEGORY_NAME = "com.example.easyfood.ui.fragment.categoryName"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        popularItemAdapter = MostPopularAdapter()
        categoryAdapter = CategoriesAdapter()
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
        viewModel.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

        viewModel.getPopularItems()
        observePopularItemLiveData()
        onPopularItemClick()

        viewModel.getCategories()
        observeCategoriesLiveData()

        prepareCategoriesRecyclerView()
        onCategoryClick()
    }

    private fun onCategoryClick() {
        categoryAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = categoryAdapter
        }
    }

    private fun observeCategoriesLiveData() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner){ categories ->
            categoryAdapter.setCategoryList(categories)
        }
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
        viewModel.observePopularItemLiveData().observe(viewLifecycleOwner){ mealList ->
            popularItemAdapter.setMeals(mealsList = mealList as ArrayList<MealsByCategory> )
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
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner) { meal ->
            Glide.with(this@HomeFragment).load(meal!!.strMealThumb).into(binding.imgRandomMeal)

            this.randomMeal = meal

        }
    }

}