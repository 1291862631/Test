package util;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class Heart
{
	JSONObject body;
	JSONObject pes;

	util ul = new util();

	/**
	 * 数据源
	 */
	public Heart()
	{
		body = new JSONObject();
		pes = new JSONObject();
		// {"Type":"Set",
		// "Ver":"1.0",
		// "Data":{"GPS":"0,0"},
		// "Cmd":"Heart",
		// "Rece":"0",
		// "Send":"15828532049",
		// "Msg":"0"}
		try
		{
			body.put("Type", "Set");
			body.put("Ver", "1.0");
			body.put("Rece", "0");
			body.put("Send", "15828532049");
			body.put("Msg", "0");
			body.put("Cmd", "Heart");

			pes.put("GPS", "0,0");
			body.put("Data", pes);
			System.out.println(body.toString());
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 获取加密数据
	 * 
	 * @return
	 */
	public byte[] getBase64Ben()
		{
			return ul.base64(body.toString());
		}

}
