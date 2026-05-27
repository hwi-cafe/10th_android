package com.example.umc_week07.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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