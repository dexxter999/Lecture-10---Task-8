package com.example.usersapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.usersapplication.databinding.ListItemBinding

class UserListAdapter(
    private val userEditClick: (User) -> Unit
) : ListAdapter<User, UserListAdapter.UserViewHolder>(UserItemCallback()) {


    inner class UserViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.tvName.text = user.name
            binding.tvLastName.text = user.lastName
            binding.tvEmail.text = user.email


            binding.buttonEdit.setOnClickListener { userEditClick(getItem(adapterPosition)) }
            binding.buttonDelete.setOnClickListener { deleteUser(user) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    fun addUser(user: User) {
        val newList = currentList.toMutableList().apply {
            add(user)
        }
        submitList(newList)
        notifyItemInserted(newList.size - 1)
    }

    private fun deleteUser(user: User) {
        val position = currentList.indexOf(user)
        if (position != -1) {
            val newList = currentList.toMutableList().apply {
                removeAt(position)
            }
            submitList(newList)
            notifyItemRemoved(position)
        }
    }

    fun editUser( editedUser: User) {
        val newList = currentList.toMutableList()
        val position = newList.indexOfFirst { it.id == editedUser.id }

        if (position != -1) {
            newList[position] = editedUser
            submitList(newList)
        }
    }

}
