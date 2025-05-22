package com.example.shopping_app

import VegetableItem
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shopping_app.databinding.ActivityVegetablesBinding

class VegetablesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVegetablesBinding
    private lateinit var adapter: VegetableAdapter
    private val cartItems = mutableMapOf<String, Pair<VegetableItem, Int>>() // Stores items with quantities

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVegetablesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "All Vegetables"

        val vegetablesList = listOf(
            VegetableItem(R.drawable.carrot, "Carrot",30.00),
            VegetableItem(R.drawable.tomato, "Tomato",60.00),
            VegetableItem(R.drawable.broccoli, "Broccoli",100.00),
            VegetableItem(R.drawable.potato, "Potato",20.00),
            VegetableItem(R.drawable.onion, "Onion",30.00),
            VegetableItem(R.drawable.cucumber, "Cucumber",50.00)
        )

        adapter = VegetableAdapter(
            vegetables = vegetablesList,
            onItemClick = { vegetable ->
                // Optional: Handle item click if needed
            },
            onQuantityChanged = { vegetable, quantity ->
                updateCart(vegetable, quantity)
                updateCartBadge()
            }
        )

        binding.rvVegetables.apply {
            layoutManager = GridLayoutManager(this@VegetablesActivity, 2)
            adapter = this@VegetablesActivity.adapter
            setHasFixedSize(true)
        }
    }

    private fun updateCart(vegetable: VegetableItem, quantity: Int) {
        if (quantity > 0) {
            cartItems[vegetable.name] = Pair(vegetable, quantity)
        } else {
            cartItems.remove(vegetable.name)
        }
    }

    private fun updateCartBadge() {
        val totalItems = cartItems.values.sumOf { it.second }
        // Update your cart badge here
        // For example, if you have a menu item with badge:
        invalidateOptionsMenu()

        // Or if you have a floating action button with badge:
        // binding.fabCart.setBadgeNumber(totalItems)
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu): Boolean {
        menuInflater.inflate(R.menu.menu_vegetables, menu)

        val cartItem = menu.findItem(R.id.action_cart)
        val badge = cartItem?.actionView?.findViewById<TextView>(R.id.cart_badge)

        val totalItems = cartItems.values.sumOf { it.second }
        badge?.text = if (totalItems > 0) totalItems.toString() else null
        badge?.visibility = if (totalItems > 0) View.VISIBLE else View.GONE

        return true
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cart -> {
                openCartActivity()
                true
            }
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openCartActivity() {
        // Convert cartItems to list and pass to CartActivity
        val cartList = cartItems.values.map { it.first to it.second }

        // Start CartActivity with the cart items
        // Intent(this, CartActivity::class.java).apply {
        //     putExtra("CART_ITEMS", ArrayList(cartList))
        //     startActivity(this)
        // }

        // For now, just show a toast
        Toast.makeText(this, "${cartList.size} items in cart", Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}