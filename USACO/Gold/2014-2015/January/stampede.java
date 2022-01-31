
import java.util.*;
import java.io.*;

public class stampede {

	// http://usaco.org/index.php?page=viewproblem2&cpid=511
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("stampede.in"));
		PrintWriter out = new PrintWriter(new FileWriter("stampede.out"));

		int n = Integer.parseInt(in.readLine());
		cow[] arr = new cow[n];
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			arr[i] = new cow(a,b,c);
		}
		
		Arrays.sort(arr);
		
		int count=0;
		TreeMap<Integer, Integer> badregions = new TreeMap<>();
				// x, y --> [x,y] is a bad region
		for (int i=0; i<n; i++) {
			//arr[i].print();
			Integer down = badregions.floorKey(arr[i].time1);	// region below time1
			Integer up = badregions.floorKey(arr[i].time2);		// region below time2
			
			if (down != null && badregions.get(down) >= arr[i].time2) {
				count++;
				continue;
			}
			
			// see if [time1,time2] intersects any of [down, badregions.get(down)]
				// or [up, badregions.get(up)]. If it does update accordingly
				// otherwise just add [time1,time2] to the map
			
			int bottom=0;
			if (down != null && up != null && up >= arr[i].time1) {
				int getup = badregions.get(up);
				badregions.remove(up);
				int getdown = getup;
				if (down != up) getdown = badregions.get(down);
				if (getdown < arr[i].time1) {
					badregions.put(arr[i].time1, Math.max(arr[i].time2, getup));
					bottom = arr[i].time1;
				}
				else {
					badregions.put(down, Math.max(arr[i].time2, getup));
					bottom = down;
				}
			}
			else if (down != null) {
				if (badregions.get(down) < arr[i].time1) {
					badregions.put(arr[i].time1, arr[i].time2);
					bottom = arr[i].time1;
				}
				else {
					badregions.put(down, arr[i].time2);
					bottom = down;
				}
			}
			else if (up != null && up >= arr[i].time1) {
				int get = badregions.get(up);
				badregions.remove(up);
				badregions.put(arr[i].time1, Math.max(arr[i].time2, get));
				bottom = arr[i].time1;
			}
			else {
				badregions.put(arr[i].time1, arr[i].time2);
				bottom = arr[i].time1;
			}
			
			// if I got rid of side elements, need to get rid of middle
				
				// ex. if I had [0,7], [8,10], [12,14] originally
				// IF [time1,time2] = [3,13], then
				// [down, badregions.get(down)] = [0,7]
				// [up, badregions.get(up)] = [12,14]
				
				// Therefore, update --> [0,14] [8,10]
					// so, need to get rid of [8,10]
			
			int bottom2 = bottom+1;
			while (true) {
				Integer key = badregions.ceilingKey(bottom2);
				if (key == null) break;
				if (badregions.get(key) <= badregions.get(bottom)) {
					badregions.remove(key);
				}
				else break;
			}
		}

		System.out.println(n-count);
		out.println(n-count);
		out.close();
	}
	
	public static int brute(int n, cow[] arr) {
		boolean[] good = new boolean[(int)(1e8)];
			// good[i] is the region [i,i+1]
			// this does not work because it should actually go up to (1e9) which
				// gives mle
		int count=0;
		for (int i=0; i<n; i++) {
			boolean works=false;
			for (int j=arr[i].time1; j<arr[i].time2 && j<good.length; ++j) {
				if (!good[j]) works=true;
				good[j]=true;
			}
			if (!works) count++;
		}
		return count;
	}
	
	static class cow implements Comparable<cow> {
		int firstx; int secondx; int y; 
		int time1; int time2; int r;
		cow (int a, int b, int r) {
			firstx = a+1; secondx = a;
			this.r = r;
			y = b;
			time1 = Math.abs(firstx * r);
			time2 = Math.abs(secondx*r);
		}
		public int compareTo(cow o) {
			return y-o.y;
		}
		void print() {
			System.out.println(time1 + " " + time2 + " " + y);
		}
	}
}