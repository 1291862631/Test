package util;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class userOut {
	JSONObject body;
	JSONObject pes;

	util ul = new util();
	String userPhone;

	/**
	 * 数据源
	 */
	public userOut(String userPhone, String key) {

		body = new JSONObject();
		pes = new JSONObject();
		try {
			pes.put("key", key);
			pes.put("HwID", "19002312412");
			pes.put("DevID", userPhone);

			body.put("Ver", "1.0");
			body.put("Send", userPhone);
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
	 * 获取加密数据
	 * 
	 * @return
	 */
	public byte[] getBase64Ben() {
		return ul.base64(body.toString());
	}

}
