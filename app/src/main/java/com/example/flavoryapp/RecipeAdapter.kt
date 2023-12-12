package com.example.flavoryapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flavoryapp.databinding.ItemRecipeBinding
import com.squareup.picasso.Picasso;

class RecipeAdapter(private val recipes: List<Recipe>, private val onItemClick: (Recipe) -> Unit) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding =
            ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipes[position])
        holder.itemView.setOnClickListener {
            onItemClick.invoke(recipes[position])
        }
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    inner class RecipeViewHolder(private val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe) {
            // Bind the recipe data to the views in your item_recipe layout
            binding.recipeName.text = recipe.nama
            binding.recipeCalorie.text = recipe.waktu.toString()
//            binding.recipeImage.setImageResource(R.drawable.flavory)
            val imgURL = "http://10.0.2.2/storage/foto-resep/"+ recipe.id + ".jpg"
            Picasso.get().load(imgURL).into(binding.recipeImage)
            // Bind other views accordingly
        }
    }
}
