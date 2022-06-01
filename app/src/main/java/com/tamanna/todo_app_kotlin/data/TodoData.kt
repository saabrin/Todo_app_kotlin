package com.tamanna.todo_app_kotlin.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "task_table")
@Parcelize
class TodoData(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var task: String
) : Parcelable