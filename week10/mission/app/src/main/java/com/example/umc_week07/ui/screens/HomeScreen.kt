package com.example.umc_week07.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage // Coil 이미지 로더 패키지 (프로젝트 환경에 맞게 매칭)
import com.example.umc_week07.R
import com.example.umc_week07.ui.viewmodel.HomeViewModel

// UI에 표현할 순수한 상품 데이터 모델 (서버 URL 대응 가능하도록 변경)
data class ProductUiModel(
    val id: String,
    val imageUrl: String, // 혹은 로컬 테스트용 String/Any
    val title: String,
    val price: String
)

// 단일 상태 관리용 Sealed Class
sealed interface HomeUiState {
    object Loading : HomeUiState
    data class Success(
        val formattedDate: String,
        val products: List<ProductUiModel>
    ) : HomeUiState
    data class Error(val message: String) : HomeUiState
}
@Composable
fun HomeProductItem(
    product: ProductUiModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.width(220.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFF6F6F6))
        ) {
            // Coil의 AsyncImage를 사용하여 URL 및 로컬 리소스 유연하게 처리
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.title,
                modifier = Modifier.fillMaxSize().padding(12.dp),
                contentScale = ContentScale.Fit,
                error = painterResource(id = R.drawable.shoe_jordan_01) // 에러 시 대체 이미지
            )
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
        Text(text = product.price, fontSize = 14.sp, color = Color.Gray)
    }
}

@Composable
fun HomeScreen(
    viewModel: HomeViewModel // Hilt에 의해 내부 혹은 Navigation에서 주입됨
) {
    // 라이프사이클을 고려하여 안전하게 StateFlow 수집 (CLAUDE.md 규칙 준수)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            is HomeUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is HomeUiState.Success -> {
                HomeContent(date = state.formattedDate, products = state.products)
            }
            is HomeUiState.Error -> {
                Text(
                    text = state.message,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
private fun HomeContent(
    date: String,
    products: List<ProductUiModel>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
    ) {
        Column(modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 32.dp)) {
            Text(text = "Discover", fontSize = 34.sp, fontWeight = FontWeight.ExtraBold, color = Color.Black)
            Text(text = date, fontSize = 16.sp, color = Color(0xFF888888), modifier = Modifier.padding(top = 4.dp))
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
            items(items = products, key = { it.id }) { product ->
                HomeProductItem(product = product)
            }
        }
    }
}