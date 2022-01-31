
import java.util.*;
import java.io.*;

public class ballet {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=278
	
	static int n;
	static char[][] instructions;
	static int max = 3000;
	static int[][] arr = new int[max][max];
	static int[] FL= new int[] {1499, 1500}, FR= new int[] {1500, 1500};
	static int[] RL= new int[] {1499, 1499}, RR= new int[] {1500, 1499};
	static int[] maxpos = new int[] {1500, 1500}, minpos = new int[] {1499, 1499};
	static int dir=0;
		// 0 = north; 1 = south; 2 = east; 3 = west
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("ballet.in"));
		PrintWriter out = new PrintWriter(new FileWriter("ballet.out"));
		
		n = Integer.parseInt(in.readLine());
		for (int i=0; i<n; i++) {
			char[] cur = in.readLine().toCharArray();
			if (cur[2] == 'P') {
				rotate(cur);
			}
			else {
				move(cur);
			}
			if (!checkwork()) {
				System.out.println(-1);
				out.println(-1);
				out.close();
				return;
			}
			updateborders();
		}

		if (!checkwork()) {
			System.out.println(-1);
			out.println(-1);
			out.close();
			return;
		}
		updateborders();
		long area = (long)(maxpos[0] - minpos[0]+1) * (long)(maxpos[1] - minpos[1]+1);
		
		System.out.println(area);
		out.println(area);
		out.close();
	}
	
	public static void move(char[] cur) {
		if (cur[0] == 'F') {
			if (cur[1] == 'L') {
				if (dir == 0) {
					if (cur[2] == 'F') FL[1]++;
					else if (cur[2] == 'B') FL[1]--;
					else if (cur[2] == 'R') FL[0]++;
					else FL[0]--;
				}
				else if (dir == 1) {
					if (cur[2] == 'F') FL[1]--;
					else if (cur[2] == 'B') FL[1]++;
					else if (cur[2] == 'R') FL[0]--;
					else FL[0]++;
				}
				else if (dir== 2) {
					if (cur[2] == 'F') FL[0]++;
					else if (cur[2] == 'B') FL[0]--;
					else if (cur[2] == 'R') FL[1]--;
					else FL[1]++;
				}
				else {
					if (cur[2] == 'F') FL[0]--;
					else if (cur[2] == 'B') FL[0]++;
					else if (cur[2] == 'R') FL[1]++;
					else FL[1]--;
				}
			}
			else {
				if (dir == 0) {
					if (cur[2] == 'F') FR[1]++;
					else if (cur[2] == 'B') FR[1]--;
					else if (cur[2] == 'R') FR[0]++;
					else FR[0]--;
				}
				else if (dir == 1) {
					if (cur[2] == 'F') FR[1]--;
					else if (cur[2] == 'B') FR[1]++;
					else if (cur[2] == 'R') FR[0]--;
					else FR[0]++;
				}
				else if (dir== 2) {
					if (cur[2] == 'F') FR[0]++;
					else if (cur[2] == 'B') FR[0]--;
					else if (cur[2] == 'R') FR[1]--;
					else FR[1]++;
				}
				else {
					if (cur[2] == 'F') FR[0]--;
					else if (cur[2] == 'B') FR[0]++;
					else if (cur[2] == 'R') FR[1]++;
					else FR[1]--;
				}
			}
		}
		else {
			if (cur[1] == 'L') {
				if (dir == 0) {
					if (cur[2] == 'F') RL[1]++;
					else if (cur[2] == 'B') RL[1]--;
					else if (cur[2] == 'R') RL[0]++;
					else RL[0]--;
				}
				else if (dir == 1) {
					if (cur[2] == 'F') RL[1]--;
					else if (cur[2] == 'B') RL[1]++;
					else if (cur[2] == 'R') RL[0]--;
					else RL[0]++;
				}
				else if (dir== 2) {
					if (cur[2] == 'F') RL[0]++;
					else if (cur[2] == 'B') RL[0]--;
					else if (cur[2] == 'R') RL[1]--;
					else RL[1]++;
				}
				else {
					if (cur[2] == 'F') RL[0]--;
					else if (cur[2] == 'B') RL[0]++;
					else if (cur[2] == 'R') RL[1]++;
					else RL[1]--;
				}
			}
			else {
				if (dir == 0) {
					if (cur[2] == 'F') RR[1]++;
					else if (cur[2] == 'B') RR[1]--;
					else if (cur[2] == 'R') RR[0]++;
					else RR[0]--;
				}
				else if (dir == 1) {
					if (cur[2] == 'F') RR[1]--;
					else if (cur[2] == 'B') RR[1]++;
					else if (cur[2] == 'R') RR[0]--;
					else RR[0]++;
				}
				else if (dir== 2) {
					if (cur[2] == 'F') RR[0]++;
					else if (cur[2] == 'B') RR[0]--;
					else if (cur[2] == 'R') RR[1]--;
					else RR[1]++;
				}
				else {
					if (cur[2] == 'F') RR[0]--;
					else if (cur[2] == 'B') RR[0]++;
					else if (cur[2] == 'R') RR[1]++;
					else RR[1]--;
				}
			}
		}
	}
	
	public static void rotate(char[] cur) {
		if (cur[0] == 'F') {
			if (cur[1] == 'L') {
				Rotate(FL);
			}
			else {
				Rotate(FR);
			}
		}
		else {
			if (cur[1] == 'L') {
				Rotate(RL);
			}
			else {
				Rotate(RR);
			}
		}
		if (dir == 0) {
			dir = 2;
		}
		else if (dir == 1) {
			dir = 3;
		}
		else if (dir== 2) {
			dir = 1;
		}
		else {
			dir = 0;
		}
	}
	
	public static void updateborders() {
		maxpos[0] = Math.max(maxpos[0], Math.max(Math.max(Math.max(FL[0], FR[0]), RL[0]), RR[0]));
		maxpos[1] = Math.max(maxpos[1], Math.max(Math.max(Math.max(FL[1], FR[1]), RL[1]), RR[1]));
		minpos[0] = Math.min(minpos[0], Math.min(Math.min(Math.min(FL[0], FR[0]), RL[0]), RR[0]));
		minpos[1] = Math.min(minpos[1], Math.min(Math.min(Math.min(FL[1], FR[1]), RL[1]), RR[1]));
	}
	
	public static boolean checkwork() {
		if (FL[0] == FR[0] && FL[1] == FR[1]) return false;
		if (FL[0] == RL[0] && FL[1] == RL[1]) return false;
		if (FL[0] == RR[0] && FL[1] == RR[1]) return false;
		if (FR[0] == RL[0] && FR[1] == RL[1]) return false;
		if (FR[0] == RR[0] && FR[1] == RR[1]) return false;
		if (RR[0] == RL[0] && RR[1] == RL[1]) return false;
		return true;
	}
	
	public static void Rotate(int[] base) {
		int a = base[0]; int b = base[1];	// rotate around base
		int[] temp = new int[] {FL[0], FL[1]};
		FL[0] = temp[1] - b + a;
		FL[1] = a - temp[0] + b;
		temp = new int[] {FR[0], FR[1]};
		FR[0] = temp[1] - b + a;
		FR[1] = a - temp[0] + b;
		temp = new int[] {RL[0], RL[1]};
		RL[0] = temp[1] - b + a;
		RL[1] = a - temp[0] + b;
		temp = new int[] {RR[0], RR[1]};
		RR[0] = temp[1] - b + a;
		RR[1] = a - temp[0] + b;
	}
	
	public static void print() {
		System.out.println("FL: " + FL[0] + " " + FL[1]);
		System.out.println("FR: " + FR[0] + " " + FR[1]);
		System.out.println("RL: " + RL[0] + " " + RL[1]);
		System.out.println("RR: " + RR[0] + " " + RR[1]);
		System.out.println("max: " + maxpos[0] + " " + maxpos[1]);
		System.out.println("min: " + minpos[0] + " " + minpos[1]);
	}
}