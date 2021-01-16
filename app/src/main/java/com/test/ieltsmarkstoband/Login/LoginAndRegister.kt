package com.test.ieltsmarkstoband

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

const val RC_SIGN_IN = 0;

class LoginAndRegister : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_and_register)

        if(FirebaseAuth.getInstance().currentUser != null){
            finish()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }else{
            triggerFirebaseAuthUI()
        }


        this.supportActionBar?.hide()
    }


     private fun triggerFirebaseAuthUI(){
         // Choose authentication providers
         val providers = arrayListOf(
                 AuthUI.IdpConfig.EmailBuilder().build())

// Create and launch sign-in intent
         startActivityForResult(
                 AuthUI.getInstance()
                         .createSignInIntentBuilder()
                         .setAvailableProviders(providers)
                         .setLogo(R.mipmap.launcher_round_image)
                         .setAlwaysShowSignInMethodScreen(true)
                         .setIsSmartLockEnabled(false)
                         .build(),
                 RC_SIGN_IN)
     }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                //triggerFirebaseAuthUI()
                Toast.makeText(this, "Failed to Sign In", Toast.LENGTH_SHORT).show();
            }
        }
    }

    override fun onStop() {
        super.onStop()
        supportActionBar?.show()
    }

}