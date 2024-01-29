package chess;
import java.util.HashSet;
public class RookMovesCalculator implements PieceMovesCalculator{
    ChessPosition myPosition;
    ChessGame.TeamColor myTeamColor;
    ChessBoard myBoard;
    int currentColumn;
    int currentRow;
    boolean rightBlock = false;
    boolean leftBlock = false;
    boolean upBlock = false;
    boolean downBlock = false;
    public RookMovesCalculator(ChessBoard board, ChessPosition position)
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

    //calculates the rooks moves
    public HashSet<ChessMove> rookMoves()
    {
        for(int i = 1; i <=7; i++)
        {
            //if rook is going right
            if((currentColumn + i <= 8) && (!rightBlock))
            {
                if (myBoard.myChessBoard[currentColumn + i][currentRow] == null)
                {
                    ChessPosition endPosition = new ChessPosition(currentRow, currentColumn + i);
                    possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                }
                else
                {
                    if(myBoard.myChessBoard[currentColumn + i][currentRow].getTeamColor() != myTeamColor)
                    {
                        ChessPosition endPosition = new ChessPosition(currentRow, currentColumn + i);
                        //capture the enemy
                        myBoard.removePiece(endPosition);
                        //add the move
                        possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                    }
                    rightBlock = true;
                }
            }

            //if rook is moving down
            if((currentRow - i >=1) && (!downBlock))
            {
                if(myBoard.myChessBoard[currentColumn][currentRow - i] == null) {

                    ChessPosition endPosition = new ChessPosition(currentRow - i, currentColumn);
                    possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                }
                else
                {
                    if(myBoard.myChessBoard[currentColumn][currentRow - i].getTeamColor() != myTeamColor)
                    {
                        ChessPosition endPosition = new ChessPosition(currentRow - i, currentColumn);
                        //capture the enemy
                        myBoard.removePiece(endPosition);
                        //add the move
                        possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                    }
                    downBlock = true;
                }
            }

            //if rook goes left
            if((currentColumn - i >= 1) && (!leftBlock))
            {
                if(myBoard.myChessBoard[currentColumn - i][currentRow] == null) {

                    ChessPosition endPosition = new ChessPosition(currentRow, currentColumn - i);
                    possibleMoves.add(new ChessMove(myPosition, endPosition, null));                    }
                else
                {
                    if(myBoard.myChessBoard[currentColumn - i][currentRow].getTeamColor() != myTeamColor)
                    {
                        ChessPosition endPosition = new ChessPosition(currentRow, currentColumn - i);
                        //capture the enemy
                        myBoard.removePiece(endPosition);
                        //add the move
                        possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                    }
                    leftBlock = true;
                }
            }

            //if rook is moving up
            if((currentRow + i <= 8) && (!upBlock))
            {
                if(myBoard.myChessBoard[currentColumn][currentRow + i] == null) {
                    ChessPosition endPosition = new ChessPosition(currentRow + i, currentColumn);
                    possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                }
                else
                {
                    if(myBoard.myChessBoard[currentColumn][currentRow + i].getTeamColor() != myTeamColor)
                    {
                        ChessPosition endPosition = new ChessPosition(currentRow + i, currentColumn);
                        //capture the enemy
                        myBoard.removePiece(endPosition);
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