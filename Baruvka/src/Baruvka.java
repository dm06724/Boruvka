public class Baruvka {
	
	public static void boruvka(Graph G) {
		int V = G.V; int E = G.E;
		int cheapest[] = new int[V];
		Subset[] s = new Subset[V];
		Edge[] e = new Edge[E];
		
		for(int i = 0; i < V; ++i) {
			s[i].P = i;
			s[i].R = 0;
			cheapest[i] = -1;
		}
		
		int numTrees = V;
		int finalMST = 0;
		
		while(numTrees > 1) {
			for(int i = 0; i < V; ++i) {
				cheapest[i] = -1;
			}
			
			for(int i = 0; i < E; i++) {
				int s1 = find(s, e[i].src);
				int s2 = find(s, e[i].dest);
				
				if(s1 == s2) {
					continue;
				}
				
				else {
					if(cheapest[s1] == -1 || e[cheapest[s1]].weight > e[i].weight) {
						cheapest[s1] = i;
					}
					
					if(cheapest[s2] == -1 || e[cheapest[s2]].weight > e[i].weight) {
						cheapest[s2] = i;
					}
				}
			}
			for(int i = 0; i < V; i++) {
				if(cheapest[i] != -1) {
					int s1 = find(s, e[cheapest[i]].src);
					int s2 = find(s, e[cheapest[i]].dest);
					
					if(s1 == s2) {
						continue;
					}
					finalMST += e[cheapest[i]].weight;
					System.out.printf("Edge %d-%d added to MST. Weight: %d\n", e[cheapest[i]].src, e[cheapest[i]].dest, e[cheapest[i]].weight);
					
					union(s, s1, s2);
					numTrees--;
				}
			}
		}
		System.out.printf("Weight of MST: %d", finalMST);
	}
	
	public static int find(Subset[] s, int i) {
		if(s[i].P != i) {
			s[i].P = find(s, s[i].P);
		}
		return s[i].P;
	}
	
	public static void union(Subset[] s, int x, int y) {
		int xr = find(s, x);
		int yr = find(s, y);
		
		if(s[xr].R < s[yr].R) {
			s[xr].P = yr;
		}else if(s[xr].R > s[yr].R){
			s[yr].P = xr;
		}else {
			s[yr].P = xr;
			s[xr].R++;
		}
	}
	
	public static void main(String[] args) {
		int V = 4;
		int E = 5;
		Graph G = new Graph();
		
		G.V = V;
		G.E = E;
		G.edge = new Edge[E];
		System.out.print(E);
		
		G.edge[0].src=0;
		G.edge[0].dest=1;
		G.edge[0].weight=10;

		G.edge[1].src=0;
		G.edge[1].dest=2;
		G.edge[1].weight=6;
		
		G.edge[2].src=0;
		G.edge[2].dest=3;
		G.edge[2].weight=5;
		
		G.edge[3].src=1;
		G.edge[3].dest=3;
		G.edge[3].weight=15;
		
		G.edge[4].src=2;
		G.edge[4].dest=3;
		G.edge[4].weight=4;
		
		boruvka(G);
	}
}