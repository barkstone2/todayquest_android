package com.todayquest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

private const val TAG = "LoginActivity"
private const val RC_SIGN_IN = 9001

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val account : GoogleSignInAccount? = null // GoogleSignIn.getLastSignedInAccount(this)
        if (account != null && account.idToken != null) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("userId", account.id)
            startActivity(intent)
            finish()
        } else {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestServerAuthCode(getString(R.string.server_client_id))
                .build()

            val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

            var loginButton = findViewById<Button>(R.id.loginButton)
            loginButton.setOnClickListener {
                val account = GoogleSignIn.getLastSignedInAccount(this)
                if (account != null && account.idToken != null) {
                    println(account)
                    println(account.id)
                } else {
                    val signInIntent = mGoogleSignInClient.signInIntent
                    startActivityForResult(signInIntent, RC_SIGN_IN)
                }
            }
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id))
            .requestServerAuthCode(getString(R.string.server_client_id))
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        var loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            val account = GoogleSignIn.getLastSignedInAccount(this)
            if (account != null && account.idToken != null) {
                println(account)
                println(account.id)
            } else {
                val signInIntent = mGoogleSignInClient.signInIntent
                startActivityForResult(signInIntent, RC_SIGN_IN)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            println(task)
            try {
                println("before account")
                println(task.isSuccessful)
                val account = task.getResult(ApiException::class.java)
                println("getAccount")
                println(account)
                val id = account.id
                println(id)
                val idToken = account!!.idToken
                println(idToken)

                println(account.serverAuthCode)

                // Send the idToken to your server for verification
            } catch (e: ApiException) {
                e.printStackTrace()
                Log.w(TAG, "Sign in failed", e)
            }
        }
    }
}
