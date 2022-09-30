package com.example.sandisubmission2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.sandisubmission2.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    companion object {
        private const val TAG = "MainActivity"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this)
        binding.rvGit.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvGit.addItemDecoration(itemDecoration)
        getUser()
    }

    private fun getUser() {
        binding.searchView.clearFocus()
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                showLoading(true)
                val client = ApiConfig.getApiService().getuser(query)
                client.enqueue(object : Callback<UserResponse>{
                    override fun onResponse(
                        call: Call<UserResponse>,
                        response: Response<UserResponse>
                    ) {
                        showLoading(false)
                        if (response.isSuccessful) {
                            val response = response.body()
                            response?.let { setGitUser(it.items) }
                    }
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        Log.e(TAG, "onFailure: ${t.message}")
                    }

                })
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
            })
        }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.search.visibility = View.VISIBLE
        } else {
            binding.search.visibility = View.GONE
        }
    }

    private fun setGitUser(gitUser : List<ItemsUser>) {
        val listGitUser = ArrayList<ItemsUser>()
        for (i in gitUser) {
            val userGit = ItemsUser(i.avatarUrl,i.id ,i.login)
            listGitUser.addAll(listOf(userGit))
        }
        val adapter = UserAdapter(listGitUser)
        binding.rvGit.adapter = adapter

        adapter.ItemClick(object : UserAdapter.OnItemCallback{
            override fun clicked(user: ItemsUser) {
                val usermove = Intent(this@MainActivity, MainActivity2::class.java)
                usermove.putExtra(MainActivity2.USERNAME, user.login)
                startActivity(usermove)
            }

        })
    }
}

