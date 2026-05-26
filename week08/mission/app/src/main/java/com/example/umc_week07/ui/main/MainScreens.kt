package com.example.umc_week07.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import java.text.SimpleDateFormat
import java.util.*

data class HomeProduct(val id: String, val imageRes: Int, val title: String, val price: String)
data class Product(val id: String, val imageRes: Int, val title: String, val description: String, val price: String, val isLiked: Boolean = false)

@Composable
fun HomeProductItem(product: HomeProduct) {
    Column(modifier = Modifier.width(220.dp)) {
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
                modifier = Modifier.fillMaxSize().padding(12.dp),
                contentScale = ContentScale.Fit
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = product.title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(text = product.price, fontSize = 14.sp, color = Color.Gray)
    }
}

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
                        contentDescription = "좋아요",
                        tint = if (product.isLiked) Color.Red else Color.DarkGray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = product.title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = product.description,
            fontSize = 12.sp,
            color = Color.Gray,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            lineHeight = 16.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = product.price,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
    }
}
@Composable
fun HomeScreen() {
    val formattedDate = remember {
        val currentTime = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("M월 d일 E요일", Locale.KOREAN)
        dateFormat.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        dateFormat.format(currentTime)
    }

    val homeProducts = remember {
        listOf(
            HomeProduct("h1", R.drawable.shoe_jordan_36, "Air Jordan XXXVI", "US$185"),
            HomeProduct("h2", R.drawable.shoe_jordan_01, "Air Jordan 1 Mid", "US$125"),
            HomeProduct("h3", R.drawable.shoe_airforce_01, "Nike Air Force 1 '07", "US$115")
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
    ) {
        Column(modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 32.dp)) {
            Text(text = "Discover", fontSize = 34.sp, fontWeight = FontWeight.ExtraBold, color = Color.Black)
            Text(text = formattedDate, fontSize = 16.sp, color = Color(0xFF888888), modifier = Modifier.padding(top = 4.dp))
            Spacer(modifier = Modifier.height(28.dp))
            Image(
                painter = painterResource(id = R.drawable.img_basketball_couple),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().aspectRatio(0.85f).clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(32.dp))
            Text(text = "What's news", fontSize = 25.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Text(text = "나이키 최신 상품", fontSize = 32.sp, fontWeight = FontWeight.Normal, color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(items = homeProducts, key = { it.id }) { product ->
                HomeProductItem(product = product)
            }
        }
    }
}

var globalShopProducts = mutableStateListOf(
    Product("s1", R.drawable.socks_nike, "Nike Everyday Plus Cushioned", "Training Ankle Socks (6Pairs) \n 5 Colours", "US$10", false),
    Product("s2", R.drawable.socks_nike_elite, "Nike Elite Crew", "Basketball Socks \n 7 Colours", "US$16", false),
    Product("s3", R.drawable.shoe_nike_ankle, "Nike Air Force 1 '07", "Women' Shoes \n 5 Colours", "US$115", false),
    Product("s4", R.drawable.shoe_airforce_01, "Jordan ENike Air Force 1 '07ssentials", "Men's Shoes \n 2 Colours", "US$125", false)
)

@Composable
fun ShopScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Spacer(modifier = Modifier.height(20.dp))

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

@Composable
fun CartScreen(onOrderClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_bagcircle),
                contentDescription = null,
                modifier = Modifier.size(110.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "장바구니가 비어있습니다",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                text = "상품을 담아 장바구니를 채워보세요.",
                fontSize = 15.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Button(
            onClick = onOrderClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(20.dp)
                .height(56.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text(
                text = "주문하기",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(Color(0xFFEEEEEE)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(60.dp),
                tint = Color.Gray
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Paul", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Text(text = "paul@umc.com", fontSize = 14.sp, color = Color.Gray)
    }
}