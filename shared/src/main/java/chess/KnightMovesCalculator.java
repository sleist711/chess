package chess;

import java.util.HashSet;

public class KnightMovesCalculator implements PieceMovesCalculator{

    ChessPosition myPosition;
    ChessGame.TeamColor myTeamColor;
    ChessBoard myBoard;
    int currentColumn;
    int currentRow;

    //used as a constructor
    public KnightMovesCalculator(ChessBoard board, ChessPosition position)
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

    //calculates the knight's moves
    public HashSet<ChessMove> knightMoves()
    {
        //right two, up one
        if((currentColumn + twoSquares <= 8) && (currentRow + oneSquare <= 8))
        {
            if(myBoard.myChessBoard[currentColumn + twoSquares][currentRow + oneSquare] == null)
            {
                ChessPosition endPosition = new ChessPosition(currentRow + oneSquare, currentColumn + twoSquares);
                possibleMoves.add(new ChessMove(myPosition, endPosition, null));
            }

            else
            {
                if(myBoard.myChessBoard[currentColumn + twoSquares][currentRow + oneSquare].getTeamColor() != myTeamColor)
                {
                    ChessPosition endPosition = new ChessPosition(currentRow + oneSquare, currentColumn + twoSquares);
                    //add the move
                    possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                }
            }
        }

        //left two, up one
        if((currentColumn - twoSquares >= 1) && (currentRow + oneSquare <= 8))
        {
            if(myBoard.myChessBoard[currentColumn - twoSquares][currentRow + oneSquare] == null)
            {
                ChessPosition endPosition = new ChessPosition(currentRow + oneSquare, currentColumn - twoSquares);
                possibleMoves.add(new ChessMove(myPosition, endPosition, null));
            }
            else
            {
                if(myBoard.myChessBoard[currentColumn - twoSquares][currentRow + oneSquare].getTeamColor() != myTeamColor)
                {
                    ChessPosition endPosition = new ChessPosition(currentRow + oneSquare, currentColumn - twoSquares);
                    //add the move
                    possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                }
            }
        }

        //right two, down one
        if((currentColumn + twoSquares <= 8) && (currentRow - oneSquare >= 1))
        {
            if(myBoard.myChessBoard[currentColumn + twoSquares][currentRow - oneSquare] == null)
            {
                ChessPosition endPosition = new ChessPosition(currentRow - oneSquare, currentColumn + twoSquares);
                possibleMoves.add(new ChessMove(myPosition, endPosition, null));
            }

            else
            {
                if(myBoard.myChessBoard[currentColumn + twoSquares][currentRow - oneSquare].getTeamColor() != myTeamColor)
                {
                    ChessPosition endPosition = new ChessPosition(currentRow - oneSquare, currentColumn + twoSquares);
                    //add the move
                    possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                }
            }
        }

        //left two, down one
        if((currentColumn - twoSquares >= 1) && (currentRow - oneSquare >= 1))
        {
            if(myBoard.myChessBoard[currentColumn - twoSquares][currentRow - oneSquare] == null)
            {
                ChessPosition endPosition = new ChessPosition(currentRow - oneSquare, currentColumn - twoSquares);
                possibleMoves.add(new ChessMove(myPosition, endPosition, null));
            }

            else
            {
                if(myBoard.myChessBoard[currentColumn - twoSquares][currentRow - oneSquare].getTeamColor() != myTeamColor)
                {
                    ChessPosition endPosition = new ChessPosition(currentRow - oneSquare, currentColumn - twoSquares);
                    //add the move
                    possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                }
            }
        }

        //right one, up two
        if((currentColumn + oneSquare <= 8) && (currentRow + twoSquares <= 8))
        {
            if(myBoard.myChessBoard[currentColumn + oneSquare][currentRow + twoSquares] == null)
            {
                ChessPosition endPosition = new ChessPosition(currentRow + twoSquares, currentColumn + oneSquare);
                possibleMoves.add(new ChessMove(myPosition, endPosition, null));
            }

            else
            {
                if(myBoard.myChessBoard[currentColumn + oneSquare][currentRow + twoSquares].getTeamColor() != myTeamColor)
                {
                    ChessPosition endPosition = new ChessPosition(currentRow + twoSquares, currentColumn + oneSquare);
                    //add the move
                    possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                }
            }
        }

        //left one, up two
        if((currentColumn - oneSquare >= 1) && (currentRow + twoSquares <= 8))
        {
            if(myBoard.myChessBoard[currentColumn - oneSquare][currentRow + twoSquares] == null)
            {
                ChessPosition endPosition = new ChessPosition(currentRow + twoSquares, currentColumn - oneSquare);
                possibleMoves.add(new ChessMove(myPosition, endPosition, null));
            }
            else
            {
                if(myBoard.myChessBoard[currentColumn - oneSquare][currentRow + twoSquares].getTeamColor() != myTeamColor)
                {
                    ChessPosition endPosition = new ChessPosition(currentRow + twoSquares, currentColumn - oneSquare);
                    //add the move
                    possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                }
            }
        }

        //right one, down two
        if((currentColumn + oneSquare <= 8) && (currentRow - twoSquares >= 1))
        {
            if(myBoard.myChessBoard[currentColumn + oneSquare][currentRow - twoSquares] == null)
            {
                ChessPosition endPosition = new ChessPosition(currentRow - twoSquares, currentColumn + oneSquare);
                possibleMoves.add(new ChessMove(myPosition, endPosition, null));
            }

            else
            {
                if(myBoard.myChessBoard[currentColumn + oneSquare][currentRow - twoSquares].getTeamColor() != myTeamColor)
                {
                    ChessPosition endPosition = new ChessPosition(currentRow - twoSquares, currentColumn + oneSquare);
                    //add the move
                    possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                }
            }
        }

        //left one, down two
        if((currentColumn - oneSquare >= 1) && (currentRow - twoSquares >= 1))
        {
            if(myBoard.myChessBoard[currentColumn - oneSquare][currentRow - twoSquares] == null)
            {
                ChessPosition endPosition = new ChessPosition(currentRow - twoSquares, currentColumn - oneSquare);
                possibleMoves.add(new ChessMove(myPosition, endPosition, null));
            }

            else
            {
                if(myBoard.myChessBoard[currentColumn - oneSquare][currentRow - twoSquares].getTeamColor() != myTeamColor)
                {
                    ChessPosition endPosition = new ChessPosition(currentRow - twoSquares, currentColumn - oneSquare);
                    //add the move
                    possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                }
            }
        }
        HashSet<ChessMove> movesToReturn = new HashSet<>(possibleMoves);
        return movesToReturn;
    }
}

