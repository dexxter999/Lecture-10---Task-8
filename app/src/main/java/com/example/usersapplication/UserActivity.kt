package com.example.usersapplication

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.usersapplication.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {

    private val binding by lazy { ActivityUserBinding.inflate(layoutInflater) }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUp()

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun setUp() {
        val user = intent.getParcelableExtra(USER, User::class.java) as User


        user.let {
            binding.etEditName.setText(it.name)
            binding.etEditLastName.setText(it.lastName)
            binding.etEditEmail.setText(it.email)
        }

        binding.buttonSave.setOnClickListener {


            if (binding.etEditName.text.isEmpty() || binding.etEditLastName.text.isEmpty() || binding.etEditEmail.text.isEmpty()) {
                Toast.makeText(this, "ველები არ უნდა იყოს ცარიელი!", Toast.LENGTH_SHORT).show()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEditEmail.text.toString()).matches()) {
                Toast.makeText(this, "შეიყვანეთ ვალიდური ელ-ფოსტა!", Toast.LENGTH_SHORT).show()
            } else {
                val editedUser = User (
                    user.id,
                    binding.etEditName.text.toString(),
                    binding.etEditLastName.text.toString(),
                    binding.etEditEmail.text.toString()
                )

                val resultIntent = Intent().apply {
                    putExtra(USER, editedUser)
                }

                setResult(RESULT_OK, resultIntent)
                finish()
            }


        }
    }


    companion object {
        const val USER = "USER"
    }


}