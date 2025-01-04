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
        DisjointSet ds = new DisjointSet(7);

        ds.unionByRank(1, 2);
        ds.unionByRank(2, 3);
        ds.unionByRank(4, 5);
        ds.unionByRank(4, 3);
        ds.unionByRank(5, 6);

        if (ds.find(3) == ds.find(7)) {
            System.out.println("They are in the same connected component.");
        } else {
            System.out.println("They are not in the same connected component.");
        }

        ds.unionByRank(3, 7);

        if (ds.find(3) == ds.find(7)) {
            System.out.println("They are now in the same connected component.");
        } else {
            System.out.println("They are still not in the same connected component.");
        }
    }
}
