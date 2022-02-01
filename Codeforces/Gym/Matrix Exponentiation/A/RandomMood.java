
import java.util.*;
import java.io.*;

public class RandomMood {

	// https://codeforces.com/gym/102644/problem/A 
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("RandomMood"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		double p = Double.parseDouble(st.nextToken());
		
		double probhappy = 1.0;
		while (n > 0) {
			if (n%2 == 1) {
				probhappy = probhappy * (1 - p) + (1 - probhappy) * p;
			}
			p = p * (1-p) + (1-p) * p;
			n >>= 1;
		}
		System.out.println(probhappy);
		
	}
}