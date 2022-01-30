
import java.util.*;
import java.io.*;

public class lostcow {
	
	// http://www.usaco.org/index.php?page=viewproblem2&cpid=735

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File("lostcow.in"));
		PrintWriter out = new PrintWriter(new File("lostcow.out"));

		long jones, bessie, jonesnew;
	    jones = in.nextLong();
	    bessie = in.nextLong();
	    
	    long k = 1;
	    long distance = 0;
	    jonesnew = jones;
	    while((jonesnew > bessie && (jones + k) > bessie) || (jonesnew < bessie && (jones + k) < bessie)) {
	        distance += Math.abs(jones + k - jonesnew);
	        jonesnew = jones + k;
	        k *= -2;
	    }

        distance += Math.abs(jonesnew - bessie);
		
		System.out.println(distance);
		out.println(distance);
		out.close();
	}
}