import java.util.*;
import java.io.*;

public class backforth {
	
	// http://www.usaco.org/index.php?page=viewproblem2&cpid=857
	
	public static void main(String[] args) throws FileNotFoundException {
	    Scanner in = new Scanner(new File("backforth.in"));
	    
	    int[] buck1 = new int[10];
	    // index: position within barn1's closet full of buckets
	    // value: size of the bucket at that position in barn1
	    
	   int[] buck2 = new int[11];
	   // index: position within barn2's closet of buckets
	   // value: size of the bucket at that position in barn2 (0 if there is no bucket)   
	    
	   for (int i = 0; i < 10; i++)  buck1[i] = in.nextInt();
	   for (int i = 0; i < 10; i++)  buck2[i] = in.nextInt();
	   
	    in.close();
	    
	    boolean[] possQuants = new boolean[2001];
	    // index = amount of milk that could be in barn1 theoretically
	    // value = true if we've found at least 1 combination of choices of 4 days
	    // 		that results in that quantity of milk
	    
	    int tank1 = 1000;
	    
	    // this checks each possible choice on day1, and what possible quantities of
	    // milk we can get starting with each day1 choice
	    for (int d1 = 0; d1 < 10; d1++) {
	    	int amount1 = buck1[d1];		//amount of milk based on choice of day 1
	    	
	    	tank1 -= amount1; 	//carried away
	    	buck2[10] = amount1; 	//bucket carried in barn 2
	    	buck1[d1] = 0; 			//bucket isn't in barn anymore (technically not necessary)
	    	
	    	for (int d2 = 0; d2 < 11; d2++) {
	            int amount2 = buck2[d2];
	
	            tank1 += amount2;
	            buck2[d2] = 0;
	            buck1[d1] = amount2;    // carried the bucket of this size back to barn1, put back into empty space
	          
	            for (int d3 = 0; d3 < 10; d3++) {
	            	int amount3 = buck1[d3];		
	            	
	            	tank1 -= amount3; 	
	            	buck2[d2] = amount3; 	
	            	buck1[d3] = 0; 	
	            	
	            	for (int d4 = 0; d4 < 11; d4++) {
	            		int amount4 = buck2[d4];
	            		tank1 += amount4;
	            		
	            		//we now have a possible quantity of milk that we need to count
	            			// unless this quantity is the same as one already counted
	            		
	            		possQuants[tank1] = true;
	            		
	            		tank1 -= amount4;		//able to continue to a different version of day 4
	            		
	            	} 	
	            	tank1 += amount3;
	            	buck2[d2] = 0;
	    			buck1[d3] = amount3;
	            }
	            tank1 -= amount2;
	            buck2[d2] = amount2;
	            buck1[d1] = 0;
	    	}	
	    	tank1 += amount1;
	    	buck1[d1] = amount1;
	    	buck2[10] = 0;
	    }
	    
	    int result = 0;
	    
	    for (int i = 0; i <= 2000; i++) {
	    	if (possQuants[i]) result++;
	    }
    
    
	    PrintWriter out = new PrintWriter(new File("backforth.out"));
	    System.out.println(result);
	    out.println(result);
	    out.close();
    }
}