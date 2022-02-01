
import java.util.*;
import java.io.*;

public class ShaassandLights {

	// https://codeforces.com/problemset/problem/294/C
	
	static long mod = (long)(1e9 + 7);
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("ShaassandLights"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int[] indiceson = new int[m];
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<m; i++) {
			indiceson[i] = Integer.parseInt(st.nextToken());
		}
		Arrays.parallelSort(indiceson);
		
		int last = 0;
		ArrayList<Integer> lengths= new ArrayList<>();
		for (int i=0; i<m; i++) {
			lengths.add((indiceson[i] - last - 1));
			last = indiceson[i];
		}
		lengths.add((n - last));
		
		long[] fact = new long[n];
		if (n>1)fact[1] =1;
		fact[0] = 1;
		for (int i=2; i<n; i++) {
			fact[i] = ((fact[i-1] %mod ) * i)%mod;
		}
		
		int sum=0;
		int powsum=0;
		long prod=1;
		for (int i=0; i<lengths.size(); i++) {
			if (i!=0 && i!=lengths.size()-1) {
				if (lengths.get(i)>0) powsum += (lengths.get(i)-1);
			}
			sum += lengths.get(i);
			prod *= fact[(lengths.get(i))]%mod;
			prod %= mod;
		}
		
		long res = fact[sum] * modInversePrime(prod, mod) %mod;
		res %= mod;
		
		res *= pow(2, powsum, mod);
		res %= mod;
		System.out.println(res);
	}
	
	static long modInversePrime(long a, long m) {
        return pow(a, m - 2, m)%m;
    }
	
	static long pow(long a, long b, long m) {
        // a^b mod m
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