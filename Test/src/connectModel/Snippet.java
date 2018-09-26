package connectModel;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import util.Heart;
import util.SocketUtil;
import util.config;
import util.login;
import util.userlogintou;

public class Snippet
{

	public static void main(String[] args) throws UnknownHostException, IOException
		{
			Socket socket = new Socket(config.IP_ADDR, config.PORT);
			userlogintou ulogin = new userlogintou("15828532049");
			SocketUtil.Sendin(new String(ulogin.getUserLoginBen()), socket);
			String userLogin = SocketUtil.AcceptBase(socket);

			if (userLogin != null)
			{
				socket = new Socket(config.IP_ADDR, config.PORT);
				socket.setSoTimeout(5000);
				login lo = new login("15828532049", "111111111111111111");
				SocketUtil.SendLn(new String(lo.getBase64String()), socket);
				String loginMsg = SocketUtil.AcceptBase(socket);
				System.out.println(loginMsg);

				Heart hea = new Heart();

				SocketUtil.SendLn(new String(hea.getBase64Ben()), socket);
				System.out.println(SocketUtil.AcceptBase64Read(socket));
			}
		}
}
