package util;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class updatePas {
	JSONObject body;
	JSONObject pes;

	util ul = new util();
	String userPhone;

	/**
	 * 数据源
	 */
	public updatePas(String userPhone) {

		body = new JSONObject();
		pes = new JSONObject();
		try {
			pes.put("DevID", userPhone);
			pes.put("HwID", "100000000000000000asdsafwqewqsawew");
			pes.put("Pass", "yhc123456");
			pes.put("Code", "9999");
			pes.put("OS", "Android6.0");

			body.put("Ver", "1.0");
			body.put("Send", userPhone);
			body.put("Rece", "0");
			body.put("Msg", "3");
			body.put("Type", "Set");
			body.put("Cmd", "UserPw");
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
	public byte[] getBase64Ben() {
		return ul.base64(body.toString());
	}

}
