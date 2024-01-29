package chess;

import java.util.HashSet;

public class BishopMovesCalculator implements PieceMovesCalculator{
    ChessPosition myPosition;
    ChessGame.TeamColor myTeamColor;
    ChessBoard myBoard;
    int currentColumn;
    int currentRow;
    boolean rightDownBlock = false;
    boolean rightUpBlock = false;
    boolean leftDownBlock = false;
    boolean leftUpBlock = false;

    //used as a constructor
    public BishopMovesCalculator(ChessBoard board, ChessPosition position)
    {
        pieceMovesCalculator(board, position);
    }
    public void pieceMovesCalculator(ChessBoard board, ChessPosition position)
    {
        myPosition = position;
        myTeamColor = board.myChessBoard[position.getColumn()][position.getRow()].getTeamColor();
        myBoard = board;
        currentColumn = myPosition.getColumn();
        currentRow = myPosition.getRow();
        //clear out the hashset
        possibleMoves.clear();
    }

    //calculates the king's moves (does not account for moves that are illegal due to leaving the king in danger)
    public HashSet<ChessMove> bishopMoves() {
        for (int i = 1; i <=7; i++)
        {
            //if bishop is going up and to the right
            if((currentRow + i <= 8) && (currentColumn + i <= 8) && (!rightUpBlock))
            {
                if (myBoard.myChessBoard[currentColumn + i][currentRow + i] == null)
                {
                    ChessPosition endPosition = new ChessPosition(currentRow + i, currentColumn + i);
                    possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                }

                else
                {
                    if(myBoard.myChessBoard[currentColumn + i][currentRow + i].getTeamColor() != myTeamColor)
                    {
                        ChessPosition endPosition = new ChessPosition(currentRow + i, currentColumn + i);
                        //capture the enemy
                        myBoard.removePiece(endPosition);
                        //add the move
                        possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                    }
                    rightUpBlock = true;
                }
            }

            //if bishop is moving down and to the right
            if((currentRow - i >=1) && (currentColumn + i <=8) && (!rightDownBlock))
            {
                if(myBoard.myChessBoard[currentColumn + i][currentRow - i] == null) {

                    ChessPosition endPosition = new ChessPosition(currentRow - i, currentColumn + i);
                    possibleMoves.add(new ChessMove(myPosition, endPosition, null));

                }
                else
                {
                    if(myBoard.myChessBoard[currentColumn + i][currentRow - i].getTeamColor() != myTeamColor)
                    {

                        ChessPosition endPosition = new ChessPosition(currentRow - i, currentColumn + i);
                        //capture the enemy
                        myBoard.removePiece(endPosition);
                        //add the move
                        possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                    }
                    rightDownBlock = true;
                }
            }

            //if bishop is moving down and to the left
            if((currentRow - i >= 1) && (currentColumn - i >= 1) && (!leftDownBlock))
            {
                if(myBoard.myChessBoard[currentColumn - i][currentRow - i] == null) {

                    ChessPosition endPosition = new ChessPosition(currentRow - i, currentColumn - i);
                    possibleMoves.add(new ChessMove(myPosition, endPosition, null));                    }
                else
                {
                    if(myBoard.myChessBoard[currentColumn - i][currentRow - i].getTeamColor() != myTeamColor)
                    {
                        ChessPosition endPosition = new ChessPosition(currentRow - i, currentColumn - i);
                        //capture the enemy
                        myBoard.removePiece(endPosition);
                        //add the move
                        possibleMoves.add(new ChessMove(myPosition, endPosition, null));

                    }
                    leftDownBlock = true;
                }
            }

            //if bishop is moving up and to the left
            if((currentRow + i <= 8) && (currentColumn - i >= 1) && (!leftUpBlock))
            {
                if(myBoard.myChessBoard[currentColumn - i][currentRow + i] == null) {
                    ChessPosition endPosition = new ChessPosition(currentRow + i, currentColumn - i);
                    possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                }
                else
                {
                    if(myBoard.myChessBoard[currentColumn - i][currentRow + i].getTeamColor() != myTeamColor)
                    {
                        ChessPosition endPosition = new ChessPosition(currentRow + i, currentColumn - i);
                        //capture the enemy
                        myBoard.removePiece(endPosition);
                        //add the move
                        possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                    }
                    leftUpBlock = true;
                }
            }
        }
        return possibleMoves;
    }

}
