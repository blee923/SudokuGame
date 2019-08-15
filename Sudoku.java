import java.util.Random;

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

  // This function checks if the number is valid in the row, column, and box.
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

  /* This function generates a valid sudoku board.
  1 2 3 | 4 5 6 | 7 8 9
  4 5 6 | 7 8 9 | 1 2 3
  7 8 9 | 1 2 3 | 4 5 6
  ---------------------
  8 9 1 | 2 3 4 | 5 6 7
  2 3 4 | 5 6 7 | 8 9 1
  5 6 7 | 8 9 1 | 2 3 4
  ---------------------
  6 7 8 | 9 1 2 | 3 4 5
  9 1 2 | 3 4 5 | 6 7 8
  3 4 5 | 6 7 8 | 9 1 2
  */
  public static void generateBoard(int[][] board) {
    int number = 1, row = 0, column = 0;
    while (board[8][8] == 0) {
      if (valid(row, column, number, board)) {
        board[row][column] = number;
        ++column;
        if (column == 9) {
          ++row;
          column = 0;
        }
      }
      if (++number == 10) {
        number = 0;
      }
    }
  }

  public static void moveRows(int[][] board) {
    Random rand = new Random();
    int first = rand.nextInt(9);
    int second = first + 1;
    if (second == 3 || second == 6 || second == 9) {
      second -= 2;
    }
    int[] temp = board[first];
    board[first] = board[second];
    board[second] = temp;
  }

  public static void moveColumns(int[][] board) {
    Random rand = new Random();
    int first = rand.nextInt(9);
    int second = first + 1;
    if (second == 3 || second == 6 || second == 9) {
      second -= 2;
    }
    for (int i = 0; i < 9; ++i) {
      int temp = board[i][first];
      board[i][first] = board[i][second];
      board[i][second] = temp;
    }
  }

  public static void moveRowsChunks(int[][] board) {
    Random rand = new Random();
    int first = rand.nextInt(3);
    int second = rand.nextInt(3);
    while (first == second) {
      second = rand.nextInt(3);
    }
    first *= 3;
    second *= 3;
    for (int i = 0; i < 3; ++i, ++first, ++second) {
      int[] temp = board[first];
      board[first] = board[second];
      board[second] = temp;
    }
  }

  public static void moveColumnsChunks(int[][] board) {
    Random rand = new Random();
    int first = rand.nextInt(3);
    int second = rand.nextInt(3);
    while (first == second) {
      second = rand.nextInt(3);
    }
    first *= 3;
    second *= 3;
    for (int i = 0; i < 3; ++i, ++first, ++second) {
      for (int j = 0; j < 9; ++j) {
        int temp = board[j][first];
        board[j][first] = board[j][second];
        board[j][second] = temp;
      }
    }
  }

  public static int[][] transpose(int[][] board) {
    int[][] temp = new int[9][9];
    for (int i = 0; i < 9; ++i) {
      for (int j = 0; j < 9; ++j) {
        temp[i][j] = board[j][i];
      }
    }
    return temp;
  }

  public static void shifting(int[][] board) {
    Random rand = new Random();
    int counter = rand.nextInt(101);
    while (counter > 0) {
      int chooser = rand.nextInt(5) + 1;
      if (chooser == 1) {
        moveRows(board);
      } else if (chooser == 2) {
        moveColumns(board);
      } else if (chooser == 3) {
        moveRowsChunks(board);
      } else if (chooser == 4) {
        moveColumnsChunks(board);
      } else {
        board = transpose(board);
      }
      counter -= chooser;
    }
  }

  // Main function runs the game.
  public static void main(String[] args) {
    int[][] sudokuBoard = new int[9][9];
    generateBoard(sudokuBoard);
    shifting(sudokuBoard);
    // moveRowsChunks(sudokuBoard);
    // sudokuBoard = transpose(sudokuBoard);
    printBoard(sudokuBoard);
  }
}
