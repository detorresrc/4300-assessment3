package com.detorresrc.tech4300.assessment3_employeemanagement.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ParcelableEmployee(
    val id: Int,
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val designation: String,
    val department: String,
    val salary: Double,
    val dateReference: Long,
    val remarks: String
) : Parcelable {
    fun getEmployeeEntity() : Employee {
        return Employee(
            id = this.id,
            firstName = this.firstName,
            middleName = this.middleName,
            lastName = this.lastName,
            designation = this.designation,
            department = this.department,
            salary = this.salary,
            dateReference = this.dateReference,
            remarks = this.remarks
        )
    }
}