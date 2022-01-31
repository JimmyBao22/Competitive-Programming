
import java.util.*;
import java.io.*;

public class moocast {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=668

	static int n;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("moocast.in"));
		
		n = Integer.parseInt(in.readLine()); 
		Cow[] array = new Cow[n];

		for (int j = 0; j < n; j++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int x = Integer.parseInt(st.nextToken());  
			int y = Integer.parseInt(st.nextToken());  
			int p = Integer.parseInt(st.nextToken());  
			array[j] = new Cow(x, y, p, j);
		}
		
	    for (int j = 0; j < n; j++) {
	        //for each cow call inrange(j, array);
	            //note: j is the place of this cow
	    	array[j].inrange(j, array);
	    }
		in.close();

		int result = Integer.MIN_VALUE;
	    // we want the largest result
	    
	    for (int i = 0; i < n; i++) {
	        // loop through all the cows
	        int f = flood(array[i], array); // flood from that cow
	        result = Math.max(result, f);
	    }   
	
		PrintWriter out = new PrintWriter(new FileWriter("moocast.out"));
		System.out.println(result);
		out.println(result);
		out.close();

	}
	
	 // this is if cow a's radio can reach cow b (not other way around)
    public static boolean distance(Cow a, Cow b) {
    	double distance = Math.sqrt(Math.pow(((double)a.x - (double)b.x), 2) + Math.pow(((double)a.y - (double)b.y), 2));
        return distance <= a.p ? true : false;
    }
    
    public static int flood(Cow i, Cow[] array) {
        ArrayDeque<Cow> check = new ArrayDeque<>();
        check.push(i);
        
	    boolean[] v = new boolean[n]; // was this cow visited
        
        while (check.size() > 0) {
            Cow cur = check.pop();
            
            if (v[cur.pos]) continue;
            
            v[cur.pos] = true;
            
            for (Cow k : cur.a) {
                // gets the hashset of the thing
                check.push(k); // you want cow k
            }  
        }   
        
        int f = 0;
        for (int j = 0; j < v.length; j++) {
        	// check which cows got visited on this run
        	if (v[j]) {
        		f++;
        	}
        }
        return f;
    }
    
    static class Cow {
	    int pos;    // pos in array
	    int x, y;
	    int p;
	        // p is the distance it can reach
	    HashSet<Cow> a;
	    
	    Cow(int x, int y, int p, int pos) {
	    	this.x = x;
	    	this.y = y;
	    	this.p = p;
	    	this.pos = pos;
	    	a = new HashSet<>();
	    }
	    
	    void inrange(int j, Cow[] array) {
	        for (int i = 0; i < n; i++) {
	            if (distance(this, array[i])) {
	            	//add cow array[i] to hashset
	                //(store index)
	            	a.add(array[i]);	
	            }
	        }
	    }
    }
}