package com.example.umc_week07.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_week07.ui.screens.HomeUiState
import com.example.umc_week07.ui.screens.ProductUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    // 추후 대망의 미션을 위해 Domain 레이어의 UseCase를 여기에 주입하게 됩니다.
    // private val getHomeProductsUseCase: GetHomeProductsUseCase
) : ViewModel() {

    // Cold Flow를 Hot Flow(StateFlow)로 변환하여 라이프사이클에 맞춤
    val uiState: StateFlow<HomeUiState> = flow {
        try {
            // 1. 날짜 로직 처리 (UI에서 뷰모델로 이관)
            val currentTime = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("M월 d일 E요일", Locale.KOREAN).apply {
                timeZone = TimeZone.getTimeZone("Asia/Seoul")
            }
            val dateString = dateFormat.format(currentTime)

            // 2. 가상 데이터 로드 (추후 Repository 호출로 대체)
            val mockProducts = listOf(
                ProductUiModel("h1", "android.resource://com.example.umc_week07/drawable/shoe_jordan_36", "Air Jordan XXXVI", "US$185"),
                ProductUiModel("h2", "android.resource://com.example.umc_week07/drawable/shoe_jordan_01", "Air Jordan 1 Mid", "US$125"),
                ProductUiModel("h3", "android.resource://com.example.umc_week07/drawable/shoe_airforce_01", "Nike Air Force 1 '07", "US$115")
            )

            emit(HomeUiState.Success(formattedDate = dateString, products = mockProducts))
        } catch (e: Exception) {
            emit(HomeUiState.Error(e.localizedMessage ?: "알 수 없는 오류가 발생했습니다."))
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000), // 불필요한 리소스 낭비 방지
        initialValue = HomeUiState.Loading
    )
}