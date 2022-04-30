package com.lch.mvvmarchsample.task.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import com.lch.mvvmarchsample.R
import com.lch.mvvmarchsample.databinding.MainFragmentBinding
import com.lch.mvvmarchsample.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val binding by viewBinding<MainFragmentBinding>()
    private val viewModel by viewModels<MainViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.uiState.map {//ui diff.
            it.isLoading
        }.distinctUntilChanged().observe(viewLifecycleOwner) {
            if (it) {
                binding.loading.text = "loading"
            } else {
                binding.loading.text = ""
            }
        }

        viewModel.uiState.map {
            it.tasks
        }.distinctUntilChanged().observe(viewLifecycleOwner) { it ->
            val text = StringBuilder()
            it?.forEach {
                text.append(it.taskName)
                    .append("[${it.isCompleted}]")
                    .append("\n")
            }

            binding.message.text = text
        }

        binding.add.setOnClickListener {
            viewModel.addTask(binding.taskId.text.toString(), binding.taskName.text.toString())
        }
        binding.del.setOnClickListener {
            viewModel.deleteTask(binding.taskId.text.toString())
        }
        binding.setComplete.setOnClickListener {
            viewModel.completeTask(binding.taskId.text.toString())
        }

    }


}