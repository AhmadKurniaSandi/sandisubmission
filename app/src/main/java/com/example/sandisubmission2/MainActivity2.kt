package com.example.sandisubmission2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.sandisubmission2.databinding.ActivityMain2Binding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private lateinit var login: String
    companion object{
        const val USERNAME = "username"
        const val LOGIN = "login"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        login = intent.getStringExtra(USERNAME)!!
        val userName = Bundle()
        userName.putString(LOGIN, login)

        val sectionsPageAdapter = SectionsPageAdapter(this, userName)
        binding.viewPager.adapter = sectionsPageAdapter
        TabLayoutMediator(binding.tabsLayout, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        userGetDetail()
    }

    private fun userGetDetail() {
        val client = ApiConfig.getApiService().userGetDetail(login)
        client.enqueue(object : Callback<UserGetDetail> {
            override fun onResponse(call: Call<UserGetDetail>, response: Response<UserGetDetail>) {
                if(response.isSuccessful){
                    response.body()?.let { UserSet(it) }
                }
            }

            override fun onFailure(call: Call<UserGetDetail>, t: Throwable) {
                Log.e("FAILED", "onFailure: ${t.message}")
            }

        })
    }

    private fun UserSet(user: UserGetDetail) {
        binding.apply {
            detailName.text = user.nama
            tvUsername.text = user.username
            Glide.with(this@MainActivity2)
                .load(user.avatarUrl)
                .into(imageView2)
            tvFollower.text = user.followers.toString()
            tvFollowing.text = user.following.toString()
            tvRepo.text = user.Repository.toString()
        }
    }
}