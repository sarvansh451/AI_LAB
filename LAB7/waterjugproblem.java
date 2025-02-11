//Implement water jug problem for 3 Jugs. Each with capacity 8l, 5l and 3l. There is no measuring instrument available to measure water in a  Jug. As an initial state 1st Jug is filled up with water from an external source, where as the second and third jug are maintained empty. But in final stage 1st and 2nd jug should contain 4l water each.//


import java.util.*;

public class waterjugproblem {

    public static final int MaxA = 8;
    public static final int MaxB = 5;
    public static final int MaxC = 3;
    public static final int finalA = 0;
    public static final int finalB = 4;
    public static final int finalC = 4;

    public static void main(String args[]) {
        HashSet<String> visited = new HashSet<>();
        Queue<int[]> q = new LinkedList<>();
        int[] initialstate = {MaxA, 0, 0};
        q.add(initialstate);
        visited.add(statetostring(initialstate));
        Map<String, String> parentMap = new HashMap<>();

        while (!q.isEmpty()) {
            int[] current = q.poll();
            if (current[0] == finalA && current[1] == finalB && current[2] == finalC) {
                System.out.println("Goal State reached");
                printpath(parentMap, current);
                return;
            }
            for (int[] nextState : AllPossiblestates(current)) {
                String stateString = statetostring(nextState);
                if (!visited.contains(stateString)) {
                    q.add(nextState);
                    visited.add(stateString);
                    parentMap.put(stateString, statetostring(current));
                }
            }
        }
        System.out.println("Solution not found");
    }

    public static void printpath(Map<String, String> parentMap, int[] current) {
        List<String> path = new ArrayList<>();
        String stateString = statetostring(current);

        while (stateString != null) {
            path.add(stateString);
            stateString = parentMap.get(stateString);
        }

        Collections.reverse(path);
        System.out.println("Solution path:");
        for (String state : path) {
            System.out.println(state);
        }
    }

    public static List<int[]> AllPossiblestates(int[] current) {
        ArrayList<int[]> nextStates = new ArrayList<>();
        int A = current[0];
        int B = current[1];
        int C = current[2];

        nextStates.add(pour(A, B, MaxB, C));
        nextStates.add(pour(B, A, MaxA, C));
        nextStates.add(pour(A, C, MaxC, B));
        nextStates.add(pour(C, A, MaxA, B));
        nextStates.add(pour(B, C, MaxC, A));
        nextStates.add(pour(C, B, MaxB, A));

        nextStates.removeIf(Objects::isNull);
        return nextStates;
    }

    public static String statetostring(int[] state) {
        return state[0] + "," + state[1] + "," + state[2];
    }

    public static int[] pour(int from, int to, int maxCapacity, int thirdJug) {
        if (from == 0 || to == maxCapacity) {
            return null;
        }
        int[] state = new int[3];
        state[2] = thirdJug;
        int transferAmount = Math.min(from, maxCapacity - to);
        state[0] = from - transferAmount;
        state[1] = to + transferAmount;
        return state;
    }
}
