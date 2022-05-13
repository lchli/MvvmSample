package com.lch.mvvmarchsample.task.ui

import androidx.lifecycle.*
import com.lch.mvvmarchsample.task.domain.ITaskRepo
import com.lch.mvvmarchsample.task.domain.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: ITaskRepo) : ViewModel(), Observer<Long> {

    private val _uiState = MutableLiveData<TaskUIState>()
    val uiState: LiveData<TaskUIState> = _uiState

    private val st: TaskUIState
        get() {
            _uiState.value?.apply {
                return this
            }
            return TaskUIState(false, null)
        }

    init {
        repo.observeTasks().observeForever(this)
    }

    fun addTask(id: String, name: String) {
        viewModelScope.launch {
            repo.addTask(Task(id, name))
        }
    }

    fun deleteTask(id: String) {
        viewModelScope.launch {
            repo.deleteTask(id)
        }
    }

    fun completeTask(id: String) {
        viewModelScope.launch {
            repo.updateTaskCompleteStatus(id)
        }
    }


    override fun onCleared() {
        super.onCleared()
        repo.observeTasks().removeObserver(this)
    }

    override fun onChanged(t: Long?) {
        refreshTasks()
    }

    private fun refreshTasks() {
        _uiState.postValue(st.copy(isLoading = true))

        viewModelScope.launch {
            val listItems = repo.getTasks().map {
                TaskItemUIState(it.isCompleted, it.taskName, it.taskId)
            }

            _uiState.postValue(st.copy(isLoading = false, tasks = listItems))
        }
    }

    data class TaskUIState(val isLoading: Boolean, val tasks: List<TaskItemUIState>?)

    data class TaskItemUIState(val isCompleted: Boolean, val taskName: String, val taskId: String)
}