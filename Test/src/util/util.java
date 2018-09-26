package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import net.sf.json.JSONObject;

public class util
{
	private static String titl = "@Yhj@";
	// private static String titl = "@SeR#";

	public static void method1()
		{
			FileWriter fw = null;
			try
			{
				// 如果文件存在，则追加内容；如果文件不存在，则创建文件
				File f = new File("E:\\dd.txt");
				fw = new FileWriter(f, true);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			PrintWriter pw = new PrintWriter(fw);
			pw.println("追加内容");
			pw.flush();
			try
			{
				fw.flush();
				pw.close();
				fw.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}

	// 数据Base64F进行加密
	public void base64(JSONObject object)
		{
		}

	/**
	 * 加密数据包 head + length + base64 加密数据包
	 * 
	 * @param hea
	 * @param content
	 * @return
	 */
	public byte[] base64(String content)
		{
			Base64.Encoder encoder = Base64.getEncoder();
			// 根据环境的编码集获取bytes
			byte[] head = titl.getBytes(Charset.defaultCharset());
			byte[] body = content.getBytes(Charset.defaultCharset());
			byte[] bodyEncode = encoder.encode(body);
			// 创建一个capacity长度的ByteBuffer
			ByteBuffer buffer = ByteBuffer.allocate(head.length + 2 + bodyEncode.length);
			// 返回ByteBuffer的字节序
			// BIG_ENDIAN 代表大字节序的ByteOrder。
			// LITTLE_ENDIAN 代表小字节序的ByteOrder。
			buffer.order(ByteOrder.BIG_ENDIAN);
			// ByteBuffer 添加相关数据
			buffer.put(head);
			buffer.put(intToBytes(bodyEncode.length));
			buffer.put(bodyEncode);
			return buffer.array();
		}

	public static byte[] intToBytes(int value)
		{
			byte[] src = new byte[2];
			src[1] = (byte) ((value >> 8) & 0xFF);
			src[0] = (byte) (value & 0xFF);
			return src;
		}

	/**
	 * 对密码进行MD5 加密
	 * 
	 * @param password
	 * @return
	 */
	public String makeMD5(String password)
		{
			MessageDigest md;
			try
			{
				// 生成一个MD5加密计算摘要
				md = MessageDigest.getInstance("MD5");
				// 计算md5函数
				md.update(password.getBytes());
				// digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5
				// hash值是16位的hex值，实际上就是8位的字符
				// BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
				String pwd = new BigInteger(1, md.digest()).toString(16);
				// System.out.println(pwd);
				return pwd;
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			return password;
		}

	/**
	 * 对密码进行MD5 加密
	 * 
	 * @param password
	 * @return
	 */
	public String md5(String plainText)
		{
			// 定义一个字节数组，接收加密完成的密码
			byte[] secretBytes = null;
			try
			{

				secretBytes = MessageDigest.getInstance("md5").digest(plainText.getBytes());

			} catch (NoSuchAlgorithmException e)
			{
				e.printStackTrace();
			}

			String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
			// 如果生成的数字未满32位，要在前面补0
			for (int i = 0; i < 32 - md5code.length(); i++)
			{
				md5code = "0" + md5code;
			}
			// System.out.println(md5code);
			return md5code;
		}

	public static String keyMd5(String key)
		{
			MessageDigest md5 = null;
			try
			{
				md5 = MessageDigest.getInstance("MD5");
				md5.update((key).getBytes("UTF-8"));

				byte b[] = md5.digest();

				int i;
				StringBuffer buf = new StringBuffer("");

				for (int offset = 0; offset < b.length; offset++)
				{
					i = b[offset];
					if (i < 0)
					{
						i += 256;
					}
					if (i < 16)
					{
						buf.append("0");
					}
					buf.append(Integer.toHexString(i));
				}
				return buf.toString();
			} catch (NoSuchAlgorithmException e)
			{
				e.printStackTrace();
				return null;
			} catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
				return null;
			}
		}

}
