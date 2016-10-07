package com.network;

import java.net.Socket;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JTabbedPane;

public abstract class Networking {
	public DefaultListModel<String> messages;
	public ArrayList<Socket> s;
	public JTabbedPane windows;
	public ReadMessages r;

	public Networking(DefaultListModel<String> messages) {

		this.messages = messages;

		s = new ArrayList<Socket>();
	}

	public void start() {

		new ReadMessages(this).start();
	}

	public abstract void writeToServer(String message);

	public void readFromServer() {
	}
}