package com.example.foodtruck.data.model.credits

import com.example.foodtruck.presentation.details.adapter.DetailsBase

data class Crew(
    val adult: Boolean, // false
    val credit_id: String, // 57f96a50c3a3686fff000f1c
    val department: String, // Production
    val gender: Int, // 2
    val id: Int, // 7695
    val job: String, // Producer
    val known_for_department: String, // Production
    val name: String, // Jean-François Lepetit
    val original_name: String, // Jean-François Lepetit
    val popularity: Double, // 1.4
    val profile_path: String? // /ol5wKO7Tn26XgvjVgXjWrlm0LHj.jpg

): DetailsBase()