package com.usfoouad.chessbot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

const val ms = "MainActivity"

class MainActivity : AppCompatActivity(), ChessRepresenter {


    var chessModel = ChessModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Log.d(ms, chessModel.toString())

        findViewById<ChessView>(R.id.chess_view).chessRepresenter = this
    }

    override fun pieceAt(col: Int, row: Int): ChessPiece? {
        return chessModel.pieceAt(col, row)
    }

    override fun movePiece(fromCol: Int, fromRow: Int, toCol: Int, toRow: Int) {
        chessModel.movePiece(fromCol, fromRow, toCol, toRow)
        findViewById<ChessView>(R.id.chess_view).invalidate()

    }
}