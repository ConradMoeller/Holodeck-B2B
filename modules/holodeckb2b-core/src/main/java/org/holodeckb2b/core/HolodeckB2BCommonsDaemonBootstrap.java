package org.holodeckb2b.core;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import org.apache.axis2.AxisFault;
import org.apache.axis2.engine.AxisServer;

/**
 * Is a class that can be used as entry point for using HolodeckB2B in
 * combination with commons-daemon (see
 * https://commons.apache.org/proper/commons-daemon).
 * 
 * The class extends wraps HolodeckB2BServer and adds the possibility to stop
 * the process with another process using a socket connection.
 * 
 * This class delivers both the start and stop option.
 * 
 * Example:
 * 
 * Use 'org.holodeckb2b.core.HolodeckB2BCommonsDaemonBootstrap start 6666
 * c:/holodeckb2b' to start a HolodeckB2B server installed in the folder
 * c:/holodeckb2b on a Windows box.
 * 
 * Use 'org.holodeckb2b.core.HolodeckB2BCommonsDaemonBootstrap stop 6666' to
 * shutdown the server.
 *
 * @author Conrad Moeller
 */
public class HolodeckB2BCommonsDaemonBootstrap extends AxisServer {

	private static HolodeckB2BServer holodeckb2bServer;

	public static void main(String[] args) {

		String[] list;
		if (args.length == 1) {
			list = args[0].split(" ");
		} else {
			list = args;
		}
		if (list.length < 2) {
			printHelp();
			return;
		}

		if (list[0].equalsIgnoreCase("start")) {
			if (list.length < 3) {
				printHelp();
				return;
			}
			try {
				int port = Integer.parseInt(list[1]);
				String homeDir = list[2];
				startServer(port, homeDir);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (list[0].equalsIgnoreCase("stop")) {
			if (list.length < 2) {
				printHelp();
				return;
			}
			try {
				stopServer(Integer.parseInt(list[1]));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			printHelp();
		}
	}

	private static void startServer(int port, String homeDir) throws Exception {

		ShutdownHandler sh = new ShutdownHandler();
		System.out.println("\n*************************************");
		System.out.println("* Holodeck B2B WindowsServiceLoader *");
		System.out.println("*************************************\n");
		sh.start(port);
		holodeckb2bServer = new HolodeckB2BServer(homeDir);
		Thread thread = new Thread(sh);
		thread.start();
		if (holodeckb2bServer.startServer()) {
			System.out.println("\n*************************************");
			System.out.println("* Holodeck B2B started.             *");
			System.out.println("*************************************\n");
			System.out.println("* Shutdown Listener started using port: " + port + "\n");
			System.out.println("*************************************\n");
		} else {
			System.out.println("\n*********************************************");
			System.out.println("* Holodeck B2B failed to start.             *");
			System.out.println("* See log for details.                      *");
			System.out.println("*********************************************\n");
			System.exit(-1);
		}
	}

	public static boolean isNullOrEmpty(final Map<?, ?> m) {
		return m == null || m.isEmpty();
	}

	private static void stopServer(int port) throws IOException {

		System.out.println("Send signal to stop Holodeck B2B.");
		Socket s = new Socket("localhost", port);
		OutputStream out = s.getOutputStream();
		out.write(new byte[] { 1, 2, 3, 4 });
		out.flush();
		out.close();
		s.close();
	}

	private static void printHelp() {

		System.out.println("Usage: " + HolodeckB2BCommonsDaemonBootstrap.class.getName() + " start|stop port homeDir");
	}

	/**
	 * 
	 * The thread that starts a ServerSocket waiting for socket connection to
	 * receive the stop signal and shutdown the process.
	 *
	 */
	private static class ShutdownHandler implements Runnable {

		private ServerSocket server;

		public void start(int port) throws IOException {

			server = new ServerSocket(port);
		}

		public void run() {

			try {
				Socket s = server.accept();
				System.out.println("Received signal to stop Holodeck B2B.");
				s.getInputStream();
				stop();
				s.close();
				System.exit(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void stop() throws AxisFault {

			System.out.println("Stopping Holodeck B2B.");
			holodeckb2bServer.stop();
			System.out.println("Holodeck B2B stopped.");
		}
	}
}