package com.lch.mvvmarchsample.task.di.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lch.mvvmarchsample.task.domain.ITaskRepo
import com.lch.mvvmarchsample.task.domain.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class DefaultTaskRepo : ITaskRepo {
    companion object {
        private val tasks = ArrayList<Task>()
        private val tasksObservable = MutableLiveData<Long>()
    }

    override fun observeTasks(): LiveData<Long> {
        return tasksObservable
    }

    override suspend fun addTask(task: Task) {
        return withContext(Dispatchers.IO) {
            tasks.add(task)
            saveToLocal(task)
            notifyDataChanged()
        }
    }

    private fun notifyDataChanged() {
        tasksObservable.postValue(System.currentTimeMillis())
    }

    private fun saveToLocal(task: Task) {
        // TODO("Not yet implemented")
    }

    override suspend fun deleteTask(taskId: String) {
        return withContext(Dispatchers.IO) {
            tasks.remove(Task(taskId, "",false))
            removeLocal(taskId)
            notifyDataChanged()
        }
    }

    private fun removeLocal(taskId: String) {
        //TODO("Not yet implemented")
    }

    override suspend fun getTasks(): List<Task> {
        return withContext(Dispatchers.IO) {
            delay(2000)//mock long time work.
            if (tasks.isNotEmpty()) {
                return@withContext tasks
            }
            return@withContext getFromLocal()
        }

    }

    override suspend fun updateTaskCompleteStatus(taskId: String) {
        return withContext(Dispatchers.IO) {
            tasks.findLast {
                it.taskId==taskId
            }?.isCompleted=true

            notifyDataChanged()
        }
    }

    private fun getFromLocal(): ArrayList<Task> {
        return ArrayList()
    }
}