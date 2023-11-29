package com.example.flavoryapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.flavoryapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var mBinding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentProfileBinding.inflate(inflater, container, false)
        return mBinding.root
    }


}