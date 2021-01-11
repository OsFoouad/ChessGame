package com.usfoouad.chessbot

class ChessModel {
    var pieceBox = mutableSetOf<ChessPiece>()

    init {
        resetBoard()
    }


    fun movePiece(fromCol: Int, fromRow: Int, toCol: Int, toRow: Int) {
        if (fromCol == toCol && fromRow == toRow) return
        val movingPiece = pieceAt(fromCol, fromRow) ?: return

        pieceAt(toCol, toRow)?.let {
            if (it.player == movingPiece.player) {
                return
            }
            pieceBox.remove(it)
        } // end of let

        pieceBox.remove(movingPiece)
        pieceBox.add(ChessPiece(toCol, toRow, movingPiece.player, movingPiece.rank, movingPiece.resAddress))


    }

    fun resetBoard() {
        pieceBox.removeAll(pieceBox)

        // adding the Rook , Knight , BISHOP to the board
        for (i in 0..1) {
            pieceBox.add(ChessPiece(0 + i * 7, 0, ChessPlayer.WHITE, ChessPieceRank.ROOK, R.drawable.white_rook))
            pieceBox.add(ChessPiece(0 + i * 7, 7, ChessPlayer.BLACK, ChessPieceRank.ROOK, R.drawable.black_rook))

            pieceBox.add(ChessPiece(1 + i * 5, 0, ChessPlayer.WHITE, ChessPieceRank.KNIGHT, R.drawable.white_knight))
            pieceBox.add(ChessPiece(1 + i * 5, 7, ChessPlayer.BLACK, ChessPieceRank.KNIGHT, R.drawable.black_knight))

            pieceBox.add(ChessPiece(2 + i * 3, 0, ChessPlayer.WHITE, ChessPieceRank.BISHOP, R.drawable.white_bishop))
            pieceBox.add(ChessPiece(2 + i * 3, 7, ChessPlayer.BLACK, ChessPieceRank.BISHOP, R.drawable.black_bishop))

        } // end of for loop

        // adding the pawns to board
        for (i in 0..7) {
            pieceBox.add(ChessPiece(i, 1, ChessPlayer.WHITE, ChessPieceRank.PAWN, R.drawable.white_pawn))
            pieceBox.add(ChessPiece(i, 6, ChessPlayer.BLACK, ChessPieceRank.PAWN, R.drawable.black_pawn))
        } //end of for loop

        // adding the King and Queen  to the board
        pieceBox.add(ChessPiece(3, 0, ChessPlayer.WHITE, ChessPieceRank.QUEEN, R.drawable.white_queen))
        pieceBox.add(ChessPiece(3, 7, ChessPlayer.BLACK, ChessPieceRank.QUEEN, R.drawable.black_queen))
        pieceBox.add(ChessPiece(4, 0, ChessPlayer.WHITE, ChessPieceRank.KING, R.drawable.white_king))
        pieceBox.add(ChessPiece(4, 7, ChessPlayer.BLACK, ChessPieceRank.KING, R.drawable.black_king))

    }

    fun pieceAt(col: Int, row: Int): ChessPiece? {
        for (piece in pieceBox) {
            if (col == piece.col && row == piece.row)
                return piece
        }
        return null
    }

    override fun toString(): String {
        var item = " \n"
        for (row in 7 downTo 0) {
            item += "$row"
            for (col in 0..7) {
                val piece = pieceAt(col, row)
                if (piece == null) {
                    item += " ."
                } else {
                    val white = piece.player == ChessPlayer.WHITE
                    item += " "
                    when (piece.rank) {
                        ChessPieceRank.KING -> item += if (white) "k" else "K"
                        ChessPieceRank.QUEEN -> item += if (white) "q" else "Q"
                        ChessPieceRank.BISHOP -> item += if (white) "b" else "B"
                        ChessPieceRank.ROOK -> item += if (white) "r" else "R"
                        ChessPieceRank.KNIGHT -> item += if (white) "n" else "N"
                        ChessPieceRank.PAWN -> item += if (white) "p" else "P"
                    }
                }

            }
            item += "\n"
        }

        item += "  0 1 2 3 4 5 6 7"
        return item
    }


}