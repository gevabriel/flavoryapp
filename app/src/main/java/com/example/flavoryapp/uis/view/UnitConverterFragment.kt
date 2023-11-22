package com.example.flavoryapp.uis.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.flavoryapp.databinding.FragmentUnitConverterBinding
import com.example.flavoryapp.R
import com.example.flavoryapp.data.api.ApiHelper
import com.example.flavoryapp.data.api.ApiHelperImpl
import com.example.flavoryapp.data.api.RetrofitBuilder
import com.example.flavoryapp.uis.intent.UnitIntent
import com.example.flavoryapp.uis.viewmodel.UnitConverterViewModel
import com.example.flavoryapp.uis.viewstate.UnitState
import com.example.flavoryapp.util.ViewModelFactory
import kotlinx.coroutines.launch

class UnitConverterFragment : Fragment() {

    private lateinit var mBinding: FragmentUnitConverterBinding
    private lateinit var mUnitArray: Array<String>

    private lateinit var mViewModel: UnitConverterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentUnitConverterBinding.inflate(inflater, container, false)
        return mBinding.root
    }

}