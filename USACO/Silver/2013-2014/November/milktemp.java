
import java.util.*;
import java.io.*;

public class milktemp {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=341
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("milktemp.in"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());  
		int x = Integer.parseInt(st.nextToken());  
		int y = Integer.parseInt(st.nextToken());  
		int z = Integer.parseInt(st.nextToken());  
		
		ProdChange[] changes = new ProdChange[2*n];
		for (int i = 0; i < n; i++) {
			StringTokenizer stt = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(stt.nextToken());  
			int b = Integer.parseInt(stt.nextToken());  
			
			changes[2*i] = new ProdChange(a, y-x); // cold --> JR
			changes[2*i+1] = new ProdChange(b+1, z-y); // JR --> hot
		}
		
		in.close();

		Arrays.sort(changes);
		
		int result = n*x;	// best
		int prod = result;	// total rn
		// rn all cows too cold
		
		for (int i = 0; i < 2 * n; i++) {
			prod += changes[i].p;
			
			// if at end of all changes at that temp, then update
			if (i == 2*n-1 || changes[i+1].t != changes[i].t) {
				result = Math.max(result, prod);
			}
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("milktemp.out"));
		System.out.println(result);
		out.println(result);
		out.close();
	}

	static class ProdChange implements Comparable<ProdChange> {
		int t, p;
		// t --> temp where change occurs
		// p --> production change at that temp
		
		ProdChange (int t, int p) {
			this.t = t;
			this.p = p;
		}
		
		public int compareTo(ProdChange other) {
			return this.t - other.t;
		}
		
	}
	
}

/*
	4 7 (cold) 9 (JR) 6 (hot)
	5 8
	3 4
	13 20
	7 10
	
	34
	  5--8
	  	7--10
	  			13 ... 20
	  			
	 
	 -1 --> 4 * 7 = 28 (all cold)
	 
	 testing temps
	 	coord compression --> imp temps
	 	
	 	3 --> change by 2 (9 (JR)-7 (cold)) ==> 30
	 	5 --> change by -3 (6 (hot) -9 (JR)) and change by 2 (9 (JR)-7 (cold)) ==> 29
		7 --> change by 2 (9 (JR)-7 (cold)) ==> 31
		9 ==> 28
		11 ==> 25
		13  ==> 27
		21 ==> 24
*/