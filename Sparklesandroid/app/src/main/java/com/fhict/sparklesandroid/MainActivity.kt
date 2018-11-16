package com.fhict.sparklesandroid

import android.content.Intent
import android.provider.Settings
import android.support.design.widget.TabLayout
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import android.widget.Toast
import com.auth0.android.jwt.Claim
import com.auth0.android.jwt.JWT
import com.fhict.sparklesandroid.data.model.LoginResponse
import com.fhict.sparklesandroid.data.model.User
import com.fhict.sparklesandroid.data.remote.APIService
import com.fhict.sparklesandroid.data.remote.ApiUtils
import com.fhict.sparklesandroid.onboarding.OnboardingWelcome
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
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
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    /**
     * The [ViewPager] that will host the section contents.
     */
    private var mViewPager: ViewPager? = null
    private var mAPIService: APIService? = null
    private var mResponseTv: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById<View>(R.id.container) as ViewPager
        mViewPager!!.adapter = mSectionsPagerAdapter

        val tabLayout = findViewById<View>(R.id.tabs) as TabLayout

        mViewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(mViewPager))

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
    }

    fun login(firstName: String, device_id: String) {
        mAPIService?.loginUser(firstName, device_id )!!.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful()) {
                    Log.i( "pipo de clown","post submitted to API." + response.body()!!.token)

                    var decoded = JWTUtils.decoded(response.body()!!.token);
                    var jsonOb = JSONObject(decoded);
                    var userId=jsonOb.get("userId").toString();

                    //Toast.makeText(applicationContext, userId.toString(), Toast.LENGTH_SHORT).show()

                    getUser(userId)
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

                    Toast.makeText(applicationContext, response.body()!!.preference.toString(), Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("pipo de clown",t.message)
            }
        })
    }

    fun showResponse(response: String) {
        if (mResponseTv?.getVisibility() === View.GONE) {
            mResponseTv?.setVisibility(View.VISIBLE)
        }
        mResponseTv?.setText(response)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    class PlaceholderFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_main, container, false)
            val textView = rootView.findViewById<View>(R.id.section_label) as TextView
            textView.text = getString(R.string.section_format, arguments!!.getInt(ARG_SECTION_NUMBER))
            return rootView
        }

        companion object {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private val ARG_SECTION_NUMBER = "section_number"

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }

    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1)
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 3
        }
    }
}
