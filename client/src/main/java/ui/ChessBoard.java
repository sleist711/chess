package ui;

import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.EscapeSequences.*;

public class ChessBoard {

    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final String EMPTY = "   ";
    private static String currentChar = EMPTY;
    private static String currentColor = null;


    public static void main(String[] args)
    {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        //drawHeaders(out);

        chess.ChessBoard board = new chess.ChessBoard();
        board.resetBoard();
        drawSquares(out, board);

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);

        drawSquaresFlipped(out, board);

        //out.print(RESET_BG_COLOR);
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(RESET_TEXT_COLOR);
    }

    private static void drawHeaders(PrintStream out) {

        setBlack(out);

        String[] headers = { "   ", " a ", " b ", " c ", " d ", " e ", " f ", " g ", " h "};
        for (int boardCol = 0; boardCol <= BOARD_SIZE_IN_SQUARES; ++boardCol) {
            out.print(SET_BG_COLOR_BLACK);
            out.print(SET_TEXT_COLOR_GREEN);

            out.print(headers[boardCol]);
        }
        out.println();
    }


    private static void drawSquares(PrintStream out, chess.ChessBoard board)
    {

        drawHeaders(out);

        String[] rowHeaders = {"   ", " 1 ", " 2 ", " 3 ", " 4 ", " 5 ", " 6 ", " 7 ", " 8 "};

        //for each row
        for (int rowInBoard = 1; rowInBoard <= BOARD_SIZE_IN_SQUARES; ++rowInBoard)
        {
            //print the column with the row names
            out.print(SET_BG_COLOR_BLACK);
            out.print(SET_TEXT_COLOR_GREEN);
            out.print(rowHeaders[rowInBoard]);

            for (int squareInRow = 1; squareInRow <= BOARD_SIZE_IN_SQUARES; squareInRow += 1) {
                //set the char to print here
                setCurrentChar(board, rowInBoard, squareInRow);
                if((squareInRow + rowInBoard) % 2 == 0) {
                    out.print(SET_BG_COLOR_WHITE);
                    out.print(SET_TEXT_COLOR_BLACK);
                    out.print(currentColor);
                    out.print(currentChar);
                }
                else
                {
                    out.print(SET_BG_COLOR_BLACK);
                    out.print(currentColor);
                    out.print(currentChar);
                }
            }

            //print the column with the row names
            out.print(SET_BG_COLOR_BLACK);
            out.print(SET_TEXT_COLOR_GREEN);
            out.print(rowHeaders[rowInBoard]);
            setBlack(out);
            out.println();

        }

        drawHeaders(out);
        out.println();
    }

    private static void drawSquaresFlipped(PrintStream out, chess.ChessBoard board)
    {

        drawHeaders(out);

        String[] rowHeaders = {"   ", " 1 ", " 2 ", " 3 ", " 4 ", " 5 ", " 6 ", " 7 ", " 8 "};

        //for each row
        for (int rowInBoard = 8; rowInBoard >= 1;  --rowInBoard)
        {
            //print the column with the row names
            out.print(SET_BG_COLOR_BLACK);
            out.print(SET_TEXT_COLOR_GREEN);
            out.print(rowHeaders[rowInBoard]);

            for (int squareInRow = 1; squareInRow <= BOARD_SIZE_IN_SQUARES; squareInRow += 1) {
                //set the char to print here
                setCurrentChar(board, rowInBoard, squareInRow);
                if((squareInRow + rowInBoard) % 2 == 0) {
                    out.print(SET_BG_COLOR_WHITE);
                    out.print(SET_TEXT_COLOR_BLACK);
                    out.print(currentColor);
                    out.print(currentChar);
                }
                else
                {
                    out.print(SET_BG_COLOR_BLACK);
                    out.print(currentColor);
                    out.print(currentChar);
                }
            }

            //print the column with the row names
            out.print(SET_BG_COLOR_BLACK);
            out.print(SET_TEXT_COLOR_GREEN);
            out.print(rowHeaders[rowInBoard]);
            setBlack(out);
            out.println();

        }

        drawHeaders(out);
        out.println();
    }

    private static void setCurrentChar(chess.ChessBoard board, int rowInBoard, int squareInRow)
    {
        if (board.getPiece(new ChessPosition(rowInBoard, squareInRow)) == null)
        {
            currentChar = EMPTY;
            currentColor = SET_TEXT_COLOR_BLACK;
            return;
        }
        else if (board.getPiece(new ChessPosition(rowInBoard, squareInRow)).getPieceType() == ChessPiece.PieceType.KING)
        {
            currentChar = " K ";
        }
        else if (board.getPiece(new ChessPosition(rowInBoard, squareInRow)).getPieceType() == ChessPiece.PieceType.QUEEN)
        {
            currentChar = " Q ";
        }
        else if (board.getPiece(new ChessPosition(rowInBoard, squareInRow)).getPieceType() == ChessPiece.PieceType.PAWN)
        {
            currentChar = " P ";
        }
        else if (board.getPiece(new ChessPosition(rowInBoard, squareInRow)).getPieceType() == ChessPiece.PieceType.ROOK)
        {
            currentChar = " R ";
        }
        else if (board.getPiece(new ChessPosition(rowInBoard, squareInRow)).getPieceType() == ChessPiece.PieceType.KNIGHT)
        {
            currentChar = "KNI";
        }
        else if (board.getPiece(new ChessPosition(rowInBoard, squareInRow)).getPieceType() == ChessPiece.PieceType.BISHOP)
        {
            currentChar = " B ";
        }
        if(board.getPiece(new ChessPosition(rowInBoard, squareInRow)).getTeamColor() == ChessGame.TeamColor.WHITE)
        {
            currentColor = SET_TEXT_COLOR_RED; //set color to red
        }
        else
        {
            //set color to blue
            currentColor = SET_TEXT_COLOR_BLUE;

        }
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }

}
