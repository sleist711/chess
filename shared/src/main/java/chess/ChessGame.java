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
    public ChessBoard myBoard;
    ChessPiece[][] tempBoard;
    public ChessGame() {
        myBoard = new ChessBoard();

        //experimenting here with making the current turn white to start
        currentTurn = TeamColor.WHITE;
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
            tempPieceToHold = myBoard.myChessBoard[endPosition.getColumn()][endPosition.getRow()];
            myBoard.myChessBoard[startPosition.getColumn()][startPosition.getRow()] = null;
            myBoard.myChessBoard[endPosition.getColumn()][endPosition.getRow()] = currentPiece;

            //check to see if the king is now in check
            if(isInCheck(currentPiece.getTeamColor()))
            {
                //if he's in check remove that move from the set
                movesToRemove.add(move);
            }

            //move the piece back to the og spot and run it again
            myBoard.myChessBoard[endPosition.getColumn()][endPosition.getRow()] = tempPieceToHold;
            myBoard.myChessBoard[startPosition.getColumn()][startPosition.getRow()] = currentPiece;

        }

        //move all of the invalid moves from the validMoves set
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
        tempBoard = copyBoard(myBoard);
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
                return isInCheck;
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
    public boolean isInCheckmate(TeamColor teamColor) {
        boolean isInCheckmate = false;
        int numRows = 9;
        int numCols = 9;
        int numPiecesWithMoves = 0;

        ChessPiece[][] checkmateTempBoard = copyBoard(myBoard);

        //first check to see if it's in check. If not, return false
        if(!isInCheck(teamColor))
        {
            return isInCheckmate;
        }

        //if none of the pieces have valid moves, then he's in checkmate
        for(int i = 0; i < numCols; i++)
        {
            for(int j = 0; j < numRows; j++)
            {
                if (checkmateTempBoard[i][j] != null && checkmateTempBoard[i][j].getTeamColor() == teamColor)
                {
                    ChessPosition currentPosition = new ChessPosition(j,i);

                    //if the valid moves aren't empty for that piece
                    if(validMoves(currentPosition) == null)
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
        boolean isInStalemate = false;
        int numWithoutMoves = 0;
        int numPieces = 0;
        ChessPiece[][] stalemateTempBoard = copyBoard(myBoard);
        int numRows = 9;
        int numCols = 9;
        if(!isInCheck(teamColor))
        {
            for(int i = 1; i < numCols; i++)
            {
                for(int j = 1; j < numRows; j++)
                {
                    if(stalemateTempBoard[i][j] != null && stalemateTempBoard[i][j].getTeamColor() == teamColor)
                    {
                        numPieces++;

                        if(validMoves(new ChessPosition(j,i)).isEmpty())
                        {
                            numWithoutMoves++;
                        }
                    }
                }
            }
        }
        if(numPieces == numWithoutMoves)
        {
            isInStalemate = true;
        }
        return isInStalemate;
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
    private ChessPiece[][] copyBoard(ChessBoard board) {
        tempBoard = new ChessPiece[9][9];
        for (int col = 0; col <= 8; col++)
        {
            for (int row = 0; row <= 8; row++)
            {
                tempBoard[col][row] = board.getPiece(new ChessPosition(row, col));
            }
        }
        return tempBoard;
    }
}
