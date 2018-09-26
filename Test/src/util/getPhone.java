package util;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class getPhone {
	// �ж��Ƿ�绰��ʽ
	public static boolean isMobileNO(String mobiles) {

		Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

		Matcher matcher = pattern.matcher(mobiles);

		return matcher.matches();
	}

	// ��������绰����
	public String getMobile() {

		while (true) {
			String randomPhone = randomPhone();
			if (getPhone.isMobileNO(randomPhone)) {
				return randomPhone;
			}

		}

	}

	// ��������绰�����ʽ����
	public static String randomPhone() {
		String phone = "1";

		Random random = new Random();
		int nextInt = random.nextInt(3);

		if (nextInt == 0) {
			phone = phone + "3" + getPhone.randomNumber();
		} else if (nextInt == 1) {
			phone = phone + "5" + getPhone.randomNumber();
		} else {
			phone = phone + "8" + getPhone.randomNumber();
		}
		return phone;
	}

	// ���ɳ���Ϊ9�������
	public static String randomNumber() {

		Random random = new Random();
		int nextInt = random.nextInt(900000000) + 100000000;
		int abs = Math.abs(nextInt);
		String valueOf = String.valueOf(abs);
		return valueOf;
	}

}
