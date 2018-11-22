package com.fhict.sparklesandroid

import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.os.Bundle
import android.support.v7.app.AppCompatDelegate
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.fhict.sparklesandroid.data.model.LoginResponse
import com.fhict.sparklesandroid.data.model.User
import com.fhict.sparklesandroid.data.remote.APIService
import com.fhict.sparklesandroid.data.remote.ApiUtils
import com.fhict.sparklesandroid.onboarding.OnboardingBirthday
import com.fhict.sparklesandroid.onboarding.OnboardingWelcome
import com.fhict.sparklesandroid.tabs.Tab1Fragment
import com.fhict.sparklesandroid.tabs.Tab2Fragment
import com.fhict.sparklesandroid.tabs.Tab3Fragment
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {

    private var mAPIService: APIService? = null
    var viewPager: ViewPager? = null
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {

        setSparkTheme()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        darkModeSwitch()

        viewPager = findViewById(R.id.container)
        setupViewPager(viewPager!!)

        // create api service
        mAPIService = ApiUtils.getAPIService()

        newUserCheck()

        setupCustomTabs()

    }

    private fun newUserCheck() {
        // check if app opens for first time
        val preferencesHelper: PreferencesHelper = PreferencesHelper(this)
        val didOnboard: Boolean = preferencesHelper.didOnboarding
        if (!didOnboard) {
            val i = Intent(this, OnboardingWelcome::class.java)
            startActivity(i)
        } else {
            val firstName = preferencesHelper.firstName
            val deviceId = preferencesHelper.deviceId
            //Toast.makeText(applicationContext, "$firstName + $deviceId", Toast.LENGTH_SHORT).show()
            if (!firstName.isEmpty() || !deviceId.isEmpty()) {
                // do something
                login(firstName, deviceId)
            }

        }
    }

    private fun darkModeSwitch() {
        var darkSwitch = findViewById<Switch>(R.id.dark_switch)
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            darkSwitch.isChecked = true
        }

        darkSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                restartApp()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                restartApp()
            }
        }
    }

    private fun setSparkTheme() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
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
    }

    public fun restartApp() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
    }

    fun setupCustomTabs() {

        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.setTabTextColors(R.color.black, R.color.sparkle_green)

        val tabLayoutHome = findViewById<View>(R.id.tabs) as TabLayout
        val tab = tabLayoutHome.getTabAt(1)
        tab!!.select()

        val headerView = (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(R.layout.custom_tab, null, false)

        val preferencesHelper = PreferencesHelper(applicationContext)
        val user = gson.fromJson(preferencesHelper.user, User::class.java)
        val linearLayoutOne = headerView.findViewById<LinearLayout>(R.id.ll)
        val linearLayout2 = headerView.findViewById<LinearLayout>(R.id.ll2)
        val linearLayout3 = headerView.findViewById<LinearLayout>(R.id.ll3)

        //val textView = headerView.findViewById<TextView>(R.id.name)
        //textView.text = user.firstName

        tabLayout!!.getTabAt(0)!!.customView = linearLayoutOne
        tabLayout!!.getTabAt(1)!!.customView = linearLayout2
        tabLayout!!.getTabAt(2)!!.customView = linearLayout3
    }


    private fun login(firstName: String, device_id: String) {
        mAPIService?.loginUser(firstName, device_id)!!.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    Log.i("pipo de clown", "post submitted to API." + response.body()!!.token)

                    val decoded = JWTUtils.decoded(response.body()!!.token)
                    val jsonOb = JSONObject(decoded)
                    val userId = jsonOb.get("userId").toString()

                    //Toast.makeText(applicationContext, "login succes", Toast.LENGTH_SHORT).show()

                    getUser(userId)
                } else {
                    //Toast.makeText(applicationContext, "login failed", Toast.LENGTH_SHORT).show()
                    val preferencesHelper = PreferencesHelper(applicationContext)
                    preferencesHelper.didOnboarding = false
                    val i = Intent(this@MainActivity, OnboardingWelcome::class.java)
                    startActivity(i)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("pipo de clown", t.message)
            }
        })
    }

    private fun getUser(userId: String) {
        mAPIService?.getUser(userId)!!.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {

                    val preferencesHelper = PreferencesHelper(applicationContext)

                    val gson = Gson()
                    val userObjectString = gson.toJson(response.body()!!)
                    preferencesHelper.user = userObjectString

                    // get shared preference and create object
                    // val user = gson.fromJson(userObjectString, User::class.java)
                    //setupCustomTabs()
                    //Toast.makeText(applicationContext, response.body().toString(), Toast.LENGTH_LONG).show()

                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("pipo de clown", t.message)
            }
        })
    }

    private fun setupViewPager(viewPager: ViewPager) {

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Tab1Fragment(), "")
        adapter.addFragment(Tab2Fragment(), "")
        adapter.addFragment(Tab3Fragment(), "")
        viewPager.adapter = adapter
    }

    internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList[position]
        }
    }


}
