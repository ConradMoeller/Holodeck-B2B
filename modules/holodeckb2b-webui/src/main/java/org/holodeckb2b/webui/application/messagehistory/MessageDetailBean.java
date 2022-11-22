package org.holodeckb2b.webui.application.messagehistory;

public class MessageDetailBean {

	private String timeStamp;
	private String state;

	public MessageDetailBean() {

	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public static class Builder {

		private MessageDetailBean m;

		public Builder() {
			m = new MessageDetailBean();
		}

		public MessageDetailBean build() {
			return m;
		}

		public Builder setTimestamp(String timeString) {
			m.setTimeStamp(timeString);
			return this;
		}

		public Builder setState(String description) {
			m.setState(description);
			return this;
		}

	}

}