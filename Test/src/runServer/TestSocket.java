package runServer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TestSocket {
	Socket socket;

	public TestSocket(Socket socket) {
		this.socket = socket;
		// TODO Auto-generated constructor stub
	}

	public void Test(String obj) {
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
			writer.write(obj);
			writer.newLine();
			writer.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
