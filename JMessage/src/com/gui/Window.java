package com.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.network.client.Client;
import com.network.server.Server;

public class Window extends JFrame {
	private static final int width = 500, height = 300;
	private static final long serialVersionUID = 1L;

	private DefaultListModel<String> messages = new DefaultListModel<String>();
	private JTextField ServerNameAndIP, Username, Port, output;
	private JRadioButton connect, host;
	private JList<String> messageBox;
	private JTabbedPane windows;
	private JButton start, enter;
	private String ip, user;
	private ButtonGroup g;
	private JLabel j3;

	private Server s;
	private Client c;

	public Window() {

		try {

			ip = Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {

			e.printStackTrace();
		}

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("JMessage v1.0");
		this.setSize(width, height);
		this.setResizable(false);
		this.setLayout(null);

		windows = new JTabbedPane();
		windows.setBounds(0, 0, width, height);
		windows.addTab("Connection", addConnectionTab());

		this.add(windows);

		this.setVisible(true);
	}

	protected JComponent addConnectionTab() {
		JPanel panel = new JPanel();
		panel.setLayout(null);

		JPanel holder = new JPanel();
		holder.setLayout(null);
		holder.setBounds(50, 10, 375, 200);
		holder.setBorder(BorderFactory.createEtchedBorder());

		connect = new JRadioButton("Connect");
		connect.setSelected(true);
		connect.setBounds(5, 5, 100, 20);
		connect.addActionListener(new RadioListener());
		holder.add(connect);

		host = new JRadioButton("Host");
		host.setBounds(5, 25, 100, 20);
		host.addActionListener(new RadioListener());
		holder.add(host);

		g = new ButtonGroup();
		g.add(connect);
		g.add(host);

		JLabel j1 = new JLabel("IPV4: " + ip);
		j1.setBounds(10, 175, 200, 20);
		holder.add(j1);

		start = new JButton("Start");
		start.setBounds(120, 95, 249, 20);
		start.addActionListener(new StartListener());
		holder.add(start);

		JLabel j2 = new JLabel("Username: ");
		j2.setBounds(120, 5, 100, 20);
		holder.add(j2);

		Username = new JTextField();
		Username.setBounds(220, 5, 150, 20);
		holder.add(Username);

		j3 = new JLabel("IPV4 Address: ");
		j3.setBounds(120, 35, 100, 20);
		holder.add(j3);

		ServerNameAndIP = new JTextField();
		ServerNameAndIP.setBounds(220, 35, 150, 20);
		holder.add(ServerNameAndIP);

		JLabel j5 = new JLabel("Port: ");
		j5.setBounds(120, 65, 100, 20);
		holder.add(j5);

		Port = new JTextField();
		Port.setBounds(220, 65, 150, 20);
		holder.add(Port);

		panel.add(holder);

		return panel;
	}

	protected JComponent addChatTab() {
		JPanel panel = new JPanel();
		panel.setLayout(null);

		messageBox = new JList<String>(messages);
		messageBox.setLayoutOrientation(JList.VERTICAL);
		messageBox.setBounds(5, 5, 480, 230);
		messageBox.setEnabled(false);

		JScrollPane p = new JScrollPane(messageBox);
		p.setBounds(5, 5, 480, 205);
		panel.add(p);

		output = new JTextField();
		output.setBounds(5, 218, 325, 20);
		output.addKeyListener(new EnterListener());
		panel.add(output);

		enter = new JButton("Send");
		enter.setBounds(335, 218, 150, 19);
		enter.addActionListener(new EnterListener());
		panel.add(enter);

		return panel;
	}

	public class RadioListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (host.isSelected()) {

				j3.setText("Server Name: ");
			} else {

				j3.setText("IPV4 Address: ");
			}
		}
	}

	public class StartListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (!Username.getText().equals("") && !Port.getText().equals("")
					&& !ServerNameAndIP.getText().equals("")) {

				user = Username.getText();

				windows.addTab("Chat", addChatTab());
				windows.setSelectedIndex(1);
				
				windows.setTitleAt(1, ServerNameAndIP.getText());
				
				if (host.isSelected()) {

					s = new Server(messages, ServerNameAndIP.getText(),
							Integer.parseInt(Port.getText()));
				} else {

					c = new Client(windows, messages, Username.getText(), ServerNameAndIP.getText(),
							Integer.parseInt(Port.getText()));
				}

				start.setEnabled(false);
				connect.setEnabled(false);
				host.setEnabled(false);
				ServerNameAndIP.setEnabled(false);
				Username.setEnabled(false);
				Port.setEnabled(false);
			} else {

				JOptionPane.showMessageDialog(null,
						"Error: Please fill in required fields!", "JMessage",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public class EnterListener implements ActionListener, KeyListener {

		public void actionPerformed(ActionEvent e) {

			sendMessage();
		}

		private void sendMessage() {

			if (host.isSelected()) {

				s.writeToServer(user + ": " + output.getText());
				messages.addElement(user + ": " + output.getText());
			} else {

				c.writeToServer(user + ": " + output.getText());
			}

			output.setText("");
		}

		public void keyTyped(KeyEvent e) {

		}

		public void keyPressed(KeyEvent e) {

			if (e.getKeyCode() == KeyEvent.VK_ENTER) {

				sendMessage();
			}
		}

		public void keyReleased(KeyEvent e) {

		}
	}
}
