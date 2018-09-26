package util;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class AllRealDev
{
	JSONObject body;
	JSONObject pes;

	util ul = new util();

	/**
	 * 数据源
	 */
	public AllRealDev(String userPhone)
	{

		body = new JSONObject();
		pes = new JSONObject();

		try
		{

			body.put("Cmd", "List");
			body.put("Type", "Get");
			body.put("Rece", "0");
			body.put("Msg", "19");
			body.put("Send", userPhone);
			body.put("Ver", "1.0");

			pes.put("Type", "AllRealDev");
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
