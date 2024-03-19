package ui;

import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.EscapeSequences.*;

public class ChessBoard {

    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_CHARS = 3;
    private static final int LINE_WIDTH_IN_CHARS = 1;
    private static final String EMPTY = "   ";
    private static String currentChar = EMPTY;
    //private static final String X = " X ";
    //private static final String O = " O ";
    //private static Random rand = new Random();


    public static void main(String[] args)
    {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        drawHeaders(out);

        chess.ChessBoard board = new chess.ChessBoard();
        board.resetBoard();
        drawTicTacToeBoard(out, board);

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
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

    private static void drawTicTacToeBoard(PrintStream out, chess.ChessBoard board) {

        drawSquares(out, board);
    }

    private static void drawSquares(PrintStream out, chess.ChessBoard board)
    {

        String[] rowHeaders = { " 1 ", " 2 ", " 3 ", " 4 ", " 5 ", " 6 ", " 7 ", " 8 ", "   "};

        //for each row
        for (int rowInBoard = 1; rowInBoard <= BOARD_SIZE_IN_SQUARES; ++rowInBoard)
        {
            //print the column with the row names
            out.print(SET_BG_COLOR_BLACK);
            out.print(SET_TEXT_COLOR_GREEN);
            out.print(rowHeaders[rowInBoard]);

            //if row+col is even
            //if row+col is odd
            for (int squareInRow = 1; squareInRow <= BOARD_SIZE_IN_SQUARES; squareInRow += 1) {
                //set the char to print here
                setCurrentChar(board, rowInBoard, squareInRow);
                if((squareInRow + rowInBoard) % 2 == 0) {
                    out.print(SET_BG_COLOR_WHITE);
                    out.print(SET_TEXT_COLOR_BLACK);
                    out.print(currentChar);
                }
                else
                {
                    out.print(SET_BG_COLOR_BLACK);
                    out.print(SET_TEXT_COLOR_WHITE);
                    out.print(currentChar);
                }

                /*
                //if it's an even row
                if (rowInBoard % 2 == 0) {
                    //for each square in the row
                    {
                        out.print(SET_BG_COLOR_WHITE);
                        out.print(SET_TEXT_COLOR_BLACK);
                        out.print(currentChar);

                        //this needs to be changed so that it's the next element in the board
                        out.print(SET_BG_COLOR_BLACK);
                        out.print(SET_TEXT_COLOR_WHITE);
                        setCurrentChar(board, rowInBoard, squareInRow+1);
                        out.print(currentChar);
                    }

                }
                //if it's an even row
                else {
                    //for each square in the row
                    out.print(SET_BG_COLOR_BLACK);
                    out.print(SET_TEXT_COLOR_WHITE);
                    out.print(currentChar);

                    out.print(SET_BG_COLOR_WHITE);
                    out.print(SET_TEXT_COLOR_BLACK);
                    setCurrentChar(board, rowInBoard, squareInRow+1);
                    out.print(currentChar);

                 */
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
    }
    private static void setWhite(PrintStream out) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void setRed(PrintStream out) {
        out.print(SET_BG_COLOR_RED);
        out.print(SET_TEXT_COLOR_RED);
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void printPlayer(PrintStream out, String player) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_BLACK);

        out.print(player);

        setWhite(out);
    }
}
