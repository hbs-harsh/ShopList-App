package com.example.shopping_app

import FruitItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopping_app.databinding.ItemFruitBinding
import java.text.NumberFormat
import java.util.Locale

class FruitAdapter(
    private val fruits: List<FruitItem>,
    private val onItemClick: (FruitItem) -> Unit,
    private val onQuantityChanged: (FruitItem, Int) -> Unit
) : RecyclerView.Adapter<FruitAdapter.FruitViewHolder>() {

    // Currency formatter for consistent price display
    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale("en", "IN"))

    inner class FruitViewHolder(private val binding: ItemFruitBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(fruit: FruitItem) {
            binding.apply {
                ivFruit.setImageResource(fruit.imageResId)
                tvFruitName.text = fruit.name
                tvPrice.text = currencyFormat.format(fruit.price) // Format price with currency symbol

                if (fruit.quantity > 0) {
                    showQuantitySelector(fruit)
                } else {
                    showAddButton(fruit)
                }

                root.setOnClickListener { onItemClick(fruit) }
            }
        }

        private fun showAddButton(fruit: FruitItem) {
            with(binding) {
                btnAddToCart.visibility = View.VISIBLE
                quantitySelector.visibility = View.GONE

                btnAddToCart.setOnClickListener {
                    fruit.quantity = 1
                    onQuantityChanged(fruit, 1)
                    notifyItemChanged(adapterPosition)
                }
            }
        }

        private fun showQuantitySelector(fruit: FruitItem) {
            with(binding) {
                btnAddToCart.visibility = View.GONE
                quantitySelector.visibility = View.VISIBLE
                tvQuantity.text = fruit.quantity.toString()

                btnIncrease.setOnClickListener {
                    fruit.quantity++
                    tvQuantity.text = fruit.quantity.toString()
                    onQuantityChanged(fruit, fruit.quantity)
                }

                btnDecrease.setOnClickListener {
                    if (fruit.quantity > 1) {
                        fruit.quantity--
                        tvQuantity.text = fruit.quantity.toString()
                    } else {
                        fruit.quantity = 0
                        showAddButton(fruit)
                    }
                    onQuantityChanged(fruit, fruit.quantity)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitViewHolder {
        val binding = ItemFruitBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FruitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FruitViewHolder, position: Int) {
        holder.bind(fruits[position])
    }

    override fun getItemCount() = fruits.size

    fun getCartItems(): List<CartItem> {
        return fruits.filter { it.quantity > 0 }
            .map { CartItem(it, it.quantity, it.price * it.quantity) }
    }

    // Data class for cart items with total price calculation
    data class CartItem(
        val fruitItem: FruitItem,
        val quantity: Int,
        val totalPrice: Double
    )
}