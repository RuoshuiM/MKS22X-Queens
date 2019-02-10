import java.util.Arrays;

public class QueenBoard {
    private int[][] board;

    public QueenBoard(int size) {
        board = new int[size][size];
    }
    
    private void clearBoard() {
        board = new int[board.length][board.length];
    }

    private boolean addQueen(int r, int c) {
        if (board[r][c] == 0) {
            // set invalid areas
            for (int i = r, j = c; i < board.length && j < board.length; i++, j++) {
                board[i][j]++;
            }
            for (int i = r, j = c; i > -1 && j < board.length; i--, j++) {
                board[i][j]++;
            }
            for (int j = c; j < board.length; j++) {
                board[r][j]++;
            }

            board[r][c] = Integer.MIN_VALUE;
            return true;
        } else {
            return false;
        }
    }

    private boolean removeQueen(int r, int c) {
        if (board[r][c] < 0) {
            // reset invalid areas
            for (int i = r, j = c; i < board.length && j < board.length; i++, j++) {
                board[i][j]--;
            }
            for (int i = r, j = c; i > -1 && j < board.length; i--, j++) {
                board[i][j]--;
            }
            for (int j = c; j < board.length; j++) {
                board[r][j]--;
            }

            board[r][c] = 0;
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return The output string formatted as follows: All numbers that represent
     *         queens are replaced with 'Q', all others are displayed as underscores
     *         '_'. There are spaces between each symbol:
     *
     *         <pre>
     *         """_ _ Q _
     *         Q _ _ _
     *         _ _ _ Q
     *         _ Q _ _"""
     *         </pre>
     *
     *         (pythonic string notation for clarity, excludes the character up to
     *         the *)
     */
    @Override
    public String toString() {
        StringBuilder boardStr = new StringBuilder("");

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                if (board[row][col] < 0) {
                    boardStr.append("Q ");
                } else {
                    boardStr.append("_ ");
                }

            }
            boardStr.delete(boardStr.length(), boardStr.length()).append("\n");
        }
        return boardStr.toString();
    }

    /**
     * @return false when the board is not solveable and leaves the board filled
     *         with zeros; true when the board is solveable, and leaves the board in
     *         a solved state
     * @throws IllegalStateException when the board starts with any non-zero value
     */
    public boolean solve() {
        if (!isValidBoard()) {
            throw new IllegalStateException("Board should not start with any non-zero value");
        }
        return isSolvable(0);
    }

    /**
     * @return the number of solutions found, and leaves the board filled with only
     *         0's
     * @throws IllegalStateException when the board starts with any non-zero value
     */
    public int countSolutions() {
        if (!isValidBoard()) {
            throw new IllegalStateException("Board should not start with any non-zero value");
        }
        int numSolutions = 0;
        for (int row = 0, col = 0; row < board.length; row++) {
            addQueen(row, col);
            if (isSolvable(col + 1)) {
                numSolutions++;
            }
            removeQueen(row, col);
        }
        
        return numSolutions;
    }

    private boolean isValidBoard() {
        return Arrays.deepEquals(board, new int[board.length][board.length]);
    }

    private boolean isSolvable(int col) {
      if (col == board.length) {
        return true;
      } else {
        for (int row = 0; row < board.length; row++) {
          if (addQueen(row, col)) {
            if (isSolvable(col + 1)) {
              return true;
            }
          }
          removeQueen(row, col);
        }
        return false;
      }
    }
    
    public static void main(String...args) {
//      QueenBoard q = new QueenBoard(Integer.parseInt(args[0]));
      QueenBoard q = new QueenBoard(28);
      System.out.println(q.solve());
      System.out.println(q.toString());
      q.clearBoard();
      System.out.println(q.countSolutions());
    }
}