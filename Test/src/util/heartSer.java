package util;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class heartSer {
	JSONObject body;
	JSONObject pes;

	String userPhone;
	getPhone phone = new getPhone();
	util ul = new util();

	/**
	 * 心跳
	 */
	public heartSer() {

		body = new JSONObject();
		pes = new JSONObject();
		try {

			pes.put("GPS", "19002312412");

			body.put("Ver", "1.0");
			body.put("Send", "13405969308");
			body.put("Rece", "0");
			body.put("Msg", "0");
			body.put("Type", "Set");
			body.put("Cmd", "Heart");
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
	public byte[] getBase64String() {

		return ul.base64(body.toString());
	}

}
