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
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.fhict.sparklesandroid.data.model.LoginResponse
import com.fhict.sparklesandroid.data.model.User
import com.fhict.sparklesandroid.data.remote.APIService
import com.fhict.sparklesandroid.data.remote.ApiUtils
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

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * [FragmentPagerAdapter] derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */

    /**
     * The [ViewPager] that will host the section contents.
     */
    private var mAPIService: APIService? = null
    private var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.white))
            window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getResources().getColor(R.color.white))
        }

        viewPager = findViewById(R.id.container) as ViewPager
        setupViewPager(viewPager!!)

        tabLayout = findViewById(R.id.tabs) as TabLayout
        tabLayout!!.setupWithViewPager(viewPager)

        // create api service
        mAPIService = ApiUtils.getAPIService()

        // check if app opens for first time
        val preferencesHelper : PreferencesHelper = PreferencesHelper(this)
        val didOnboard : Boolean = preferencesHelper.didOnboarding

        if (!didOnboard){
            val i = Intent(this, OnboardingWelcome::class.java)
            startActivity(i)
        } else {
            val firstName = preferencesHelper.firstName
            val deviceId = preferencesHelper.deviceId
            Toast.makeText(applicationContext, "$firstName + $deviceId", Toast.LENGTH_SHORT).show()
            if (!firstName.isEmpty() || !deviceId.isEmpty()){
                // do something
                login(firstName,deviceId)
            }

        }

        val tabLayout = findViewById<TabLayout>(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(R.color.black, R.color.sparkle_green)

    }

    fun setupCustomTabs() {

        val headerView = (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(R.layout.custom_tab, null, false)

        val preferencesHelper = PreferencesHelper(applicationContext)
        val user = gson.fromJson(preferencesHelper.user, User::class.java)
        val linearLayoutOne = headerView.findViewById<LinearLayout>(R.id.ll)
        val linearLayout2 = headerView.findViewById<LinearLayout>(R.id.ll2)
        val linearLayout3 = headerView.findViewById<LinearLayout>(R.id.ll3)

        val textView = headerView.findViewById<TextView>(R.id.name)
        textView.setText("${user.firstName.toString()}")

        tabLayout!!.getTabAt(0)!!.setCustomView(linearLayoutOne)
        tabLayout!!.getTabAt(1)!!.setCustomView(linearLayout2)
        tabLayout!!.getTabAt(2)!!.setCustomView(linearLayout3)
    }

    fun login(firstName: String, device_id: String) {
        mAPIService?.loginUser(firstName, device_id )!!.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful()) {
                    Log.i( "pipo de clown","post submitted to API." + response.body()!!.token)

                    var decoded = JWTUtils.decoded(response.body()!!.token);
                    var jsonOb = JSONObject(decoded);
                    var userId=jsonOb.get("userId").toString();

                    Toast.makeText(applicationContext, "login succes", Toast.LENGTH_SHORT).show()

                    getUser(userId)
                }else {
                    Toast.makeText(applicationContext, "login failed", Toast.LENGTH_SHORT).show()
                    val preferencesHelper = PreferencesHelper(applicationContext)
                    preferencesHelper.didOnboarding = false
                    val i = Intent(this@MainActivity, OnboardingWelcome::class.java)
                    startActivity(i)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("pipo de clown",t.message)
            }
        })
    }

    private fun getUser(userId: String) {
        mAPIService?.getUser(userId)!!.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful()) {

                    val preferencesHelper = PreferencesHelper(applicationContext)

                    val gson = Gson()
                    val userObjectString = gson.toJson(response.body()!!)
                    preferencesHelper.user = userObjectString;

                        // get shared preference and create object
                     // val user = gson.fromJson(userObjectString, User::class.java)
                    setupCustomTabs();
                    Toast.makeText(applicationContext, response.body()!!.preference.toString(), Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("pipo de clown",t.message)
            }
        })
    }

    private fun setupViewPager(viewPager: ViewPager) {

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Tab1Fragment(),"")
        adapter.addFragment(Tab2Fragment(), "")
        adapter.addFragment(Tab3Fragment(),"")
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
