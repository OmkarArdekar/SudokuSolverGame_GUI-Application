import tkinter as tk
from tkinter import ttk
from tkinter import messagebox

# Sudoku of 9 X 9
sudoku = [[0 for _ in range(9)] for _ in range(9)]
sudoku_board = [[0 for _ in range(9)] for _ in range(9)]

# method to check is Sudoku solution is possible or not
def is_solve(row, col, digit):
    count = 0
    for i in range(9):
        if sudoku[i][col] == digit:
            count += 1
        if count > 1:
            return False

    count = 0
    for j in range(9):
        if sudoku[row][j] == digit:
            count += 1
        if count > 1:
            return False

    sr = (row // 3) * 3
    sc = (col // 3) * 3

    count = 0
    for i in range(sr, sr + 3):
        for j in range(sc, sc + 3):
            if sudoku[i][j] == digit:
                count += 1
            if count > 1:
                return False
            
    return True

# function to Solve the Sudoku
def is_safe(row, col, digit):
    for i in range(9):
        if sudoku[i][col] == digit:
            return False

    for j in range(9):
        if sudoku[row][j] == digit:
            return False

    sr = (row // 3) * 3
    sc = (col // 3) * 3

    for i in range(sr, sr + 3):
        for j in range(sc, sc + 3):
            if sudoku[i][j] == digit:
                return False

    return True


# function to Solve the Sudoku
def sudoku_solver(row, col):
    if row == 9:
        print_sudoku()
        return True

    next_row, next_col = row, col + 1

    if col + 1 == 9:
        next_row = row + 1
        next_col = 0

    if sudoku[row][col] != 0:
        return sudoku_solver(next_row, next_col)

    for digit in range(1, 10):
        if is_safe(row, col, digit):
            sudoku[row][col] = digit
            if sudoku_solver(next_row, next_col):
                return True
            sudoku[row][col] = 0

    return False


# method to print sudoku in terminal
def print_sudoku():
    print("\n======================================>SUDOKU SOLUTION<======================================\n")
    for i in range(9):
        print("--------------------------------------")
        print("| ", end="")
        for j in range(9):
            print(sudoku[i][j], "|", end=" ")
        print()
    print("--------------------------------------")


# method to display message in the terminal
def print_message(msg):
    print(f"\n----> {msg} <----\n")


# method to copy input sudoku
def to_copy():
    for i in range(9):
        for j in range(9):
            sudoku_board[i][j] = sudoku[i][j]


def on_submit():
    for i in range(9):
        for j in range(9):
            input_val = sudoku_cells[i][j].get()
            if not input_val:
                sudoku[i][j] = 0
            else:
                try:
                    value = int(input_val)
                    if 1 <= value <= 9:
                        sudoku[i][j] = value
                    else:
                        messagebox.showerror("Error", "INVALID INPUT !!. ENTER A NUMBERS BETWEEN 1 TO 9.")
                        return
                except ValueError:
                    messagebox.showerror("Error", "INVALID INPUT !!. ENTER A NUMBERS BETWEEN 1 TO 9.")
                    return

    to_copy()

    # checking is Sudoku solution is possible
    canSolve = True
    for i in range(9):
        for j in range(9):
            val = sudoku[i][j]
            if val != 0:
                canSolve = is_solve(i, j, val)            
                if canSolve == False:
                    messagebox.showerror("Error", "YOU HAVE ENTER THE INVALID SUDOKU !!")
                    print_message("YOU HAVE ENTER THE INVALID SUDOKU !!")
                    return



    solution = sudoku_solver(0, 0)

    title_label.configure(text="S U D O K U   S O L U T I O N")

    # to print the solution of the sudoku
    for i in range(9):
        for j in range(9):
            value = sudoku[i][j]

            if sudoku_board[i][j] == sudoku[i][j] and sudoku_board[i][j] != 0:
                sudoku_cells[i][j].configure(bg="black", highlightbackground="yellow", highlightthickness=5, fg="blue", font=("SansSerif", "32", "bold italic"))
                sudoku_cells[i][j].delete(0, tk.END)
                sudoku_cells[i][j].insert(tk.END, value)
            else:
                sudoku_cells[i][j].configure(bg="black", highlightbackground="yellow", highlightthickness=5, fg="red", font=("SansSerif", "32", "bold italic"))
                sudoku_cells[i][j].delete(0, tk.END)
                sudoku_cells[i][j].insert(tk.END, value)

# method to clear values
def on_clear():
    for i in range(9):
        for j in range(9):
            sudoku_cells[i][j].delete(0, tk.END)
            sudoku_cells[i][j].configure(bg="yellow", highlightbackground="black", highlightthickness=2, fg="black", font=("SansSerif", 25, "bold"))
            
    title_label.configure(text="E N T E R     S U D O K U")



root = tk.Tk()
root.geometry("693x760")
root.maxsize(693, 760)
root.minsize(693, 760)
root.configure(bg="silver")
root.title("S U D O K U   B O A R D")
title_label = tk.Label(root, text="E N T E R     S U D O K U", font=('SansSerif', 10, "bold"), bg="gold", 
                       highlightthickness=3, highlightbackground="black",bd=2, padx=100)
title_label.pack()


panel = tk.Frame(root, bg="black", bd=5)
panel.pack(fill=tk.BOTH, expand=True)

sudoku_cells = [[0 for _ in range(9)] for _ in range(9)]

cell_size = 685 // 9

for i in range(9):
    for j in range(9):
        sudoku_cells[i][j] = tk.Entry(panel, width=5, bg="yellow", fg="black", font=("SansSerif", 25, "bold"),
                                bd=5, relief=tk.SOLID, justify=tk.CENTER, highlightthickness=2, highlightbackground="black", highlightcolor="white")
        sudoku_cells[i][j].place(x=j * cell_size, y=i * cell_size, width=cell_size, height=cell_size)
        sudoku_cells[i][j].insert(tk.END, "")

submit_button = tk.Button(root, text="S  U  B  M  I  T", command=on_submit, bg="gold", fg="navy",
                          bd=5, highlightbackground="silver", font=('Times New Roman', 10, 'bold'))
clear_button = tk.Button(root, text="C L E A R   A L L", command=on_clear, bg="white", fg="black",
                          bd=5, highlightbackground="silver", font=('Times New Roman', 10, 'bold'))


buttom_label = tk.Label(root, bg="silver", pady=10) 
buttom_label.pack()
submit_button.place(x=240, y=725)
clear_button.place(x=360, y=725)

root.mainloop()