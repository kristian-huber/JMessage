package com.network.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.DefaultListModel;

import com.network.Networking;

public class Server extends Networking {
	public ServerSocket server;
	public String ServerName;
	public ArrayList<RM> rm;

	public Server( DefaultListModel<String> messages, String ServerName, int port) {

		super(messages);

		this.ServerName = ServerName;

		try {
			server = new ServerSocket(port);
			messages.addElement("Server Successfully Created.");
		} catch (IOException e) {

			e.printStackTrace();
		}

		rm = new ArrayList<RM>();

		new ClientListener(this).start();
	}

	public void writeToServer(String message) {

		for (int i = 0; i < s.size(); i++) {

			try {

				PrintWriter out = new PrintWriter(s.get(i).getOutputStream());
				out.println(message);
				out.flush();

			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("resource")
	public void readFromServer(int id) {

		try {

			Scanner in = new Scanner(s.get(id).getInputStream());

			String msg = in.nextLine();
			
			messages.addElement(msg);
			
			writeToServer(msg);

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
