package com.example.flavoryapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flavoryapp.databinding.FragmentMyrecipesBinding

class MyrecipesFragment : Fragment() {
    private lateinit var mBinding: FragmentMyrecipesBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMyrecipesBinding.inflate(inflater)
        return mBinding.root
    }
}