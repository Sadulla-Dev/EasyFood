package com.example.easyfood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfood.databinding.PopularItemBinding
import com.example.easyfood.model.CategoryList
import com.example.easyfood.model.CategoryMeals

class MostPopularAdapter(): RecyclerView.Adapter<MostPopularAdapter.PopularViewHolder>() {

    private var mealList = ArrayList<CategoryMeals>()
    lateinit var onItemClick:((CategoryMeals) -> Unit)
    fun setMeals(mealsList: ArrayList<CategoryMeals>){
        this.mealList = mealsList
        notifyDataSetChanged()
    }
    class PopularViewHolder(var binding: PopularItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(PopularItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        Glide.with(holder.itemView).load(mealList[position].strMealThumb).into(holder.binding.imgPopularMeal)
        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealList[position])
        }
    }

    override fun getItemCount(): Int {
        return mealList.size
    }
}