package com.example.flavoryapp.uis.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flavoryapp.data.model.AnalyzedInstructions
import com.example.flavoryapp.data.model.ExtendedIngredients
import com.example.flavoryapp.data.model.Steps
import com.example.flavoryapp.databinding.ItemInstructionBinding

class InstructionsRecyclerAdapter(private val context: Context, private val dataList: List<IngredientInstructionInterface>): RecyclerView.Adapter<InstructionsRecyclerAdapter.InstructionViewHolder>() {

    class InstructionViewHolder(binding: ItemInstructionBinding): RecyclerView.ViewHolder(binding.root) {
        val instructionText = binding.text
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructionViewHolder {
        val mBinding = ItemInstructionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InstructionViewHolder(mBinding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: InstructionViewHolder, position: Int) {
        val item = dataList[position]

        when (item) {
            is Steps -> {
                holder.instructionText.text = item.number.toString() + ". " + item.step
            }

            is ExtendedIngredients -> {
                holder.instructionText.text = item.original
            }
        }
    }
}