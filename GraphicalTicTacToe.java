import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphicalTicTacToe extends JFrame {
    private static final char EMPTY = ' ';
    private static final char PLAYER_X = 'X';
    private static final char PLAYER_O = 'O';
    private char[][] board;
    private char currentPlayer;
    private JButton[][] buttons;

    public GraphicalTicTacToe() {
        board = new char[3][3];
        buttons = new JButton[3][3];
        currentPlayer = PLAYER_X;
        initializeBoard();
        setTitle("Tic Tac Toe");
        setSize(300, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 3));
        initializeButtons();
    }

    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    private void initializeButtons() {
        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                int row = (int) button.getClientProperty("row");
                int col = (int) button.getClientProperty("col");
                if (makeMove(row, col)) {
                    button.setText(String.valueOf(currentPlayer));
                    if (checkWin()) {
                        JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins!");
                        resetBoard();
                    } else if (isBoardFull()) {
                        JOptionPane.showMessageDialog(null, "The game is a draw!");
                        resetBoard();
                    } else {
                        switchPlayer();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "This move is not valid");
                }
            }
        };

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 60));
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].putClientProperty("row", i);
                buttons[i][j].putClientProperty("col", j);
                buttons[i][j].addActionListener(buttonListener);
                add(buttons[i][j]);
            }
        }
    }

    private boolean makeMove(int row, int col) {
        if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == EMPTY) {
            board[row][col] = currentPlayer;
            return true;
        }
        return false;
    }

    private boolean checkWin() {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) ||
                (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer)) {
                return true;
            }
        }
        // Check diagonals
        if ((board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) ||
            (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer)) {
            return true;
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == PLAYER_X) ? PLAYER_O : PLAYER_X;
    }

    private void resetBoard() {
        initializeBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        currentPlayer = PLAYER_X;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GraphicalTicTacToe game = new GraphicalTicTacToe();
            game.setVisible(true);
        });
    }
}
