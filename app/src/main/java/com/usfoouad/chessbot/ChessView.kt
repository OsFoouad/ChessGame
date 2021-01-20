package com.usfoouad.chessbot

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlin.math.min

class ChessView(context: Context?, atts: AttributeSet?) : View(context, atts) {

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
    )  // pieces in the Drawable folder
    private var scalar = .9f // factor to edit the board size
    private var side = 130f // every quare side
    private var originX = 20f // away from left
    private var originY = 200f // away from fright
    private val paint = Paint() // Instance of the Painting class
    private val bitmaps = mutableMapOf<Int, Bitmap>() //image as bitmaps
    var chessRepresenter: ChessRepresenter? = null // instance of the representer interface
    private var fromCol = -1
    private var fromRow = -1
    private var movingPieceX = -1f
    private var movingPieceY = -1f
    private var movingPieceBitmap: Bitmap? = null
    private var movingPiece: ChessPiece? = null

    // the view drawing by canvas
    override fun onDraw(canvas: Canvas?) {

        canvas ?: return
        var boardSize = min(width, height) * scalar
        side = boardSize / 8f
        originX = (width - boardSize) / 2f
        originY = (height - boardSize) / 2f

        drawChessBoard(canvas) // for the board table
        drawPieces(canvas) //  for the pieces on the board


    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val tiny = min(widthMeasureSpec , heightMeasureSpec)
        setMeasuredDimension(tiny , tiny)

    }


    //chessBoard drawing fun
    private fun drawChessBoard(canvas: Canvas) {
        for (row in 0..7) {
            // Draw the Chess Board
            for (col in 0..7) {
                drawSquareAt(canvas, col, row, (row + col) % 2 == 0)
            }  // end of nested for
        } // end of for
    }

    // for each square in the board
    private fun drawSquareAt(canvas: Canvas, col: Int, row: Int, light: Boolean) {
        paint.color = if (light) Color.LTGRAY else Color.DKGRAY
        canvas.drawRect(originX + col * side, originY + row * side,
                originX + (col + 1) * side, originY + ((1 + row) * side), paint)

    }

    // piece draw fun
    private fun drawPieces(canvas: Canvas) {


        for (row in 0..7) {
            for (col in 0..7) {

//                if (row != fromRow || col != fromCol) {
//                    // the next line get every single piece of the ChessModel  class and draw it on the board
//                    chessRepresenter?.pieceAt(col, row)?.let { drawPieceAt(canvas, col, row, it.resAddress) }
//                }
                chessRepresenter?.pieceAt(col, row)?.let {
                    if (it != movingPiece) {
                        drawPieceAt(canvas, col, row, it.resAddress)
                    }
                }
            }// end of col for
        } //end of row for


        movingPieceBitmap?.let {

            canvas.drawBitmap(it, null, RectF(movingPieceX - side / 2, movingPieceY - side / 2,
                    movingPieceX + side / 2, movingPieceY + side / 2), paint)

        }

    }

    // draw the piece in specific square
    private fun drawPieceAt(canvas: Canvas, col: Int, row: Int, resId: Int) {

        val bitmap = bitmaps[resId]!!
        canvas.drawBitmap(bitmap, null, RectF(originX + col * side, originY + (7 - row) * side,
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

    // pieces finger move
    override fun onTouchEvent(event: MotionEvent?): Boolean {

        event ?: return false


        when (event.action) {

            MotionEvent.ACTION_DOWN -> {
                fromCol = ((event.x - originX) / side).toInt()
                fromRow = 7 - ((event.y - originY) / side).toInt()

                chessRepresenter?.pieceAt(fromCol, fromRow)?.let {
                    movingPiece = it
                    movingPieceBitmap = bitmaps[it.resAddress]
                }
            }

            MotionEvent.ACTION_MOVE -> {

                movingPieceX = event.x
                movingPieceY = event.y
                invalidate()
            }

            MotionEvent.ACTION_UP -> {

                val toCol = ((event.x - originX) / side).toInt()
                val toRow = 7 - ((event.y - originY) / side).toInt()
                Log.d(ms, " from ($fromRow , $fromCol) , to ( $toRow , $toCol )")
                chessRepresenter?.movePiece(fromCol, fromRow, toCol, toRow)
                movingPieceBitmap = null
                movingPiece = null

            }
        }

        return true
    }

}


