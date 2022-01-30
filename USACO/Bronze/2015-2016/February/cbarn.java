import java.util.*;
import java.io.*;

public class cbarn {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=616 
	
	public static void main(String[] args) throws FileNotFoundException {
	    Scanner in = new Scanner(new File("cbarn.in"));
	    
	    int n = in.nextInt();
	    int[] rooms = new int[n];
	    //value: # of cows belonging to that room
	    
	    for (int i = 0; i < n; i++) rooms[i] = in.nextInt();
	    
	    
		in.close();
	    int result = Integer.MAX_VALUE;
	    
	    for (int door = 0; door < n; door++) {
	    	// find total dist if all cows go through this door
	    	int total = 0;
	
	    	for (int r = 0; r < n; r++) {
	    		int dist = r - door;
	    		if (dist < 0) dist += n;
	    		
	    		total += dist * rooms[r];
	    	}
	    	
	    	// see if that dist is best
	    	result = Math.min(result, total);
	    }
	    
	    
	    PrintWriter out = new PrintWriter(new File("cbarn.out"));
	    System.out.println(result);
	    out.println(result);
	    out.close();
	}
}