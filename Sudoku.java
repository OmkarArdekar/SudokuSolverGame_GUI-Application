import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Sudoku {
    // JAVA PROJECT - SUDOKU SOLVER
    // Sudoku of 9 X 9
    public static int[][] sudoku = new int[9][9];
    public static int[][] sudokuBoard = new int[9][9];

    // function to Solve the Sudoku
    public static boolean isSafe(int row, int col, int digit) {
        for(int i = 0; i < 9; i++) {
            if(sudoku[i][col] == digit) {
                return false;
            }
        }

        for(int j = 0; j < 9; j++) {
            if(sudoku[row][j] == digit) {
                return false;
            }
        }

        int sr = ((row / 3) * 3);
        int sc = ((col / 3) * 3);

        for(int i = sr; i < (sr+3); i++) {
            for(int j = sc; j < (sc+3); j++) {
                if(sudoku[i][j] == digit) {
                    return false;
                }
            }
        }

        return true;
    }

    // function to Solve the Sudoku
    public static boolean sudokuSolver(int row, int col) {
        if(row == 9) {
            printSudoku();
            return true;
        }

        int nextRow = row, nextCol = col+1;

        if(col+1 == 9) {
            nextRow = row + 1;
            nextCol = 0;
        }

        if(sudoku[row][col] != 0) {
            return sudokuSolver(nextRow, nextCol);
        }

        for(int digit = 1; digit <= 9; digit++) {
            if(isSafe(row, col, digit)) {
                sudoku[row][col] = digit;
                if(sudokuSolver(nextRow, nextCol)) {
                    return true;
                }
                sudoku[row][col] = 0;
            }
        }

        return false;
    }

    // method to print sudoku in terminal
    public static void printSudoku() {
        System.out.println("\n======================================>SUDOKU SOLUTION<======================================\n");
        for(int i = 0; i < 9; i++) {
            System.out.println("--------------------------------------");
            System.out.print("| ");
            for(int j = 0; j < 9; j++) {
                System.out.print(" " + sudoku[i][j] + " |");
            }
            System.out.println();
        }
        System.out.println("--------------------------------------");
    }    

    // to display message in the terminal
    public static void printMessage(String msg) {
        System.out.println("\n----> " + msg + " <----\n");
    }

    // to copy input sudoku
    public static void toCopy() {
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                sudokuBoard[i][j] = sudoku[i][j];
            }
        }
    }

    // to check given sudoku can be solve or not
    public static boolean check(int row, int col, int digit) {
        int count = 0;
        for(int i = 0; i < 9; i++) {
            if(sudoku[i][col] == digit) {
                count++;
            }

            if(count > 1) {
                return false;
            }
        }

        count = 0;
        for(int j = 0; j < 9; j++) {
            if(sudoku[row][j] == digit) {
                count++;
            }

            if(count > 1) {
                return false;
            }
        }

        int sr = (row / 3) * 3;
        int sc = (col / 3) * 3;

        count = 0;
        for(int i = sr; i < sr+3; i++) {
            for(int j = sc; j < sc+3; j++) {
                if(sudoku[i][j] == digit) {
                    count++;
                }

                if(count > 1) {
                    return false;
                }
            }
        }

        return true;
    }
    // to check the given sudoku is valid or not
    public static boolean isPossible() {
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                int val = sudoku[i][j];
                if(val != 0) {
                    if(!check(i, j, val)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public static void main(String args[]) {
        JFrame frame = new JFrame("SUDOKU BOARD");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700,700);

        JPanel panel = new JPanel(new GridLayout(9, 9));
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

        JTextField[][] sudokuCells = new JTextField[9][9];

        Font boldFont = new Font("SansSerif", Font.BOLD, 25);

        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                sudokuCells[i][j] = new JTextField(2);
                sudokuCells[i][j].setBackground(Color.BLACK);
                sudokuCells[i][j].setForeground(Color.WHITE);
                sudokuCells[i][j].setFont(boldFont);
                sudokuCells[i][j].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
                sudokuCells[i][j].setHorizontalAlignment(JTextField.CENTER);
                panel.add(sudokuCells[i][j]);
            }
        }

        JButton submitButton = new JButton("S U B M I T");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i < 9; i++) {
                    for(int j = 0; j < 9; j++) {
                        String input = sudokuCells[i][j].getText();
                        if(input.isEmpty()) {
                            sudoku[i][j] = 0;
                        } else {
                            int value = Integer.parseInt(input);
                            if(value >= 1 && value <= 9) {
                                sudoku[i][j] = value;
                            } else {
                                JOptionPane.showMessageDialog(frame, "INVALID INPUT !!. ENTER A NUMBERS BETWEEN 1 TO 9.");
                                return;
                            }
                        }
                    }
                }

                toCopy();

                if(isPossible() == false) {
                    JOptionPane.showMessageDialog(frame, "YOU HAVE ENTER THE INVALID SUDOKU !!");
                    printMessage("YOU HAVE ENTER THE INVALID SUDOKU !!");
                    return;
                }

                boolean solution = sudokuSolver(0, 0);
                // to print the solution of the sudoku                
                for(int i = 0; i < 9; i++) {
                    for(int j = 0; j < 9; j++) {
                        Integer value = sudoku[i][j];
                        
                        if((sudokuBoard[i][j] == sudoku[i][j]) && (sudokuBoard[i][j] != 0)) {
                            sudokuCells[i][j].setBackground(Color.BLACK);
                            sudokuCells[i][j].setForeground(Color.BLUE);
                            sudokuCells[i][j].setFont(new Font("SansSerif", Font.BOLD + Font.ITALIC, 32));
                            sudokuCells[i][j].setBorder(new MatteBorder(5, 5, 5, 5, Color.YELLOW));
                            String str = value.toString();
                            sudokuCells[i][j].setText(str);
                        } else {
                            sudokuCells[i][j].setBackground(Color.BLACK);
                            sudokuCells[i][j].setForeground(Color.RED);
                            sudokuCells[i][j].setFont(new Font("SansSerif", Font.BOLD + Font.ITALIC, 32));
                            sudokuCells[i][j].setBorder(new MatteBorder(5, 5, 5, 5, Color.YELLOW));
                            String str = value.toString();
                            sudokuCells[i][j].setText(str);
                        }
                    }
                }
            }
        });

        frame.add(panel, BorderLayout.CENTER);
        frame.add(submitButton, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
