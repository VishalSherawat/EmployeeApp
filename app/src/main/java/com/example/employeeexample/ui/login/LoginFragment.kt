package com.example.employeeexample.ui.login

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.employeeexample.R
import com.google.firebase.auth.FirebaseAuth
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        if(auth.currentUser != null){
            findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToEmployeeListFragment()
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        register.setOnClickListener {
            findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            )
        }

        login.setOnClickListener {
            user_email_container.error = null
            user_pass_container.error = null

            val email = user_email.text.toString()
            val pass = user_pass.text.toString()

            if(validateInput(email, pass)){
                progress.visibility = View.VISIBLE

                auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(requireActivity()){  task ->
                    progress.visibility = View.INVISIBLE

                    if(task.isSuccessful){
                        findNavController().navigate(
                                LoginFragmentDirections.actionLoginFragmentToEmployeeListFragment()
                        )
                    }else{
                        val toast = Toast.makeText(requireActivity(), "Authentication Failed: ${task.exception?.message}", Toast.LENGTH_LONG)
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
                        toast.show()
                    }
                }
            }
        }
    }

    private fun validateInput(email : String, pass : String) : Boolean {

        var valid = true

        if(email.isBlank()){
            user_email_container.error = "Please enter an email address"
            valid = false
        }

        if(pass.isBlank()){
            user_pass_container.error = "Please enter a password"
            valid = false
        }else if(pass.length < 8){
            user_pass_container.error = "Password should be 8 characters or more"
            valid = false
        }

        return valid
    }
}