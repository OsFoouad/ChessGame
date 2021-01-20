package com.usfoouad.chessbot

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

const val ms = "MainActivity"

class MainActivity : AppCompatActivity(), ChessRepresenter {


    private var chessModel = ChessModel()
    private lateinit var chessView: ChessView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Log.d(ms, chessModel.toString())

        chessView = findViewById<ChessView>(R.id.chess_view)

        chessView.chessRepresenter = this


        // assign the reset button function
        val reset = findViewById<Button>(R.id.reset_btn)
        reset.setOnClickListener {
            chessModel.resetBoard()
            chessView.invalidate()
        }


    }

    override fun pieceAt(col: Int, row: Int): ChessPiece? {
        return chessModel.pieceAt(col, row)
    }

    override fun movePiece(fromCol: Int, fromRow: Int, toCol: Int, toRow: Int) {
        chessModel.movePiece(fromCol, fromRow, toCol, toRow)
        chessView.invalidate()

    }
}