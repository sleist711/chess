package chess;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {


    //declaring it here so that everything can see it
    ChessPiece[][] myChessBoard;
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
        //create a thing to throw an exception if it's trying to get one out of bounds

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
        throw new RuntimeException("Not implemented");
    }

    //making my own function to remove a piece
    public void removePiece(ChessPosition position)
    {
        myChessBoard[position.getColumn()][position.getRow()] = null;
    }
}
