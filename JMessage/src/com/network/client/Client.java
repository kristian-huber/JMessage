package com.network.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JTabbedPane;

import com.network.Networking;

public class Client extends Networking {

	@SuppressWarnings("unused")
	private String IPV4;
	private int Port;
	@SuppressWarnings("unused")
	private String username;

	private JTabbedPane windows;

	public Client(JTabbedPane windows, DefaultListModel<String> messages,
			String username, String IPV4, int port) {

		super(messages);

		this.windows = windows;
		this.Port = port;
		this.IPV4 = IPV4;
		this.username = username;

		try {

			this.s.add(new Socket(IPV4, Port));

			start();

			messages.addElement("Connected to Server.");
			this.writeToServer(username + " has connected.");

		} catch (Exception noServer) {

			messages.addElement("Server Not Avalible, Please Restart Client");
		}
	}

	public void writeToServer(String message) {

		try {

			PrintWriter out = new PrintWriter(s.get(0).getOutputStream());
			out.println(message);
			out.flush();

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@SuppressWarnings("resource")
	public void readFromServer() {

		try {

			Scanner in = new Scanner(s.get(0).getInputStream());

			String msg = in.nextLine();

			if (msg.startsWith("#Server_Name=$")) {

				windows.setTitleAt(1, msg.substring(14));
			} else {

				messages.addElement(msg);
			}

		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
