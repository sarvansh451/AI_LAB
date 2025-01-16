import java.util.*;

public class Mazesolver {

    private static final int[] dirX = {0, 1, 0, -1};
    private static final int[] dirY = {1, 0, -1, 0};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of rows in the maze: ");
        int rows = scanner.nextInt();
        System.out.print("Enter the number of columns in the maze: ");
        int cols = scanner.nextInt();

        int[][] maze = new int[rows][cols];
        System.out.println("Enter the maze row by row (0 for wall, 1 for path):");

        // Input the maze
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                maze[i][j] = scanner.nextInt();
            }
        }

        System.out.print("Enter the start position (row column): ");
        int startX = scanner.nextInt();
        int startY = scanner.nextInt();

        System.out.print("Enter the end position (row column): ");
        int endX = scanner.nextInt();
        int endY = scanner.nextInt();


        List<int[]> bfsPath = bfs(maze, startX, startY, endX, endY);
        List<int[]> dfsPath = new ArrayList<>();
    
        boolean dfsFound = dfs(maze, startX, startY, endX, endY, dfsPath);

        System.out.println("BFS Path (Shortest Path):");
        for (int[] step : bfsPath) {
            System.out.println(Arrays.toString(step));
        }

        System.out.println("DFS Path (One Valid Path):");
        if (dfsFound) {
            for (int[] step : dfsPath) {
                System.out.println(Arrays.toString(step));
            }
        } else {
            System.out.println("No valid path found.");
        }

        System.out.println("BFS Nodes Explored: " + bfsPath.size());
        System.out.println("DFS Nodes Explored: " + dfsPath.size());
    }
    public static List<int[]> bfs(int[][] maze, int startX, int startY, int endX, int endY) {
        int rows = maze.length, cols = maze[0].length;
        boolean[][] visited = new boolean[rows][cols];
        Queue<int[]> queue = new LinkedList<>();
        Map<String, int[]> parentMap = new HashMap<>();
        List<int[]> path = new ArrayList<>();

       
        queue.add(new int[]{startX, startY});
        visited[startX][startY] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0], y = current[1];

          
            if (x == endX && y == endY) {
                return reconstructPath(parentMap, startX, startY, x, y);
            }

          
            for (int i = 0; i < 4; i++) {
                int newX = x + dirX[i], newY = y + dirY[i];

                if (isValid(maze, newX, newY, visited)) {
                    visited[newX][newY] = true;
                    queue.add(new int[]{newX, newY});
                    
                    parentMap.put(newX + "," + newY, current);  
                }
            }
        }

        return path; 
    }

    private static List<int[]> reconstructPath(Map<String, int[]> parentMap, int startX, int startY, int endX, int endY) {
        List<int[]> path = new ArrayList<>();
        String current = endX + "," + endY;

        while (!current.equals(startX + "," + startY)) {
            int[] currentPos = parentMap.get(current);
            path.add(currentPos);
            current = currentPos[0] + "," + currentPos[1];
        }

        // Add the start position
        path.add(new int[]{startX, startY});
        Collections.reverse(path);  // Reverse the path to start from the beginning
        return path;
    }

   

    public static boolean dfs(int[][] maze, int startX, int startY, int endX, int endY, List<int[]> path) {
        int rows = maze.length, cols = maze[0].length;
        boolean[][] visited = new boolean[rows][cols];
        return dfsHelper(maze, startX, startY, endX, endY, visited, path);
    }

    private static boolean dfsHelper(int[][] maze, int x, int y, int endX, int endY, boolean[][] visited, List<int[]> path) {
        if (x < 0 || y < 0 || x >= maze.length || y >= maze[0].length || maze[x][y] == 0 || visited[x][y]) {
            return false;
        }

        visited[x][y] = true;
        path.add(new int[]{x, y});

        if (x == endX && y == endY) {
            return true;
        }

        for (int i = 0; i < 4; i++) {
            if (dfsHelper(maze, x + dirX[i], y + dirY[i], endX, endY, visited, path)) {
                return true;
            }
        }

        path.remove(path.size() - 1);
        return false;
    }

   

    private static boolean isValid(int[][] maze, int x, int y, boolean[][] visited) {
        return x >= 0 && y >= 0 && x < maze.length && y < maze[0].length && maze[x][y] == 1 && !visited[x][y];
    }
}
