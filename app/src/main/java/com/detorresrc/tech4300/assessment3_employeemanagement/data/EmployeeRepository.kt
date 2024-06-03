package com.detorresrc.tech4300.assessment3_employeemanagement.data

import androidx.lifecycle.LiveData

class EmployeeRepository(
    private val employeeDao: EmployeeDao // This is like a gatekeeper, it knows how to access the database
) {
    val allEmployees: LiveData<List<Employee>> = employeeDao.getAllEmployees() // This gets all the employees from the database

    // This function adds a new employee to the database
    suspend fun insert(employee: Employee) {
        employeeDao.insertEmployee(employee)
    }

    // This function updates the details of an existing employee in the database
    suspend fun update(employee: Employee) {
        employeeDao.updateEmployee(employee)
    }

    // This function removes an employee from the database
    suspend fun delete(employee: Employee) {
        employeeDao.deleteEmployee(employee)
    }
}