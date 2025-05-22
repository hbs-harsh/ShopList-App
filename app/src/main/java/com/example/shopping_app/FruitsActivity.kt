package com.example.shopping_app

import FruitItem
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shopping_app.databinding.ActivityFruitsBinding

class FruitsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFruitsBinding
    private lateinit var adapter: FruitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFruitsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "All Fruits"

        val fruitsList = listOf(
            FruitItem(R.drawable.apple, "Apple", 99.0),
            FruitItem(R.drawable.banana, "Banana", 49.0),
            FruitItem(R.drawable.orange, "Orange", 79.0),
            FruitItem(R.drawable.mango, "Mango", 129.0),
            FruitItem(R.drawable.strawberry, "Strawberry", 199.0),
            FruitItem(R.drawable.grapes, "Grapes", 89.0)
        )

        adapter = FruitAdapter(
            fruits = fruitsList,
            onItemClick = { fruit ->
                // Optional: Handle item click if needed
            },
            onQuantityChanged = { fruit, quantity ->
                // Optional: Handle quantity change if needed
            }
        )

        binding.rvFruits.apply {
            layoutManager = GridLayoutManager(this@FruitsActivity, 2)
            adapter = this@FruitsActivity.adapter
            setHasFixedSize(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}