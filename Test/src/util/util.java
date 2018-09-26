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
				// ����ļ����ڣ���׷�����ݣ�����ļ������ڣ��򴴽��ļ�
				File f = new File("E:\\dd.txt");
				fw = new FileWriter(f, true);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			PrintWriter pw = new PrintWriter(fw);
			pw.println("׷������");
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

	// ����Base64F���м���
	public void base64(JSONObject object)
		{
		}

	/**
	 * �������ݰ� head + length + base64 �������ݰ�
	 * 
	 * @param hea
	 * @param content
	 * @return
	 */
	public byte[] base64(String content)
		{
			Base64.Encoder encoder = Base64.getEncoder();
			// ���ݻ����ı��뼯��ȡbytes
			byte[] head = titl.getBytes(Charset.defaultCharset());
			byte[] body = content.getBytes(Charset.defaultCharset());
			byte[] bodyEncode = encoder.encode(body);
			// ����һ��capacity���ȵ�ByteBuffer
			ByteBuffer buffer = ByteBuffer.allocate(head.length + 2 + bodyEncode.length);
			// ����ByteBuffer���ֽ���
			// BIG_ENDIAN ������ֽ����ByteOrder��
			// LITTLE_ENDIAN ����С�ֽ����ByteOrder��
			buffer.order(ByteOrder.BIG_ENDIAN);
			// ByteBuffer ����������
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
	 * ���������MD5 ����
	 * 
	 * @param password
	 * @return
	 */
	public String makeMD5(String password)
		{
			MessageDigest md;
			try
			{
				// ����һ��MD5���ܼ���ժҪ
				md = MessageDigest.getInstance("MD5");
				// ����md5����
				md.update(password.getBytes());
				// digest()���ȷ������md5 hashֵ������ֵΪ8Ϊ�ַ�������Ϊmd5
				// hashֵ��16λ��hexֵ��ʵ���Ͼ���8λ���ַ�
				// BigInteger������8λ���ַ���ת����16λhexֵ�����ַ�������ʾ���õ��ַ�����ʽ��hashֵ
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
	 * ���������MD5 ����
	 * 
	 * @param password
	 * @return
	 */
	public String md5(String plainText)
		{
			// ����һ���ֽ����飬���ռ�����ɵ�����
			byte[] secretBytes = null;
			try
			{

				secretBytes = MessageDigest.getInstance("md5").digest(plainText.getBytes());

			} catch (NoSuchAlgorithmException e)
			{
				e.printStackTrace();
			}

			String md5code = new BigInteger(1, secretBytes).toString(16);// 16��������
			// ������ɵ�����δ��32λ��Ҫ��ǰ�油0
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
