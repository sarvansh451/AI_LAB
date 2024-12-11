import java.util.Scanner;
class Solution{
    public static void main(String args[])
    {
        Scanner scanner=new Scanner(System.in);

        System.out.println("enter the number of nodes");
        int nodes=scanner.nextInt();
        int[][] adj=new int[nodes][nodes];
        int vis[]=new int[nodes];
        for(int k=0;k<vis.length;k++)
        {
            vis[k]=-1;
        }
        for(int i=0;i<adj.length;i++)
        {
            for(int j=0;j<adj.length;j++)
            {
                //if there is a edge between i and j enter 1 else 0//
                adj[i][j]=scanner.nextInt();

            }
        }
        //logic for dfs//
       
        System.out.println("DFS TRAVERSAL");
        for(int i=0;i<nodes;i++)
        {
            if(vis[i]==-1)
            {
               dfs(adj,vis,i);
            }
        }


    }
    public static void dfs(int[][] adj,int[] vis,int index)
    {
      vis[index]=1;
      System.out.print(index+" ");
      for(int nb=0;nb<adj.length;nb++)
      {
        if(vis[nb]==-1 && adj[index][nb]==1)
        {
            dfs(adj,vis,nb);
        }
      }

    }

}