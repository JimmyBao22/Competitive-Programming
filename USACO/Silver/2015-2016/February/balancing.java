
import java.util.*;
import java.io.*;

public class balancing {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=619

	public static void main(String[] args) throws FileNotFoundException {		
		Scanner in = new Scanner(new File("balancing.in"));
		int n = in.nextInt();
		
		Cow[] ySorted = new Cow[n];
		Cow[] xSorted = new Cow[n];
		
		for (int i = 0; i < n; i++) {
			Cow a = new Cow(in);
			ySorted[i] = a;
			xSorted[i] = a;
		}
		
		 Arrays.sort(ySorted, new Comparator<Cow>() {
		      public int compare(Cow a, Cow b) {
		        return a.y - b.y;
		        // this result will be +, 0, or - depending on the
		        //   sorted relationship of a and b
		      }
		    });
		 Arrays.sort(xSorted, new Comparator<Cow>() {
		      public int compare(Cow a, Cow b) {
		        return a.x - b.x;
		        // this result will be +, 0, or - depending on the
		        //   sorted relationship of a and b
		      }
		    });
		 
		in.close();

		int result = Integer.MAX_VALUE;
		
		int topleft = 0;
		int bottomleft = 0;
		int topright = 0;
		int bottomright = n;
		// these will keep track of cows in quadrants
		
		// start with the top cow vertically
		for (int i = ySorted.length-1; i >= 0; i--) {
			if (i != ySorted.length-1 && ySorted[i].y == ySorted[i+1].y) continue; 
			// if this y-value is the same as the one before it, continue
			// this is because u already checked the one before
				// note that this is diff from below because u are including
				// these in ur count for bottom not the top
			
			// put in values for top right and bottom left...
			topright = ySorted.length - i - 1;
			bottomright = i+1;
			
			for (int j = 0; j < xSorted.length; j++) {
				if (j != xSorted.length-1 && xSorted[j].x == xSorted[j+1].x) {
					if (xSorted[j].y > ySorted[i].y) {
						topleft++;
						topright--;
					}
					else {
						bottomleft++;
						bottomright--;
					}
					continue;	// same reasoning as above
				}
				
				// check quadrants
				if (xSorted[j].y > ySorted[i].y) {
					topleft++;
					topright--;
				}
				else {
					bottomleft++;
					bottomright--;
				}
				result = Math.min(result, Math.max(Math.max(bottomleft, bottomright), Math.max(topleft, topright)));
			}
						
			// reset the vertical bar to the left
			// topright += topleft;
			// bottomright += bottomleft;
			topleft = 0;
			bottomleft = 0;
		}
		
		PrintWriter out = new PrintWriter(new File("balancing.out"));
		System.out.println(result);
		out.println(result);
		out.close();
	}

	static class Cow {
	    int x, y;
	    
	    Cow(Scanner in) {
	      x = in.nextInt();
	      y = in.nextInt();
	    }
	}
}