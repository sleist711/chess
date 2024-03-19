package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.EscapeSequences.*;

public class ChessBoard {

    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_CHARS = 3;
    private static final int LINE_WIDTH_IN_CHARS = 1;
    private static final String EMPTY = " ";
    //private static final String X = " X ";
    //private static final String O = " O ";
    //private static Random rand = new Random();


    public static void main(String[] args)
    {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        drawHeaders(out);

        drawTicTacToeBoard(out);

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

    private static void drawTicTacToeBoard(PrintStream out) {

        drawSquares(out);
    }

    private static void drawSquares(PrintStream out)
    {

        String[] rowHeaders = { " 1 ", " 2 ", " 3 ", " 4 ", " 5 ", " 6 ", " 7 ", " 8 "};

        //for each row
        for (int rowInBoard = 0; rowInBoard < BOARD_SIZE_IN_SQUARES; ++rowInBoard)
        {
            //print the column with the row names
            out.print(SET_BG_COLOR_BLACK);
            out.print(SET_TEXT_COLOR_GREEN);
            out.print(rowHeaders[rowInBoard]);

            //if it's an even row
            if(rowInBoard % 2 == 0)
            {
                //for each square in the row
                for (int squareInRow = 0; squareInRow < BOARD_SIZE_IN_SQUARES; squareInRow +=2)
                {
                    setWhite(out);
                    out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
                    setBlack(out);
                    out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
                }

            }
            //if it's an even row
            else
            {
                //for each square in the row
                for (int squareInRow = 0; squareInRow < BOARD_SIZE_IN_SQUARES; squareInRow +=2)
                {
                    setBlack(out);
                    out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
                    setWhite(out);
                    out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));

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

