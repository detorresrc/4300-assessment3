package com.detorresrc.tech4300.assessment3_employeemanagement

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.detorresrc.tech4300.assessment3_employeemanagement.data.Employee
import com.detorresrc.tech4300.assessment3_employeemanagement.data.ParcelableEmployee
import com.detorresrc.tech4300.assessment3_employeemanagement.presentation.EmployeeViewModel

// AddEditEmployeeActivity class for adding and editing employees
class AddEditEmployeeActivity : AppCompatActivity() {
    // Declare EditTexts for employee details
    private lateinit var etFirstName: EditText
    private lateinit var etMiddleName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etDesignation: EditText
    private lateinit var etDepartment: EditText
    private lateinit var etSalary: EditText
    private lateinit var etRemarks: EditText
    // Declare ParcelableEmployee variable for storing employee details
    private var employee : ParcelableEmployee? = null
    // Declare String for storing action type (add or edit)
    private lateinit var action: String

    // Declare ViewModel for managing data
    private lateinit var viewModel: EmployeeViewModel
    // Declare Int for storing employee ID
    private var employeeId = 0;

    // Override onCreate function
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the layout for this activity
        setContentView(R.layout.activity_add_edit_employee)

        // Initialize ViewModel
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(EmployeeViewModel::class.java)

        // Initialize EditTexts
        etFirstName = findViewById(R.id.etFirstName)
        etMiddleName = findViewById(R.id.etMiddleName)
        etLastName = findViewById(R.id.etLastName)
        etDesignation = findViewById(R.id.etDesignation)
        etDepartment = findViewById(R.id.etDepartment)
        etSalary = findViewById(R.id.etSalary)
        etRemarks = findViewById(R.id.etRemarks)

        // Initialize Button and set its OnClickListener
        val idBtn = findViewById<Button>(R.id.idBtn)
        idBtn.setOnClickListener {
            // Validate fields before performing action
            if (validateFields()){
                // Perform action based on action type
                Log.d("AddEditEmployeeActivity", "Action: $action")
                when (action) {
                    "add" -> saveEmployee()
                    "edit" -> employee?.let { updateEmployee() }
                }
                // Start MainActivity and finish current activity
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }
        }

        // Get action type from Intent extras
        action = intent.getStringExtra("action")!!
        // Perform actions based on action type
        if(action == "edit"){
            // Extract employee details if action type is "edit"
            employee = extractEmployee()
            // Set employee details in EditTexts
            employee?.let { setEmployeeObject(it) }
            // Change Button text to "Update Employee"
            idBtn.text = getString(R.string.update_employee)
        }else{
            // Change Button text to "Add Employee" if action type is "add"
            idBtn.text = getString(R.string.add_employee)
        }
    }

    // Function for updating employee details
    private fun updateEmployee() {
        // Create Employee object from EditTexts
        val emp = createEmployeeObject()
        // Update employee in database
        viewModel.updateEmployee(emp)
        // Show Toast message
        Toast.makeText(this, getString(R.string.employee_updated), Toast.LENGTH_SHORT).show()
    }

    // Function for saving new employee
    private fun saveEmployee() {
        this.employeeId = 0
        // Create Employee object from EditTexts
        val emp = createEmployeeObject()
        // Insert new employee in database
        viewModel.insertEmployee(emp)
        // Show Toast message
        Toast.makeText(this, getString(R.string.employee_added), Toast.LENGTH_SHORT).show()
    }

    // Function for creating Employee object from EditTexts
    private fun createEmployeeObject() = Employee(
        firstName = etFirstName.text.toString(),
        middleName = etMiddleName.text.toString(),
        lastName = etLastName.text.toString(),
        designation = etDesignation.text.toString(),
        department = etDepartment.text.toString(),
        salary = etSalary.text.toString().toDouble(),
        remarks = etRemarks.text.toString(),
        dateReference = System.currentTimeMillis(),
        id = employeeId
    )

    // Function for extracting ParcelableEmployee from Intent extras
    private fun extractEmployee() : ParcelableEmployee? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("employee", ParcelableEmployee::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("employee")
        }
    }

    // Function for setting employee details in EditTexts
    private fun setEmployeeObject(employee: ParcelableEmployee) {
        etFirstName.setText(employee.firstName)
        etMiddleName.setText(employee.middleName)
        etLastName.setText(employee.lastName)
        etDesignation.setText(employee.designation)
        etDepartment.setText(employee.department)
        etSalary.setText(employee.salary.toString())
        etRemarks.setText(employee.remarks)
        employeeId = employee.id
    }

    // Function for validating fields
    private fun validateFields() : Boolean {
        // List of required fields
        val fields = listOf(etFirstName, etLastName, etDesignation, etDepartment, etSalary)
        var isValid = true

        // Check each field if it's empty
        fields.forEach { field ->
            if (field.text.toString().isEmpty()) {
                // Set error background if field is empty
                field.setBackgroundResource(R.drawable.error_border)
                isValid = false
            } else {
                // Set normal background if field is not empty
                field.setBackgroundResource(R.drawable.custom_border)
            }
        }

        // Show Toast message if any field is empty
        if (!isValid) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
        }

        // Return validation result
        return isValid
    }
}