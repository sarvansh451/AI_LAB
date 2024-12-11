import java.util.*;

class Answer{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

       
        System.out.println("enter the number of nodes:");
        int nodes = scanner.nextInt();

       
        int[][] adj = new int[nodes][nodes];
        System.out.println("Enter the adjacency matrix:");
        for (int i = 0; i < nodes; i++) {
            for (int j = 0; j < nodes; j++) {
                adj[i][j] = scanner.nextInt();
            }
        }

        int[] vis = new int[nodes];
        Arrays.fill(vis, -1);

     
        Queue<Integer> q = new LinkedList<>();
        q.add(0);
        vis[0] = 1; 

      
        System.out.println("BFS Traversal:");
        while (!q.isEmpty()) {
            int node = q.poll(); 
            System.out.print(node + " "); 

          
            for (int nb = 0; nb < nodes; nb++) {
                if (vis[nb] == -1 && adj[node][nb] == 1) {
                    q.add(nb); 
                    vis[nb] = 1; 
                }
            }
        }

        scanner.close();
    }
}
