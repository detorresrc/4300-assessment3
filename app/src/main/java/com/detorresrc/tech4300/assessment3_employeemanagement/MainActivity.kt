package com.detorresrc.tech4300.assessment3_employeemanagement

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.detorresrc.tech4300.assessment3_employeemanagement.data.Employee
import com.detorresrc.tech4300.assessment3_employeemanagement.data.ParcelableEmployee
import com.detorresrc.tech4300.assessment3_employeemanagement.presentation.EmployeeClickDeleteInterface
import com.detorresrc.tech4300.assessment3_employeemanagement.presentation.EmployeeClickInterface
import com.detorresrc.tech4300.assessment3_employeemanagement.presentation.EmployeeRVAdapter
import com.detorresrc.tech4300.assessment3_employeemanagement.presentation.EmployeeViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

// MainActivity class that implements EmployeeClickDeleteInterface and EmployeeClickInterface
class MainActivity : AppCompatActivity(), EmployeeClickDeleteInterface, EmployeeClickInterface {
    // Declare ViewModel
    lateinit var viewModel: EmployeeViewModel
    // Declare RecyclerView and FloatingActionButton
    private lateinit var rvEmployeeList: RecyclerView
    private lateinit var fabAdd: FloatingActionButton

    // Override onCreate function
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the layout for this activity
        setContentView(R.layout.activity_main)

        // Initialize RecyclerView and FloatingActionButton
        this.rvEmployeeList = findViewById(R.id.rvEmployeeList)
        this.fabAdd = findViewById(R.id.fabAdd)

        // Set the layout manager for the RecyclerView
        this.rvEmployeeList.layoutManager = LinearLayoutManager(this)
        // Initialize the adapter for the RecyclerView
        val employeeRVAdapter = EmployeeRVAdapter(this, this, this)
        // Set the adapter for the RecyclerView
        this.rvEmployeeList.adapter = employeeRVAdapter

        // Initialize the ViewModel
        this.viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[EmployeeViewModel::class.java]
        // Observe changes in the ViewModel's allEmployees LiveData
        this.viewModel.allEmployees.observe(this, Observer { list ->
            list?.let {
                // Log when the observer is triggered
                Log.d("MainActivity", "Observer triggered! Total employees: ${it.size}")
                // Update the list in the adapter
                employeeRVAdapter.updateList(it)
            }
        })
        // Set an OnClickListener for the FloatingActionButton
        this.fabAdd.setOnClickListener{
            // Create an Intent to start AddEditEmployeeActivity
            val intent = Intent(this, AddEditEmployeeActivity::class.java)
            // Put "action" extra in the Intent
            intent.putExtra("action", "add")
            // Start AddEditEmployeeActivity
            startActivity(intent)
        }
    }

    // Override onDeleteIconClick function from EmployeeClickDeleteInterface
    override fun onDeleteIconClick(employee: Employee) {
        // Create an AlertDialog
        AlertDialog.Builder(this)
            .setTitle("Delete Employee")
            .setMessage("Are you sure you want to delete this employee?")
            .setPositiveButton("Yes") { _, _ ->
                // Delete the employee when "Yes" is clicked
                viewModel.deleteEmployee(employee)
                // Show a Toast message
                Toast.makeText(this, "Employee deleted", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No", null)
            .show()
    }

    // Override onItemClick function from EmployeeClickInterface
    override fun onItemClick(employee: Employee) {
        // Create a ParcelableEmployee from the clicked Employee
        val parcelableEmployee = ParcelableEmployee(
            employee.id,
            employee.firstName,
            employee.middleName,
            employee.lastName,
            employee.designation,
            employee.department,
            employee.salary,
            employee.dateReference,
            employee.remarks
        )
        // Create an Intent to start AddEditEmployeeActivity
        val intent = Intent(this, AddEditEmployeeActivity::class.java)
        // Put "employee" and "action" extras in the Intent
        intent.putExtra("employee", parcelableEmployee)
        intent.putExtra("action", "edit")
        // Start AddEditEmployeeActivity
        startActivity(intent)
    }
}