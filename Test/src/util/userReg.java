package util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Base64;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class userReg {
	JSONObject body;
	JSONObject pes;

	getPhone phone = new getPhone();
	util ul = new util();
	String userPhone;

	/**
	 * 数据源
	 */
	public userReg() {
		userPhone = phone.getMobile();
		body = new JSONObject();
		pes = new JSONObject();
		try {
			pes.put("Phone", userPhone);
			pes.put("HwID", "100000000000000000asdsafwqewqsawew");
			pes.put("DevID", userPhone);
			pes.put("Pass", "yhc123456");
			pes.put("Code", "9999");
			pes.put("OS", "Android6.0");

			body.put("Ver", "1.0");
			body.put("Send", userPhone);
			body.put("Rece", "0");
			body.put("Msg", "2");
			body.put("Type", "Set");
			body.put("Cmd", "UserReg");
			body.put("Data", pes);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取加密数据
	 * 
	 * @return
	 */
	public byte[] getUserRegBen() {
		return ul.base64(body.toString());
	}

}
