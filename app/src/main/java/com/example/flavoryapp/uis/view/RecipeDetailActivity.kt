package com.example.flavoryapp.uis.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.flavoryapp.R
import com.example.flavoryapp.data.api.ApiHelperImpl
import com.example.flavoryapp.data.api.RetrofitBuilder
import com.example.flavoryapp.data.model.AnalyzedInstructions
import com.example.flavoryapp.data.model.ExtendedIngredients
import com.example.flavoryapp.data.model.RecipeData
import com.example.flavoryapp.databinding.ActivityRecipedetailBinding
import com.example.flavoryapp.uis.intent.DataIntent
import com.example.flavoryapp.uis.viewmodel.MainViewModel
import com.example.flavoryapp.uis.viewstate.DataState
import com.example.flavoryapp.util.ViewModelFactory
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import kotlin.math.abs

class RecipeDetailActivity: AppCompatActivity(), TabLayout.OnTabSelectedListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var mBinding: ActivityRecipedetailBinding
    private var recipeId = 0

    private var isToolbarVisible = false
    private val titleList = arrayOf("Ingredients", "Instructions")

    private lateinit var fragmentViewModel: MyViewModel
    private lateinit var recipeData: RecipeData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRecipedetailBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        recipeId = intent.getIntExtra("recipe_id", 0)

        initViewModel()
        initView()

        fetchDetailData(recipeId)
    }

    private fun initView() {
        setSupportActionBar(mBinding.toolbar)

        mBinding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        mBinding.appBar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            val maxScroll = appBarLayout.totalScrollRange
            val percentage = (abs(verticalOffset).toFloat() / maxScroll.toFloat())

            if (percentage > 0.7 && !isToolbarVisible) {
                // Change toolbar title color to white and toolbar background color
                mBinding.toolbar.background = ContextCompat.getDrawable(this, R.drawable.toolbar_background)
                mBinding.toolbarTitle.visibility = View.VISIBLE
                mBinding.recipeName.visibility = View.GONE
                isToolbarVisible = true
            } else if (percentage <= 0.7 && isToolbarVisible) {
                // Change toolbar title color to your desired color and toolbar background color
                mBinding.toolbar.background = ColorDrawable(Color.TRANSPARENT)
                mBinding.toolbarTitle.visibility = View.GONE
                mBinding.recipeName.visibility = View.VISIBLE
                isToolbarVisible = false
            }
        }
    }

    private fun initViewModel() {

        fragmentViewModel = ViewModelProvider(this)[MyViewModel::class.java]

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService), this)
        )[MainViewModel::class.java]

        lifecycleScope.launch {
            viewModel.dataState.collect {
                when (it) {
                    is DataState.Loading -> {

                    }

                    is DataState.RecipeDetail -> {
                        Log.d("haancha", "initViewModel: "+it.recipeData)
                        recipeData = it.recipeData
                        populateView(recipeData)
                    }

                    else -> {

                    }
                }
            }
        }
    }

    private fun populateView(recipeData: RecipeData) {
        mBinding.toolbarTitle.text = recipeData.title
        mBinding.recipeName.text = recipeData.title

        var mealType = ""
        for (cuisine in recipeData.cuisines!!) {
            mealType += cuisine.plus(", ") //-2 below coz i added 2 characters here
        }

        if (mealType.isNotEmpty())
            mBinding.mealType.text = mealType.substring(0, mealType.length - 2)

        Picasso.with(this).load(recipeData.image).into(mBinding.recipeImage)

        if (recipeData.vegetarian == true) {
            mBinding.recipeDetail.vegImage.setImageResource(R.drawable.icon_veg)
            mBinding.recipeDetail.vegTag.text = "Vegetarian"
        } else {
            mBinding.recipeDetail.vegImage.setImageResource(R.drawable.icon_meat)
            mBinding.recipeDetail.vegTag.text = "Contains Egg/Meat"
        }

        mBinding.recipeDetail.timerTag.text = recipeData.readyInMinutes.toString() + " mins"
        mBinding.recipeDetail.servingTag.text = recipeData.servings.toString() + " servings"

        for (nutrients in recipeData.nutrition?.nutrients!!) {
            if (nutrients.name.equals("calories", true)) {
                mBinding.recipeDetail.calorieTag.text = nutrients.amount.toString() + " " +nutrients.unit + "/serving"
                break
            }
        }

        mBinding.recipeDetail.tabLayout.addOnTabSelectedListener(this)
        val pagerAdapter = MyPagerAdapter(this)
        mBinding.recipeDetail.viewPager.adapter = pagerAdapter

        TabLayoutMediator(mBinding.recipeDetail.tabLayout, mBinding.recipeDetail.viewPager) { tab, position ->
            // Set tab text or leave it empty if you want to display only icons
            tab.text = titleList[position]
        }.attach()
    }

    private fun fetchDetailData(id: Int) {
        lifecycleScope.launch {
            viewModel.dataIntent.send(
                DataIntent.FetchRecipeDetail(
                    id
                )
            )
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab) {

        when(tab.position) {
            0 -> { //ingredients
                fragmentViewModel.whichData.value = "ingredient"
                fragmentViewModel.ingredients.value = recipeData.extendedIngredients
            }
            else -> { //instructions
                fragmentViewModel.whichData.value = "instruction"
                fragmentViewModel.instructions.value = recipeData.analyzedInstructions
            }
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }
}

class MyPagerAdapter(fragmentActivity: FragmentActivity?) :
    FragmentStateAdapter(fragmentActivity!!) {

    override fun createFragment(position: Int): Fragment {
        return InstructionsFragment()
    }

    override fun getItemCount(): Int {
        return 2
    }
}

class MyViewModel : ViewModel() {
    val whichData = MutableLiveData<String>()
    val ingredients = MutableLiveData<List<ExtendedIngredients>>()
    val instructions = MutableLiveData<List<AnalyzedInstructions>>()
}