
import java.util.*;
import java.io.*;

public class Equation {

	// https://codeforces.com/edu/course/2/lesson/6/2/practice/contest/283932/problem/E
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Equation"));

		double c = Double.parseDouble(in.readLine());
		double min=0.0;
		double max=1e6;
		for (int i=0; i<100; i++) {
			double middle = (min+max)/2;
			double val = middle*middle + Math.sqrt(middle);
			if (val>c) {
				max = middle;
			}
			else min=middle;
		}
		System.out.println(min);

	}

}
