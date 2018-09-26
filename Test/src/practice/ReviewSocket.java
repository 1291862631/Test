
package practice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ReviewSocket {
	public static void main(String[] args) {
		Socket socket;
		try {
			Socket socket1 = new Socket("192.168.31.88", 8080);
			OutputStream os = socket1.getOutputStream();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
			InputStream is = socket1.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String info = "�û�2";
			bw.write(info);
			bw.flush();
			socket1.shutdownOutput();
			String reply;
			while ((reply = br.readLine()) != null) {
				System.out.println("���ǿͻ��˷���������Ӧ: " + reply);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}