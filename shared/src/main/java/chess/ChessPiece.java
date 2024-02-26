package chess;
import chess.MoveCalculators.*;

import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    final public ChessGame.TeamColor TEAM_COLOR;
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
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        if (PIECE_TYPE == PieceType.ROOK)
        {
            RookMovesCalculator rookCalculator = new RookMovesCalculator(board, myPosition);
            return rookCalculator.rookMoves();
        }
        else if (PIECE_TYPE == PieceType.KING)
        {
            KingMovesCalculator kingCalculator = new KingMovesCalculator(board, myPosition);
            return kingCalculator.kingsMoves();
        }
        else if (PIECE_TYPE == PieceType.PAWN)
        {
            PawnMovesCalculator pawnCalculator = new PawnMovesCalculator(board, myPosition);
            return pawnCalculator.pawnMoves();
        }
        else if (PIECE_TYPE == PieceType.KNIGHT)
        {
            KnightMovesCalculator knightCalculator = new KnightMovesCalculator(board, myPosition);
            return knightCalculator.knightMoves();
        }
        else if(PIECE_TYPE == PieceType.QUEEN)
        {
            QueenMovesCalculator queenCalculator = new QueenMovesCalculator(board, myPosition);
            return queenCalculator.queenMoves();
        }
        else if(PIECE_TYPE == PieceType.BISHOP)
        {
            BishopMovesCalculator bishopCalculator = new BishopMovesCalculator(board, myPosition);
            return bishopCalculator.bishopMoves();
        }
        //in case the piece type doesn't match anything
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return TEAM_COLOR == that.TEAM_COLOR && PIECE_TYPE == that.PIECE_TYPE;
    }

    @Override
    public int hashCode() {
        return Objects.hash(TEAM_COLOR, PIECE_TYPE);
    }
}
