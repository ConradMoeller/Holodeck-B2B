package org.holodeckb2b.webui.application;

public class Settings {

	public static String getSecurityToken() {
		return System.getProperty("hb2b.webui.token");
	}

	public static String getDataDir() {
		return System.getProperty("", "./data/");
	}

}
