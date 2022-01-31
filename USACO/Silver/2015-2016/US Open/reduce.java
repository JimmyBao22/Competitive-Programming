
import java.util.*;
import java.io.*;

public class reduce {
	
	// http://www.usaco.org/index.php?page=viewproblem2&cpid=642
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("reduce.in"));

		int n = Integer.parseInt(in.readLine()); 
		
		Cow[] sortedHoriz = new Cow[n];
		for (int i = 0; i < n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int x = Integer.parseInt(st.nextToken());  
			int y = Integer.parseInt(st.nextToken());  

			sortedHoriz[i] = new Cow(x, y);
		}
		
		in.close();

		Cow[] sortedVert = Arrays.copyOf(sortedHoriz, n);
		
		Arrays.sort(sortedHoriz, new HComparator());
		Arrays.sort(sortedVert, new VComparator());

		int result = (sortedHoriz[n-1].x - sortedHoriz[0].x) * (sortedVert[n-1].y - sortedVert[0].y);
		// this is og area
		
		// most extreme 3 cows in each direction can be sold
		ArrayList<Cow> extremes = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			extremes.add(sortedHoriz[i]);
			extremes.add(sortedHoriz[n-1-i]);
			extremes.add(sortedVert[i]);
			extremes.add(sortedVert[n-1-i]);
		}
		
		for (int i = 0; i < extremes.size(); i++) {
			// 'sell' cow i
			extremes.get(i).sold = true;
			
			// order of sales is irrelevant
			for (int j = i+1; j < extremes.size(); j++) {
				extremes.get(j).sold = true;
				
				for (int k = j+1; k < extremes.size(); k++) {
					extremes.get(k).sold = true;
					
					
					// find new extremes by finding first and last unsold cows
					int p = 0;
					while (sortedHoriz[p].sold) p++;
					int xMin = sortedHoriz[p].x;
					p = n-1;
					while (sortedHoriz[p].sold) p--;
					int xMax = sortedHoriz[p].x;
					
					p = 0;
					while (sortedVert[p].sold) p++;
					int yMin = sortedVert[p].y;
					p = n-1;
					while (sortedVert[p].sold) p--;
					int yMax = sortedVert[p].y;
					
					int area = (xMax - xMin) * (yMax - yMin);
					result = Math.min(result, area);
					
					extremes.get(k).sold = false;
				}
				extremes.get(j).sold = false;
			}
			extremes.get(i).sold = false;
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("reduce.out"));
		System.out.println(result);
		out.println(result);
		out.close();

	}

	static class HComparator implements Comparator<Cow> {
		public int compare(Cow a, Cow b) {
			return a.x - b.x;
		}
	}
	
	static class VComparator implements Comparator<Cow> {
		public int compare(Cow a, Cow b) {
			return a.y - b.y;
		}
	}
	
	static class Cow {
		int x, y;
		boolean sold;
		
		Cow(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}