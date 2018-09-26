package util;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class unband {
	JSONObject body;
	JSONObject pes;

	util ul = new util();

	/**
	 * 解除绑定
	 */
	public unband(String userPhone, String devid) {

		body = new JSONObject();
		pes = new JSONObject();

		try {

			pes.put("Type", "GateWay");
			pes.put("DevId", devid);

			body.put("Ver", "1.0");
			body.put("Send", userPhone);
			body.put("Rece", "0");
			body.put("Msg", "101");
			body.put("Type", "Set");
			body.put("Cmd", "UnBand");

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
