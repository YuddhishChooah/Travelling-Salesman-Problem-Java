/*
    UnionFind.java
    Author: M00851520
    Created: 10/11/2023
    Updated: 06/12/2023
*/


/**
 * UnionFind class for disjoint-set data structure
 */
public class UnionFind 
{
    private int[] parent;
    private int[] rank;   

    /**
     * Constructor to initialize UnionFind with a given size
     */
    public UnionFind(int size) 
    {
        parent = new int[size];
        rank = new int[size];
        // Setting each element to be parent
        for (int i = 0; i < size; i++) 
        {
            parent[i] = i;
        }
    }

    /**
     * Finds the representative of the set that node belongs to
     */
    public int find(int node) 
    {
            if (parent[node] != node) 
            {
            parent[node] = find(parent[node]);
            }
        return parent[node]; // Return the root of the set
    }

    /**
     * Unites the sets that node1 and node2 belong to
     */
    public void union(int node1, int node2) 
    {
        int root1 = find(node1); 
        int root2 = find(node2); 

        if (root1 != root2) 
        {
            // Attach the shorter tree under the root of the deeper tree
            if (rank[root1] < rank[root2]) 
            {
                parent[root1] = root2;
            } 
            else if (rank[root1] > rank[root2]) 
            {
                parent[root2] = root1;
            } 
            else 
            {
                parent[root2] = root1;
                rank[root1]++;
            }
        }
    }
}
