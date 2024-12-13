import java.util.*;

public class Traversal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // make adjacency list
        List<List<Integer>> graph = new ArrayList<>();
        System.out.println("enter the number of vertex");
        int edge = scanner.nextInt();

        // input for the graph
        for (int i = 0; i < edge; i++) {
            // enter the edges whom they are connected to
            List<Integer> row = new ArrayList<>();
            // number of edges they are connected to
            System.out.print("enter number of edges it is connected to for vertex ");
            System.out.println(i);
            int num = scanner.nextInt();
            for (int j = 0; j < num; j++) { // Fixed iteration variable
                int n = scanner.nextInt();
                row.add(n);
            }
            graph.add(row);
        }

        System.out.println("enter the source node");
        int source = scanner.nextInt();
        System.out.println("enter the target node");
        int dest = scanner.nextInt();

        // Compare algorithms
        System.out.println("Bidirectional BFS:");
        bidirectionalBFS(graph, source, dest);

        System.out.println("Normal BFS:");
        normalBFS(graph, source);

        System.out.println("DFS:");
        boolean[] visited = new boolean[edge];
        dfs(graph, source, visited);
        System.out.println();
    }

    public static void bidirectionalBFS(List<List<Integer>> graph, int source, int dest) {
        int edge = graph.size();
        int[] parent_s = new int[edge];
        int[] parent_t = new int[edge];
        Queue<Integer> queue_s = new LinkedList<>();
        Queue<Integer> queue_t = new LinkedList<>();

        queue_s.add(source);
        queue_t.add(dest);

        // creating the visited array
        int[] vis_s = new int[edge];
        int[] vis_t = new int[edge];
        Arrays.fill(vis_s, -1);
        Arrays.fill(vis_t, -1);

        vis_s[source] = 1;
        vis_t[dest] = 1;

        while (!queue_s.isEmpty() && !queue_t.isEmpty()) {
            bfs(queue_s, parent_s, vis_s, graph);
            bfs(queue_t, parent_t, vis_t, graph);

            int intersection = check(vis_s, vis_t);
            if (intersection != -1) {
                System.out.println("they intersect");
                printpath(graph, parent_s, parent_t, source, dest, intersection);
                return;
            }
        }
        System.out.println("No intersection found.");
    }

    public static void bfs(Queue<Integer> queue, int[] parent, int[] vis, List<List<Integer>> graph) {
        int current = queue.poll();
        System.out.println("Visiting: " + current);
        for (int nb : graph.get(current)) {
            if (vis[nb] == -1) {
                vis[nb] = 1;
                queue.add(nb);
                parent[nb] = current;
            }
        }
    }

    public static int check(int[] vis_s, int[] vis_t) {
        for (int i = 0; i < vis_s.length; i++) {
            if (vis_s[i] == 1 && vis_t[i] == 1) {
                return i;
            }
        }
        return -1;
    }

    public static void printpath(List<List<Integer>> graph, int[] parent_s, int[] parent_t, int source, int dest, int intersection) {
        // print path before intersection
        Stack<Integer> path_s = new Stack<>();
        int i = intersection;
        while (i != source) {
            path_s.push(i);
            i = parent_s[i];
        }
        path_s.push(source);

        while (!path_s.isEmpty()) {
            System.out.print(path_s.pop() + " ");
        }

        // print path after intersection
        int j = intersection;
        while (j != dest) {
            System.out.print(parent_t[j] + " ");
            j = parent_t[j];
        }
        System.out.println(dest);
    }

    public static void normalBFS(List<List<Integer>> graph, int source) {
        boolean[] visited = new boolean[graph.size()];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;

        while (!queue.isEmpty()) {
            int current = queue.poll();
            System.out.print(current + " ");
            for (int neighbor : graph.get(current)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }
        System.out.println();
    }

    public static void dfs(List<List<Integer>> graph, int current, boolean[] visited) {
        visited[current] = true;
        System.out.print(current + " ");
        for (int neighbor : graph.get(current)) {
            if (!visited[neighbor]) {
                dfs(graph, neighbor, visited);
            }
        }
    }
}
