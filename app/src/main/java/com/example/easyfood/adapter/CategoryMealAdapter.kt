package com.example.easyfood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfood.databinding.MealItemBinding
import com.example.easyfood.model.MealsByCategory

class CategoryMealAdapter(): RecyclerView.Adapter<CategoryMealAdapter.CategoryMealSViewHolder>() {

    private var mealList = ArrayList<MealsByCategory>()
    fun setMealList(mealList: List<MealsByCategory>){
       this.mealList = mealList as ArrayList<MealsByCategory>
        notifyDataSetChanged()
    }
    inner class CategoryMealSViewHolder(val binding:MealItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealSViewHolder {
        return CategoryMealSViewHolder(MealItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CategoryMealSViewHolder, position: Int) {
        Glide.with(holder.itemView).load(mealList[position].strMealThumb).into(holder.binding.imgMeal)
        holder.binding.tvMealName.text = mealList[position].strMeal
    }

    override fun getItemCount(): Int {
        return mealList.size
    }
}