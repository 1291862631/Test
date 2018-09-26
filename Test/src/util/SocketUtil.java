package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import net.sf.json.JSONObject;

public class SocketUtil
{

	private static Base64.Decoder decoder = Base64.getDecoder();
	private static String text;
	private static SimpleDateFormat df = new SimpleDateFormat("dd HH:mm:ss");
	private static Date time;

	// 发送数据
	public static void Send(String obj, Socket socket)
		{
			BufferedWriter writer;
			try
			{
				writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
				writer.write(obj);
				writer.newLine();
				writer.flush();

			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	public static void Sendin(String obj, Socket socket)
		{
			BufferedWriter writer;
			try
			{
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				out.print(obj);
				out.flush();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	// 发送数据
	public static void SendLn(String obj, Socket socket)
		{

			try
			{
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				out.println(obj);
				out.flush();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	public static void sendHeart(Socket socket, String login) throws Exception
		{
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
			writer.append(login);
			writer.newLine();
			writer.flush();
			heartSer ser = new heartSer();
			while (true)
			{
				writer.append(ser.getBase64String().toString());
				writer.newLine();
				writer.flush();
				Thread.sleep(30000);
			}
		}

	// 接受数据
	public static String AcceptBase64er(Socket socket) throws IOException
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			String info = null;

			while ((info = reader.readLine()) != null)
			{
				byte[] textBytes = decoder.decode(info.substring(7, info.length()));
				text = new String(textBytes);
			}

			return new String(text);
		}

	public static String AcceptBase64(Socket socket) throws IOException, NullPointerException
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			String info = reader.readLine();

			byte[] textBytes = decoder.decode(info.substring(7, info.length()));

			return new String(textBytes);

		}

	public static String AcceptBase64Read(Socket socket)
		{
			InputStream in;
			byte[] textBytes = null;

			String head;
			try
			{
				in = socket.getInputStream();
				String text = null;
				int len = 0;
				int but = 0;
				int exing = 0;
				byte[] buf = new byte[99999];
				while ((len = in.read(buf)) != -1)
				{
					System.out.println(len);
					int cont = byteArrayToInt(ArrayUtil.SubArray(buf, 5, 7));
					if (cont + 7 < len)
					{
						int lenght = cont + 7;
						for (int i = 1; i <= len / lenght; i++)
						{
							time = new Date();
							exing = lenght + exing;
							byte[] arry = ArrayUtil.SubArray(buf, but, exing);
							but += lenght;
							text = new String(arry, 0, lenght);
							textBytes = decoder.decode(text.substring(7, lenght));
							showTime(textBytes);
						}
					} else
					{
						time = new Date();
						text = new String(buf, 0, len);
						textBytes = decoder.decode(text.substring(7, len));
						showTime(textBytes);
					}

					but = 0;
					buf.clone();

				}
			} catch (SocketTimeoutException e)
			{
			} catch (IOException e)
			{

				e.printStackTrace();
			}

			return new String(textBytes);
		}

	public static void showTime(byte[] array)
		{
			JSONObject object = JSONObject.fromObject(new String(array));
			System.out.println(object);
			System.out.println(df.format(time) + ": " + object.get("Rece"));
		}

	public static int byteArrayToInt(byte[] b)
		{

			return (b[1] & 0xFF) << 8 | (b[0] & 0xFF);
		}

	public static String AcceptBase(Socket socket)
		{
			InputStream in;
			byte[] textBytes = null;
			try
			{
				in = socket.getInputStream();
				String text = null;
				int len = 0;
				byte[] buf = new byte[9999];
				while ((len = in.read(buf)) != -1)
				{
					text = new String(buf, 0, len);
					textBytes = decoder.decode(text.substring(7, text.length()));
					break;
				}
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return new String(textBytes);
		}

	public static String Accept(Socket socket) throws IOException
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			String line = reader.readLine();
			return line;
		}
}