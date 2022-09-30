package com.example.sandisubmission2

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("items")
	val items: List<ItemsUser>
)

data class ItemsUser(

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("login")
	val login: String
)

data class UserGetDetail(
	@field:SerializedName("login")
	val nama: String,

	@field:SerializedName("name")
	val username : String,

	@field:SerializedName("following")
	val following : Int,

	@field:SerializedName("followers")
	val followers : Int,

	@field:SerializedName("public_repos")
	val Repository : Int,

	@field:SerializedName("avatar_url")
	val avatarUrl: String

)
