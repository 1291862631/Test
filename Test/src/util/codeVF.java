package util;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class codeVF {
	JSONObject body;
	JSONObject pes;

	util ul = new util();
	String userPhone;

	/**
	 * 数据源
	 */
	public codeVF(String userPhone) {

		body = new JSONObject();
		pes = new JSONObject();
		try {
			pes.put("OS", "Android6.0");
			pes.put("HwID", "100000000000000000asdsafwqewqsawew");
			pes.put("DevID", userPhone);
			pes.put("Code", "9999");

			body.put("Ver", "1.0");
			body.put("Send", userPhone);
			body.put("Rece", "0");
			body.put("Msg", "2");
			body.put("Type", "Set");
			body.put("Cmd", "CodeVf");
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
