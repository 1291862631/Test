package util;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class inNetben {
	JSONObject body;
	JSONObject pes;

	util ul = new util();
	String userPhone;

	/**
	 * 设备入网
	 */
	public inNetben(String userPhone) {

		body = new JSONObject();
		pes = new JSONObject();
		try {
			pes.put("Time", "200");

			body.put("Ver", "1.0");
			body.put("Send", userPhone);
			body.put("Rece", "100003");
			body.put("Msg", "101");
			body.put("Type", "Set");
			body.put("Cmd", "IntNet");
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
