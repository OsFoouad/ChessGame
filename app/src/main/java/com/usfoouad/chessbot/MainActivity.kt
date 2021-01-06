package com.usfoouad.chessbot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

private const val ms = "MainActivity"

class MainActivity : AppCompatActivity() {


    var chessModel = ChessModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Log.d(ms, chessModel.toString())


    }
}