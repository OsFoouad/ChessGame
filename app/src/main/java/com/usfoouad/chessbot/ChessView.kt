package com.usfoouad.chessbot

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class ChessView(context: Context?, atts: AttributeSet?) : View(context, atts) {

    private val side = 130f // every quare side
    private val originX = 20f // away from left
    private val originY = 200 // away from fright
    private val paint = Paint() // Instance of the Painting class

    // pieces in the Drawable folder
    private val piecesImgIds = setOf(
            R.drawable.white_king,
            R.drawable.black_king,
            R.drawable.white_queen,
            R.drawable.black_queen,
            R.drawable.white_bishop,
            R.drawable.black_bishop,
            R.drawable.white_knight,
            R.drawable.black_knight,
            R.drawable.white_rook,
            R.drawable.black_rook,
            R.drawable.white_pawn,
            R.drawable.black_pawn,
    )
    private val bitmaps = mutableMapOf<Int, Bitmap>()

    // the view drawing by canvas
    override fun onDraw(canvas: Canvas?) {
        drawChessBoard(canvas) // for the board table
        drawPieces(canvas) //  for the pieces on the board

    }

    //chessBoard drawing fun
    private fun drawChessBoard(canvas: Canvas?) {
        for (row in 0..7) {
            // Draw the Chess Board
            for (col in 0..7) {
                drawSquareAt(canvas, col, row, (row + col) % 2 == 0)
            }  // end of nested for
        } // end of for
    }

    private fun drawSquareAt(canvas: Canvas?, col: Int, row: Int, light: Boolean) {
        paint.color = if (light) Color.LTGRAY else Color.DKGRAY
        canvas?.drawRect(originX + col * side, originY + row * side,
                originX + (col + 1) * side, originY + ((1 + row) * side), paint)

    }


    // piece draw fun
    private fun drawPieces(canvas: Canvas?) {

        val chessModel = ChessModel()
        chessModel.resetBoard()

        for (row in 0..7) {
            for (col in 0..7) {

                // the next line get every single piece of the ChessModel  class and draw it on the board
                chessModel.pieceAt(col, row)?.let { drawPieceAt(canvas, col, row, it.resAddress) }

            }// end of col for
        } //end of row for

        drawPieceAt(canvas, 0, 0, R.drawable.white_rook)

    }

    // draw the piece in specific square
    private fun drawPieceAt(canvas: Canvas?, col: Int, row: Int, resId: Int) {

        val bitmap = bitmaps[resId]!!
        canvas?.drawBitmap(bitmap, null, RectF(originX + col * side, originY + (7 - row) * side,
                originX + (1 + col) * side, originY + ((7 - row) + 1) * side), paint)

    }

    init {
        buildBitmaps()
    }

    //Bitmap factory
    private fun buildBitmaps() {
        piecesImgIds.forEach {
            bitmaps[it] = BitmapFactory.decodeResource(resources, it)
        }
    }

}