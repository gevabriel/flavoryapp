package com.example.flavoryapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flavoryapp.databinding.FragmentMyrecipesBinding
import android.content.Intent

class MyrecipesFragment : Fragment() {
    private lateinit var mBinding: FragmentMyrecipesBinding
    private val viewModel: MyRecipesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMyrecipesBinding.inflate(inflater)
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.rvFavorite.layoutManager = LinearLayoutManager(requireContext())

        viewModel.recipes.observe(viewLifecycleOwner, Observer { recipes ->
            val adapter = RecipeAdapter(recipes) { clickedRecipe ->
                // Handle item click, e.g., navigate to RecipeDetailActivity
                val intent = Intent(requireContext(), RecipeDetailActivity::class.java)
                intent.putExtra("recipe_id", clickedRecipe.id)
                startActivity(intent)
            }
            mBinding.rvFavorite.adapter = adapter
        })

        mBinding.chipRecipes.setOnClickListener {
            val intent = Intent(requireContext(), AddRecipeActivity::class.java)
            startActivity(intent)
        }

        viewModel.fetchData(requireContext())
    }
}

