package com.lch.mvvmarchsample.task.data

data class Task(val taskId:String,val taskName:String,var isCompleted:Boolean=false){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Task

        if (taskId != other.taskId) return false

        return true
    }

    override fun hashCode(): Int {
        return taskId.hashCode()
    }
}
