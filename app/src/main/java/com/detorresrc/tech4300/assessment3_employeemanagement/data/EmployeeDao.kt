package com.detorresrc.tech4300.assessment3_employeemanagement.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface EmployeeDao {
    // Insert operation for Employee
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEmployee(employee: Employee)

    // Update operation for Employee
    @Update
    suspend fun updateEmployee(employee: Employee)

    // Delete operation for Employee
    @Delete
    suspend fun deleteEmployee(employee: Employee)

    // Get all employees
    @Query("Select * from employee_table order by date_reference DESC")
    fun getAllEmployees(): LiveData<List<Employee>>
}