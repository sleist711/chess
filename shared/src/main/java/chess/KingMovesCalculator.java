package chess;

import java.util.HashSet;

public class KingMovesCalculator implements PieceMovesCalculator{

    //ChessBoard myChessBoard;
    ChessPosition myPosition;
    ChessGame.TeamColor myTeamColor;
    ChessBoard myBoard;
    int currentColumn;
    int currentRow;

    //used as a constructor
    public KingMovesCalculator(ChessBoard board, ChessPosition position)
    {
        pieceMovesCalculator(board, position);
    }
    public void pieceMovesCalculator(ChessBoard board, ChessPosition position)
    {
        myPosition = position;
        //myChessBoard = board.myChessBoard;
        myTeamColor = board.myChessBoard[position.getColumn()][position.getRow()].getTeamColor();
        myBoard = board;
        currentColumn = myPosition.getColumn();
        currentRow = myPosition.getRow();
        //clear out the hashset
        possibleMoves.clear();
    }
   // public HashSet<ChessMove> kingsMoves(ChessBoard board, ChessPosition position)

    //calculates the king's moves (does not account for moves that are illegal due to leaving the king in danger)
    public HashSet<ChessMove> kingsMoves()
    {
        //if king going right
        if((currentColumn + oneSquare <= 8))
        {
            if (myBoard.myChessBoard[currentColumn + oneSquare][currentRow] == null)
            {
                ChessPosition endPosition = new ChessPosition(currentRow, currentColumn + oneSquare);
                possibleMoves.add(new ChessMove(myPosition, endPosition, null));
            }
            else
            {
                if(myBoard.myChessBoard[currentColumn + oneSquare][currentRow].getTeamColor() != myTeamColor)
                {
                    ChessPosition endPosition = new ChessPosition(currentRow, currentColumn + oneSquare);
                    //add the move
                    possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                }
            }
        }

        //if king is moving down
        if(currentRow - oneSquare >= 1)
        {
            if(myBoard.myChessBoard[currentColumn][currentRow - oneSquare] == null) {

                ChessPosition endPosition = new ChessPosition(currentRow - oneSquare, currentColumn);
                possibleMoves.add(new ChessMove(myPosition, endPosition, null));
            }
            else
            {
                if(myBoard.myChessBoard[currentColumn][currentRow - oneSquare].getTeamColor() != myTeamColor)
                {
                    ChessPosition endPosition = new ChessPosition(currentRow - oneSquare, currentColumn);
                    //add the move
                    possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                }
            }
        }

        //if king goes left
        if(currentColumn - oneSquare >= 1)
        {
            if(myBoard.myChessBoard[currentColumn - oneSquare][currentRow] == null) {

                ChessPosition endPosition = new ChessPosition(currentRow, currentColumn - oneSquare);
                possibleMoves.add(new ChessMove(myPosition, endPosition, null));                    }
            else
            {
                if(myBoard.myChessBoard[currentColumn - oneSquare][currentRow].getTeamColor() != myTeamColor)
                {
                    ChessPosition endPosition = new ChessPosition(currentRow, currentColumn - oneSquare);
                    //add the move
                    possibleMoves.add(new ChessMove(myPosition, endPosition, null));

                }
            }
        }

        //if king is moving up
        if(currentRow + oneSquare <= 8)
        {
            if(myBoard.myChessBoard[currentColumn][currentRow + oneSquare] == null) {
                ChessPosition endPosition = new ChessPosition(currentRow + oneSquare, currentColumn);
                possibleMoves.add(new ChessMove(myPosition, endPosition, null));
            }
            else if(myBoard.myChessBoard[currentColumn][currentRow + oneSquare].getTeamColor() != myTeamColor)
            {
                ChessPosition endPosition = new ChessPosition(currentRow + oneSquare, currentColumn);
                //add the move
                possibleMoves.add(new ChessMove(myPosition, endPosition, null));
            }
        }

        //king moving up and to the right
        if((currentRow + oneSquare <= 8) && (currentColumn + oneSquare <= 8))
        {
            if (myBoard.myChessBoard[currentColumn + oneSquare][currentRow + oneSquare] == null)
            {
                ChessPosition endPosition = new ChessPosition(currentRow + oneSquare, currentColumn + oneSquare);
                possibleMoves.add(new ChessMove(myPosition, endPosition, null));
            }
            else if(myBoard.myChessBoard[currentColumn + oneSquare][currentRow + oneSquare].getTeamColor() != myTeamColor)
            {
                ChessPosition endPosition = new ChessPosition(currentRow + oneSquare, currentColumn + oneSquare);
                //add the move
                possibleMoves.add(new ChessMove(myPosition, endPosition, null));
            }

        }

        //if king is moving down and to the right
        if((currentRow - oneSquare >=1) && (currentColumn + oneSquare <=8))
        {
            if(myBoard.myChessBoard[currentColumn + oneSquare][currentRow - oneSquare] == null) {

                ChessPosition endPosition = new ChessPosition(currentRow - oneSquare, currentColumn + oneSquare);
                possibleMoves.add(new ChessMove(myPosition, endPosition, null));

            }
            else if(myBoard.myChessBoard[currentColumn + oneSquare][currentRow - oneSquare].getTeamColor() != myTeamColor) {

                ChessPosition endPosition = new ChessPosition(currentRow - oneSquare, currentColumn + oneSquare);
                //add the move
                possibleMoves.add(new ChessMove(myPosition, endPosition, null));
            }
        }

        //if king is moving down and to the left
        if((currentRow - oneSquare >= 1) && (currentColumn - oneSquare >= 1)) {
            if (myBoard.myChessBoard[currentColumn - oneSquare][currentRow - oneSquare] == null) {

                ChessPosition endPosition = new ChessPosition(currentRow - oneSquare, currentColumn - oneSquare);
                possibleMoves.add(new ChessMove(myPosition, endPosition, null));
            }
            else if (myBoard.myChessBoard[currentColumn - oneSquare][currentRow - oneSquare].getTeamColor() != myTeamColor)
            {
                ChessPosition endPosition = new ChessPosition(currentRow - oneSquare, currentColumn - oneSquare);
                //add the move
                possibleMoves.add(new ChessMove(myPosition, endPosition, null));
            }
        }

        //if king is moving up and to the left
        if((currentRow + oneSquare <= 8) && (currentColumn - oneSquare >= 1))
        {
            if(myBoard.myChessBoard[currentColumn - oneSquare][currentRow + oneSquare] == null) {
                ChessPosition endPosition = new ChessPosition(currentRow + oneSquare, currentColumn - oneSquare);
                possibleMoves.add(new ChessMove(myPosition, endPosition, null));
            }
            else if(myBoard.myChessBoard[currentColumn - oneSquare][currentRow + oneSquare].getTeamColor() != myTeamColor)
            {
                ChessPosition endPosition = new ChessPosition(currentRow + oneSquare, currentColumn - oneSquare);
                //add the move
                possibleMoves.add(new ChessMove(myPosition, endPosition, null));
            }
        }
        HashSet<ChessMove> movesToReturn = new HashSet<>(possibleMoves);
        return movesToReturn;
    }
}
