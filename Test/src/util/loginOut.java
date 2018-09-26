package util;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class loginOut {
	JSONObject body;
	JSONObject pes;

	String userPhone;
	getPhone phone = new getPhone();
	util ul = new util();

	/**
	 * 用户退出
	 */
	public loginOut(String userphone, String key) {
		body = new JSONObject();
		pes = new JSONObject();
		try {
			pes.put("DevID", userphone);
			pes.put("HwID", "100000000000000000asdsafwqewqsawew");
			pes.put("Key", key);

			body.put("Ver", "1.0");
			body.put("Send", userphone);
			body.put("Rece", "0");
			body.put("Msg", "3");
			body.put("Type", "Set");
			body.put("Cmd", "LoginOut");
			body.put("Data", pes);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取加密数据s
	 * 
	 * @return
	 */
	public byte[] getBase64String() {

		return ul.base64(body.toString());
	}

}
