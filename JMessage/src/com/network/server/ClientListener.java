package com.network.server;

import java.io.IOException;

public class ClientListener extends Thread{
	Server s;
	
	public ClientListener(Server s){
		
		this.s = s;
	}
	
	@Override
	public void run(){
		
		int count = 0;
		
		while(true){
			
			try {
				
				s.s.add(s.server.accept());
				s.writeToServer("#Server_Name=$" + s.ServerName);
				s.rm.add(new RM(s, count));
				s.rm.get(count).start();
				count++;
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}
}
