package util;

import net.sf.json.JSONArray;

public class ArrayUtil {

	/**
	 * intת�ֽ�����
	 *
	 * @param paramInt
	 * @return
	 */
	public static byte[] IntToLBytes(int paramInt) {
		return new byte[] { (byte) (paramInt & 0xFF), (byte) (paramInt >> 8 & 0xFF), (byte) (paramInt >> 16 & 0xFF),
				(byte) (paramInt >> 24 & 0xFF) };
	}

	/**
	 * ƴ���ֽڵ��ֽ�������
	 *
	 * @param paramArrayOfByte
	 *            ԭʼ�ֽ�����
	 * @param paramByte
	 *            Ҫƴ�ӵ��ֽ�
	 * @return ƴ�Ӻ������
	 */
	public static byte[] MergerArray(byte[] paramArrayOfByte, byte paramByte) {
		byte[] arrayOfByte = new byte[paramArrayOfByte.length + 1];
		System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, paramArrayOfByte.length);
		arrayOfByte[paramArrayOfByte.length] = paramByte;
		return arrayOfByte;
	}

	/**
	 * �����ֽ�����ƴ��
	 *
	 * @param paramArrayOfByte1
	 *            �ֽ�����1
	 * @param paramArrayOfByte2
	 *            �ֽ�����2
	 * @return ƴ�Ӻ������
	 */
	public static byte[] MergerArray(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2) {
		byte[] arrayOfByte = new byte[paramArrayOfByte1.length + paramArrayOfByte2.length];
		System.arraycopy(paramArrayOfByte1, 0, arrayOfByte, 0, paramArrayOfByte1.length);
		System.arraycopy(paramArrayOfByte2, 0, arrayOfByte, paramArrayOfByte1.length, paramArrayOfByte2.length);
		return arrayOfByte;
	}

	public static byte[] MergerArray10(byte[] b1, byte[] b2) {

		byte[] arrayOfByte = new byte[b1.length + b2.length];
		System.arraycopy(b1, 0, arrayOfByte, 0, b1.length);
		System.arraycopy(b2, 0, arrayOfByte, b1.length, b2.length);

		return arrayOfByte;
	}

	/**
	 * �ֽ�������
	 *
	 * @param paramArrayOfByte
	 *            ԭʼ����
	 * @param paramInt1
	 *            ��ʼ�±�
	 * @param paramInt2
	 *            Ҫ��ȡ�ĳ���
	 * @return ����������
	 */
	public static byte[] SubArray(byte[] paramArrayOfByte, int paramInt1, int paramInt2) {

		byte[] arrayOfByte = new byte[paramInt2];
		int i = 0;
		while (true) {
			if (i >= paramInt2)
				return arrayOfByte;

			arrayOfByte[i] = paramArrayOfByte[(i + paramInt1)];
			i += 1;
		}
	}

	/**
	 * int����תbyte����
	 *
	 * @param paramArrayOfInt
	 *            int����
	 * @return ת�����byte����
	 */
	public static byte[] intsToBytes(int[] paramArrayOfInt) {
		byte[] arrayOfByte = new byte[paramArrayOfInt.length];
		int i = 0;
		while (true) {
			if (i >= paramArrayOfInt.length)
				return arrayOfByte;
			arrayOfByte[i] = (byte) paramArrayOfInt[i];
			i += 1;
		}
	}

	/**
	 * �ַ���תbyte����
	 *
	 * @param paramString
	 *            �ַ���
	 * @param paramInt
	 *            �ַ������鳤��
	 * @return ת���������
	 */
	public static byte[] stringToBytes(String paramString, int paramInt) {
		while (true) {
			if (paramString.getBytes().length >= paramInt)
				return paramString.getBytes();
			paramString = paramString + " ";
		}
	}
}