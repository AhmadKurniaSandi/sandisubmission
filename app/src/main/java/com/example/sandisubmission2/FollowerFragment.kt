package com.example.sandisubmission2

import FollowAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sandisubmission2.databinding.FragmentFollower2Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FollowerFragment : Fragment() {
    private var binding: FragmentFollower2Binding? = null
    private val bind get() = binding!!

    companion object {
        const val LOGIN = "login"
        const val TAG = "FollowerFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollower2Binding.inflate(layoutInflater)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getFollower()
    }

    private fun getFollower() {
        showLoading(true)
        val login = arguments?.getString(MainActivity2.LOGIN)
        val client = ApiConfig.getApiService().getFollower(login!!)
        client.enqueue(object : Callback<ArrayList<ItemsUser>> {
            override fun onResponse(
                call: Call<ArrayList<ItemsUser>>,
                response: Response<ArrayList<ItemsUser>>
            ) {
                showLoading(false)
                response.body()?.let { setFollowerUser(it) }
            }

            override fun onFailure(call: Call<ArrayList<ItemsUser>>, t: Throwable) {
                Log.e(TAG, "onfailure ${t.message}")
            }


        })
    }

    private fun setFollowerUser(follower: ArrayList<ItemsUser>) {
        val userFollow = ArrayList<ItemsUser>()
        for (follow in follower) {
            val userfollow = ItemsUser(follow.avatarUrl, follow.id, follow.login)
            userFollow.addAll(listOf(userfollow))
        }
        val adapter = FollowAdapter(userFollow)
        bind.follower.layoutManager = LinearLayoutManager(context)
        bind.follower.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            bind.search.visibility = View.VISIBLE
        } else {
            bind.search.visibility = View.GONE
        }
    }
}