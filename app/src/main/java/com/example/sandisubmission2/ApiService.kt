package com.example.sandisubmission2

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface  ApiService{
    @GET("search/users")
    fun getuser(
        @Query("q") username: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun userGetDetail(
        @Path("username")username: String
    ):Call<UserGetDetail>

    @GET("users/{username}/followers")
    fun getFollower(
        @Path("username") username: String
    ): Call<ArrayList<ItemsUser>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<ItemsUser>>

}

