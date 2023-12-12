package com.example.flavoryapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.SearchView
import com.example.flavoryapp.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchRecipesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up RecyclerView
        binding.rvSearchRecipes.layoutManager = LinearLayoutManager(requireContext())

        // Set up ViewModel
        viewModel.searchResults.observe(viewLifecycleOwner, Observer { searchResults ->
            val adapter = RecipeAdapter(searchResults) { clickedRecipe ->
                // Handle item click, e.g., navigate to RecipeDetailActivity
                // Use the same logic as in MyrecipesFragment
                val intent = Intent(requireContext(), RecipeDetailActivity::class.java)
                intent.putExtra("recipe_id", clickedRecipe.id)
                startActivity(intent)
            }
            binding.rvSearchRecipes.adapter = adapter
        })

        // Set up SearchView listener
        binding.svRecipe.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    // Perform the search when the user submits the query
                    viewModel.searchRecipes(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle query text change if needed
                return true
            }
        })
    }
}
