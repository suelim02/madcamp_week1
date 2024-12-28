package com.example.week1

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class pushListActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_push_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dbHelper = allListDB(this)
        dbHelper.insertTeam("KIA", "광주 - 광주기아챔피언스필드")
        dbHelper.insertTeam("삼성", "대구 - 대구삼성라이온즈파크")
        dbHelper.insertTeam("LG", "서울 - 잠실종합운동장")
        dbHelper.insertTeam("KT", "수원 - 수원KT위즈파크")
        dbHelper.insertTeam("SSG", "인천 - 인천SSG랜더스필드")
        dbHelper.insertTeam("두산", "서울 - 잠실종합운동장")
        dbHelper.insertTeam("한화", "대전 - 대전한화생명이글스파크")
        dbHelper.insertTeam("롯데", "부산 - 사직야구장")
        dbHelper.insertTeam("NC", "창원 - 창원NC파크")
        dbHelper.insertTeam("넥센", "서울 - 고척스카이돔")

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview_alllist)
        val teamList = dbHelper.getAllTeams()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = Myadapter_addpage(teamList)  { selectedData ->
            val resultIntent = Intent().apply {
                putExtra("selected_data", arrayListOf(selectedData))
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(divider)
    }
}