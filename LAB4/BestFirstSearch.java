//Assignment 3: Search for Treasure using the Best-First Search
 
//Objective: Use Best-First Search to find a treasure in a grid.
 
//Problem Statement: The treasure is hidden in a grid, and each cell has a heuristic value representing its "closeness" to the treasure. Implement Best-First Search to locate the treasure.
 
//Tasks:
//● Use Manhattan distance as a heuristic.
//● Implement the algorithm to always move to the most promising cell first (minimum heuristic value).
//● Analyze how heuristic choice affects performance.
import java.util.*;

class BestFirstSearch {
    static class Cell {
        int x, y, heuristic;

        Cell(int x, int y, int heuristic) {
            this.x = x;
            this.y = y;
            this.heuristic = heuristic;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter grid size (rows and columns):");
        int rows = scanner.nextInt();
        int cols = scanner.nextInt();

        System.out.println("Enter the grid values (0 for free cell, -1 for obstacle, -2 for treasure):");
        int[][] grid = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = scanner.nextInt();
            }
        }

        System.out.println("Enter the starting position (row and column):");
        int startX = scanner.nextInt();
        int startY = scanner.nextInt();

        System.out.println("Enter the treasure position (row and column):");
        int treasureX = scanner.nextInt();
        int treasureY = scanner.nextInt();

        List<int[]> path = findTreasure(grid, rows, cols, startX, startY, treasureX, treasureY);

        System.out.println("Path to the treasure:");
        for (int[] cell : path) {
            System.out.println(Arrays.toString(cell));
        }
    }

    public static List<int[]> findTreasure(int[][] grid, int rows, int cols, int startX, int startY, int treasureX, int treasureY) {
        PriorityQueue<Cell> pq = new PriorityQueue<>(Comparator.comparingInt(c -> c.heuristic));
        boolean[][] visited = new boolean[rows][cols];
        List<int[]> path = new ArrayList<>();

        pq.add(new Cell(startX, startY, 0));

        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        while (!pq.isEmpty()) {
            Cell current = pq.poll();

            if (visited[current.x][current.y]) continue;

            visited[current.x][current.y] = true;
            path.add(new int[]{current.x, current.y});

            if (grid[current.x][current.y] == -2) break;

            for (int[] dir : directions) {
                int newX = current.x + dir[0];
                int newY = current.y + dir[1];

                if (isValid(newX, newY, rows, cols, grid, visited)) {
                    int heuristic = Math.abs(newX - treasureX) + Math.abs(newY - treasureY);
                    pq.add(new Cell(newX, newY, heuristic));
                }
            }
        }

        return path;
    }

    private static boolean isValid(int x, int y, int rows, int cols, int[][] grid, boolean[][] visited) {
        return x >= 0 && x < rows && y >= 0 && y < cols && grid[x][y] != -1 && !visited[x][y];
    }
}
