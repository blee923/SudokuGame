public class Sudoku {
  // This function prints the Sudoku board in text.
  public static void printBoard(int[][] board) {
    for (int i = 0; i < 9; ++i) {
      String tempString = "";
      for (int j = 0; j < 9; ++j) {
        tempString += " " + board[i][j];
        if (j == 2 || j == 5) {
          tempString += " |";
        }
      }
      System.out.println(tempString.trim());
      if (i == 2 || i == 5) {
        System.out.println("---------------------");
      }
    }
  }

  // This function fills the board with ones as temporary place holders.
  public static void fillBoardWithOnes(int[][] board) {
    for (int i = 0; i < 9; ++i) {
      for (int j = 0; j < 9; ++j) {
        board[i][j] = 1;
      }
    }
  }

  public static boolean valid(int row, int column, int number, int[][] board) {
    for (int i = 0; i < 9; ++i) {
      if (i == row) {
        continue;
      }
      if (board[row][i] == number) {
        return false;
      }
    }
    for (int j = 0; j < 9; ++j) {
      if (j == column) {
        continue;
      }
      if (board[j][column] == number) {
        return false;
      }
    }
    int newRow = row - (row % 3);
    int newColumn = column - (column % 3);
    for (int i = 0; i < 3; ++i, ++newRow) {
      for (int j = 0; j < 3; ++j, ++newColumn) {
        if (newRow == row && newColumn == column) {
          continue;
        }
        if (board[newRow][newColumn] == number) {
          return false;
        }
      }
      newColumn -= 3;
    }
    return true;
  }

  // Main function runs the game.
  public static void main(String[] args) {
    int[][] sudokuBoard = new int[9][9];
    fillBoardWithOnes(sudokuBoard);
    boolean valid = valid(0, 0, 2, sudokuBoard);
    printBoard(sudokuBoard);
  }
}
