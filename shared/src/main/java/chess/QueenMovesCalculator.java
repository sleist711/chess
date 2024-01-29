package chess;

import java.util.HashSet;

public class QueenMovesCalculator implements PieceMovesCalculator {

    ChessPosition myPosition;
    ChessGame.TeamColor myTeamColor;
    ChessBoard myBoard;
    int currentColumn;
    int currentRow;

    public QueenMovesCalculator(ChessBoard board, ChessPosition position)
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

    //calculates the queen's moves
    public HashSet<ChessMove> queenMoves()
    {
        RookMovesCalculator rookCalculator = new RookMovesCalculator(myBoard, myPosition);
        BishopMovesCalculator bishopCalculator = new BishopMovesCalculator(myBoard, myPosition);
        HashSet<ChessMove> possibleMoves = rookCalculator.rookMoves();
        possibleMoves.addAll(bishopCalculator.bishopMoves());

        return possibleMoves;
    }
}
