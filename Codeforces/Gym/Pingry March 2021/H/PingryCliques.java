
import java.util.*;
import java.io.*;

public class PingryCliques {

	// https://codeforces.com/gym/310127/problem/H
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("PingryCliques"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		
		dsu s = new dsu(n);
		A[] arr = new A[m];
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken())-1;
			int two =Integer.parseInt(st.nextToken())-1;
			long three = Integer.parseInt(st.nextToken());
			arr[i] = new A(one, two, three);
		}
		
		Arrays.parallelSort(arr);
		
		long product = 1;
		long mod = (long)(1e9+7);
		
		int i=0;
		StringBuilder sb = new StringBuilder();
		for (int t=1; t<=n; t++) {
			while (i < m && arr[i].time == t) {
				// combine arr[i].a, arr[i].b
				
				int para = s.FindSet(arr[i].a);
				int parb = s.FindSet(arr[i].b);
				
				if (para == parb) {
					i++;
					continue;
				}
				
				long sizea = s.size[para];
				long sizeb = s.size[parb];
				
				product *= modInverse(sizea + s.T[para], mod);
				product %= mod;
				product *= modInverse(sizeb + s.T[parb], mod);
				product %= mod;
				
				product *= (sizea + sizeb + s.T[para] + s.T[parb] + arr[i].time);
				product %= mod;
				
				s.Union(para, parb, arr[i].time);
				
				i++;
			}
			sb.append(product);
			sb.append("\n");
		}
		System.out.print(sb);
	}
	
	static class A implements Comparable<A> {
		int a, b; long time;
		A (int a, int b, long c) {
			this.a = a;
			this.b = b;
			time = c;
		}
		public int compareTo(A o) {
			return Long.compare(time, o.time);
		}
	}

	static class dsu {
		int n;
		int[] parent;
		long[] size;
		long[] T;
		
		dsu (int n) {
			this.n = n;
			parent = new int[n];
			size = new long[n];
			T = new long[n];
			for (int i=0; i<n; i++) {parent[i] = i; size[i] = 1; T[i] = 0;}
		}

		public int FindSet(int a) {
			if (a == parent[a]) return a;
			return parent[a] = FindSet(parent[a]);
		}
		
		public void Union(int a, int b, long time) {
			a = FindSet(a);
			b = FindSet(b);
			if (a == b) return;
			
			if (size[a] < size[b]) {
				parent[a] = b;
				size[b] += size[a];
				T[b] += T[a];
				T[b] += time;
			}
			else {
				parent[b] = a;
				size[a] += size[b];
				T[a] += T[b];
				T[a] += time;
			}
		}
	}
	
	static long modInverse(long a, long m) {
        return pow(a, m - 2, m)%m;
    }
    
    static long pow(long a, long b, long m) {
    	long ans=1;
    	while (b >0) {
    		if (b%2 == 1) {
    			ans *= a%m;
    			ans %= m;
    		}
    		a *= a %m;
    		a%=m;
    		b >>=1;
    	}
    	return ans;
    }
}