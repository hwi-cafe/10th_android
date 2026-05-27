package com.example.umc_week07.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.umc_week07.R

data class Product(val id: String, val imageRes: Int, val title: String, val description: String, val price: String, val isLiked: Boolean = false)

var globalShopProducts = mutableStateListOf(
    Product("s1", R.drawable.socks_nike, "Nike Everyday Plus Cushioned", "Training Ankle Socks (6Pairs) \n 5 Colours", "US$10", false),
    Product("s2", R.drawable.socks_nike_elite, "Nike Elite Crew", "Basketball Socks \n 7 Colours", "US$16", false),
    Product("s3", R.drawable.shoe_nike_ankle, "Nike Air Force 1 '07", "Women' Shoes \n 5 Colours", "US$115", false),
    Product("s4", R.drawable.shoe_airforce_01, "Jordan ENike Air Force 1 '07ssentials", "Men's Shoes \n 2 Colours", "US$125", false)
)

@Composable
fun ShopProductItem(
    product: Product,
    onLikeClick: () -> Unit,
    showHeart: Boolean = true
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFF6F6F6))
        ) {
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.title,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentScale = ContentScale.Fit
            )
            if (showHeart) {
                IconButton(
                    onClick = onLikeClick,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                ) {
                    Icon(
                        imageVector = if (product.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = if (product.isLiked) Color.Red else Color.DarkGray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = product.title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = product.description, fontSize = 12.sp, color = Color.Gray, maxLines = 2, overflow = TextOverflow.Ellipsis, lineHeight = 16.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = product.price, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
    }
}

@Composable
fun ShopScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(52.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(items = globalShopProducts, key = { it.id }) { product ->
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