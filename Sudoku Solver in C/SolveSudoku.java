/*
Created by:                                                                  
                                                                                      
   SSSSSSSSSSSSSSS   iiii                                                             
 SS:::::::::::::::S i::::i                                                            
S:::::SSSSSS::::::S  iiii                                                             
S:::::S     SSSSSSS                                                                   
S:::::S            iiiiiii    mmmmmmm    mmmmmmm      ooooooooooo xxxxxxx      xxxxxxx
S:::::S            i:::::i  mm:::::::m  m:::::::mm  oo:::::::::::oox:::::x    x:::::x 
 S::::SSSS          i::::i m::::::::::mm::::::::::mo:::::::::::::::ox:::::x  x:::::x  
  SS::::::SSSSS     i::::i m::::::::::::::::::::::mo:::::ooooo:::::o x:::::xx:::::x   
    SSS::::::::SS   i::::i m:::::mmm::::::mmm:::::mo::::o     o::::o  x::::::::::x    
       SSSSSS::::S  i::::i m::::m   m::::m   m::::mo::::o     o::::o   x::::::::x     
            S:::::S i::::i m::::m   m::::m   m::::mo::::o     o::::o   x::::::::x     
            S:::::S i::::i m::::m   m::::m   m::::mo::::o     o::::o  x::::::::::x    
SSSSSSS     S:::::Si::::::im::::m   m::::m   m::::mo:::::ooooo:::::o x:::::xx:::::x   
S::::::SSSSSS:::::Si::::::im::::m   m::::m   m::::mo:::::::::::::::ox:::::x  x:::::x  
S:::::::::::::::SS i::::::im::::m   m::::m   m::::m oo:::::::::::oox:::::x    x:::::x 
 SSSSSSSSSSSSSSS   iiiiiiiimmmmmm   mmmmmm   mmmmmm   ooooooooooo xxxxxxx      xxxxxxx

This program is a sudoku solver. Insert the sudoku puzzle in the matrix below. 
When I'll have time I'll add:
- An option to read the matrix from file.

ASCII Art generated from: https://patorjk.com/software/taag/#p=display&f=Doh&t=Simox

*/

class SolveSudoku {

  public static final int MAX_LENGTH = 16;
  public static final int SQUARE_SIZE = 4;
  public static int cont = 0;
  public static long time_spent = 0;
  public static int curr_column;
  public static int curr_row;
  public static final String ANSI_COLOR_RESET = "\u001B[0m";
  public static final String ANSI_COLOR_RED = "\u001B[31m";
  public static final String ANSI_COLOR_YELLOW = "\u001B[33m";
  public static final String ANSI_COLOR_BLUE = "\u001B[34m";


  //checks if num can be put in the given cell of coordinates x, y 
  public static boolean is_cell_ok(int table[][], int x, int y, int num) {
    int squarex = x - (x % SQUARE_SIZE);
    int squarey = y - (y % SQUARE_SIZE);
    return (is_row_ok(table, x, num) && is_column_ok(table, y, num) && is_square_ok(table, squarex, squarey, num));
  }

  //checks if num can be put in the given row 
  public static boolean is_row_ok(int table[][], int row, int num) {
    for (int i = 0; i < MAX_LENGTH; i++) {
      if (table[row][i] == num) return false;
    }
    return true;
  }

  //checks if num can be put in the given column
  public static boolean is_column_ok(int table[][], int column, int num) {
    for (int i = 0; i < MAX_LENGTH; i++) {
      if (table[i][column] == num) return false;
    }
    return true;
  }

  //checks if the num can be put in the given square ((squarex;squarey) to (squarex+2;squarey+2))
  public static boolean is_square_ok(int table[][], int squarex, int squarey, int num) {
    for (int i = 0; i < SQUARE_SIZE; i++) {
      for (int j = 0; j < SQUARE_SIZE; j++) {
        if (table[squarex + i][squarey + j] == num) return false;
      }
    }
    return true;
  }

  //checks if there is a not valued cell and returns its coordinates through pointers
  public static boolean find_not_valued_cell(int table[][]) {
    for (curr_row = 0; curr_row < MAX_LENGTH; curr_row++) {
      for (curr_column = 0; curr_column < MAX_LENGTH; curr_column++) {
        if (table[curr_row][curr_column] == 0) return true;
      }
    }
    return false;
  }

  //recursive function used to apply the backtracking and solving the puzzle
  public static boolean is_solved(int table[][]) {
    cont++;
    //System.out.println(cont);

    //if there is no cell with a 0, then the puzzle is solved
    if (!find_not_valued_cell(table)) {
      return true;
    } 

    int row = curr_row;
    int col = curr_column;

    //recursion here; gives the value to the unvalued cell, then calls itself 
    //which finds the next not valued cell and gives it a value, exc... 
    for (int num = 1; num < MAX_LENGTH+1; num++) {
      if (is_cell_ok(table, row, col, num)) {
        //cell in row and col appears to be ok for num 
        table[row][col] = num;
        //if it returns true, break the recursion (the true is given from the 
        //previous lines of code, in which no unvalued cell is found)
        if (is_solved(table)) return true;
      }
      //the cell is not ok for the number, resetting to 0. With the use of 
      //pointers this makes the backtracking reset the previous attemps of 
      //giving values to a cell aswell.
      table[row][col] = 0;
    }
    //only reached if exhausted and sudoku isn't solved
    return false;
  }

  public static void basic_printer(int table[][]) {
    for (int i = 0; i < MAX_LENGTH; i++) {
      for (int j = 0; j < MAX_LENGTH; j++) {
        System.out.print(table[i][j] + " ");
      }
      System.out.println();
    }
    return;
  }

  public static void pretty_printer_before(int table[][]) {
    int spaces_needed = Integer.toString(MAX_LENGTH).length();
    for (int x = 0; x < MAX_LENGTH; x++) {
      for (int y = 0; y < MAX_LENGTH; y++) {
        if (table[x][y] == 0) {
          System.out.print(ANSI_COLOR_RED + "?" + ANSI_COLOR_RESET);
        } else {
          System.out.print(ANSI_COLOR_YELLOW + table[x][y] + ANSI_COLOR_RESET);
        }
        for (int k = 0; k < (spaces_needed - Integer.toString(table[x][y]).length()); k++) {
          System.out.print(" ");
        }
        if (y != MAX_LENGTH-1) System.out.print(" | ");
      }
      System.out.println();
      if (x != MAX_LENGTH-1) {
        for (int k = 0; k < MAX_LENGTH-1; k++) {
          if ((k + 1) % SQUARE_SIZE == 0) {
            for (int h = 0; h < spaces_needed; h++) {
              System.out.print("-");
            }
            System.out.print(" ║ ");  
          } else if ((x + 1) % SQUARE_SIZE == 0 && x != MAX_LENGTH-1) {
            for (int h = 0; h < spaces_needed; h++) {
              System.out.print("-");
            }
            System.out.print(" = "); 
          } else {
            for (int h = 0; h < spaces_needed; h++) {
              System.out.print("-");
            }
            System.out.print("   ");
          }
        } 
        for (int h = 0; h < spaces_needed; h++) System.out.print("-");
        System.out.println();
      }
    }
  }

  public static void pretty_printer_after(int table_before[][], int table_after[][]) {
    int spaces_needed = Integer.toString(MAX_LENGTH).length();
    for (int x = 0; x < MAX_LENGTH; x++) {
      for (int y = 0; y < MAX_LENGTH; y++) {
        if (table_before[x][y] == table_after[x][y]) {
          System.out.print(ANSI_COLOR_YELLOW + table_after[x][y] + ANSI_COLOR_RESET);
        } else if (table_before[x][y] != table_after[x][y]) {
          System.out.print(ANSI_COLOR_BLUE + table_after[x][y] + ANSI_COLOR_RESET);
        } else { //should never go in the else branch
          System.out.print(ANSI_COLOR_RED + "?" + ANSI_COLOR_RESET);
        }
        for (int k = 0; k < (spaces_needed - Integer.toString(table_after[x][y]).length()); k++) {
          System.out.print(" ");
        }
        if (y != MAX_LENGTH-1) System.out.print(" | ");
      }
      System.out.println();
      if (x != MAX_LENGTH-1) {
        for (int k = 0; k < MAX_LENGTH-1; k++) {
          if ((k + 1) % SQUARE_SIZE == 0) {
            for (int h = 0; h < spaces_needed; h++) {
              System.out.print("-");
            }
            System.out.print(" ║ ");  
          } else if ((x + 1) % SQUARE_SIZE == 0 && x != MAX_LENGTH-1) {
            for (int h = 0; h < spaces_needed; h++) {
              System.out.print("-");
            }
            System.out.print(" = "); 
          } else {
            for (int h = 0; h < spaces_needed; h++) {
              System.out.print("-");
            }
            System.out.print("   ");
          }
        } 
        for (int h = 0; h < spaces_needed; h++) System.out.print("-");
        System.out.println();
      }
    }
  }

  public static void main(String[] args) {

    //850
    //chcp 65001 in order not to have issues in windows

    
    /*
    //i do not suggest to run this, it takes up to hours to finish the computation
    //(even on good machines).
    //5x5
    int table[][] = {
      {16, 23, 7, 0, 0, 24, 0, 4, 0, 0, 0, 10, 0, 0, 0, 1, 0, 18, 0, 0, 8, 21, 14, 0, 17},
      {0, 0, 20, 0, 0, 19, 15, 16, 0, 0, 0, 0, 0, 5, 24, 4, 0, 2, 14, 23, 0, 0, 18, 0, 7},
      {9, 2, 12, 0, 0, 0, 0, 0, 20, 11, 13, 0, 0, 7, 0, 0, 0, 0, 0, 6, 0, 0, 10, 25, 1},
      {4, 0, 0, 0, 19, 0, 0, 0, 14, 0, 8, 0, 0, 23, 21, 10, 0, 9, 7, 17, 0, 0, 0, 0, 0},
      {18, 0, 0, 0, 0, 0, 1, 17, 10, 0, 11, 15, 19, 0, 0, 12, 0, 20, 0, 0, 0, 13, 0, 0, 0},
      {0, 7, 1, 3, 0, 0, 12, 0, 0, 0, 0, 0, 16, 0, 0, 8, 20, 11, 0, 0, 0, 0, 0, 9, 21},
      {0, 6, 0, 10, 0, 0, 2, 21, 18, 0, 12, 19, 23, 0, 0, 0, 0, 0, 24, 16, 1, 0, 0, 14, 0},
      {8, 20, 0, 18, 16, 11, 0, 0, 24, 0, 9, 0, 0, 0, 3, 0, 0, 0, 22, 0, 12, 0, 0, 10, 4},
      {0, 0, 0, 0, 0, 1, 0, 0, 9, 22, 4, 0, 0, 0, 0, 0, 17, 23, 2, 0, 24, 8, 13, 0, 0},
      {15, 21, 0, 17, 9, 8, 0, 0, 0, 0, 0, 18, 7, 2, 0, 0, 1, 0, 0, 0, 0, 0, 19, 0, 0},
      {0, 4, 0, 16, 0, 0, 0, 14, 0, 0, 0, 22, 0, 10, 0, 0, 11, 17, 8, 0, 21, 24, 9, 0, 0},
      {0, 10, 11, 22, 0, 0, 0, 0, 0, 21, 24, 3, 0, 17, 1, 7, 0, 0, 18, 0, 5, 0, 0, 0, 14},
      {0, 0, 0, 0, 17, 10, 4, 0, 0, 20, 0, 0, 0, 0, 0, 25, 0, 0, 9, 5, 16, 0, 0, 0, 0},
      {25, 0, 0, 0, 6, 0, 16, 0, 0, 19, 14, 13, 0, 8, 9, 23, 0, 0, 0, 0, 0, 12, 4, 18, 0},
      {0, 0, 23, 21, 20, 0, 7, 18, 13, 0, 0, 4, 0, 6, 0, 0, 0, 3, 0, 0, 0, 17, 0, 19, 0},
      {0, 0, 10, 0, 0, 0, 0, 0, 17, 0, 0, 7, 14, 12, 0, 0, 0, 0, 0, 4, 25, 16, 0, 22, 19},
      {0, 0, 14, 11, 13, 0, 10, 19, 12, 0, 0, 0, 0, 0, 16, 18, 15, 0, 0, 7, 0, 0, 0, 0, 0},
      {5, 16, 0, 0, 24, 0, 14, 0, 0, 0, 17, 0, 0, 0, 11, 0, 19, 0, 0, 1, 6, 10, 0, 4, 18},
      {0, 18, 0, 0, 3, 21, 11, 0, 0, 0, 0, 0, 6, 13, 22, 0, 25, 24, 10, 0, 0, 5, 0, 23, 0},
      {12, 19, 0, 0, 0, 0, 0, 8, 2, 23, 0, 0, 9, 0, 0, 0, 0, 6, 0, 0, 7, 15, 11, 0},
      {0, 0, 0, 9, 0, 0, 0, 12, 0, 7, 0, 0, 10, 24, 14, 0, 5, 19, 1, 0, 0, 0, 0, 0, 13},
      {0, 0, 0, 0, 0, 22, 23, 24, 0, 14, 21, 12, 0, 0, 17, 0, 9, 0, 0, 0, 10, 0, 0, 0, 3},
      {23, 25, 18, 0, 0, 4, 0, 0, 0, 0, 0, 9, 0, 0, 20, 6, 24, 0, 0, 0, 0, 0, 12, 1, 16},
      {14, 0, 19, 0, 0, 15, 3, 1, 0, 9, 7, 5, 0, 0, 0, 0, 0, 8, 11, 12, 0, 0, 17, 0, 0},
      {11, 0, 16, 5, 1, 0, 0, 13, 0, 8, 0, 0, 0, 25, 0, 0, 0, 10, 0, 14, 0, 0, 24, 2, 23}
    };
    */

    //this specific table makes 14579935 recursive calls. 
    //takes around 12 seconds on a Ryzen 5 3600 machine to finish.
    //takes around 27 seconds on a Intel Core Duo machine to finish.
    //4x4
    int table[][] = {
      {0, 15, 0, 1, 0, 2, 10, 14, 12, 0, 0, 0, 0, 0, 0, 0},
      {0, 6, 3, 16, 12, 0, 8, 4, 14, 15, 1, 0, 2, 0, 0, 0},
      {14, 0, 9, 7, 11, 3, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0},
      {4, 13, 2, 12, 0, 0, 0, 0, 6, 0, 0, 0, 0, 15, 0, 0},
      {0, 0, 0, 0, 14, 1, 11, 7, 3, 5, 10, 0, 0, 8, 0, 12},
      {3, 16, 0, 0, 2, 4, 0, 0, 0, 14, 7, 13, 0, 0, 5, 15},
      {11, 0, 5, 0, 0, 0, 0, 0, 0, 9, 4, 0, 0, 6, 0, 0},
      {0, 0, 0, 0, 13, 0, 16, 5, 15, 0, 0, 12, 0, 0, 0, 0},
      {0, 0, 0, 0, 9, 0, 1, 12, 0, 8, 3, 10, 11, 0, 15, 0},
      {2, 12, 0, 11, 0, 0, 14, 3, 5, 4, 0, 0, 0, 0, 9, 0},
      {6, 3, 0, 4, 0, 0, 13, 0, 0, 11, 9, 1, 0, 12, 16, 2},
      {0, 0, 10, 9, 0, 0, 0, 0, 0, 0, 12, 0, 8, 0, 6, 7},
      {12, 8, 0, 0, 16, 0, 0, 10, 0, 13, 0, 0, 0, 5, 0, 0},
      {5, 0, 0, 0, 3, 0, 4, 6, 0, 1, 15, 0, 0, 0, 0, 0},
      {0, 9, 1, 6, 0, 14, 0, 11, 0, 0, 2, 0, 0, 0, 10, 8},
      {0, 14, 0, 0, 0, 13, 9, 0, 4, 12, 11, 8, 0, 0, 2, 0}
    };

    /*
    //this specific table makes 119595 recursive calls.
    //takes around 0.0352 seconds on a Ryzen 5 3600 machine.
    //takes around 0.1121 seconds on a Intel Core Duo machine.
    //3x3
    int table[][] = {
      { 8, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 5, 7, 0, 0 },
      { 0, 0, 0, 0, 3, 0, 0, 9, 1 },
      { 5, 0, 0, 0, 0, 3, 0, 0, 2 },
      { 0, 3, 0, 6, 0, 8, 0, 0, 0 },
      { 0, 0, 0, 0, 9, 0, 0, 0, 8 },
      { 0, 2, 7, 5, 0, 0, 9, 0, 0 },
      { 0, 1, 0, 0, 0, 4, 0, 0, 0 },
      { 0, 0, 3, 1, 0, 0, 0, 5, 0 }
    };
    */

    int table_copy[][] = new int[MAX_LENGTH][MAX_LENGTH];

    for (int i = 0; i < MAX_LENGTH; i++) {
      for (int j = 0; j < MAX_LENGTH; j++) {
        table_copy[i][j] = table[i][j];
      }
    }
    
    System.out.println("Trying to solve this table: ");
    pretty_printer_before(table_copy);
    System.out.println("Computing the solution...\n");

    long start = System.currentTimeMillis();

    if (is_solved(table)) {
      long end = System.currentTimeMillis();
      time_spent = end - start;
      System.out.println("Found solution!\n");
      pretty_printer_after(table_copy, table);
    } else {
      long end = System.currentTimeMillis();
      time_spent = end - start;
      System.out.println("There is no solution for the table.\n");
    }
    System.out.println("The program made " + cont + " attempts to find values for the table and ran for " + time_spent/1000.0 + " seconds");

    //basic_printer(table_copy);
    //basic_printer(table);
  }
}

