package com.example.employeeexample.ui.list

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.employeeexample.R
import com.example.employeeexample.data.Employee
import com.example.employeeexample.data.Gender
import com.example.employeeexample.data.Role
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item.view.*

class EmployeeAdapter(private val listener: (Boolean, Long) -> Unit):
    ListAdapter<Employee, EmployeeAdapter.ViewHolder>(
        DiffCallback()
    ){

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val itemLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ViewHolder(itemLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder (override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        init{
            itemView.setOnClickListener{
                listener.invoke(true, getItem(adapterPosition).id)
            }

            containerView.edit_employee.setOnClickListener{
                listener.invoke(false, getItem(adapterPosition).id)
            }
        }

        fun bind(employee: Employee){

            with(employee) {

                containerView.employee_name.text = name
                containerView.employee_role.text =  Role.values()[employee.role].name
                containerView.employee_age.text = containerView.context.resources
                    .getString(R.string.years, employee.age)

                containerView.employee_gender.text = Gender.values()[employee.gender].name

                with(photo){
                    if(isNotEmpty()){
                        containerView.employee_photo.setImageURI(Uri.parse(this))
                    } else{
                        containerView.employee_photo.setImageResource(R.drawable.blank_photo)
                    }
                }
                containerView.employee_responsibility?.text = responsibility

            }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Employee>() {
    override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
        return oldItem == newItem
    }
}