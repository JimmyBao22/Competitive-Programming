
import java.util.*;
import java.io.*;

public class FancyFence {

	// https://codeforces.com/contest/1402/problem/A
		
	static long mod = (long)(1e9+7);
	static long modInverse = 500000004;		// 2^-1 mod mod
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		// BufferedReader in = new BufferedReader(new FileReader("FancyFence"));

		int n = Integer.parseInt(in.readLine());
		StringTokenizer st = new StringTokenizer(in.readLine());
		long[] h = new long[n];
		long[] w = new long[n];
		long[] wsum = new long[n];
		for (int i=0; i<n; i++) {
			h[i] = Integer.parseInt(st.nextToken());
		}
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) {
			w[i] = Integer.parseInt(st.nextToken());
			if (i == 0) wsum[i] = w[i];
			else wsum[i] = wsum[i-1] + w[i];
			wsum[i] %= mod;
		}
		
		long ans=0;
		ArrayDeque<Integer> s = new ArrayDeque<>();
        int i=0;
        while (i<n) {
            if (s.isEmpty() || h[i] >= h[s.peek()]) {
            	s.push(i++);
            }
            else {
                int cur = s.pop();
                int left = 0;
                if (!s.isEmpty()) left = s.peek()+1;
                int right = i-1;
                
                long width = 0;
                if (left <= 0) {
                	width = wsum[right];
                }
                else {
                	width = wsum[right] - wsum[left-1];
                }
                width = (width%mod+mod)%mod;
                
                long height = h[cur];
                long partheight = h[cur];
                if (!s.isEmpty()) {
                	partheight = Math.max(h[i], h[s.peek()]);
                }
                else {
                	partheight = h[i];
                }
                
                ans += count1(width, (height - partheight + 1), (height - partheight), mod);
                ans %= mod;
                ans += count2(width, height - partheight, partheight, mod);
                ans %= mod;
            }
        }
        
        while (!s.isEmpty()) {
        	int cur = s.pop();
            int left = 0;
            if (!s.isEmpty()) left = s.peek()+1;
            int right = n-1;
            
            long width = 0;
            if (left <= 0) {
            	width = wsum[right];
            }
            else {
            	width = wsum[right] - wsum[left-1];
            }
            width = (width%mod+mod)%mod;
            
            long height = h[cur];
            long partheight = h[cur];
            if (!s.isEmpty()) {
            	partheight = h[s.peek()];
            }
            else {
            	partheight = 0;
            }
            
            ans += count1(width, (height - partheight + 1), (height - partheight), mod);
            ans %= mod;
            ans += count2(width, height - partheight, partheight, mod);
            ans %= mod;
        }
                
        ans = (ans%mod+mod)%mod;
        System.out.println(ans);
		
	}
	
	public static long count1(long width, long height1, long height2, long m) {
		width++;
		long ans = width * (width-1)/2;
		ans %= m;
		ans *= height1;
		ans %= m;
		ans *= height2;
		ans %= m;
		ans *= modInverse;
		ans %= m;
		return ans;
	}
	
	public static long count2(long width, long height1, long height2, long m) {
		width++;
		long ans = width * (width-1)/2;
		ans %= m;
		ans *= height1;
		ans %= m;
		ans *= height2;
		ans %= m;
		return ans;
	}
}

/*
	keep track of heights. When you reach a new height that is lower, remove everything previous
		that is lower such that this new height is >= the previous height (or there is no more previous height)
		
		Similar to the "Largest Rectangle in Historgram" problem utilizing stacks
		
		width = how wide this section is
		height = current height
		partheight = the height I want to decrease to
		
		count1 basically just calculates, within a rectangle of width "width" and height "height - partheight" the 
			number of different possible rectangles
			Does this using combo --> (width+1 Choose 2)(height-partheight+1 Choose 2)
				Choose 2 vertical bars then 2 horizontal bars
				
		count2 is similar to count1, except the first horizontal bar has to be from some range and the second from another range
			Does (width+1 Choose 2)(height1)(height2)
				Choose 2 vertical bars for width
				For height, the first horizontal bar can be chosen from any height1 bars, while the second
					horizontal bar can be chosen from any height2 bars
					
				This is basically because the vertical bars can be anything, but what I want is the height1 to be in the top
					section, while height 2 is in the bottom section. I need this because I don't want ot undercount
						(see example, you need 2x1 and 2x2 rectangles)
						Though, does not overcount stuff from count1
						
	At end, when this height is the new highest, then push into the set
*/