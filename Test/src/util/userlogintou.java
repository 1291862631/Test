package util;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class userlogintou
{
	JSONObject body;
	JSONObject pes;

	String userPhone;
	getPhone phone = new getPhone();
	util ul = new util();

	/**
	 * 用户登录
	 */
	public userlogintou(String userphone)
	{
		body = new JSONObject();
		pes = new JSONObject();
		try
		{

			pes.put("HwID", "d33c8a358f93be6c753ae6d188d196e4");
			pes.put("DevID", userphone);
			pes.put("PassMd5", util.keyMd5("asd123"));
			pes.put("OS", "Android6.0");

			body.put("Ver", "1.0");
			body.put("Send", userphone);
			body.put("Rece", "0");
			body.put("Msg", "3");
			body.put("Type", "Set");
			body.put("Cmd", "UserLogin");
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
	public byte[] getUserLoginBen()
		{

			return ul.base64(body.toString());
		}

}
