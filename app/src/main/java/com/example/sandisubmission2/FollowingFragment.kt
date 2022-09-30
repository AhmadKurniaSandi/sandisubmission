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
import com.example.sandisubmission2.databinding.FragmentFollowingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingFragment : Fragment() {
    private var binding: FragmentFollowingBinding? = null
    private val bind get() = binding!!

    companion object {
        const val LOGIN = "login"
        const val TAG = "FollowingFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowingBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getFollowing()
    }

    private fun getFollowing() {
        showLoading(true)
        val login = arguments?.getString(MainActivity2.LOGIN)!!
        val client = ApiConfig.getApiService().getFollowing(login)
        client.enqueue(object : Callback<ArrayList<ItemsUser>> {
            override fun onResponse(
                call: Call<ArrayList<ItemsUser>>,
                response: Response<ArrayList<ItemsUser>>
            ) {
                showLoading(false)
                response.body()?.let { setFollowingUser(it) }
            }

            override fun onFailure(call: Call<ArrayList<ItemsUser>>, t: Throwable) {
                Log.e(TAG, "onfailure ${t.message}")
            }


        })
    }

    private fun setFollowingUser(following: ArrayList<ItemsUser>) {
        val userFollow = ArrayList<ItemsUser>()
        for (follow in following) {
            val userfollowing = ItemsUser(follow.avatarUrl, follow.id, follow.login)
            userFollow.addAll(listOf(userfollowing))
        }
        val adapter = FollowAdapter(userFollow)
        bind.following.layoutManager = LinearLayoutManager(context)
        bind.following.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            bind.search.visibility = View.VISIBLE
        } else {
            bind.search.visibility = View.GONE
        }
    }
}