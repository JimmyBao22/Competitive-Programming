
import java.util.*;
import java.io.*;

public class PetyasExams {

	// https://codeforces.com/contest/978/problem/G
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("PetyasExams"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		PriorityQueue<Exam> examdays = new PriorityQueue<>(
	    		new Comparator<Exam>() {
	    			public int compare(Exam x, Exam y) {
	    				return x.examday - y.examday;
	    			}
	    });
		
		PriorityQueue<Exam> exampublish = new PriorityQueue<>(
	    		new Comparator<Exam>() {
	    			public int compare(Exam x, Exam y) {
	    				return x.exampublished - y.exampublished;
	    			}
	    });

		HashMap<Exam, Integer> index = new HashMap<>();
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			Exam cur = new Exam(a,b,c);
			exampublish.add(cur);
			index.put(cur, i+1);
		}
	
		ArrayList<Exam> can_finish = new ArrayList<>();
		
		int day=1;
		ArrayList<Integer> ans = new ArrayList<>();
		
		while (day <= n) {
			while (exampublish.size()>0 && day == exampublish.peek().exampublished) {
				Exam cur = exampublish.poll();
				examdays.add(cur);
			}
			boolean donesomething=false;
			for (int i=0; i<can_finish.size(); i++) {
				if (can_finish.get(i).examday == day) {
					ans.add(m+1);
					donesomething=true;
					can_finish.remove(i);
					break;
				}
			}
			if (!donesomething && examdays.size()>0) {
				examdays.peek().daysfinished++;
				ans.add(index.get(examdays.peek()));
				if (examdays.peek().daysfinished == examdays.peek().daysneeededtoprepare) {
					can_finish.add(examdays.poll());
				}
			}
			
			if (ans.size()<day) ans.add(0);
			
			day++;
		}
		
		if (can_finish.size()>0 || exampublish.size()>0 || examdays.size()>0) {
			System.out.println(-1);
			return;
		}
		for (int i=0; i<ans.size(); i++) {
			System.out.print(ans.get(i) + " ");
		}
	}
	
	static class Exam {
		int examday;
		int daysneeededtoprepare;
		int exampublished;
		int daysfinished;
		Exam (int a,int b,  int c) {
			exampublished = a;
			examday = b;
			daysneeededtoprepare =c;
			daysfinished=0;
		}
	}
}