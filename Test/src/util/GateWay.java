package util;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class GateWay
{
	JSONObject body;
	JSONObject pes;

	util ul = new util();

	/**
	 * ����Դ
	 */
	public GateWay(String userPhone)
	{
		body = new JSONObject();
		pes = new JSONObject();
		try
		{

			body.put("Cmd", "List");
			body.put("Msg", "12");
			body.put("Rece", "0");
			body.put("Send", "15828532049");
			body.put("Type", "Get");
			body.put("Ver", "1.0");
			pes.put("Type", "GateWay");
			pes.put("Ver", "0");

			body.put("Data", pes);

			System.out.println(body.toString());
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡ��������
	 * 
	 * @return
	 */
	public byte[] getBase64Ben()
		{
			return ul.base64(body.toString());
		}

}
