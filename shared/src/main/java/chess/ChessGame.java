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
        //making a new board here for the new game.
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

        HashSet<ChessMove> validMoves;
        HashSet<ChessMove> movesToRemove = new HashSet<>();
        HashSet<ChessMove> movesToReturn = new HashSet<>();
        //initializing this as a default pawn
        ChessPiece tempPieceToHold = new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        ChessPiece currentPiece;
        //copyBoard(myBoard);

        //if there's no piece at startPosition
        if (myBoard.getPiece(startPosition) == null)
        {
            return null;
        }


        currentPiece = myBoard.getPiece(startPosition);

        //first, add all possible moves
        validMoves = (HashSet<ChessMove>) currentPiece.pieceMoves(myBoard, startPosition);

        //remove moves that will leave the king in check
        for (ChessMove move : validMoves)
        {
            //move the piece on the board
            ChessPosition endPosition = move.getEndPosition();
            myBoard.myChessBoard[startPosition.getColumn()][startPosition.getRow()] = null;
            tempPieceToHold = myBoard.myChessBoard[endPosition.getColumn()][endPosition.getRow()];
            myBoard.myChessBoard[endPosition.getColumn()][endPosition.getRow()] = currentPiece;


            //check to see if the king is now in check
            if(isInCheck(currentPiece.getTeamColor()))
            {
                //if he's in check, remove that move from the set
                movesToRemove.add(new ChessMove(startPosition, endPosition, null));
            }

            //move the piece back to the og spot and run it again
            myBoard.myChessBoard[endPosition.getColumn()][endPosition.getRow()] = tempPieceToHold;
            myBoard.myChessBoard[startPosition.getColumn()][startPosition.getRow()] = currentPiece;

        }

        //in the king trapped test - it's saying that the king can capture the pawn, which is only true if it's actually
        //the king's turn. How do I check that it's the king's turn? Does that matter?
        for(ChessMove move : validMoves)
        {
            if (!movesToRemove.contains(move))
            {
                movesToReturn.add(move);
            }
        }
        return movesToReturn;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPiece pieceToMove = myBoard.myChessBoard[move.startPosition.getColumn()][move.startPosition.getRow()];
        TeamColor currentTeam;
        HashSet<ChessMove> validMoves = (HashSet<ChessMove>) validMoves(move.startPosition);

        //if it's not that team's turn
        if (pieceToMove.getTeamColor() != this.getTeamTurn())
        {
            throw new InvalidMoveException();
        }

        //if that move will leave the king in danger, or they can't actually move there
        if(validMoves.isEmpty())
        {
            throw new InvalidMoveException();
        }

        //make sure that the move is actually in the valid moves
        if(!validMoves.contains(move))
        {
            throw new InvalidMoveException();
        }

        else
        {
            //checking if it's ready to be promoted
            if(move.getPromotionPiece() != null)
            {
                currentTeam = pieceToMove.getTeamColor();
                pieceToMove = new ChessPiece(currentTeam, move.getPromotionPiece());
            }

            myBoard.myChessBoard[move.startPosition.getColumn()][move.startPosition.getRow()] = null;
            myBoard.myChessBoard[move.endPosition.getColumn()][move.endPosition.getRow()] = pieceToMove;

            //set the next turn
            if(pieceToMove.getTeamColor() == TeamColor.BLACK)
            {
                setTeamTurn(TeamColor.WHITE);
            }
            else
            {
                setTeamTurn(TeamColor.BLACK);
            }
        }

    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        //creates a copy of the board at tempBoard
        copyBoard(myBoard);
        ChessPosition kingPosition = new ChessPosition(0,0);
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
                    }
                }
            }
        }
        //check if any of the moves match the king's position
        for (ChessMove move : possibleMoves) {
            if (move.getEndPosition().equals(kingPosition))
            {
                isInCheck = true;
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
        boolean isInCheckmate = false;
        int numRows = 9;
        int numCols = 9;
        int numPiecesWithMoves = 0;

        copyBoard(myBoard);

        //first check to see if it's in check. If not, return false
        if(!isInCheck(teamColor))
        {
            return isInCheckmate;
        }

        //if none of the pieces have valid moves, then he's in checkmate
        //valid moves takes in a start position - maybe iterate through the temp board, give the start position of every one of the pieces of a certain color
        for(int i = 0; i < numCols; i++)
        {
            for(int j = 0; j < numRows; j++)
            {
                if (tempBoard[i][j] != null && tempBoard[i][j].getTeamColor() == teamColor)
                {
                    ChessPosition currentPosition = new ChessPosition(j,i);
                    //if the valid moves aren't empty for that piece
                    if(!validMoves(currentPosition).isEmpty())
                    {
                        numPiecesWithMoves++;
                    }
                }
            }
        }

        if(numPiecesWithMoves == 0)
        {
            isInCheckmate = true;
        }
        return isInCheckmate;
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
