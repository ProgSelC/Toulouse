package fermat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

public class Fermat {
	static long resultsCnt = 0L;

	public static void main(String[] args) {
		String encr = "74 545 1003 792 32 118 545 792 32 78 473 334 1039 190 792 792 46 32 1137 854 46 51 1137 50 890 571 32 45 396 1137 46 643 890 890 149 51 51 32 32 49 396 890 643";
		String mod = "1271";
		System.out.println(Decrypt(encr,getKey("Ферма.txt"),mod));
	}

	public static int getKey(String filename) {
		boolean beginRead = false;
		int spacesCount = 0;
		ArrayList<String> pyr = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String str = "";
			while ((str = br.readLine()) != null) {
				if (beginRead) {
					if (str.matches("[0-9]+")) {
						pyr.add(str);
					}
				}
				if (str.matches("\\s*")) {
					if (++spacesCount == 4) {
						beginRead = true;
					}
				} else {
					spacesCount = 0;
				}
			}
		} catch (IOException e) {
			System.out.println("Error reading file!");
		}

		return evalData(pyr);
	}

	public static int evalData(ArrayList<String> pyr) {
		int[] sum = new int[pyr.size() + 1];

		for (int i = (pyr.size() - 1); i >= 0; i--) {
			for (int j = 0; j < pyr.get(i).length(); j++) {
				int curNum = Integer.parseInt(pyr.get(i).charAt(j) + "");
				if (curNum + sum[j] < curNum + sum[j + 1]) {
					sum[j] = curNum + sum[j + 1];
				} else {
					sum[j] += curNum;
				}
			}
		}
		return sum[0];
	}
	
	public static String Decrypt(String encr, int key, String mod){
		String decr = "";
		for (String lt : encr.split(" ")) {
			BigInteger code = new BigInteger(lt);
			decr += (char)code.modPow(new BigInteger(key+""),
					new BigInteger(mod)).intValue();
		}
		return decr;
	}
}
