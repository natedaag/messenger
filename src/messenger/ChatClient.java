package messenger;

import java.io.*;
import java.net.Socket;

import javax.swing.*;

public class ChatClient extends JFrame implements Runnable {
	
	Socket socket;
	JTextArea ta;
	
	Thread thread;
	
	DataInputStream din;
	DataOutputStream dout;
	
	String loginName;
	
	ChatClient(String login) throws IOException{
		super(login);
		loginName = login;
		
		ta = new JTextArea(18, 50);
		
		din = new DataInputStream(socket.getInputStream());
		dout = new DataOutputStream(socket.getOutputStream());
		
		dout.writeUTF(loginName);
		dout.writeUTF(loginName + " " + "LOGIN");
		
		thread = new Thread(this);
		thread.start();
		setup();
	}
	
	public void setup(){
		setSize(600, 400);
		
		JPanel panel = new JPanel();
	
		panel.add(new JScrollPane(ta));
		
		add(panel);
		
		setVisible(true);
	}

	@Override
	public void run() {
		while(true){
			try {
				ta.append("\n" + din.readUTF());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) throws IOException{
		ChatClient client = new ChatClient("User1");
	}

}
