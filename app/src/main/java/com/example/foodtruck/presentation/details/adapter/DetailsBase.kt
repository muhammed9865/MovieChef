package com.example.foodtruck.presentation.details.adapter
 abstract class DetailsBase {
     override fun equals(other: Any?): Boolean {
         return super.equals(other)
     }

     override fun hashCode(): Int {
         return javaClass.hashCode()
     }
 }