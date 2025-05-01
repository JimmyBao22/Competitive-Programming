import java.io.*;

public class WeirdAlgorithm {

	// https://cses.fi/problemset/task/1068
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("WeirdAlgorithm"));

		long n = Long.parseLong(in.readLine());  
		while (n != 1) {
			System.out.print(n + " ");
			if (n % 2 == 0) n /= 2;
			else n = n*3+1;
		} 
		System.out.print(1);
	}
}