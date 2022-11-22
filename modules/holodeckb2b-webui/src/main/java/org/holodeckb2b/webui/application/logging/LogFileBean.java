package org.holodeckb2b.webui.application.logging;

import java.nio.file.Path;

public class LogFileBean {

	private Path path;

	public LogFileBean() {

	}

	public LogFileBean(Path path) {
		this.path = path;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public String getFileName() {
		return path.toString();
	}
}
