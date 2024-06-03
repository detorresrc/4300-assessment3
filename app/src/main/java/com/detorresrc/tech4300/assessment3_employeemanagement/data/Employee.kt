package com.detorresrc.tech4300.assessment3_employeemanagement.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee_table")
data class Employee(
    @ColumnInfo(name = "first_name")
    val firstName: String,
    @ColumnInfo(name = "last_name")
    val lastName: String,
    @ColumnInfo(name = "middle_name")
    val middleName: String,
    @ColumnInfo(name = "designation")
    val designation: String,
    @ColumnInfo(name = "department")
    val department: String,
    @ColumnInfo(name = "salary")
    val salary: Double,
    @ColumnInfo(name = "date_reference")
    val dateReference: Long,

    val remarks: String,

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
