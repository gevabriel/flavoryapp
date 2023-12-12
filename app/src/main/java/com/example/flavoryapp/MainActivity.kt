package com.example.flavoryapp

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.flavoryapp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.github.kittinunf.fuel.Fuel
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private val imageList = intArrayOf(
        R.drawable.nav_home,
        R.drawable.nav_search,
        R.drawable.nav_recipes,
        R.drawable.nav_more,
    )

    private lateinit var pagerAdapter: MyPagerAdapter

    private fun isLoggedIn(): Boolean {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.contains("username") && sharedPreferences.contains("password")
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if the user is logged in
        if (!isLoggedIn()) {
            navigateToLogin()
            return
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.isUserInputEnabled = false

        val fragments: MutableList<Fragment> = ArrayList()
        fragments.add(HomeFragment())
        fragments.add(SearchFragment())
        fragments.add(MyrecipesFragment())
        fragments.add(ProfileFragment())

        //code to change selected tab color
        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.icon!!.setColorFilter(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.primary_color
                    ), PorterDuff.Mode.SRC_IN
                )
                binding.viewPager.setCurrentItem(tab.position, false)

                when (tab.position) {
                    1 -> {
                        binding.toolbar.toolbarTitle.text = getString(R.string.nav_search)
                    }

                    2 -> {
                        binding.toolbar.toolbarTitle.text = getString(R.string.nav_recipes)
                    }

                    3 -> {
                        binding.toolbar.toolbarTitle.text = getString(R.string.nav_converter)
                    }

                    else -> {
                        binding.toolbar.toolbarTitle.text = getString(R.string.app_name)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.icon!!.setColorFilter(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.default_tab_color
                    ), PorterDuff.Mode.SRC_IN
                )
            }
            override fun onTabReselected(tab: TabLayout.Tab) {}

        })

        pagerAdapter = MyPagerAdapter(this, fragments)
        binding.viewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            // Set tab text or leave it empty if you want to display only icons
            tab.text = ""
            tab.icon = ContextCompat.getDrawable(this, imageList[position])
        }.attach()
    }

    class MyPagerAdapter(fragmentActivity: FragmentActivity?, fragments: List<Fragment>) :
        FragmentStateAdapter(fragmentActivity!!) {
        private val fragments: List<Fragment>

        init {
            this.fragments = fragments
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }

        override fun getItemCount(): Int {
            return fragments.size
        }
    }
}