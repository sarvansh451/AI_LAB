import java.util.*;

public class Robotclass {
    static class Node implements Comparable<Node> {
        int row, col;
        int g, h, f;
        Node parent;

        public Node(int row, int col, int g, int h, Node parent) {
            this.row = row;
            this.col = col;
            this.g = g;
            this.h = h;
            this.f = g + h;
            this.parent = parent;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.f, other.f);
        }
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the size of the grid:");
        int size = scanner.nextInt();
        int[][] grid = new int[size][size];
        // Enter 1 for path and 0 for no path
        System.out.println("Enter the grid (1 for path, 0 for block):");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = scanner.nextInt();
            }
        }
        Solve(grid);
    }

    public static void Solve(int[][] initialstate) {
        int goali = initialstate.length - 1;
        int goalj = initialstate[0].length - 1;
        PriorityQueue<Node> openlist = new PriorityQueue<>();
        boolean[][] visited = new boolean[initialstate.length][initialstate[0].length];
        int initialheuristic = calculateh(0, 0, goali, goalj);
        Node startnode = new Node(0, 0, 0, initialheuristic, null);
        openlist.add(startnode); // Important to add the initial node to the list
        int[] row = {-1, 0, 1, 0};
        int[] col = {0, 1, 0, -1};

        while (!openlist.isEmpty()) {
            Node currentnode = openlist.poll();
            visited[currentnode.row][currentnode.col] = true;
            if (currentnode.row == goali && currentnode.col == goalj) {
                System.out.println("Goal state reached");
                printpath(currentnode);
                return;
            }
            for (int i = 0; i < 4; i++) {
                int newx = currentnode.row + row[i];
                int newy = currentnode.col + col[i];
                if (isvalid(newx, newy, goali, goalj, initialstate) && !visited[newx][newy]) {
                    int heuristicvalue = calculateh(newx, newy, goali, goalj);
                    Node thisnode = new Node(newx, newy, currentnode.g + 1, heuristicvalue, currentnode);
                    openlist.add(thisnode);
                }
            }
        }
    }

    public static boolean isvalid(int newx, int newy, int goali, int goalj, int[][] grid) {
        return (newx >= 0 && newx <= goali && newy >= 0 && newy <= goalj && grid[newx][newy] != 0);
    }

    public static int calculateh(int row, int col, int finalrow, int finalcol) {
        return Math.abs(row - finalrow) + Math.abs(col - finalcol);
    }

    public static void printpath(Node node) {
        if (node == null) return;
        printpath(node.parent);
        System.out.println("(" + node.row + ", " + node.col + ")");
    }
}
