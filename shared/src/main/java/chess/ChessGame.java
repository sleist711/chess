package chess;

import java.util.Collection;
import java.util.HashSet;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    TeamColor currentTurn;
    ChessBoard myBoard;
    ChessPiece[][] tempBoard;
    public ChessGame() {
        //making a new board here for the new game. Not sure if that's right
        myBoard = new ChessBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return currentTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        currentTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        //if there's no piece at startPosition
        if (myBoard.getPiece(startPosition) == null)
        {
            return null;
        }
        //calls isInCheck
        throw new RuntimeException("Not implemented");

    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {

        //current problem - making a copy of the board isn't working


        //creates a copy of the board at tempBoard
        copyBoard(myBoard);
        ChessPosition kingPosition = new ChessPosition(0,0);
        //if any of the moves of the opposite team can get my king but the king can get out of check, return true
        //otherwise, call isincheckmate??
        boolean isInCheck = false;
        HashSet<ChessMove> possibleMoves = new HashSet<>();
        int numRows = 9;
        int numCols = 9;

        //iterating through the whole tempBoard
        //iterating through the rows
        for(int i = 0; i < numRows; i++)
        {
            //iterating through the columns
            for (int j = 0; j < numCols; j++)
            {
                if (tempBoard[j][i] != null)
                {
                    //if this is my king, set the kingPosition
                    if(tempBoard[j][i].getTeamColor() == teamColor && tempBoard[j][i].getPieceType() == ChessPiece.PieceType.KING)
                    {
                        kingPosition = new ChessPosition(i, j);
                    }
                    //if the color of this piece doesn't match my team color
                    if(tempBoard[j][i].getTeamColor() != teamColor)
                    {
                        //to the possible moves set, add the piecemoves from the piece at this spot on the board. Running it off of the og chessboard
                        possibleMoves.addAll(tempBoard[j][i].pieceMoves(myBoard, new ChessPosition(i,j)));

                        //check if the set of chessmoves contains the same end position as the king's position

                    }
                }

            }

        }
        for (ChessMove move : possibleMoves) {
            if (move.getEndPosition().equals(kingPosition))
            {
                isInCheck = true;
                //can terminate the loop i think
            }
        }

        return isInCheck;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     *
     */
    //gets team color
    //gets king position
    //may need to use the valid move function - pieces can only move their valid moves?
    //goes through the board and checks to see if any of the pieces with the opposite team color can move to the position of the king
    //
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        myBoard = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return myBoard;
    }

    //Helper function to deep copy a board
    private void copyBoard(ChessBoard board) {
        tempBoard = new ChessPiece[9][9];
        for (int col = 0; col <= 8; col++)
        {
            for (int row = 0; row <= 8; row++)
            {
                tempBoard[col][row] = board.myChessBoard[col][row];
            }
        }
    }
}
