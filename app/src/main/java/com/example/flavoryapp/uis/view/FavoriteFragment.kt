package com.example.flavoryapp.uis.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.flavoryapp.data.api.ApiHelperImpl
import com.example.flavoryapp.data.api.RetrofitBuilder
import com.example.flavoryapp.data.model.RecipeData
import com.example.flavoryapp.databinding.FragmentFavoriteBinding
import com.example.flavoryapp.uis.intent.DataIntent
import com.example.flavoryapp.uis.viewmodel.MainViewModel
import com.example.flavoryapp.uis.viewstate.DataState
import com.example.flavoryapp.util.ViewModelFactory
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() {

    private lateinit var mBinding: FragmentFavoriteBinding
    private lateinit var mAdapter: RecipeRecyclerAdapter
    private lateinit var mViewModel: MainViewModel

    private var mListener: FavoriteListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentFavoriteBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    override fun onAttach(context: Context) {
        if (context is FavoriteListener) {
            mListener = context
        }
        super.onAttach(context)
    }

    private fun initView() {
        mAdapter = RecipeRecyclerAdapter(requireContext(),
            object : RecipeRecyclerAdapter.RecipeClickListener {
                override fun onFavoriteChanged(
                    recipe: RecipeData,
                    isToFavorite: Boolean
                ) {
                    changeFavoriteStatus(recipe, isToFavorite)
                }

                override fun onRecipeClicked(recipe: RecipeData) {
                    val intent = Intent(context, RecipeDetailActivity::class.java)
                    intent.putExtra("recipe_id", recipe.id)
                    startActivity(intent)
                }

            })

        mBinding.rvFavorite.adapter = mAdapter

        mBinding.rvFavorite.setHasFixedSize(true)
        mBinding.rvFavorite.layoutManager = GridLayoutManager(requireContext(), 2)

    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService), requireContext())
        )[MainViewModel::class.java]

        lifecycleScope.launch {
            mViewModel.dataState.collect {
                when (it) {
                    is DataState.Loading -> {

                    }

                    is DataState.ResponseData -> {

                    }

                    is DataState.AddToFavoriteResponse -> {
                        val isFavorite = it.recipe.isFavorite ?: true
                        if (!isFavorite) {
                            mAdapter.removeRecipe(it.recipe)
                        }
                        mListener?.refreshHomeFragment()
                    }

                    is DataState.FavoriteResponse -> {
                        val recipes = it.recipes ?: ArrayList()
                        mAdapter.setRecipes(recipes)
                    }

                    else -> {

                    }
                }
            }
        }

        fetchData()
    }


    private fun changeFavoriteStatus(recipe: RecipeData, isToFavorite: Boolean) {
        lifecycleScope.launch {
            mViewModel.dataIntent.send(
                DataIntent.ChangeFavoriteStatus(
                    recipe, isToFavorite
                )
            )
        }
    }


    private fun fetchData() {
        lifecycleScope.launch {
            mViewModel.dataIntent.send(
                DataIntent.FetchFavoriteRecipe(
                    true
                )
            )
        }
    }


    interface FavoriteListener {
        fun refreshHomeFragment()
    }
}