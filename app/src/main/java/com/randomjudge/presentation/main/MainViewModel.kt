package com.randomjudge.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.randomjudge.domain.model.AppSettings
import com.randomjudge.domain.model.DecisionType
import com.randomjudge.domain.usecase.GenerateDecisionUseCase
import com.randomjudge.domain.usecase.GetAppSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 主界面ViewModel，管理决策逻辑和状态
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val generateDecisionUseCase: GenerateDecisionUseCase,
    private val getAppSettingsUseCase: GetAppSettingsUseCase
) : ViewModel() {
    private val _question = MutableStateFlow("")
    private val _result = MutableStateFlow("点击生成判断")
    private val _isLoading = MutableStateFlow(false)
    private val _decisionType = MutableStateFlow("yes_no")
    private val _customOptions = MutableStateFlow<Pair<String, String>?>(
        Pair("选项1", "选项2")
    )
    private val _decisionTypes = MutableStateFlow<List<DecisionType>>(emptyList())
    
    val question: StateFlow<String> = _question
    val result: StateFlow<String> = _result
    val isLoading: StateFlow<Boolean> = _isLoading
    val decisionType: StateFlow<String> = _decisionType
    val customOptions: StateFlow<Pair<String, String>?> = _customOptions
    val decisionTypes: StateFlow<List<DecisionType>> = _decisionTypes

    init {
        loadSettings()
        loadDecisionTypes()
    }
    
    private fun loadSettings() {
        viewModelScope.launch {
            try {
                val settings = getAppSettingsUseCase.getCurrentSettings()
                _decisionType.value = "yes_no" // Default to yes_no, could be updated based on settings
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    private fun loadDecisionTypes() {
        // For now, we'll initialize some default decision types
        _decisionTypes.value = listOf(
            DecisionType("yes_no", "是" to "否"),
            DecisionType("agree_disagree", "同意" to "拒绝"),
            DecisionType("win_lose", "赢" to "输"),
            DecisionType("true_false", "真" to "假")
        )
    }

    /**
     * 设置问题
     */
    fun setQuestion(question: String) {
        _question.value = question
    }

    /**
     * 生成随机决策
     */
    fun generateDecision() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val options = if (_decisionType.value == "custom" && _customOptions.value != null) {
                    listOf(_customOptions.value!!.first, _customOptions.value!!.second)
                } else {
                    emptyList() // Will use default options based on decisionType
                }
                val result = generateDecisionUseCase.execute(_decisionType.value, options)
                _result.value = result
            } catch (e: Exception) {
                _result.value = "生成判断时出错"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    /**
     * 设置决策类型
     */
    fun setDecisionType(type: String) {
        _decisionType.value = type
    }
    
    /**
     * 设置自定义选项
     */
    fun setCustomOptions(options: Pair<String, String>) {
        _customOptions.value = options
    }

    /**
     * 添加用户交互熵源
     */
    fun addInteractionEntropy(x: Float, y: Float, time: Long) {
        generateDecisionUseCase.addInteractionEntropy(x, y, time)
    }
}