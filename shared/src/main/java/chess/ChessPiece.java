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


        //get rid of these ints
        int currentRow = myPosition.getRow();
        int currentColumn = myPosition.getColumn();
        HashSet<ChessMove> possibleMoves = new HashSet<>();

        if (PIECE_TYPE == PieceType.ROOK)
        {
            possibleMoves = rookMoves(board, myPosition, myPosition.getRow(), myPosition.getColumn());
        }
        else if (PIECE_TYPE == PieceType.KING)
        {

        }
        else if (PIECE_TYPE == PieceType.PAWN)
        {

        }
        else if (PIECE_TYPE == PieceType.KNIGHT)
        {
            possibleMoves = knightMoves(board, myPosition, myPosition.getRow(), myPosition.getColumn());

        }
        else if(PIECE_TYPE == PieceType.QUEEN)
        {

        }
        else if(PIECE_TYPE == PieceType.BISHOP)
        {
            possibleMoves = bishopMoves(board, myPosition, myPosition.getRow(), myPosition.getColumn());

        }


        //going to return a hashset (?)
        return possibleMoves;
    }

    public HashSet<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition, int currentRow, int currentColumn)
    {
        int twoSquares = 2;
        int oneSquare = 1;
        HashSet<ChessMove> possibleMoves = new HashSet<>();

        //right two, up one
        if((currentColumn + twoSquares <= 8) && (currentRow + oneSquare <= 8))
        {
            if(board.myChessBoard[currentColumn + twoSquares][currentRow + oneSquare] == null)
            {
                ChessPosition endPosition = new ChessPosition(currentRow + oneSquare, currentColumn + twoSquares);
                possibleMoves.add(new ChessMove(myPosition, endPosition, null));
            }

            else
            {
                if(board.myChessBoard[currentColumn + twoSquares][currentRow + oneSquare].getTeamColor() != TEAM_COLOR)
                {
                    ChessPosition endPosition = new ChessPosition(currentRow + oneSquare, currentColumn + twoSquares);
                    //capture the enemy
                    board.removePiece(endPosition);
                    //add the move
                    possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                }
            }
        }

        //left two, up one
        if((currentColumn - twoSquares >= 1) && (currentRow + oneSquare <= 8))
        {
            if(board.myChessBoard[currentColumn - twoSquares][currentRow + oneSquare] == null)
            {
                ChessPosition endPosition = new ChessPosition(currentRow + oneSquare, currentColumn - twoSquares);
                possibleMoves.add(new ChessMove(myPosition, endPosition, null));
            }
            else
            {
                if(board.myChessBoard[currentColumn - twoSquares][currentRow + oneSquare].getTeamColor() != TEAM_COLOR)
                {
                    ChessPosition endPosition = new ChessPosition(currentRow + oneSquare, currentColumn - twoSquares);
                    //capture the enemy
                    board.removePiece(endPosition);
                    //add the move
                    possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                }
            }
        }

        //right two, down one
        if((currentColumn + twoSquares <= 8) && (currentRow - oneSquare >= 1))
        {
            if(board.myChessBoard[currentColumn + twoSquares][currentRow - oneSquare] == null)
            {
                ChessPosition endPosition = new ChessPosition(currentRow - oneSquare, currentColumn + twoSquares);
                possibleMoves.add(new ChessMove(myPosition, endPosition, null));
            }

            else
            {
                if(board.myChessBoard[currentColumn + twoSquares][currentRow - oneSquare].getTeamColor() != TEAM_COLOR)
                {
                    ChessPosition endPosition = new ChessPosition(currentRow - oneSquare, currentColumn + twoSquares);
                    //capture the enemy
                    board.removePiece(endPosition);
                    //add the move
                    possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                }
            }
        }

        //left two, down one
        if((currentColumn - twoSquares >= 1) && (currentRow - oneSquare >= 1))
        {
            if(board.myChessBoard[currentColumn - twoSquares][currentRow - oneSquare] == null)
            {
                ChessPosition endPosition = new ChessPosition(currentRow - oneSquare, currentColumn - twoSquares);
                possibleMoves.add(new ChessMove(myPosition, endPosition, null));
            }

            else
            {
                if(board.myChessBoard[currentColumn - twoSquares][currentRow - oneSquare].getTeamColor() != TEAM_COLOR)
                {
                    ChessPosition endPosition = new ChessPosition(currentRow - oneSquare, currentColumn - twoSquares);
                    //capture the enemy
                    board.removePiece(endPosition);
                    //add the move
                    possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                }
            }
        }

        //right one, up two
        if((currentColumn + oneSquare <= 8) && (currentRow + twoSquares <= 8))
        {
            if(board.myChessBoard[currentColumn + oneSquare][currentRow + twoSquares] == null)
            {
                ChessPosition endPosition = new ChessPosition(currentRow + twoSquares, currentColumn + oneSquare);
                possibleMoves.add(new ChessMove(myPosition, endPosition, null));
            }

            else
            {
                if(board.myChessBoard[currentColumn + oneSquare][currentRow + twoSquares].getTeamColor() != TEAM_COLOR)
                {
                    ChessPosition endPosition = new ChessPosition(currentRow + twoSquares, currentColumn + oneSquare);
                    //capture the enemy
                    board.removePiece(endPosition);
                    //add the move
                    possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                }
            }
        }

        //left one, up two
        if((currentColumn - oneSquare >= 1) && (currentRow + twoSquares <= 8))
        {
            if(board.myChessBoard[currentColumn - oneSquare][currentRow + twoSquares] == null)
            {
                ChessPosition endPosition = new ChessPosition(currentRow + twoSquares, currentColumn - oneSquare);
                possibleMoves.add(new ChessMove(myPosition, endPosition, null));
            }
            else
            {
                if(board.myChessBoard[currentColumn - oneSquare][currentRow + twoSquares].getTeamColor() != TEAM_COLOR)
                {
                    ChessPosition endPosition = new ChessPosition(currentRow + twoSquares, currentColumn - oneSquare);
                    //capture the enemy
                    board.removePiece(endPosition);
                    //add the move
                    possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                }
            }
        }

        //right one, down two
        if((currentColumn + oneSquare <= 8) && (currentRow - twoSquares >= 1))
        {
            if(board.myChessBoard[currentColumn + oneSquare][currentRow - twoSquares] == null)
            {
                ChessPosition endPosition = new ChessPosition(currentRow - twoSquares, currentColumn + oneSquare);
                possibleMoves.add(new ChessMove(myPosition, endPosition, null));
            }

            else
            {
                if(board.myChessBoard[currentColumn + oneSquare][currentRow - twoSquares].getTeamColor() != TEAM_COLOR)
                {
                    ChessPosition endPosition = new ChessPosition(currentRow - twoSquares, currentColumn + oneSquare);
                    //capture the enemy
                    board.removePiece(endPosition);
                    //add the move
                    possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                }
            }
        }

        //left one, down two
        if((currentColumn - oneSquare >= 1) && (currentRow - twoSquares >= 1))
        {
            if(board.myChessBoard[currentColumn - oneSquare][currentRow - twoSquares] == null)
            {
                ChessPosition endPosition = new ChessPosition(currentRow - twoSquares, currentColumn - oneSquare);
                possibleMoves.add(new ChessMove(myPosition, endPosition, null));
            }

            else
            {
                if(board.myChessBoard[currentColumn - oneSquare][currentRow - twoSquares].getTeamColor() != TEAM_COLOR)
                {
                    ChessPosition endPosition = new ChessPosition(currentRow - twoSquares, currentColumn - oneSquare);
                    //capture the enemy
                    board.removePiece(endPosition);
                    //add the move
                    possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                }
            }
        }

        return possibleMoves;
    }


    public HashSet<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition, int currentRow, int currentColumn)
    {
        HashSet<ChessMove> possibleMoves = new HashSet<>();
        boolean rightDownBlock = false;
        boolean rightUpBlock = false;
        boolean leftDownBlock = false;
        boolean leftUpBlock = false;

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
        return possibleMoves;
    }

    public HashSet<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition, int currentRow, int currentColumn)
    {
        HashSet<ChessMove> possibleMoves = new HashSet<>();
        boolean rightBlock = false;
        boolean leftBlock = false;
        boolean upBlock = false;
        boolean downBlock = false;

        for(int i = 1; i <=7; i++)
        {
            //if rook is going right
            if((currentColumn + i <= 8) && (rightBlock == false))
            {
                if (board.myChessBoard[currentColumn + i][currentRow] == null)
                {
                    ChessPosition endPosition = new ChessPosition(currentRow, currentColumn + i);
                    possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                }
                else
                {
                    if(board.myChessBoard[currentColumn + i][currentRow].getTeamColor() != TEAM_COLOR)
                    {
                        ChessPosition endPosition = new ChessPosition(currentRow, currentColumn + i);
                        //capture the enemy
                        board.removePiece(endPosition);
                        //add the move
                        possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                    }
                    rightBlock = true;
                }
            }

            //if rook is moving down
            if((currentRow - i >=1) && (downBlock == false))
            {
                if(board.myChessBoard[currentColumn][currentRow - i] == null) {

                    ChessPosition endPosition = new ChessPosition(currentRow - i, currentColumn);
                    possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                }
                else
                {
                    if(board.myChessBoard[currentColumn][currentRow - i].getTeamColor() != TEAM_COLOR)
                    {
                        ChessPosition endPosition = new ChessPosition(currentRow - i, currentColumn);
                        //capture the enemy
                        board.removePiece(endPosition);
                        //add the move
                        possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                    }
                    downBlock = true;
                }
            }

            //if rook goes left
            if((currentColumn - i >= 1) && (leftBlock == false))
            {
                if(board.myChessBoard[currentColumn - i][currentRow] == null) {

                    ChessPosition endPosition = new ChessPosition(currentRow, currentColumn - i);
                    possibleMoves.add(new ChessMove(myPosition, endPosition, null));                    }
                else
                {
                    if(board.myChessBoard[currentColumn - i][currentRow].getTeamColor() != TEAM_COLOR)
                    {
                        ChessPosition endPosition = new ChessPosition(currentRow, currentColumn - i);
                        //capture the enemy
                        board.removePiece(endPosition);
                        //add the move
                        possibleMoves.add(new ChessMove(myPosition, endPosition, null));

                    }
                    leftBlock = true;
                }
            }

            //if rook is moving up
            if((currentRow + i <= 8) && (upBlock == false))
            {
                if(board.myChessBoard[currentColumn][currentRow + i] == null) {
                    ChessPosition endPosition = new ChessPosition(currentRow + i, currentColumn);
                    possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                }
                else
                {
                    if(board.myChessBoard[currentColumn][currentRow + i].getTeamColor() != TEAM_COLOR)
                    {
                        ChessPosition endPosition = new ChessPosition(currentRow + i, currentColumn);
                        //capture the enemy
                        board.removePiece(endPosition);
                        //add the move
                        possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                    }
                    upBlock = true;
                }
            }
        }
        return possibleMoves;

    }

}
