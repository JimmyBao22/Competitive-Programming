
import java.util.*;
import java.io.*;

public class assign {
	
	// http://www.usaco.org/index.php?page=viewproblem2&cpid=261
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("assign.in"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());  
		int k = Integer.parseInt(st.nextToken());  

		Rule[] rules = new Rule[k];
		for (int i = 0; i < k; i++) {
			st = new StringTokenizer(in.readLine());
			rules[i] = new Rule();
			rules[i].same = st.nextToken().equals("S");  
			rules[i].a = Integer.parseInt(st.nextToken()) - 1;  
			rules[i].b = Integer.parseInt(st.nextToken()) - 1;  
		}

		in.close();

		int count = 0;
		int[]  assignmentArray = new int[n];
		for (int i = 0; i < Math.pow(3, n); i++) {
			
			int assignment = i;
			
			for (int j = 0; j < n; j++) {
				  // put in base 3
				assignmentArray[j] = assignment % 3;
				assignment /= 3;
			}
			if (ok(assignmentArray, n, rules)) {
				count++;
			}
			
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("assign.out"));
		System.out.println(count);
		out.println(count);
		out.close();
	}

	static boolean ok(int[] assignmentArray, int n, Rule[] rules) {
		for (Rule rule : rules) {
			if (assignmentArray[rule.a] == assignmentArray[rule.b] != rule.same) {
				return false;
			}
		}
		return true;
	}
	
	static class Rule {
		int a;
		int b;
		boolean same;
	}
}