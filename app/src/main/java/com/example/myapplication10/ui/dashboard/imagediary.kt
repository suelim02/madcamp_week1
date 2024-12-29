package com.example.myapplication10.ui.dashboard

import android.net.Uri

data class ImageDiary(
    val imageUri: Uri,    // 이미지를 저장
    var description: String, // 이미지를 설명-다이어리제목
    var date: String // 날짜
)
