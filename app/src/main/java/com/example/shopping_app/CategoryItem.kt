package com.example.shopping_app

data class CategoryItem(
    val imageResId: Int,    // Resource ID for the category image (e.g., R.drawable.apple)
    val title: String,      // Display name (e.g., "Fruits")
    val categoryType: String // Type identifier (e.g., "fruits")
)