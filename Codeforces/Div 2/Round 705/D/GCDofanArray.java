
import java.util.*;
import java.io.*;

public class GCDofanArray {

	// https://codeforces.com/contest/1493/problem/D
	
	static int n, q;
	static long mod = (long)(1e9+7);
	static HashMap<Integer, HashMap<Integer, Integer>> map = new HashMap<>();
		// prime = index = count
	static int[] arr;
	static int INF = (int)(1e9);
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("GCDofanArray"));

		getprimes();
		int t = 1;
		while (t-->0) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			n = Integer.parseInt(st.nextToken());
			q = Integer.parseInt(st.nextToken());

			for (int i=0; i<primes.size(); i++) {
				map.put(primes.get(i), new HashMap<>());
			}
			arr = new int[n];
			st = new StringTokenizer(in.readLine());
			for (int i=0; i<n; i++) {
				arr[i] = Integer.parseInt(st.nextToken());
				getprimeDivisors(i);
			}
			
			long gcd=1;
			for (Integer p : map.keySet()) {
				if (map.get(p).size() == n) {
					int min = INF;
					for (Integer a : map.get(p).keySet()) {
						min = Math.min(min, map.get(p).get(a));
					}
					gcd *= pow(p, min, mod);
					gcd %= mod;
					ArrayList<Integer> remove=new ArrayList<>();
					for (Integer a : map.get(p).keySet()) {
						map.get(p).put(a, map.get(p).get(a) - min);
						if (map.get(p).get(a) == 0) {
							remove.add(a);
						}
					}
					for (Integer a : remove) {
						map.get(p).remove(a);
					}
				}
			}
			
			for (int i=0; i<q; i++) {
				st = new StringTokenizer(in.readLine());
				int index = Integer.parseInt(st.nextToken());
				int val = Integer.parseInt(st.nextToken());
				index--;
				
				HashMap<Integer, Integer> allp = getprimeDivisors2(val);
				for (Integer p : allp.keySet()) {
					map.get(p).put(index, map.get(p).getOrDefault(index, 0) + allp.get(p));
					if (map.get(p).size() == n) {
						int min = INF;
						for (Integer a : map.get(p).keySet()) {
							min = Math.min(min, map.get(p).get(a));
						}
						gcd *= pow(p, min, mod);
						gcd %= mod;
						ArrayList<Integer> remove=new ArrayList<>();
						for (Integer a : map.get(p).keySet()) {
							map.get(p).put(a, map.get(p).get(a) - min);
							if (map.get(p).get(a) == 0) {
								remove.add(a);
							}
						}
						for (Integer a : remove) {
							map.get(p).remove(a);
						}
					}
				}
				
				System.out.println(gcd);
			}
		}
	}
	
	public static long pow(long a, long b, long m) {
    	long ans = 1;
    	while (b > 0) {
    		if (b%2 == 1) {
    			ans *= a;
    			ans %= m;
    		}
    		a *= a;
    		a %= m;
    		b >>= 1;
    	}
    	return ans;
    }	
	
	public static HashMap<Integer, Integer> getprimeDivisors2(int n) { 
        HashMap<Integer, Integer> a = new HashMap<>();
		if (n%2 == 0) {
			int c=0;
			while (n%2 == 0) {
				n >>=1;
				c++;
			}
			a.put(2, c);
		}
        for (int i = 3; i*i<=n; i+=2)  { 
            if (n%i==0) { 
                int c=0;
                while (n%i ==0) {
                	n/=i;
                	c++;
                }
                a.put(i, c);
            } 
        }
        if (n!=1) {
        	a.put(n, 1);
        }
		return a;
	}
	
	public static void getprimeDivisors(int index) { 
		int n = arr[index];
		if (n%2 == 0) {
			int c=0;
			while (n%2 == 0) {
				n >>=1;
				c++;
			}
			map.get(2).put(index, c);
		}
        for (int i = 3; i*i<=n; i+=2)  { 
            if (n%i==0) {
                int c=0;
                while (n%i ==0) {
                	n/=i;
                	c++;
                }
    			map.get(i).put(index, c);
            } 
        }
        if (n!=1) {
			map.get(n).put(index, 1);
        }
	}
	
	static int max = (int)2e5+2;
	static int[] temp = new int[max];
	static ArrayList<Integer> primes = new ArrayList<>();

	public static void getprimes() {
		primes.add(2);
		for (int i=2; i<max; i++) {
			temp[i] = i;
		}
		for (int i=4; i<max; i+=2) {
			temp[i] = 2;
		}
		int i=3;
		for (; i*i<max; i+=2) {
			if (temp[i] == i) {
				primes.add(i);
				for (int j= i*i; j < max; j+= i) {
					temp[j] = i;
				}
			}
		}
		for (; i<max; i+=2) {
			if (temp[i] == i) primes.add(i);
		}
	}
}