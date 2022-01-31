
import java.util.*;
import java.io.*;

public class whatbase {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=509
		
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("whatbase.in"));
		PrintWriter out = new PrintWriter(new FileWriter("whatbase.out"));

		int t = Integer.parseInt(in.readLine());
		StringBuilder s = new StringBuilder();
		while (t-->0) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			long one = Integer.parseInt(st.nextToken());
			long two = Integer.parseInt(st.nextToken());
			
			for (long base1=10; base1 <= 15000; base1++) {
				long temp = one;
				long val1=0;
				long pow=1;
				while (temp>0) {
					val1 += (temp%10) * pow;
					pow *= base1;
					temp /= 10;
				}
				
				long a,b,c;
				temp = two;
				c = temp%10;
				temp /= 10;
				b = temp%10;
				temp /= 10;
				a = temp%10;
				
				// ax^2+bx+ c = val1
				c -= val1;
				// ax^2 + bx + c
				
				// b^2 - 4*a*c
				long root = b*b - 4*a*c;
				if (Math.sqrt(root) == (long)Math.sqrt(root)) {
					long num = (long) (-b + Math.sqrt(root));
					if (num % (2*a) == 0) {
						num /= (2*a);
						if (num >= 10) {
							// this works!
							s.append(base1 + " " + num);
							s.append("\n");
							break;
						}
					}
				}
			}
		}

		System.out.print(s);
		out.print(s);
		out.close();
	}
}