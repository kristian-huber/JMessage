package com.network;

public class ReadMessages extends Thread {

	private Networking n;

	public ReadMessages(Networking n) {

		this.n = n;
	}

	@Override
	public void run() {

		while (true) {

			n.readFromServer();
		}
	}
}
