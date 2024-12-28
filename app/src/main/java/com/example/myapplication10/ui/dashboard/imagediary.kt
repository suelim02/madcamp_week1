package com.example.myapplication10.ui.dashboard

import android.net.Uri

data class ImageDiary(
    val imageUri: Uri,    // 이미지를 저장하는 URI
    val description: String // 이미지를 설명하는 텍스트
)
