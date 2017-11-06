package messenger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.*;

import javax.swing.*;


public class ChatClient extends JFrame implements Runnable {
	
	Socket socket;
	JTextArea ta;
	
	Thread thread;
	
	DataInputStream din;
	DataOutputStream dout;
	
	String loginName;
	
	ChatClient(String login) throws UnknownHostException, IOException{
		super(login);
		loginName = login;
		
		ta = new JTextArea(18, 50);
		
		socket = new Socket("localhost", 5217);
		
		din = new DataInputStream(socket.getInputStream());
		dout = new DataOutputStream(socket.getOutputStream());		
		
		dout.writeUTF(loginName);
		dout.writeUTF(loginName + " " + "LOGIN");
		
		thread = new Thread(this);
		thread.start();
		setup();
	}

	private void setup() {
		setSize(500, 400);
		
		JPanel panel = new JPanel();
		
		panel.add(new JScrollPane(ta));
		
		add(panel);
		
		setVisible(true);
	}

	@Override
	public void run() {
		while (true){
			try {
				ta.append("/n" + din.readUTF());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException{
		ChatClient client = new ChatClient("user1");
		
	}

}























