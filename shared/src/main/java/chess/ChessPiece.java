package chess;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    //not sure if these are right?
    //hopefully this is just setting the color and type for this specific object, not the entire class
    final ChessGame.TeamColor TEAM_COLOR;
    final ChessPiece.PieceType PIECE_TYPE;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type)
    {
        TEAM_COLOR = pieceColor;
        PIECE_TYPE = type;

    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return TEAM_COLOR;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return PIECE_TYPE;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {


        int currentRow = myPosition.getRow();
        int currentColumn = myPosition.getColumn();
        HashSet<ChessMove> possibleMoves = new HashSet<>();

        if (PIECE_TYPE == PieceType.ROOK)
        {
            //maybe create a function for each piece to evaluate how it can move and have it return a possible move

            //Going to need to find a way to change this to be 8 i think
            for(int i = 0; i <= 7; i++)
            {
                if (board.myChessBoard[i][currentRow] == null)
                {
                    //this part is going to get repetitive, can make it a function
                    ChessPosition endPosition = new ChessPosition(currentRow, i);
                    ChessMove newMove = new ChessMove(myPosition, endPosition, null);
                    possibleMoves.add(newMove);
                }

                if(board.myChessBoard[currentColumn][i] == null)
                {
                    ChessPosition endPosition = new ChessPosition(i, currentColumn);
                    ChessMove newMove = new ChessMove(myPosition, endPosition, null);
                    possibleMoves.add(newMove);
                }
            }

            //HERE STILL NEED TO IMPLEMENT CAPTURING ENEMY
        }
        else if (PIECE_TYPE == PieceType.KING)
        {

        }
        else if (PIECE_TYPE == PieceType.PAWN)
        {

        }
        else if (PIECE_TYPE == PieceType.KNIGHT)
        {

        }
        else if(PIECE_TYPE == PieceType.QUEEN)
        {

        }
        else if(PIECE_TYPE == PieceType.BISHOP)
        {
            boolean rightDownBlock = false;
            boolean rightUpBlock = false;
            boolean leftDownBlock = false;
            boolean leftUpBlock = false;

            //these numbers should be right??
            for (int i = 1; i <=7; i++)
            {
                //if bishop is going up and to the right
                if((currentRow + i <= 8) && (currentColumn + i <= 8) && (rightUpBlock == false))
                {
                    if (board.myChessBoard[currentColumn + i][currentRow + i] == null)
                    {
                        ChessPosition endPosition = new ChessPosition(currentRow + i, currentColumn + i);
                        possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                    }

                    else
                    {
                        if(board.myChessBoard[currentColumn + i][currentRow + i].getTeamColor() != TEAM_COLOR)
                        {
                            ChessPosition endPosition = new ChessPosition(currentRow + i, currentColumn + i);
                            //capture the enemy
                            board.removePiece(endPosition);
                            //add the move
                            possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                        }
                        rightUpBlock = true;
                    }
                }

                //if bishop is moving down and to the right
                if((currentRow - i >=1) && (currentColumn + i <=8) && (rightDownBlock == false))
                {
                    if(board.myChessBoard[currentColumn + i][currentRow - i] == null) {

                        ChessPosition endPosition = new ChessPosition(currentRow - i, currentColumn + i);
                        possibleMoves.add(new ChessMove(myPosition, endPosition, null));

                    }
                    else
                    {
                        if(board.myChessBoard[currentColumn + i][currentRow - i].getTeamColor() != TEAM_COLOR)
                        {

                            ChessPosition endPosition = new ChessPosition(currentRow - i, currentColumn + i);
                            //capture the enemy
                            board.removePiece(endPosition);
                            //add the move
                            possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                        }
                        rightDownBlock = true;
                    }
                }

                //if bishop is moving down and to the left
                if((currentRow - i >= 1) && (currentColumn - i >= 1) && (leftDownBlock == false))
                {
                    if(board.myChessBoard[currentColumn - i][currentRow - i] == null) {

                        ChessPosition endPosition = new ChessPosition(currentRow - i, currentColumn - i);
                        possibleMoves.add(new ChessMove(myPosition, endPosition, null));                    }
                    else
                    {
                        if(board.myChessBoard[currentColumn - i][currentRow - i].getTeamColor() != TEAM_COLOR)
                        {
                            ChessPosition endPosition = new ChessPosition(currentRow - i, currentColumn - i);
                            //capture the enemy
                            board.removePiece(endPosition);
                            //add the move
                            possibleMoves.add(new ChessMove(myPosition, endPosition, null));

                        }
                        leftDownBlock = true;
                    }
                }

                //if bishop is moving up and to the left
                if((currentRow + i <= 8) && (currentColumn - i >= 1) && (leftUpBlock == false))
                {
                    if(board.myChessBoard[currentColumn - i][currentRow + i] == null) {
                        ChessPosition endPosition = new ChessPosition(currentRow + i, currentColumn - i);
                        possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                    }
                    else
                    {
                        if(board.myChessBoard[currentColumn - i][currentRow + i].getTeamColor() != TEAM_COLOR)
                        {
                            ChessPosition endPosition = new ChessPosition(currentRow + i, currentColumn - i);
                            //capture the enemy
                            board.removePiece(endPosition);
                            //add the move
                            possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                        }
                        leftUpBlock = true;
                    }
                }
            }
        }


        //going to return a hashset (?)
        return possibleMoves;
    }

}
