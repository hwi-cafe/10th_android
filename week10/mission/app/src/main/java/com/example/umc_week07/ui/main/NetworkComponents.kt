package com.example.umc_week07.ui.main

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject

data class UserResponse(
    @field:SerializedName("data") val data: UserData
)

data class UserData(
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("email") val email: String,
    @field:SerializedName("first_name") val firstName: String,
    @field:SerializedName("last_name") val lastName: String,
    @field:SerializedName("avatar") val avatar: String
)

interface UserApiService {
    @GET("api/users/{userId}")
    suspend fun getUserProfile(@Path("userId") userId: Int): UserResponse
}

class UserRepository @Inject constructor(
    private val apiService: UserApiService
) {
    suspend fun getUser(userId: Int): UserData = apiService.getUserProfile(userId).data
}