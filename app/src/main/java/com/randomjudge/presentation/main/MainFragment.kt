package com.randomjudge.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.randomjudge.R
import com.randomjudge.data.util.DecisionAnimator
import com.randomjudge.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * 主界面Fragment，展示决策器核心功能
 */
@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: MainViewModel by viewModels()
    private val animator = DecisionAnimator()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
    binding.apply {
        // 输入框
        etQuestion?.let { questionEditText ->
            questionEditText.addTextChangedListener(object : android.text.TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                
                override fun afterTextChanged(s: android.text.Editable?) {
                    viewModel.setQuestion(s.toString())
                }
            })
        }
        
        // 判断按钮
        btnGenerate.setOnClickListener {
            try {
                viewModel.generateDecision()
                // 添加点击位置到熵源
                viewModel.addInteractionEntropy(it.x, it.y, System.currentTimeMillis())
            } catch (e: Exception) {
                // Handle error gracefully
                binding.tvResult.text = "生成判断时出错，请重试"
            }
        }
        
        // 菜单按钮
        btnMenu?.let { menuButton ->
            menuButton.setOnClickListener {
                showMenu(it)
            }
        }
    }
}

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.result.collect { result ->
                    if (result != "点击生成判断") {
                        animator.animateDecisionProcess(
                            binding.btnGenerate,
                            binding.tvResult,
                            result
                        )
                    } else {
                        binding.tvResult.text = result
                    }
                }
            }
        }
    }

    private fun showMenu(view: View) {
        val popup = PopupMenu(requireContext(), view)
        popup.menuInflater.inflate(R.menu.main_menu, popup.menu)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_settings -> {
                    findNavController().navigate(R.id.action_mainFragment_to_settingsFragment)
                    true
                }
                R.id.menu_about -> {
                    findNavController().navigate(R.id.action_mainFragment_to_aboutFragment)
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}