import java.util.*;

public class UCP {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter the number of nodes:");
        int n = scanner.nextInt();
        
        int[][] graph = new int[n][n];
        System.out.println("Enter the adjacency matrix:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                graph[i][j] = scanner.nextInt();
            }
        }
        
        System.out.println("Enter the source node:");
        int source = scanner.nextInt();
        
        System.out.println("Enter the destination node:");
        int destination = scanner.nextInt();
        
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.add(new int[]{source, 0});
        
        boolean[] visited = new boolean[n];
        
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int node = current[0];
            int cost = current[1];
            
            if (visited[node]) continue;
            
            visited[node] = true;
            
            if (node == destination) {
                System.out.println("The total cost from source to destination is: " + cost);
                return;
            }
            
            for (int i = 0; i < n; i++) {
                if (graph[node][i] > 0 && !visited[i]) {
                    pq.add(new int[]{i, cost + graph[node][i]});
                }
            }
        }
        
        System.out.println("No path found from source to destination.");
    }
}
