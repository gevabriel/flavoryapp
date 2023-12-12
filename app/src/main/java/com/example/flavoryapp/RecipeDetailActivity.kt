package com.example.flavoryapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.flavoryapp.databinding.ActivityRecipedetailBinding
import com.squareup.picasso.Picasso

class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipedetailBinding
    private val viewModel: RecipeDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecipedetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the recipe ID from the intent
        val recipeId = intent.getIntExtra("recipe_id", -1)

        binding.backBtn.setOnClickListener {
            // Navigate back to the previous page
            onBackPressed()
        }

        // Observe changes in the LiveData
        viewModel.recipe.observe(this, Observer { recipe ->
            // Update the UI with the recipe data
            binding.recipeName.text = recipe.nama
            binding.recipeDetail.timerTag.text = recipe.waktu.toString() + " Menit"
            binding.recipeDetail.recipeStep.text = "Langkah\n" + recipe.langkah.replace(";", "\n")
            binding.recipeDetail.recipeIngredient.text = "Bahan\n" + recipe.bahan.replace(";", "\n")
            val imgURL = "http://10.0.2.2/storage/foto-resep/"+ recipe.id + ".jpg"
            Picasso.get().load(imgURL).into(binding.recipeImage)
            // Update other UI components as needed
        })

        // Fetch the recipe data
        viewModel.fetchRecipe(recipeId)
    }
}

