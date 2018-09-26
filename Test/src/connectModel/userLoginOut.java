package connectModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Date;
import net.sf.json.JSONObject;
import util.SocketUtil;
import util.config;
import util.userLogin;
import util.userReg;

/**
 * 用户注册稳定性
 * 
 * @author yangh
 *
 */
public class userLoginOut implements Runnable {
	userLogin ben;

	PrintWriter writeFail, writeSucess, userPhone;
	String phone;
	Date date;

	public userLoginOut(Date time) {
		this.date = time;
	}

	public void run() {
		socket();
	}

	public void socket() {
		try {

			userReg reg = new userReg();
			Socket socket = new Socket(config.IP_ADDR, config.PORT);
			SocketUtil.Send(new String(reg.getUserRegBen()), socket);
			socket.shutdownOutput();

			phone = getReg(SocketUtil.AcceptBase64(socket));

			System.out.println(phone);
			if (phone != null) {
				inputSucess(phone);
			}
			socket.shutdownInput();
			// 关闭资源
			socket.close();
		} catch (SocketTimeoutException | NullPointerException e) {
			e.printStackTrace();
		} catch (ConnectException e) {
			inputFail("refuse");
		} catch (SocketException e) {
			inputFail("interrupt");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getReg(String accept) {
		String phone = null;
		JSONObject json = JSONObject.fromString(accept);
		JSONObject jsonPes = json.getJSONObject("Data");
		if (jsonPes.get("Sta").equals("Ok")) {
			phone = json.getString("Rece");
		}
		return phone;
	}

	public void inputSucess(String phone) {
		try {
			File fileSucess = new File("E:/Client/Stable/Reg" + config.initTime.format(date) + "Success.txt");
			File user = new File("E:/Client/Phone/TestLogin.txt");
			userPhone = new PrintWriter(new FileWriter(user, true));
			writeSucess = new PrintWriter(new FileWriter(fileSucess, true));
			userPhone.println(phone);
			writeSucess.println(phone);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		userPhone.flush();
		writeSucess.flush();

		writeSucess.close();
		userPhone.close();
	}

	public void inputFail(String ConnectException) {
		try {
			File fielFail = new File("E:/Client/Stable/Reg" + config.initTime.format(date) + "Fail.txt");
			writeFail = new PrintWriter(new FileWriter(fielFail, true));
			writeFail.println(ConnectException);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writeFail.flush();
		writeFail.close();

	}
}
