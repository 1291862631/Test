package util;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class LgRoom
{
	JSONObject body;
	JSONObject pes;

	util ul = new util();

	/**
	 * 数据源
	 */
	public LgRoom(String userPhone)
	{

		body = new JSONObject();
		pes = new JSONObject();

		try
		{

			body.put("Cmd", "List");
			body.put("Type", "Get");
			body.put("Rece", "0");
			body.put("Msg", "18");
			body.put("Send", userPhone);
			body.put("Ver", "1.0");

			pes.put("Type", "LgRoom");
			pes.put("Ver", "0");

			body.put("Data", pes);

			// System.out.println(body.toString());
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
