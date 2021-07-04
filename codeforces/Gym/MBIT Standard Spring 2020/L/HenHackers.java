
import java.util.*;
import java.io.*;

public class HenHackers {

	// https://codeforces.com/gym/102620/problem/L
	// https://mbit.mbhs.edu/archive/2020/standard.pdf
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("HenHackers"));

		ArrayList<Character> all = new ArrayList<>();
			// contain all characters in pw
		
		for (int i=0; i<26; ++i) {
			System.out.println((char)(i + 'a'));
			char ret = in.readLine().charAt(0);
			if (ret == 'C') return;
			if (ret == 'Y') all.add((char) (i + 'a'));
			System.out.println((char)(i + 'A'));
			ret = in.readLine().charAt(0);
			if (ret == 'C') return;
			if (ret == 'Y') all.add((char) (i + 'A'));
		}
		
		for (int i=0; i<10; i++ ) {
			System.out.println((char)(i + '0'));
			char ret = in.readLine().charAt(0);
			if (ret == 'C') System.exit(0);
			if (ret == 'Y') all.add((char)(i + '0'));
		}
		
		if (all.size() == 0) {
			System.out.println("");
			System.exit(0);
		}
		
		String s = all.get(0) + "";
		for (int i=1; i<all.size(); i++) {
			Character cur = all.get(i);
			
			int min = 0;
			int max = s.length();
			while (min < max) {
				int middle = (min + max) / 2;
				String c = "" + cur + s.charAt(middle);
				System.out.println(c);
				char ret = in.readLine().charAt(0);
				if (ret == 'C') System.exit(0);
				if (ret == 'Y') {
					max = middle;
				}
				else min = middle+1;
			}
			
			if (min == s.length()) {
				s += cur;
			}
			else {
				s = s.substring(0, min) + cur + s.substring(min);
			}
		}
		System.out.println(s);
		System.exit(0);
	}
}