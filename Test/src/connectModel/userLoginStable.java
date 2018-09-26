package connectModel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;

import net.sf.json.JSONObject;
import util.ArrayUtil;
import util.SocketUtil;
import util.codeVF;
import util.config;
import util.heartSer;
import util.inNetben;
import util.login;
import util.loginOut;
import util.updatePas;
import util.userLogin;

/**
 * 用户登录接口测试
 * 
 * @author yangh·
 *
 */
public class userLoginStable implements Runnable {
	userLogin ben;
	PrintWriter loginWrite;
	String phone;
	Date date;
	ArrayUtil util = new ArrayUtil();
	Base64.Decoder decoder = Base64.getDecoder();
	// Socket socket = new Socket();

	SocketAddress socketaddress = new InetSocketAddress(config.IP_ADDR, config.PORT);

	public userLoginStable(String pho, Date date) {
		this.date = date;
		this.phone = pho;
	}

	public void run() {
		try {
			ben = new userLogin(phone);
			// updatePas us = new updatePas("15588186404");
			// codeVF code = new codeVF("15588186404");
			// inNetben net = new inNetben("15588186404");
			// band ban = new band("13000000000",);
			Socket socket = new Socket(config.IP_ADDR, config.PORT);
			// byte[] by = util.MergerArray(ben.getUserLoginBen(),
			// ben.getUserLoginBen());
			byte[] sub = ben.getUserLoginBen();
			byte[] su1 = ben.getUserLoginBen();
			int sta = sub.length / 2;
			int len = sub.length;
			byte[] one = util.SubArray(sub, 0, sta);
			byte[] two = util.SubArray(sub, sta, len - sta);
			SocketUtil.SendLn(new String(su1), socket);
			SocketUtil.SendLn(new String(one), socket);
			SocketUtil.SendLn(new String(two), socket);
			//
			String respstr = SocketUtil.AcceptBase64Read(socket);
			System.out.println(respstr);
			//
			// Map map = isStategetMap(respstr);
			// if (map != null) {
			// String phone = (String) map.get("phone");
			// String key = (String) map.get("key");
			// login log = new login(phone, key);
			// SocketUtil.SendLn(new String(log.getBase64String()), socket);
			// System.out.println("登入" + SocketUtil.AcceptBase64Read(socket));
			//
			// }
			// SocketUtil.SendLn(new String(net.getBase64Ben()), socket);
			// String respstrcode = SocketUtil.AcceptBase64Read(socket);

			// System.out.println(respstrcode);
			//
			// SocketUtil.SendLn(new String(us.getBase64Ben()), socket);
			// String respstr = SocketUtil.AcceptBase64Read(socket);
			// System.out.println(respstr);
			socket.close();

		} catch (SocketTimeoutException |

				NullPointerException e) {
			e.printStackTrace();
		} catch (ConnectException e) {
			inputWrite("fai");
		} catch (SocketException e) {
			inputWrite("fai");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 心跳
	// public void heart(String phone, Socket socket) throws Exception {
	// heartSer ser = new heartSer(phone);
	// while (true) {
	//
	// SocketUtil.Send(new String(ser.getBase64String()), socket);
	// socket.shutdownOutput();
	// String respstr = SocketUtil.AcceptBase64(socket);
	// System.out.println(respstr);
	// Thread.sleep(30000);
	// }
	// }

	public Map isStategetMap(String accept) {
		Map<String, Object> map = null;
		JSONObject json = JSONObject.fromString(accept);
		JSONObject jsonPes = json.getJSONObject("Data");
		if (jsonPes.get("Sta").equals("Ok")) {
			map = new HashMap<String, Object>();
			map.put("key", jsonPes.get("Key"));
			map.put("phone", json.get("Rece"));
		}
		return map;

	}

	public void inputWrite(String state) {
		try {
			File user = new File("E:/Client/Stable/Login/" + config.initTime.format(date) + ".txt");
			loginWrite = new PrintWriter(new FileWriter(user, true));
			loginWrite.println(state);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loginWrite.flush();
		loginWrite.close();

	}
}
