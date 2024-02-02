package chess;
import java.util.HashSet;

public class PawnMovesCalculator implements PieceMovesCalculator{
    ChessPosition myPosition;
    ChessGame.TeamColor myTeamColor;
    ChessBoard myBoard;
    int currentColumn;
    int currentRow;

    //used as a constructor
    public PawnMovesCalculator(ChessBoard board, ChessPosition position)
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

    //calculates pawn's moves
    public HashSet<ChessMove> pawnMoves()
    {
        //if it's a white pawn
        if (myBoard.myChessBoard[currentColumn][currentRow].TEAM_COLOR == ChessGame.TeamColor.WHITE) {

            if(currentRow + oneSquare != 8) {
                //if the first square is clear
                if ((myBoard.myChessBoard[currentColumn][currentRow + oneSquare] == null) && (currentRow + oneSquare <= 8)) {
                    ChessPosition endPosition = new ChessPosition(currentRow + oneSquare, currentColumn);
                    possibleMoves.add(new ChessMove(myPosition, endPosition, null));

                    //if it's the first move and the second square is also clear
                    if ((currentRow == 2) && (myBoard.myChessBoard[currentColumn][currentRow + twoSquares] == null)) {
                        endPosition = new ChessPosition(currentRow + twoSquares, currentColumn);
                        possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                    }
                }

                //if enemy at diagonal right
                if ((currentColumn + oneSquare <= 8) && (currentRow + oneSquare <= 8)) {
                    if ((myBoard.myChessBoard[currentColumn + oneSquare][currentRow + oneSquare] != null) && (myBoard.myChessBoard[currentColumn + oneSquare][currentRow + oneSquare].TEAM_COLOR != myTeamColor)) {
                        ChessPosition endPosition = new ChessPosition(currentRow + oneSquare, currentColumn + oneSquare);
                        possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                    }
                }

                //if enemy at diagonal left
                if ((currentColumn - oneSquare >= 1) && (currentRow + oneSquare <= 8)) {
                    if ((myBoard.myChessBoard[currentColumn - oneSquare][currentRow + oneSquare] != null) && (myBoard.myChessBoard[currentColumn - oneSquare][currentRow + oneSquare].TEAM_COLOR != myTeamColor)) {
                        ChessPosition endPosition = new ChessPosition(currentRow + oneSquare, currentColumn - oneSquare);
                        possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                    }
                }
            }

            //if it's going to reach the last row, need to calculate its move based on what type of piece it could turn into
            else
            {
                //if pawn moves straight
                if (myBoard.myChessBoard[currentColumn][currentRow + oneSquare] == null) {
                    ChessPosition endPosition = new ChessPosition(currentRow + oneSquare, currentColumn);
                    //if pawn is promoted to rook
                    possibleMoves.add(new ChessMove(myPosition, endPosition, ChessPiece.PieceType.ROOK));
                    //if pawn promoted to bishop
                    possibleMoves.add(new ChessMove(myPosition, endPosition, ChessPiece.PieceType.BISHOP));
                    //if pawn promoted to knight
                    possibleMoves.add(new ChessMove(myPosition, endPosition, ChessPiece.PieceType.KNIGHT));
                    //if pawn promoted to queen
                    possibleMoves.add(new ChessMove(myPosition, endPosition, ChessPiece.PieceType.QUEEN));
                }

                //if there's an enemy diagonal right
                if ((myBoard.myChessBoard[currentColumn + oneSquare][currentRow + oneSquare] != null) && (myBoard.myChessBoard[currentColumn + oneSquare][currentRow + oneSquare].TEAM_COLOR != myTeamColor)) {
                    ChessPosition endPosition = new ChessPosition(currentRow + oneSquare, currentColumn + oneSquare);
                    //if pawn is promoted to rook
                    possibleMoves.add(new ChessMove(myPosition, endPosition, ChessPiece.PieceType.ROOK));
                    //if pawn promoted to bishop
                    possibleMoves.add(new ChessMove(myPosition, endPosition, ChessPiece.PieceType.BISHOP));
                    //if pawn promoted to knight
                    possibleMoves.add(new ChessMove(myPosition, endPosition, ChessPiece.PieceType.KNIGHT));
                    //if pawn promoted to queen
                    possibleMoves.add(new ChessMove(myPosition, endPosition, ChessPiece.PieceType.QUEEN));
                }

                //if enemy at diagonal left
                if ((myBoard.myChessBoard[currentColumn - oneSquare][currentRow + oneSquare] != null) && (myBoard.myChessBoard[currentColumn - oneSquare][currentRow + oneSquare].TEAM_COLOR != myTeamColor)) {
                    ChessPosition endPosition = new ChessPosition(currentRow + oneSquare, currentColumn - oneSquare);
                    //if pawn is promoted to rook
                    possibleMoves.add(new ChessMove(myPosition, endPosition, ChessPiece.PieceType.ROOK));
                    //if pawn promoted to bishop
                    possibleMoves.add(new ChessMove(myPosition, endPosition, ChessPiece.PieceType.BISHOP));
                    //if pawn promoted to knight
                    possibleMoves.add(new ChessMove(myPosition, endPosition, ChessPiece.PieceType.KNIGHT));
                    //if pawn promoted to queen
                    possibleMoves.add(new ChessMove(myPosition, endPosition, ChessPiece.PieceType.QUEEN));
                }
            }
        }

        //if it's a black pawn
        else {
            //if it's not at the last row
            if(currentRow - oneSquare != 1)
            {
                //if the first square is clear
                if ((myBoard.myChessBoard[currentColumn][currentRow - oneSquare] == null) && (currentRow - oneSquare >= 1)) {
                    ChessPosition endPosition = new ChessPosition(currentRow - oneSquare, currentColumn);
                    possibleMoves.add(new ChessMove(myPosition, endPosition, null));

                    //if it's the first move and the second square is also clear
                    if ((currentRow == 7) && (myBoard.myChessBoard[currentColumn][currentRow - twoSquares] == null)) {
                        endPosition = new ChessPosition(currentRow - twoSquares, currentColumn);
                        possibleMoves.add(new ChessMove(myPosition, endPosition, null));

                    }
                }

                //if enemy at diagonal right
                if ((currentColumn + oneSquare <= 8) && (currentRow - oneSquare >= 1)) {
                    if ((myBoard.myChessBoard[currentColumn + oneSquare][currentRow - oneSquare] != null) && (myBoard.myChessBoard[currentColumn + oneSquare][currentRow - oneSquare].TEAM_COLOR != myTeamColor)) {
                        ChessPosition endPosition = new ChessPosition(currentRow - oneSquare, currentColumn + oneSquare);
                        possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                    }
                }

                //if enemy at diagonal left
                if ((currentColumn - oneSquare >= 1) && (currentRow - oneSquare >= 1)) {
                    if ((myBoard.myChessBoard[currentColumn - oneSquare][currentRow - oneSquare] != null) && (myBoard.myChessBoard[currentColumn - oneSquare][currentRow - oneSquare].TEAM_COLOR != myTeamColor)) {
                        ChessPosition endPosition = new ChessPosition(currentRow - oneSquare, currentColumn - oneSquare);
                        possibleMoves.add(new ChessMove(myPosition, endPosition, null));
                    }
                }
            }

            //if it's going to reach the last row, need to calculate its move based on what type of piece it could turn into
            else {
                //if pawn moves straight
                if (myBoard.myChessBoard[currentColumn][currentRow - oneSquare] == null) {
                    ChessPosition endPosition = new ChessPosition(currentRow - oneSquare, currentColumn);
                    //if pawn is promoted to rook
                    possibleMoves.add(new ChessMove(myPosition, endPosition, ChessPiece.PieceType.ROOK));
                    //if pawn promoted to bishop
                    possibleMoves.add(new ChessMove(myPosition, endPosition, ChessPiece.PieceType.BISHOP));
                    //if pawn promoted to knight
                    possibleMoves.add(new ChessMove(myPosition, endPosition, ChessPiece.PieceType.KNIGHT));
                    //if pawn promoted to queen
                    possibleMoves.add(new ChessMove(myPosition, endPosition, ChessPiece.PieceType.QUEEN));
                }

                //if there's an enemy diagonal right
                if ((myBoard.myChessBoard[currentColumn + oneSquare][currentRow - oneSquare] != null) && (myBoard.myChessBoard[currentColumn + oneSquare][currentRow - oneSquare].TEAM_COLOR != myTeamColor)) {
                    ChessPosition endPosition = new ChessPosition(currentRow - oneSquare, currentColumn + oneSquare);
                    //if pawn is promoted to rook
                    possibleMoves.add(new ChessMove(myPosition, endPosition, ChessPiece.PieceType.ROOK));
                    //if pawn promoted to bishop
                    possibleMoves.add(new ChessMove(myPosition, endPosition, ChessPiece.PieceType.BISHOP));
                    //if pawn promoted to knight
                    possibleMoves.add(new ChessMove(myPosition, endPosition, ChessPiece.PieceType.KNIGHT));
                    //if pawn promoted to queen
                    possibleMoves.add(new ChessMove(myPosition, endPosition, ChessPiece.PieceType.QUEEN));
                }

                //if enemy at diagonal left
                if ((myBoard.myChessBoard[currentColumn - oneSquare][currentRow - oneSquare] != null) && (myBoard.myChessBoard[currentColumn - oneSquare][currentRow - oneSquare].TEAM_COLOR != myTeamColor)) {
                    ChessPosition endPosition = new ChessPosition(currentRow - oneSquare, currentColumn - oneSquare);
                    //if pawn is promoted to rook
                    possibleMoves.add(new ChessMove(myPosition, endPosition, ChessPiece.PieceType.ROOK));
                    //if pawn promoted to bishop
                    possibleMoves.add(new ChessMove(myPosition, endPosition, ChessPiece.PieceType.BISHOP));
                    //if pawn promoted to knight
                    possibleMoves.add(new ChessMove(myPosition, endPosition, ChessPiece.PieceType.KNIGHT));
                    //if pawn promoted to queen
                    possibleMoves.add(new ChessMove(myPosition, endPosition, ChessPiece.PieceType.QUEEN));
                }
            }
        }
        //return possibleMoves;
        HashSet<ChessMove> movesToReturn = new HashSet<>(possibleMoves);
        return movesToReturn;
    }
}
