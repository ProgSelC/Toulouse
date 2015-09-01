package dalembert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

public class Dalembert {
	public static void main(String[] args) {
		System.out.println(processFile("Даламбер.txt"));
	}

	public static String processFile(String filename) {
		boolean beginRead = false;
		int spacesCount = 0;
		ArrayList<String> lines = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String str = "";
			while ((str = br.readLine()) != null) {
				if (beginRead) {
					if (str.matches("[0-9]{1,2}\\s.*")) {
						lines.add(str);
					}
				}
				if (str.matches("\\s*")) {
					if (++spacesCount == 3) {
						beginRead = true;
					}
				} else {
					spacesCount = 0;
				}
			}
		} catch (IOException e) {
			System.out.println("Error reading file!");
		}
		String result = "";
		for(String line:lines){
			result += (char)evalSingle(line, 175);
		}
		return result;
	}

	static int evalSingle(String line, int mod) {
		TreeSet<Integer> ts = new TreeSet<Integer>();
		String[] digits = line.split(" ");
		for (int[] places : genVariants()) {
			String res = "";
			for (int i : places) {
				res += digits[i];
			}
			ts.add(Integer.parseInt(res));
			res = "";
		}
		return (ts.first() + ts.last()) % mod;
	}

	static ArrayList<int[]> genVariants() {
		int[] a = { 0, 1, 2, 3, 4 };
		boolean[] used = new boolean[a.length];
		int[] places = new int[a.length];
		ArrayList<int[]> variants = new ArrayList<int[]>();

		makeVariants(0, a, places, used, variants);
		return variants;
	}

	static void makeVariants(int index, int[] a, int[] places, boolean[] used,
			ArrayList<int[]> variants) {
		if (index == a.length) {
			variants.add(Arrays.copyOf(places, places.length));
			return;
		}
		for (int i = 0; i < a.length; i++) {
			if (!used[i]) {
				used[i] = true;
				places[index] = i;
				makeVariants(index + 1, a, places, used, variants);

				used[i] = false;

			}
		}
	}
}
