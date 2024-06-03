package com.detorresrc.tech4300.assessment3_employeemanagement.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.detorresrc.tech4300.assessment3_employeemanagement.R
import com.detorresrc.tech4300.assessment3_employeemanagement.data.Employee
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EmployeeRVAdapter (
    private val context: Context,
    private val deleteInterface: EmployeeClickDeleteInterface,
    private val clickInterface: EmployeeClickInterface
) : RecyclerView.Adapter<EmployeeRVAdapter.ViewHolder>()  {

    private val employees = ArrayList<Employee>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFirstName: TextView = itemView.findViewById(R.id.tvFirstName)
        val tvMiddleInitial: TextView = itemView.findViewById(R.id.tvMiddleInitial)
        val tvLastName: TextView = itemView.findViewById(R.id.tvLastName)
        val tvDesignation: TextView = itemView.findViewById(R.id.tvDesignation)
        val tvDateReference: TextView = itemView.findViewById(R.id.tvDateReference)
        val ivDeleteButton: ImageView = itemView.findViewById(R.id.ivDeleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.employee_recyclerview_item,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = employees.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val emp = employees[position]
        with(holder) {
            tvFirstName.text = emp.firstName
            if(emp.middleName.isNotEmpty())
                tvMiddleInitial.text = emp.middleName.takeIf { it.isNotEmpty() }?.substring(0, 1)?.uppercase(Locale.getDefault()) + "."
            else
                tvMiddleInitial.text = ""
            tvLastName.text = emp.lastName
            tvDesignation.text = emp.designation.uppercase(Locale.getDefault())
            tvDateReference.text = formatDate(emp.dateReference)
            ivDeleteButton.setOnClickListener { deleteInterface.onDeleteIconClick(emp) }
            itemView.setOnClickListener { clickInterface.onItemClick(emp) }
        }
    }

    private fun formatDate(dateReference: Long): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return formatter.format(Date(dateReference))
    }

    fun updateList(newList: List<Employee>) {
        employees.clear()
        employees.addAll(newList)
        notifyDataSetChanged()
    }
}

interface EmployeeClickDeleteInterface {
    fun onDeleteIconClick(employee: Employee)
}

interface EmployeeClickInterface {
    fun onItemClick(employee: Employee)
}