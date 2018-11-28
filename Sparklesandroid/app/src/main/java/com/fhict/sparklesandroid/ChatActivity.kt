package com.fhict.sparklesandroid

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.TextView
import android.widget.Toast
import com.fhict.sparklesandroid.data.model.*
import com.fhict.sparklesandroid.data.remote.APIService
import com.fhict.sparklesandroid.data.remote.ApiUtils
import com.google.gson.Gson
import com.jaeger.library.StatusBarUtil
import com.r0adkll.slidr.Slidr
import com.stfalcon.chatkit.commons.models.IMessage
import com.stfalcon.chatkit.messages.MessageInput
import com.stfalcon.chatkit.messages.MessagesListAdapter
import kotlinx.android.synthetic.main.activity_chat.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.airbnb.lottie.LottieCompositionFactory.fromJson
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import java.net.URISyntaxException


class ChatActivity : AppCompatActivity() {

    private var mAPIService: APIService? = null
    private val gson = Gson()
    private var adapter : MessagesListAdapter<IMessage>? = null
    private var socket: Socket? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        val preferencesHelper = PreferencesHelper(applicationContext)

        if (preferencesHelper.darkMode) {
            setTheme(R.style.DarkTheme_NoAni)
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.navigationBarColor = ContextCompat.getColor(this, R.color.black)
                window.statusBarColor = ContextCompat.getColor(this, R.color.black)
            }
        } else {
            setTheme(R.style.AppTheme_NoTitleBar_NoAni)
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.navigationBarColor = ContextCompat.getColor(this, R.color.sparkle_background)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                window.statusBarColor = ContextCompat.getColor(this, R.color.sparkle_background)
            }
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        try {

            //if you are using a phone device you should connect to same local network as your laptop and disable your pubic firewall as well
            socket = IO.socket("https://sparklesapi.azurewebsites.net");
            //create connection
            socket!!.connect()
            // emit the event join along side with the nickname
            socket!!.emit("join", "rick");
        } catch (e: URISyntaxException) {
            e.printStackTrace();
        }


        // Let activity slide
        Slidr.attach(this)
        StatusBarUtil.setTransparent(this)
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out)

        // Get relationID
        val relationId = intent.getStringExtra(CustomViewHolder.RELATION_ID)
        Toast.makeText(this, relationId , Toast.LENGTH_SHORT).show()

        // create api service
        mAPIService = ApiUtils.getAPIService()

        // get button
        val sendMessage = findViewById<MessageInput>(R.id.input)

        // get user date
        val user = gson.fromJson(preferencesHelper.user, User::class.java)
        //val relation = gson.fromJson(preferencesHelper.relation, Relation::class.java)
        // val mainSpark = gson.fromJson(preferencesHelper.mainSpark, User::class.java)

        Toast.makeText(this, preferencesHelper.relation, Toast.LENGTH_LONG).show()

        // create adapter
        adapter = MessagesListAdapter<IMessage>(user.id, null)
        messagesList.setAdapter(adapter)

        // get all messages
        getMessagesByRelationId(relationId)
        
        socket!!.on("addMessage") {
            getMessagesByRelationId(relationId)
        }

        // on send message
        sendMessage.setInputListener {
            if (it.length > 1) {
                addMessage(user.id.toString(),user.firstName.toString(),it.toString(),relationId)
            }
            //adapter.addToStart(Message, true)
            true
        }

        val sparkName = findViewById<TextView>(R.id.spark_name)

        sparkName.setOnClickListener {
            finish()
        }

        // sparkName.text = mainSpark.firstName
    }

    private fun addMessage(userId: String,userName: String, text: String, relationId: String) {
        mAPIService?.addMessage(userId, userName, text, relationId)!!.enqueue(object : Callback<MessageResponse> {
            override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                if (response.isSuccessful) {
                    Log.i("addMessage", response.body()?.message)
                    adapter?.addToStart(response.body()?.createdMessage, true)
                    // socket.io
                   // Toast.makeText(applicationContext, response.body().toString(), Toast.LENGTH_LONG).show()

                } else {
                  // do something
                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                Log.e("pipo de clown", t.message)
            }
        })
    }

        private fun getMessagesByRelationId(relationId: String) {
            mAPIService?.getMessagesByRelationId(relationId)!!.enqueue(object : Callback<RelationMessagesResponse> {
                override fun onResponse(call: Call<RelationMessagesResponse>, response: Response<RelationMessagesResponse>) {
                    if (response.isSuccessful) {

                        Log.i("addMessage", response.body()!!.messagesList.toString())

                        adapter?.addToEnd(response.body()!!.messagesList as List<IMessage>?, true)

                    } else {
                        Toast.makeText(applicationContext, "no connection", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<RelationMessagesResponse>, t: Throwable) {
                    Log.e("pipo de clown", t.message)
                    Toast.makeText(applicationContext, "shit", Toast.LENGTH_SHORT).show()
                }
            })
        }

}
