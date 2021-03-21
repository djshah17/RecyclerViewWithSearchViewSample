package com.example.recyclerviewwithsearchfiltersample.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewwithsearchfiltersample.R
import com.example.recyclerviewwithsearchfiltersample.models.Employee


class EmployeesAdapter(private val context: Context, private var listEmployees: MutableList<Employee>?) : RecyclerView.Adapter<EmployeesAdapter.MyViewHolder>(), Filterable {

    private var filteredListEmployees : MutableList<Employee>? = null

    init {
        this.filteredListEmployees = listEmployees
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.employee_row,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        filteredListEmployees?.let {
            return it.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val employee = filteredListEmployees?.get(position)
        holder.txtEmployeeName?.text = employee?.name
        holder.txtEmployeeInfo1?.text = employee?.userName + " | " + employee?.email
        holder.txtEmployeeInfo2?.text = employee?.phone + " | " + employee?.website

        val employeeAddress = employee?.employeeAddress
        holder.txtEmployeeAddress?.text = employeeAddress?.suite + "," + employeeAddress?.street + "," + employeeAddress?.city + "," + employeeAddress?.zipCode
    }

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view){

        var txtEmployeeName: TextView? = null
        var txtEmployeeInfo1: TextView? = null
        var txtEmployeeInfo2: TextView? = null
        var txtEmployeeAddress: TextView? = null

        init {
            txtEmployeeName = view.findViewById(R.id.txt_employee_name)
            txtEmployeeInfo1 = view.findViewById(R.id.txt_employee_info1)
            txtEmployeeInfo2 = view.findViewById(R.id.txt_employee_info2)
            txtEmployeeAddress = view.findViewById(R.id.txt_employee_address)
        }

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                if (charSequence.toString().isEmpty()) {
                    filteredListEmployees = listEmployees
                } else {
                    val employeesFilteredList: MutableList<Employee> = ArrayList()
                    listEmployees?.let {
                        for (employee in it) {
                            employee.name?.let { mName ->
                                employee.userName?.let { mUserName ->
                                    if (mName.toLowerCase().contains(charSequence.toString().toLowerCase())
                                        || mUserName.toLowerCase().contains(charSequence.toString().toLowerCase()) ) {
                                        employeesFilteredList.add(employee)
                                    }
                                }
                            }
                        }
                    }
                    filteredListEmployees = employeesFilteredList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredListEmployees
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: FilterResults
            ) {
                filteredListEmployees = filterResults.values as MutableList<Employee>
                notifyDataSetChanged()
            }
        }
    }

}