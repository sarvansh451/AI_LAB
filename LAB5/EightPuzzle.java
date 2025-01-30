import java.util.*;

public class EightPuzzle {

    private static final int[][] GOAL_STATE = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};

    static class Node implements Comparable<Node> {
        int[][] state;
        int g;
        int h;
        int f;
        Node parent;

        public Node(int[][] state, int g, int h, Node parent) {
            this.state = copyState(state);
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

    public static void main(String[] args) {
        int[][] initialState = {
            {1, 2, 3},
            {4, 0, 5},
            {7, 8, 6}
        };
        solve(initialState);
    }

    public static void solve(int[][] initialState) {
        PriorityQueue<Node> openList = new PriorityQueue<>();
        Set<String> visited = new HashSet<>();

        int h = calculateHeuristic(initialState, GOAL_STATE);
        Node initialNode = new Node(initialState, 0, h, null);
        openList.add(initialNode);
        visited.add(stateToString(initialState));

        while (!openList.isEmpty()) {
            Node currentNode = openList.poll();

            if (isGoal(currentNode.state)) {
                printPath(currentNode);
                return;
            }

            List<int[][]> children = generateChildren(currentNode.state);
            for (int[][] child : children) {
                String childStr = stateToString(child);
                if (!visited.contains(childStr)) {
                    visited.add(childStr);
                    int childH = calculateHeuristic(child, GOAL_STATE);
                    Node childNode = new Node(child, currentNode.g + 1, childH, currentNode);
                    openList.add(childNode);
                }
            }
        }

        System.out.println("No solution found.");
    }

    private static int calculateHeuristic(int[][] current, int[][] goal) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (current[i][j] != goal[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    private static boolean isGoal(int[][] state) {
        return Arrays.deepEquals(state, GOAL_STATE);
    }

    private static List<int[][]> generateChildren(int[][] state) {
        List<int[][]> children = new ArrayList<>();
        int[] blankPos = findBlankPosition(state);
        int row = blankPos[0], col = blankPos[1];

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (newRow >= 0 && newRow < 3 && newCol >= 0 && newCol < 3) {
                int[][] child = copyState(state);
                swap(child, row, col, newRow, newCol);
                children.add(child);
            }
        }
        return children;
    }

    private static int[] findBlankPosition(int[][] state) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] == 0) {
                    return new int[]{i, j};
                }
            }
        }
        throw new IllegalArgumentException("No blank tile (0) found.");
    }

    private static int[][] copyState(int[][] state) {
        int[][] newState = new int[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(state[i], 0, newState[i], 0, 3);
        }
        return newState;
    }

    private static void swap(int[][] state, int row1, int col1, int row2, int col2) {
        int temp = state[row1][col1];
        state[row1][col1] = state[row2][col2];
        state[row2][col2] = temp;
    }

    private static String stateToString(int[][] state) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sb.append(state[i][j]);
            }
        }
        return sb.toString();
    }

    private static void printPath(Node node) {
        List<Node> path = new ArrayList<>();
        while (node != null) {
            path.add(node);
            node = node.parent;
        }
        Collections.reverse(path);
        System.out.println("Solution Path (" + (path.size()-1) + " moves):");
        for (Node n : path) {
            printState(n.state);
            System.out.println();
        }
    }

    private static void printState(int[][] state) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(state[i][j] + " ");
            }
            System.out.println();
        }
    }
}