package com.example.foodtruck.presentation.details.adapter
 abstract class DetailsBase {


     override fun hashCode(): Int {
         return javaClass.hashCode()
     }

     override fun equals(other: Any?): Boolean {
         if (this === other) return true
         if (javaClass != other?.javaClass) return false
         return true
     }
 }