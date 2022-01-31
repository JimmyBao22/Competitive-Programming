
import java.util.*;
import java.io.*;

public class ClosestPick {

	// https://codingcompetitions.withgoogle.com/codejam/round/00000000004362d7/00000000007c0f00
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("ClosestPick"));

		int t = Integer.parseInt(in.readLine());
		for (int tt = 1; tt < t + 1; tt++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int n = Integer.parseInt(st.nextToken());
			int k = Integer.parseInt(st.nextToken());
			int[] arr2=new int[n];
			st = new StringTokenizer(in.readLine());
			for (int i=0; i<n; i++) {
				arr2[i] = Integer.parseInt(st.nextToken());
			}
			Arrays.parallelSort(arr2);
			ArrayList<Integer> better = new ArrayList<>();
			better.add(arr2[0]);
			for (int i=1; i<n; i++) {
				if (arr2[i] != arr2[i-1]) better.add(arr2[i]);
			}
			
			int[] arr = new int[better.size()];
			for (int i=0; i<better.size(); i++) {
				arr[i] = better.get(i);
			}
			
			long ans = 0;
			
			n = better.size();
			
			for (int i=0; i<n; i++) {
				for (int leftone = 0; leftone<2; leftone++) {
					if (leftone == 0) {
						if (arr[i] == 1) continue;
						if (i != 0 && arr[i-1] == arr[i]-1) continue;
					}
					else {
						if (arr[i] == k) continue;
						if (i != n-1 && arr[i+1] == arr[i]+1) continue;
					}
					for (int j=i; j<n; j++) {
						for (int lefttwo = 0; lefttwo<2; lefttwo++) {
							if (lefttwo == 0) {
								if (arr[j] == 1) continue;
								if (j != 0 && arr[j-1] == arr[j]-1) continue;
							}
							else {
								if (arr[j] == k) continue;
								if (j != n-1 && arr[j+1] == arr[j]+1) continue;
							}

							long curans = 0;
							if (i == j && leftone == lefttwo) {
								if (leftone == 0) {
									if (i == 0) {
										curans += arr[i]-1;
									}
									else {
										int lastmiddle = (arr[i-1] + arr[i]-1 )/2;
										while (Math.abs(lastmiddle - (arr[i]-1)) >= Math.abs(lastmiddle - arr[i-1])) lastmiddle++;
										curans += (arr[i]-1 - lastmiddle+1);
									}
								}
								else {
									if (i == n-1) {
										curans += k - (arr[i]+1) + 1;
									}
									else {
										int nextmiddle = (arr[i+1] + arr[i]+1 )/2;
										while (Math.abs(nextmiddle - (arr[i]+1)) >= Math.abs(nextmiddle - arr[i+1])) nextmiddle--;
										curans += (nextmiddle - (arr[i]+1) + 1);
									}
								}
							}
							else if (j == i+1 && leftone == 1 && lefttwo == 0) {
								curans = arr[i+1]-1 - (arr[i]+1) + 1;
							}
							else {
								if (leftone == 0) {
									if (i == 0) {
										curans += arr[i]-1;
									}
									else {
										int lastmiddle = (arr[i-1] + arr[i]-1 )/2;
										while (Math.abs(lastmiddle - (arr[i]-1)) >= Math.abs(lastmiddle - arr[i-1])) lastmiddle++;
										curans += (arr[i]-1 - lastmiddle+1);
									}
								}
								else {
									if (i == n-1) {
										curans += k - (arr[i]+1) + 1;
									}
									else {
										int nextmiddle = (arr[i+1] + arr[i]+1 )/2;
										while (Math.abs(nextmiddle - (arr[i]+1)) >= Math.abs(nextmiddle - arr[i+1])) nextmiddle--;
										curans += (nextmiddle - (arr[i]+1) + 1);
									}
								}
								
								if (lefttwo == 0) {
									if (j == 0) {
										curans += arr[j]-1;
									}
									else {
										int lastmiddle = (arr[j-1] + arr[j]-1 )/2;
										while (Math.abs(lastmiddle - (arr[j]-1)) >= Math.abs(lastmiddle - arr[j-1])) lastmiddle++;
										curans += (arr[j]-1 - lastmiddle+1);
									}
								}
								else {
									if (j == n-1) {
										curans += k - (arr[j]+1) + 1;
									}
									else {
										int nextmiddle = (arr[j+1] + arr[j]+1 )/2;
										while (Math.abs(nextmiddle - (arr[j]+1)) >= Math.abs(nextmiddle - arr[j+1])) nextmiddle--;
										curans += (nextmiddle - (arr[j]+1) + 1);
									}
								}
							}
							
							ans = Math.max(ans, curans);
						}
					}	
				}
			}
			
			System.out.println("Case #" + tt + ": " + (double)ans/(double)k);
		}
	}
	
	public static int brute(int[] arr, int k) {
		int n = arr.length;
		int maxc=0;
		o: for (int i=1; i<=k; i++) {
			oo: for (int j=i; j<=k; j++) {
				for (int t=0; t<arr.length; t++) {
					if (arr[t] == i) continue o;
					if (arr[t] == j) continue oo;
				}
				int c=0;
				for (int t=1; t<=k; t++) {
					int minbad = (int)(1e9);
					for (int p=0; p<n; p++) {
						minbad =Math.min(minbad, Math.abs(arr[p] - t));
					}
					if (Math.min(Math.abs(i - t), Math.abs(j - t)) < minbad) {
						c++;
					}
				}
				maxc = Math.max(maxc, c);
			}
		}
		return maxc;
	}
}