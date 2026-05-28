package com.example.umc_week07.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WishlistScreen() {
    val likedItems = remember { derivedStateOf { globalShopProducts.filter { it.isLiked } } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = "위시리스트", fontSize = 34.sp, fontWeight = FontWeight.ExtraBold, color = Color.Black, modifier = Modifier.padding(start = 8.dp))
        Spacer(modifier = Modifier.height(20.dp))

        if (likedItems.value.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(items = likedItems.value, key = { it.id }) { product ->
                    ShopProductItem(
                        product = product,
                        onLikeClick = {
                            val index = globalShopProducts.indexOf(product)
                            if (index != -1) {
                                globalShopProducts[index] = product.copy(isLiked = !product.isLiked)
                            }
                        }
                    )
                }
            }
        }
    }
}