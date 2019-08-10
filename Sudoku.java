public class Sudoku {
  public static void printTest(int[][] temp) {
    for (int i = 0; i < 9; ++i) {
      String tempString = "";
      for (int j = 0; j < 9; ++j) {
        tempString += " " + temp[i][j];
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
  public static int[][] createBoard() {
    return new int[9][9];
  }
  public static void main(String[] args) {
    int[][] sudokuBoard = createBoard();
    for (int i = 0; i < 9; ++i) {
      for (int j = 0; j < 9; ++j) {
        sudokuBoard[i][j] = 1;
      }
    }
    printTest(sudokuBoard);
  }
}
