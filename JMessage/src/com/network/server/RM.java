package com.network.server;

public class RM extends Thread {

	private Server s;
	private int id;

	public RM(Server s, int id) {

		this.s = s;
		this.id = id;
	}

	@Override
	public void run() {

		while (true) {

			s.readFromServer(id);
		}
	}
}
