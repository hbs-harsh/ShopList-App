package com.example.shopping_app

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopping_app.CartAdapter
import com.example.shopping_app.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartAdapter  // FIX HERE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cartList = mutableListOf(
            CartItem(R.drawable.carrot, "Carrot", "$1.20"),
            CartItem(R.drawable.tomato, "Tomato", "$1.50")
        )

        cartAdapter = CartAdapter(cartList) { itemToRemove ->  // FIX HERE
            cartAdapter.removeItem(itemToRemove)
            Toast.makeText(this, "${itemToRemove.name} removed", Toast.LENGTH_SHORT).show()
        }

        binding.rvCart.apply {
            layoutManager = LinearLayoutManager(this@CartActivity)
            adapter = cartAdapter
        }
    }
}
