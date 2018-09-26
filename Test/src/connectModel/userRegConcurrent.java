package connectModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import net.sf.json.JSONObject;
import util.AllRealDev;
import util.ArrayUtil;
import util.SocketUtil;
import util.banduuid;
import util.config;
import util.GateWay;
import util.Heart;
import util.LgRoom;
import util.login;
import util.updatePas;
import util.userLogin;
import util.userReg;
import util.userlogintou;
import util.util;

/**
 * 
 * @author yangh
 *
 */
public class userRegConcurrent implements Runnable
{
	userLogin ben;
	Socket socket;
	PrintWriter writeFail, writeSucess, userPhone;
	CyclicBarrier cyclicBarrier;
	String phone;
	Date date;
	ArrayUtil util = new ArrayUtil();
	userReg reg;
	updatePas Pas;
	GateWay gate;
	banduuid band;
	Map<String, Object> map;

	public userRegConcurrent(CyclicBarrier cyclicBarrier, Date time)
	{
		this.cyclicBarrier = cyclicBarrier;
		this.date = time;
	}

	public void run()
		{
			try
			{
				cyclicBarrier.await();
				socket();
			} catch (InterruptedException | BrokenBarrierException e)
			{
				e.printStackTrace();
			}

		}

	public void band()
		{
			try
			{
				band = new banduuid("15680607927", "121818060803A000005");
				socket = new Socket(config.IP_ADDR, config.PORT);
				socket.setSoTimeout(10000);
				SocketUtil.Sendin(new String(band.getBase64Ben()), socket);
				String pas = SocketUtil.AcceptBase64Read(socket);
				String phone = getRegState(pas);
				if (phone != null)
				{
					/**
					 * 绑定成功，设备入网1
					 */
				}

			} catch (Exception e)
			{
			}
		}

	public void subcontract()
		{
			try
			{
				reg = new userReg();
				socket = new Socket(config.IP_ADDR, config.PORT);
				socket.setSoTimeout(10000);
				byte[] by =
					{};
				for (int i = 0; i < 20; i++)
				{
					reg = new userReg();
					by = ArrayUtil.MergerArray10(by, reg.getUserRegBen());
				}

				int cont = by.length / 5;
				int start = 0;

				for (int i = 0; i < 5; i++)
				{

					byte[] array = ArrayUtil.SubArray(by, start, cont);
					SocketUtil.Sendin(new String(array), socket);
					start += cont;
				}
				SocketUtil.AcceptBase64Read(socket);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}

	public void regloginupdate()
		{
			try
			{
				reg = new userReg();
				socket = new Socket(config.IP_ADDR, config.PORT);
				socket.setSoTimeout(5000);
				SocketUtil.SendLn(new String(reg.getUserRegBen()), socket);

				String obj = SocketUtil.AcceptBase(socket);
				socket.close();
				String phone = getRegState(obj);

				if (getRegState(obj) != null)
				{
					socket = new Socket(config.IP_ADDR, config.PORT);
					socket.setSoTimeout(5000);
					userLogin ulogin = new userLogin(phone);
					SocketUtil.SendLn(new String(ulogin.getUserLoginBen()), socket);
					String userLogin = SocketUtil.AcceptBase(socket);
					map = getuserLoginState(userLogin);
					if (map != null)
					{

						login lo = new login(map.get("phone"), map.get("key"));
						SocketUtil.SendLn(new String(lo.getBase64String()), socket);
						String login = SocketUtil.AcceptBase(socket);
						String phoneState = loginstate(login);
						if (phoneState != null)
						{
							Pas = new updatePas(phoneState);
							SocketUtil.SendLn(new String(Pas.getBase64Ben()), socket);
							String pas = SocketUtil.AcceptBase(socket);
							System.out.println(pas);
						}

					}

				}
				socket.close();
			} catch (SocketTimeoutException | NullPointerException e)
			{
				e.printStackTrace();
			} catch (ConnectException e)
			{
				inputFail("refuse");
			} catch (SocketException e)
			{
				inputFail("interrupt");
			} catch (Exception e)
			{
				e.printStackTrace();
			}

		}

	public void socket()
		{
			try
			{
				socket = new Socket(config.IP_ADDR, config.PORT);
				socket.setSoTimeout(60000);
				userlogintou ulogin = new userlogintou("15828532049");
				SocketUtil.Sendin(new String(ulogin.getUserLoginBen()), socket);
				String userLogin = SocketUtil.AcceptBase(socket);
				System.out.println("user");
				map = getuserLoginState(userLogin);
				if (map != null)
				{
					System.out.println(map);
					System.out.println("login");
					login lo = new login(map.get("phone"), map.get("key"));
					SocketUtil.Sendin(new String(lo.getBase64String()), socket);
					String login = SocketUtil.AcceptBase(socket);
					String phoneState = loginstate(login);
					if (phoneState != null)
					{
						gate = new GateWay("");
						SocketUtil.SendLn(new String(gate.getBase64Ben()), socket);
						String pas = SocketUtil.AcceptBase64Read(socket);
						System.out.println("返回：" + pas);
					}
				}
				socket.close();
			} catch (Exception e)
			{
				e.printStackTrace();
			}

		}

	// 获取登入状态
	public String loginstate(String obj)
		{

			String phone = null;
			JSONObject object = JSONObject.fromString(obj);
			JSONObject data = object.getJSONObject("Data");
			if (data.get("Sta").equals("Ok"))
			{
				phone = object.getString("Rece");
			}
			return phone;

		}

	// 获取注册状态
	public String getRegState(String obj)
		{
			String phone = null;
			JSONObject object = JSONObject.fromString(obj);
			JSONObject data = object.getJSONObject("Data");
			if (data.get("Sta").equals("Ok"))
			{
				phone = object.getString("Rece");
			}
			return phone;

		}

	// 获取登陆状态
	public Map<String, Object> getuserLoginState(String obj)
		{
			HashMap<String, Object> map = null;
			JSONObject object = JSONObject.fromString(obj);
			JSONObject data = object.getJSONObject("Data");
			if (data.get("Sta").equals("Ok"))
			{
				map = new HashMap<String, Object>();
				map.put("phone", object.getString("Rece"));
				map.put("key", data.getString("Key"));
			}
			return map;

		}

	// 粘包服务器测试
	public void sticky() throws UnknownHostException, IOException
		{
			Socket socket = new Socket(config.IP_ADDR, config.PORT);
			socket.setSoTimeout(5000);
			byte[] by =
				{};
			for (int i = 0; i < 100; i++)
			{
				reg = new userReg();
				by = ArrayUtil.MergerArray10(by, reg.getUserRegBen());
			}
			SocketUtil.Send(new String(by), socket);
			System.out.println(SocketUtil.AcceptBase64Read(socket));

			socket.close();
		}

	public String getReg(String accept)
		{
			String phone = null;
			JSONObject json = JSONObject.fromString(accept);
			JSONObject jsonPes = json.getJSONObject("Data");
			if (jsonPes.get("Sta").equals("Ok"))
			{
				phone = json.getString("Rece");
			}
			return phone;
		}

	public void inputSucess(String phone)
		{
			try
			{
				File fileSucess = new File("E:/Client/Concurrency/Reg/" + config.initTime.format(date) + "Success.txt");
				File user = new File("E:/Client/Phone/TestLogin.txt");
				userPhone = new PrintWriter(new FileWriter(user, true));
				writeSucess = new PrintWriter(new FileWriter(fileSucess, true));
				userPhone.println(phone);
				writeSucess.println(phone);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			userPhone.flush();
			writeSucess.flush();

			writeSucess.close();
			userPhone.close();
		}

	public void inputFail(String ConnectException)
		{
			try
			{
				File fielFail = new File("E:/Client/Concurrency/Reg/" + config.initTime.format(date) + "Fail.txt");
				writeFail = new PrintWriter(new FileWriter(fielFail, true));
				writeFail.println(ConnectException);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			writeFail.flush();
			writeFail.close();

		}
}
