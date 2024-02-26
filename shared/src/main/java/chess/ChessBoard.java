package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    public ChessPiece[][] myChessBoard;
    ArrayList<ChessPosition> initialPositions;


    public ChessBoard() {

        //initializing a chessboard of 8x8 chess pieces
        //setting it to 9x9 so that I can have spaces 1-8
        myChessBoard = new ChessPiece[9][9];
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        //adds the piece to the chessboard array at the specified column and row
        myChessBoard[position.getColumn()][position.getRow()] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        //returns the piece or null
        if (myChessBoard[position.getColumn()][position.getRow()] == null)
            return null;
        else
            return myChessBoard[position.getColumn()][position.getRow()];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        //sets up all the pieces
        //setting up pawns
        for(int col = 1; col <= 8; col++)
        {
            ChessPosition whitePosition = new ChessPosition(2, col);
            ChessPiece newWhitePawn = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
            addPiece(whitePosition, newWhitePawn);
            ChessPosition blackPosition = new ChessPosition(7, col);
            ChessPiece newBlackPawn = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
            addPiece(blackPosition, newBlackPawn);
        }

        //setting up rooks
        for(int col = 1; col <= 8; col += 7)
        {
            ChessPosition whitePosition = new ChessPosition(1, col);
            ChessPiece newWhiteRook = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
            addPiece(whitePosition, newWhiteRook);
            ChessPosition blackPosition = new ChessPosition(8, col);
            ChessPiece newBlackRook = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
            addPiece(blackPosition, newBlackRook);
        }

        //setting up knights
        for(int col = 2; col <= 7; col += 5)
        {
            ChessPosition whitePosition = new ChessPosition(1, col);
            ChessPiece newWhiteKnight = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
            addPiece(whitePosition, newWhiteKnight);
            ChessPosition blackPosition = new ChessPosition(8, col);
            ChessPiece newBlackKnight = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
            addPiece(blackPosition, newBlackKnight);
        }

        //setting up bishops
        for(int col = 3; col <= 6; col += 3)
        {
            ChessPosition whitePosition = new ChessPosition(1, col);
            ChessPiece newWhiteBishop = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
            addPiece(whitePosition, newWhiteBishop);
            ChessPosition blackPosition = new ChessPosition(8, col);
            ChessPiece newBlackBishop = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
            addPiece(blackPosition, newBlackBishop);
        }

        //setting up queens
        ChessPosition whitePosition = new ChessPosition(1, 4);
        ChessPiece newWhiteQueen = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        addPiece(whitePosition, newWhiteQueen);
        ChessPosition blackPosition = new ChessPosition(8, 4);
        ChessPiece newBlackQueen = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        addPiece(blackPosition, newBlackQueen);

        //setting up kings
        ChessPosition whiteKingPosition = new ChessPosition(1, 5);
        ChessPiece newWhiteKing = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
        addPiece(whiteKingPosition, newWhiteKing);
        ChessPosition blackKingPosition = new ChessPosition(8, 5);
        ChessPiece newBlackKing = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
        addPiece(blackKingPosition, newBlackKing);
    }

    //making my own function to remove a piece
    public void removePiece(ChessPosition position)
    {
        myChessBoard[position.getColumn()][position.getRow()] = null;
    }

    @Override
    public String toString() {
        String returnString = "initialPositions =";

        for(ChessPiece[] column : myChessBoard)
        {
            for(ChessPiece piece: column)
            {
                if (piece != null)
                {
                    returnString += piece.getPieceType() + ", ";
                }
                else
                {
                    returnString += "null, ";
                }
            }
        }
        return returnString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Arrays.deepEquals(myChessBoard, that.myChessBoard) && Objects.equals(initialPositions, that.initialPositions);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(initialPositions);
        result = 31 * result + Arrays.hashCode(myChessBoard);
        return result;
    }
}
