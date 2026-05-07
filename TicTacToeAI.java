import java.util.Scanner;

public class TicTacToeAI {

    static char[][] board = {
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '}
    };

    static final char HUMAN = 'X';
    static final char AI = 'O';

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=================================");
        System.out.println("        TIC TAC TOE AI");
        System.out.println("=================================");

        printBoard();

        while (true) {

            // Human Move
            int row, col;

            while (true) {

                System.out.print("Enter row (0-2): ");
                row = sc.nextInt();

                System.out.print("Enter col (0-2): ");
                col = sc.nextInt();

                if (isValidMove(row, col)) {
                    board[row][col] = HUMAN;
                    break;
                } else {
                    System.out.println("Invalid move! Try again.");
                }
            }

            printBoard();

            // Human wins
            if (checkWinner(HUMAN)) {
                System.out.println("🎉 You Win!");
                break;
            }

            // Draw
            if (isBoardFull()) {
                System.out.println(" Match Draw!");
                break;
            }

            // AI Move
            System.out.println("AI is thinking...");

            int[] bestMove = minimaxBestMove();
            board[bestMove[0]][bestMove[1]] = AI;

            printBoard();

            // AI wins
            if (checkWinner(AI)) {
                System.out.println("🤖 AI Wins!");
                break;
            }

            // Draw
            if (isBoardFull()) {
                System.out.println(" Match Draw!");
                break;
            }
        }

        sc.close();
    }

    // Print Board
    static void printBoard() {

        System.out.println();

        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 3; j++) {

                System.out.print(" " + board[i][j] + " ");

                if (j < 2)
                    System.out.print("|");
            }

            System.out.println();

            if (i < 2)
                System.out.println("-----------");
        }

        System.out.println();
    }

    // Check valid move
    static boolean isValidMove(int row, int col) {

        return row >= 0 &&
               row < 3 &&
               col >= 0 &&
               col < 3 &&
               board[row][col] == ' ';
    }

    // Check winner
    static boolean checkWinner(char player) {

        // Rows
        for (int i = 0; i < 3; i++) {

            if (board[i][0] == player &&
                board[i][1] == player &&
                board[i][2] == player)

                return true;
        }

        // Columns
        for (int i = 0; i < 3; i++) {

            if (board[0][i] == player &&
                board[1][i] == player &&
                board[2][i] == player)

                return true;
        }

        // Diagonals
        if (board[0][0] == player &&
            board[1][1] == player &&
            board[2][2] == player)

            return true;

        if (board[0][2] == player &&
            board[1][1] == player &&
            board[2][0] == player)

            return true;

        return false;
    }

    // Check draw
    static boolean isBoardFull() {

        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 3; j++) {

                if (board[i][j] == ' ')
                    return false;
            }
        }

        return true;
    }

    // Find best AI move
    static int[] minimaxBestMove() {

        int bestScore = Integer.MIN_VALUE;
        int[] move = new int[2];

        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 3; j++) {

                if (board[i][j] == ' ') {

                    board[i][j] = AI;

                    int score = minimax(false);

                    board[i][j] = ' ';

                    if (score > bestScore) {

                        bestScore = score;

                        move[0] = i;
                        move[1] = j;
                    }
                }
            }
        }

        return move;
    }

    // Minimax Algorithm
    static int minimax(boolean isMaximizing) {

        if (checkWinner(AI))
            return 1;

        if (checkWinner(HUMAN))
            return -1;

        if (isBoardFull())
            return 0;

        if (isMaximizing) {

            int bestScore = Integer.MIN_VALUE;

            for (int i = 0; i < 3; i++) {

                for (int j = 0; j < 3; j++) {

                    if (board[i][j] == ' ') {

                        board[i][j] = AI;

                        int score = minimax(false);

                        board[i][j] = ' ';

                        bestScore = Math.max(score, bestScore);
                    }
                }
            }

            return bestScore;

        } else {

            int bestScore = Integer.MAX_VALUE;

            for (int i = 0; i < 3; i++) {

                for (int j = 0; j < 3; j++) {

                    if (board[i][j] == ' ') {

                        board[i][j] = HUMAN;

                        int score = minimax(true);

                        board[i][j] = ' ';

                        bestScore = Math.min(score, bestScore);
                    }
                }
            }

            return bestScore;
        }
    }
}