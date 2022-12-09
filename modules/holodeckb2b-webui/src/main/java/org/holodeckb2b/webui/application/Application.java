/**
 * Copyright (C) 2021 The Holodeck B2B Team, Conrad Moeller
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.holodeckb2b.webui.application;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

	private static ConfigurableApplicationContext context;

	public static void main(String[] args) {
		if (args.length == 1 && args[0].equals("stop")) {
			try {
				sendStop(6666);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		context = SpringApplication.run(Application.class, args);
		ShutdownHandler sh = new ShutdownHandler();
		try {
			sh.start(6666);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		Thread thread = new Thread(sh);
		thread.start();
	}

	private static void sendStop(int port) throws IOException {

		System.out.println("Send signal to stop Holodeck B2B web-UI.");
		Socket s = new Socket("localhost", port);
		OutputStream out = s.getOutputStream();
		out.write(new byte[] { 1, 2, 3, 4 });
		out.flush();
		out.close();
		s.close();
	}

	public static void stop() {
		SpringApplication.exit(context, new ExitCodeGenerator() {

			@Override
			public int getExitCode() {
				return 0;
			}
		});
	}

	private static class ShutdownHandler implements Runnable {

		private ServerSocket server;

		public void start(int port) throws IOException {

			server = new ServerSocket(port);
		}

		public void run() {

			try {
				System.out.println("Holodeck B2B Web-UI listen for stop signal.");
				while(true) {
					Socket s = server.accept();
					InputStream is = s.getInputStream();
					byte[] b = new byte[4];
					int bytes = is.read(b);
					if (bytes == 4 && b[0] == 1 && b[1] == 2 && b[2] == 3 && b[3] == 4) {
						System.out.println("Received signal to stop Holodeck B2B Web-UI.");
						s.close();
						stop();
						return;
					} else {
						System.out.println("Invalid signal to stop Holodeck B2B Web-UI.");
						s.close();					
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void stop() {

			Application.stop();
		}
	}

}
