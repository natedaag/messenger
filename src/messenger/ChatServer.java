package messenger;

import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import java.util.Vector;

public class ChatServer {
	
	static Vector clientSockets;
	static Vector loginNames;
	
	ChatServer() throws IOException {
		ServerSocket server = new ServerSocket(5217);
		clientSockets = new Vector();
		loginNames = new Vector();
		
		while(true) {
			Socket client = server.accept();
			AcceptClient acceptClient = new AcceptClient(client);
			
		}
	}
	
	public static void main(String[] args) throws IOException {
		ChatServer server = new ChatServer();
	}
	
	class AcceptClient extends Thread {
		Socket clientSocket;
		DataInputStream din;
		DataOutputStream dout;
		AcceptClient(Socket client) throws IOException {
			clientSocket = client;
			din = new DataInputStream(clientSocket.getInputStream());
			dout = new DataOutputStream(clientSocket.getOutputStream());
			
			String loginName = din.readUTF();
			
			loginNames.add(loginName);
			clientSockets.add(clientSocket);
			
			start();
		}
		
		public void run(){
			while(true){
				try {
					String msgFromClient = din.readUTF();
					StringTokenizer st = new StringTokenizer(msgFromClient);
					String loginName = st.nextToken();
					String msgType = st.nextToken();
					
					for(int i = 0; i < loginNames.size(); i++){
						Socket pSocket = (Socket) clientSockets.elementAt(i);
						DataOutputStream pOut = new DataOutputStream(pSocket.getOutputStream());
						pOut.writeUTF(loginName + "has logged in.");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}
	
}

