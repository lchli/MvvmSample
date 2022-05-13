package com.lch.mvvmarchsample.task.domain

import androidx.lifecycle.LiveData

interface ITaskRepo {
    fun observeTasks(): LiveData<Long>
    suspend fun addTask(task: Task)
    suspend fun deleteTask(taskId: String)
    suspend fun getTasks(): List<Task>
    suspend fun updateTaskCompleteStatus(taskId: String)
}