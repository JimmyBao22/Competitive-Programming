import java.io.*;

public class palindrome {

	// https://usaco.org/index.php?page=viewproblem2&cpid=1395
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//      BufferedReader in = new BufferedReader(new FileReader("palindrome.in"));
		//		PrintWriter out = new PrintWriter(new FileWriter("palindrome.out"));

		int t = Integer.parseInt(in.readLine());
		StringBuilder sb = new StringBuilder();
		while (t-->0) {
			String s = in.readLine();
			
			// E only wins if multiple of 10
			if (s.charAt(s.length() - 1) == '0') {
				sb.append("E");
			} else {
				sb.append("B");
			}
			sb.append("\n");
			
		}
		
		System.out.print(sb);
	}
}