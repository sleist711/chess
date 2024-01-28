package chess;

import java.util.HashSet;

public interface PieceMovesCalculator {
    void pieceMovesCalculator(ChessBoard board, ChessPosition position);
    HashSet<ChessMove> possibleMoves = new HashSet<>();
    int oneSquare = 1;
    int twoSquares = 2;

}
