package com.detorresrc.tech4300.assessment3_employeemanagement.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.detorresrc.tech4300.assessment3_employeemanagement.data.Employee
import com.detorresrc.tech4300.assessment3_employeemanagement.data.EmployeeDatabase
import com.detorresrc.tech4300.assessment3_employeemanagement.data.EmployeeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// This is the ViewModel for the Employee. It's like a bridge between the UI and the data.
class EmployeeViewModel(application: Application) : AndroidViewModel(application) {
    val allEmployees: LiveData<List<Employee>> // This holds all the employees that we can show on the UI.
    private val repository: EmployeeRepository // This is the repository that knows how to work with the data.

    init {
        val dao = EmployeeDatabase.getDatabase(application).employeeDao() // Get the DAO so we can access the database.
        repository = EmployeeRepository(dao) // Create the repository with the DAO.
        allEmployees = repository.allEmployees // Get all the employees from the repository.
    }

    // This function deletes an employee. It's like telling the repository to remove the employee from the database.
    fun deleteEmployee(employee: Employee) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(employee)
    }

    // This function adds a new employee. It's like telling the repository to add the employee to the database.
    fun insertEmployee(employee: Employee) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(employee)
    }

    // This function updates an employee. It's like telling the repository to update the employee in the database.
    fun updateEmployee(employee: Employee) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(employee)
    }
}