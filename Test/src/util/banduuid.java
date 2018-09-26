package util;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class banduuid {
	JSONObject body;
	JSONObject pes;
	JSONObject att;

	util ul = new util();

	/**
	 * 数据源
	 */
	public banduuid(String userPhone, String uuid) {

		body = new JSONObject();
		pes = new JSONObject();
		att = new JSONObject();
		try {
			att.put("UID", uuid);

			pes.put("Type", "GateWay");

			body.put("Ver", "1.0");
			body.put("Send", userPhone);
			body.put("Rece", "0");
			body.put("Msg", "101");
			body.put("Type", "Set");
			body.put("Cmd", "Band");

			pes.put("Attrib", att);
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
