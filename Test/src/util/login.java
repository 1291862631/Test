package util;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class login
{
	JSONObject body;
	JSONObject pes;

	String userPhone;
	getPhone phone = new getPhone();
	util ul = new util();

	/**
	 * 登入
	 */
	public login(Object object, Object object2)
	{

		body = new JSONObject();
		pes = new JSONObject();
		try
		{
			pes.put("HwID", "d33c8a358f93be6c753ae6d188d196e4");
			pes.put("DevID", object);
			pes.put("Key", object2);
			pes.put("Ver", "1.0");
			pes.put("OS", "Android6.0");

			body.put("Ver", "1.0");
			body.put("Send", object);
			body.put("Rece", "0");
			body.put("Msg", "3");
			body.put("Type", "Set");
			body.put("Cmd", "Login");
			body.put("Data", pes);
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
	public byte[] getBase64String()
		{
			return ul.base64(body.toString());
		}

}
