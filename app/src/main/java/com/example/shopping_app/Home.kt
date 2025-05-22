package com.example.shopping_app

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class Home : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize bottom navigation
        val bottomNav = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)
//        bottomNav.setOnNavigationItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.nav_home -> {
//                    // Already in home, no action needed
//                    true
//                }
//                R.id.nav_cart -> {
//                    startActivity(Intent(requireContext(), CartActivity::class.java))
//                    true
//                }
//
//                else -> false
//            }
//        }

        setupBestSellerRecyclerView(view)
        setupNewArrivalsRecyclerView(view)
    }

    private fun setupBestSellerRecyclerView(view: View) {
        val bestSellerRecyclerView = view.findViewById<RecyclerView>(R.id.rvbestseller)
        val bestSellerList = listOf(
            CategoryItem(R.drawable.apple, "Fruits", "fruits"),
            CategoryItem(R.drawable.carrot, "Vegetables", "vegetables"),
            CategoryItem(R.drawable.milk, "Dairy", "dairy"),
            CategoryItem(R.drawable.grains, "Grains", "grains")
        )

        bestSellerRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = CategoryAdapter(bestSellerList) { clickedItem ->
                openCategoryList(clickedItem)
            }
            setHasFixedSize(true)
            addItemDecoration(HorizontalSpacingItemDecoration(16))
        }
    }

    private fun setupNewArrivalsRecyclerView(view: View) {
        val newArrivalsRecyclerView = view.findViewById<RecyclerView>(R.id.rvNewArrivals)
        val newArrivalsList = listOf(
            CategoryItem(R.drawable.strawberry, "Strawberry", "fruits"),
            CategoryItem(R.drawable.coffee, "Tea, Coffee & Milk", "beverages"),
            CategoryItem(R.drawable.instant, "Instant Food", "snacks"),
            CategoryItem(R.drawable.choco, "Sweets & Chocolates", "snacks"),
            CategoryItem(R.drawable.drinks, "Drinks & Juices", "beverages"),
            CategoryItem(R.drawable.icecream, "Ice Creams & More", "desserts")
        )

        newArrivalsRecyclerView.apply {
            val spanCount = 3
            layoutManager = GridLayoutManager(requireContext(), spanCount)
            adapter = CategoryAdapter(newArrivalsList) { clickedItem ->
                openCategoryList(clickedItem)
            }
            setHasFixedSize(true)
            addItemDecoration(GridSpacingItemDecoration(spanCount, 8, true))
        }
    }

    private fun openCategoryList(item: CategoryItem) {
        val intent = when (item.categoryType.lowercase()) {
            "fruits" -> Intent(requireContext(), FruitsActivity::class.java)
            "vegetables" -> Intent(requireContext(), VegetablesActivity::class.java)
            else -> Intent(requireContext(), Vegis::class.java).apply {
                putExtra("CATEGORY_TYPE", item.categoryType)
            }
        }.apply {
            putExtra("CATEGORY_NAME", item.title)
            putExtra("CATEGORY_IMAGE", item.imageResId)

        }
        startActivity(intent)
    }

    class GridSpacingItemDecoration(
        private val spanCount: Int,
        private val spacing: Int,
        private val includeEdge: Boolean
    ) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view)
            val column = position % spanCount

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount
                outRect.right = (column + 1) * spacing / spanCount
                if (position < spanCount) outRect.top = spacing
                outRect.bottom = spacing
            } else {
                outRect.left = column * spacing / spanCount
                outRect.right = spacing - (column + 1) * spacing / spanCount
                if (position >= spanCount) outRect.top = spacing
            }
        }
    }

    class HorizontalSpacingItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.right = spacing
        }
    }
}