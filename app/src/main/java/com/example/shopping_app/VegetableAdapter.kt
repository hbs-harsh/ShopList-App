package com.example.shopping_app

import VegetableItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopping_app.databinding.ItemVegetableBinding

class VegetableAdapter(
    private val vegetables: List<VegetableItem>,
    private val onItemClick: (VegetableItem) -> Unit,
    private val onQuantityChanged: (VegetableItem, Int) -> Unit
) : RecyclerView.Adapter<VegetableAdapter.VegetableViewHolder>() {

    inner class VegetableViewHolder(private val binding: ItemVegetableBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(vegetable: VegetableItem) {
            binding.ivVegetable.setImageResource(vegetable.imageResId)
            binding.tvVegetableName.text = vegetable.name

            if (vegetable.quantity > 0) {
                showQuantitySelector(vegetable)
            } else {
                showAddButton(vegetable)
            }

            binding.root.setOnClickListener {
                onItemClick(vegetable)
            }
        }

        private fun showAddButton(vegetable: VegetableItem) {
            with(binding) {
                btnAddToCart.visibility = View.VISIBLE
                quantitySelector.visibility = View.GONE

                btnAddToCart.setOnClickListener {
                    vegetable.quantity = 1
                    onQuantityChanged(vegetable, 1)
                    notifyItemChanged(adapterPosition)
                }
            }
        }

        private fun showQuantitySelector(vegetable: VegetableItem) {
            with(binding) {
                btnAddToCart.visibility = View.GONE
                quantitySelector.visibility = View.VISIBLE
                tvQuantity.text = vegetable.quantity.toString()

                btnIncrease.setOnClickListener {
                    vegetable.quantity++
                    tvQuantity.text = vegetable.quantity.toString()
                    onQuantityChanged(vegetable, vegetable.quantity)
                }

                btnDecrease.setOnClickListener {
                    if (vegetable.quantity > 1) {
                        vegetable.quantity--
                        tvQuantity.text = vegetable.quantity.toString()
                    } else {
                        vegetable.quantity = 0
                        showAddButton(vegetable)
                    }
                    onQuantityChanged(vegetable, vegetable.quantity)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VegetableViewHolder {
        val binding = ItemVegetableBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VegetableViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VegetableViewHolder, position: Int) {
        holder.bind(vegetables[position])
    }

    override fun getItemCount() = vegetables.size

    fun getCartItems(): List<Pair<VegetableItem, Int>> {
        return vegetables.filter { it.quantity > 0 }
            .map { it to it.quantity }
    }
    data class CartItem(
        val vegetableItem: VegetableItem,
        val quantity: Int,
        val totalPrice: Double
    )
}