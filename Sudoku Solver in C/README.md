# Sudoku Solver in C
This small project is a Sudoku Solver in C. The algorithm I created uses backtracking in order to find the correct solution.
<br>
Quite a lot of effort also went in making the output easily readable by using some pretty printer functions.
<br>
The program also measures the execution time and the amount of recursive calls that has been done in the execution.
<br>
The Sudoku table is currently hard-coded, but the program can be modified quite easily so that the Sudoku table can be read from a file. The size of the table is also hard-coded and parametirized. In the commented code there are a 25x25 Sudoku table (takes more than 3-4 hours to solve) and a 9x9 classic Sudoku Table (takes less then a second to solve), while the uncommented table is 16x16. 
<br>
