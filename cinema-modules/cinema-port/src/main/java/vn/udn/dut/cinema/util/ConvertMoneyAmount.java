package vn.udn.dut.cinema.util;

public class ConvertMoneyAmount {
	private static final String[] t = { "không", "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín" };

	private static String r(int r, boolean n) {
		String o = "";
		int a = r / 10;
		int e = r % 10;

		if (a > 1) {
			o = " " + t[a] + " mươi";
			if (e == 1) {
				o += " mốt";
			}
		} else if (a == 1) {
			o = " mười";
			if (e == 1) {
				o += " một";
			}
		} else if (n && e > 0) {
			o = " lẻ";
		}

		if (e != 5 && a >= 1) {
			switch (e) {
			case 1:
				o += " một";
				break;
			case 4:
				o += " tư";
				break;
			default:
				if (e > 1 || (e == 1 && a == 0)) {
					o += " " + t[e];
				}
			}
		} else if (e == 5) {
			o += " lăm";
		}

		return o;
	}

	private static String n(int n, boolean o) {
		String a = "";
		int e = n / 100;
		n %= 100;

		if (o || e > 0) {
			a = " " + t[e] + " trăm";
			a += r(n, true);
		} else {
			a = r(n, false);
		}

		return a;
	}

	public static String read(long r) {
		if (r == 0) {
			return t[0];
		}

		// String ty = "";
		String n = "";
		String a = "";

		do {
			// ty = Long.toString(r % 1_000_000_000);
			r = r / 1_000_000_000;

			if (r > 0) {
				n = n((int) r, true) + a + n;
				a = " tỷ";
			} else {
				n = n((int) r, false) + a + n;
			}
		} while (r > 0);

		String totalText = n.trim();
		totalText = totalText.substring(0, 1).toUpperCase() + totalText.substring(1);
		return totalText;
	}
}
