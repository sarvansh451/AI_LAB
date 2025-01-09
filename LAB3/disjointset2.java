import java.util.*;

class DisjointSet {
    ArrayList<Integer> parent = new ArrayList<>();
    ArrayList<Integer> rank = new ArrayList<>();

    public DisjointSet(int n) {
        for (int i = 0; i <= n; i++) {
            parent.add(i);
            rank.add(0);
        }
    }

    public int find(int u) {
        if (parent.get(u) != u) {
            parent.set(u, find(parent.get(u)));
        }
        return parent.get(u);
    }

    public void unionByRank(int u, int v) {
        int ulp_u = find(u);
        int ulp_v = find(v);

        if (ulp_u == ulp_v) {
            return;
        }

        if (rank.get(ulp_u) > rank.get(ulp_v)) {
            parent.set(ulp_v, ulp_u);
        } else if (rank.get(ulp_u) < rank.get(ulp_v)) {
            parent.set(ulp_u, ulp_v);
        } else {
            parent.set(ulp_v, ulp_u);
            rank.set(ulp_u, rank.get(ulp_u) + 1);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of elements in the disjoint set: ");
        int n = sc.nextInt();
        DisjointSet ds = new DisjointSet(n);

        while (true) {
            System.out.println("\nChoose an operation:");
            System.out.println("1. Union");
            System.out.println("2. Find");
            System.out.println("3. Exit");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter two elements to perform union: ");
                    int u = sc.nextInt();
                    int v = sc.nextInt();
                    ds.unionByRank(u, v);
                    System.out.println("Union operation completed.");
                    break;

                case 2:
                    System.out.print("Enter an element to find its parent: ");
                    int x = sc.nextInt();
                    int parent = ds.find(x);
                    System.out.println("The representative of the element " + x + " is: " + parent);
                    break;

                case 3:
                    System.out.println("Exiting program.");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
