import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;

public class Sudoku {
  // This function prints the Sudoku board in text.
  public static void printBoard(int[][] board) {
    System.out.println("   1 2 3   4 5 6   7 8 9");
    System.out.println();
    for (int i = 0; i < 9; ++i) {
      String tempString = (i + 1) + " ";
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
    System.out.println();
  }

  // This function checks if the number is valid in the row, column, and box.
  public static boolean valid(int row, int column, int number, int[][] board) {
    for (int i = 0; i < 9; ++i) {
      if (i == row) {
        continue;
      }
      if (board[i][column] == number) {
        return false;
      }
    }
    for (int j = 0; j < 9; ++j) {
      if (j == column) {
        continue;
      }
      if (board[row][j] == number) {
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

  // This function generates a valid sudoku board.
  public static void generateBoard(int[][] board) {
    Random rand = new Random();
    int number = rand.nextInt(8) + 1, row = 0, column = 0;
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
    int counter = rand.nextInt(201);
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

  public static boolean solver(int[][] board) {
    for (int i = 0; i < 9; ++i) {
        for (int j = 0; j < 9; ++j) {
            if (board[i][j] == 0) {
                for (int k = 1; k <= 9; ++k) {
                    board[i][j] = k;
                    if (valid(i, j, k, board) && solver(board)) {
                        board[i][j] = 0;
                        return true;
                    }
                }
                board[i][j] = 0;
                return false;
            }
        }
    }
    return true;
  }

  // avg nums - easy: 45, med: 51, hard: 54
  public static void remover(int[][] board, boolean[][] change, ArrayList<Integer> changed) {
    Random rand = new Random();
    int counter = 40;
    while (counter > 0) {
      int temp = rand.nextInt(9);
      int temp2 = rand.nextInt(9);
      while (board[temp][temp2] == 0) {
        temp = rand.nextInt(9);
        temp2 = rand.nextInt(9);
      }
      board[temp][temp2] = 0;
      change[temp][temp2] = true;
      changed.add(((temp + 1) * 8) + temp2 + 1);
      --counter;
    }
  }

  public static void playGame() {
    Scanner scan = new Scanner(System.in);
    int[][] sudokuBoard = new int[9][9];
    ArrayList<Integer> changed = new ArrayList<Integer>();
    boolean[][] changeable = new boolean[9][9];
    generateBoard(sudokuBoard);
    shifting(sudokuBoard);
    printBoard(sudokuBoard);
    remover(sudokuBoard, changeable, changed);
    while (changed.size() > 0) {
      printBoard(sudokuBoard);
      System.out.println("Choose your row (1-9)");
      int row = scan.nextInt() - 1;
      System.out.println("Choose your column (1-9)");
      int column = scan.nextInt() - 1;
      if (changeable[row][column] == false) {
        System.out.println("That block was not empty, please try again.");
      } else {
        System.out.println("What would you like to put there?");
        int number = scan.nextInt();
        sudokuBoard[row][column] = number;
        int removal = (row + 1) * 8 + column + 1;
        removal = changed.indexOf(removal);
        changed.remove(removal);
      }
    }
    printBoard(sudokuBoard);
    boolean solved = true;
    for (int i = 0; i < 9; ++i) {
      for (int j = 0; j < 9; ++j) {
        int number = sudokuBoard[i][j];
        if (!valid(i, j, number, sudokuBoard)) {
          solved = false;
          break;
        }
      }
      if (!solved) {
        System.out.println("You lost!");
        break;
      } else {
        System.out.println("You win!");
        break;
      }
    }
  }

  // Main function runs the game.
  public static void main(String[] args) {
    playGame();
  }
}
