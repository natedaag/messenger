package messenger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

import javax.swing.*;

public class ChatClient extends JFrame implements Runnable {
	
	Socket socket;
	JTextArea ta;
	JButton send, logout;
	JTextField tf;
	
	Thread thread;
	
	DataInputStream din;
	DataOutputStream dout;
	
	String loginName;
	
	ChatClient(String login) throws IOException{
		super(login);
		loginName = login;
		
		ta = new JTextArea(18, 50);
		tf = new JTextField(50);
		
		send = new JButton("Send");
		logout = new JButton("Logout");
		
		send.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			try {
				dout.writeUTF(loginName + " " + "DATA " + tf.getText().toString());
				tf.setText("");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
			}
			
		});
		
		logout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			try {
				dout.writeUTF(loginName + " " + "LOGOUT");
				System.exit(1);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
			}
			
		});
		
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
		panel.add(tf);
		panel.add(send);
		panel.add(logout);
		
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
