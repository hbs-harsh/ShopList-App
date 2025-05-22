package com.example.shopping_app



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopping_app.databinding.ItemCartBinding

class CartAdapter(
    private val cartItems: MutableList<CartItem>,
    private val onRemoveClick: (CartItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartItem) {
            binding.ivCartImage.setImageResource(item.imageResId)
            binding.tvCartItemName.text = item.name
            binding.tvCartItemPrice.text = item.price

            binding.btnRemoveFromCart.setOnClickListener {
                onRemoveClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    override fun getItemCount(): Int = cartItems.size

    // Optional: Remove item from list
    fun removeItem(item: CartItem) {
        val index = cartItems.indexOf(item)
        if (index != -1) {
            cartItems.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    companion object {
        fun removeItem(itemToRemove: CartItem) {

        }
    }
}
