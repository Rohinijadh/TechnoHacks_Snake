import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class SnakeGame {
    private static final int BOARD_SIZE = 10;
    private static final char EMPTY = '.';
    private static final char SNAKE_BODY = 'O';
    private static final char FOOD = '*';

    private static char[][] board = new char[BOARD_SIZE][BOARD_SIZE];
    private static LinkedList<int[]> snake = new LinkedList<>();
    private static int[] food = new int[2];
    private static int directionX = 1;
    private static int directionY = 0;
    private static boolean gameOver = false;

    public static void main(String[] args) {
        initializeBoard();
        spawnFood();
        displayBoard();

        Scanner scanner = new Scanner(System.in);
        while (!gameOver) {
            System.out.print("Enter direction (WASD): ");
            String input = scanner.nextLine().toUpperCase();
            changeDirection(input);
            moveSnake();
            checkCollision();
            displayBoard();
        }
        scanner.close();
        System.out.println("Game Over!");
    }

    private static void initializeBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }
        snake.add(new int[] { 2, 2 });
    }

    private static void spawnFood() {
        Random random = new Random();
        do {
            food[0] = random.nextInt(BOARD_SIZE);
            food[1] = random.nextInt(BOARD_SIZE);
        } while (board[food[0]][food[1]] != EMPTY);
        board[food[0]][food[1]] = FOOD;
    }

    private static void displayBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void changeDirection(String input) {
        switch (input) {
            case "W":
                directionX = -1;
                directionY = 0;
                break;
            case "A":
                directionX = 0;
                directionY = -1;
                break;
            case "S":
                directionX = 1;
                directionY = 0;
                break;
            case "D":
                directionX = 0;
                directionY = 1;
                break;
        }
    }

    private static void moveSnake() {
        int[] head = snake.getFirst();
        int[] newHead = new int[] { head[0] + directionX, head[1] + directionY };
        snake.addFirst(newHead);
        if (newHead[0] == food[0] && newHead[1] == food[1]) {
            board[food[0]][food[1]] = EMPTY;
            spawnFood();
        } else {
            int[] tail = snake.removeLast();
            board[tail[0]][tail[1]] = EMPTY;
        }
        board[newHead[0]][newHead[1]] = SNAKE_BODY;
    }

    private static void checkCollision() {
        int[] head = snake.getFirst();
        if (head[0] < 0 || head[0] >= BOARD_SIZE || head[1] < 0 || head[1] >= BOARD_SIZE) {
            gameOver = true;
        }
        for (int i = 1; i < snake.size(); i++) {
            if (head[0] == snake.get(i)[0] && head[1] == snake.get(i)[1]) {
                gameOver = true;
                break;
            }
        }
    }
}
