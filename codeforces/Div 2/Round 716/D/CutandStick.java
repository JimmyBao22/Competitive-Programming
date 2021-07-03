
import java.util.*;
import java.io.*;

public class CutandStick {

	// https://codeforces.com/contest/1514/problem/D
	
	static int n, maxcount=0, numvals=0;
	static int[] arr;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("CutandStick"));

		int t = 1;
		while (t-- > 0) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			n = Integer.parseInt(st.nextToken());
			int q = Integer.parseInt(st.nextToken());
			A[] queries =new A[q];
			arr = new int[n];
			st = new StringTokenizer(in.readLine());
			for (int i=0; i<n; i++) {
				arr[i] = Integer.parseInt(st.nextToken());
			}
			
			for (int i=0; i<q; i++) {
				st = new StringTokenizer(in.readLine());
				queries[i] = new A(Integer.parseInt(st.nextToken())-1, Integer.parseInt(st.nextToken())-1, i);
			}
			
			MO(queries, q, (int) (Math.sqrt(n) + 1));
			
			
		}

	}
	
	public static void MO(A[] queries, int q, int len) {
		Arrays.parallelSort(queries, new Comparator<A>() {
			public int compare(A a, A b) {
				if (a.left/len == b.left/len) {
					return a.right - b.right;
				}
				else {
					return a.left/len - b.left/len;
				}
			}
		});
		
		int[] answer = new int[q];
		int ans = 0;
		int[] count = new int[n+1];
		int[] val = new int[n+1];
		int left = 0;
		int right = 0;
		ans = add(arr[0], val, count);
		for (int i=0; i<q; i++) {
//			queries[i].print();
			
			while (left > queries[i].left) {
				left--;
				ans = add(arr[left], val, count);
			}
			
			while (right < queries[i].right) {
				right++;
				ans = add(arr[right], val, count);
			}
			
			while (left < queries[i].left) {
				ans = remove(arr[left], val, count);
				left++;
			}
			
			while (right > queries[i].right) {
				ans = remove(arr[right], val, count);
				right--;
			}
			answer[queries[i].index]= ans;
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<q; i++) {
			sb.append(answer[i] + "\n");
		}
		System.out.print(sb);
	}
	
	public static int add(int index, int[] count, int[] vals) {
		if (vals[index] > 0) {
			count[vals[index]]--;
		}
		vals[index]++;
		count[vals[index]]++;
		maxcount = Math.max(maxcount, vals[index]);
		numvals++;
		
		int last = maxcount;
		int rest = numvals - last;
		if (last <= rest+1) {
			return 1;
		}
		
		last -= rest;
		
		return last;
	}
	
	public static int remove(int index, int[] count, int[] vals) {
		if (vals[index] > 0) {
			count[vals[index]]--;
			if (vals[index] == maxcount && count[vals[index]] == 0) {
				maxcount=0;
			}
		}
		
		vals[index]--;
		count[vals[index]]++;
		maxcount = Math.max(maxcount, vals[index]);
		numvals--;
		
		int last = maxcount;
		int rest = numvals - last;
		if (last <= rest+1) {
			return 1;
		}
		
		last -= rest;
		
		return last;
	}
	
	static class A {
		int left, right, index;
		A (int a, int b, int c) {
			left = a; right = b; index = c;
		}
		void print() {
			System.out.println(left + " " + right);
		}
	}
}