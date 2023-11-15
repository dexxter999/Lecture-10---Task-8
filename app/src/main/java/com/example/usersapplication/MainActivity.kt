package com.example.usersapplication

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.usersapplication.databinding.ActivityMainBinding
import java.util.UUID

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val userAdapter: UserListAdapter by lazy {
        UserListAdapter(
            userEditClick = { user ->
                val intent = Intent(this, UserActivity::class.java).apply {
                    putExtra(UserActivity.USER, user)
                }
                getContent.launch(intent)
            }
        )
    }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val editedUser = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra(UserActivity.USER, User::class.java)
                } else {
                    result.data?.getParcelableExtra(UserActivity.USER)
                }

                if (editedUser != null) {
                    userAdapter.editUser(editedUser)
                } else {
                    Log.e("MainActivityasdasdasdasd", "Invalid data received from UserActivity")
                }
            } else {
                Log.e("MainActivityasdasdasdasd", "UserActivity result not OK")
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpRecycler()
        addUserClick()

    }

    private fun addUserClick() {
        binding.buttonAdd.setOnClickListener {
            val firstName = binding.etName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val email = binding.etEmail.text.toString()
            val newUser = User(
                UUID.randomUUID().toString(),
                firstName,
                lastName,
                email
            )

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "ველები არ უნდა იყოს ცარიელი!", Toast.LENGTH_SHORT).show()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "შეიყვანეთ ვალიდური ელ-ფოსტა!", Toast.LENGTH_SHORT).show()
            } else {
                addUser(newUser).also {
                    clearEditTexts()
                }
            }

        }
    }

    private fun addUser(user: User) {
        userAdapter.addUser(user)
    }

    private fun setUpRecycler() {
        binding.recyclerView.adapter = userAdapter
    }

    private fun clearEditTexts() {
        binding.etName.text.clear()
        binding.etLastName.text.clear()
        binding.etEmail.text.clear()
    }
}