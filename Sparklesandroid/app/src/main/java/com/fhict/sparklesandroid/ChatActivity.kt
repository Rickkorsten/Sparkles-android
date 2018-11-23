package com.fhict.sparklesandroid

import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.fhict.sparklesandroid.data.model.LoginResponse
import com.fhict.sparklesandroid.data.model.MessageResponse
import com.fhict.sparklesandroid.data.model.Relation
import com.fhict.sparklesandroid.data.model.User
import com.fhict.sparklesandroid.data.remote.APIService
import com.fhict.sparklesandroid.data.remote.ApiUtils
import com.fhict.sparklesandroid.onboarding.OnboardingWelcome
import com.google.gson.Gson
import com.stfalcon.chatkit.messages.MessageInput
import kotlinx.android.synthetic.main.activity_chat.*
import org.json.JSONObject
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatActivity : AppCompatActivity() {

    private var mAPIService: APIService? = null
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {

        val preferencesHelper = PreferencesHelper(applicationContext)

        if (preferencesHelper.darkMode) {
            setTheme(R.style.DarkTheme)
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.navigationBarColor = resources.getColor(R.color.black)
                window.statusBarColor = resources.getColor(R.color.black)
            }
        } else {
            setTheme(R.style.AppTheme_NoTitleBar)
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.navigationBarColor = resources.getColor(R.color.sparkle_background)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                window.statusBarColor = resources.getColor(R.color.sparkle_background)
            }
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // create api service
        mAPIService = ApiUtils.getAPIService()

        // get button
        val sendMessage = findViewById<MessageInput>(R.id.input)

        val user = gson.fromJson(preferencesHelper.user, User::class.java)
        val relation = gson.fromJson(preferencesHelper.relation, Relation::class.java)

        sendMessage.setInputListener {
            if (it.length > 1) {
                Toast.makeText(this, it , Toast.LENGTH_SHORT).show()
                addMessage(user.firstName,it.toString(),relation.id)
            }
            // adapter.addToStart(message, true)
            true
        }

        val sparkName = findViewById<TextView>(R.id.spark_name)

        sparkName.setOnClickListener {
            finish()
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun setSparkTheme() {


    }

    private fun addMessage(user: String, text: String, relationId: String) {
        mAPIService?.addMessage(user, text, relationId)!!.enqueue(object : Callback<MessageResponse> {
            override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                if (response.isSuccessful) {
                    Log.i("addMessage", response.body().toString())


                    Toast.makeText(applicationContext, response.body().toString(), Toast.LENGTH_SHORT).show()

                } else {
                  // do something
                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                Log.e("pipo de clown", t.message)
            }
        })
    }
}
