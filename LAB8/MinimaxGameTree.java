import java.util.*;

public class MinimaxGameTree {
    public static int minimax(int depth, int nodeIndex, boolean isMaximizing, int[] values, int maxDepth) {
        if (depth == maxDepth) {
            return values[nodeIndex];
        }

        if (isMaximizing) {
            return Math.max(
                minimax(depth + 1, nodeIndex * 2, false, values, maxDepth),
                minimax(depth + 1, nodeIndex * 2 + 1, false, values, maxDepth)
            );
        } else {
            return Math.min(
                minimax(depth + 1, nodeIndex * 2, true, values, maxDepth),
                minimax(depth + 1, nodeIndex * 2 + 1, true, values, maxDepth)
            );
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of leaf nodes: ");
        int n = scanner.nextInt();
        int[] leafValues = new int[n];

        System.out.println("Enter the leaf node values:");
        for (int i = 0; i < n; i++) {
            leafValues[i] = scanner.nextInt();
        }

        int maxDepth = (int) (Math.log(n) / Math.log(2));
        int result = minimax(0, 0, true, leafValues, maxDepth);
        
        System.out.println("The utility score for the root node is: " + result);
    }
}
