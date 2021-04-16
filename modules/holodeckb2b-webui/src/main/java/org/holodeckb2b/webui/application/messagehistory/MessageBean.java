package org.holodeckb2b.webui.application.messagehistory;

public class MessageBean {

	private String id;
	private String timeStamp;
	private String currentState;
	private String messageUnitType;
	private String direction;
	private String refMessageId;
	private String pmode;

	public MessageBean() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getCurrentState() {
		return currentState;
	}

	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}

	public String getMessageUnitType() {
		return messageUnitType;
	}

	public void setMessageUnitType(String messageUnitType) {
		this.messageUnitType = messageUnitType;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getRefMessageId() {
		return refMessageId;
	}

	public void setRefMessageId(String refMessageId) {
		this.refMessageId = refMessageId;
	}

	public String getPmode() {
		return pmode;
	}

	public void setPmode(String pmode) {
		this.pmode = pmode;
	}

	public static class Builder {

		private MessageBean m;

		public Builder() {
			m = new MessageBean();
		}

		public Builder setId(String id) {
			m.id = id;
			return this;
		}

		public MessageBean build() {
			return m;
		}

		public Builder setTimestamp(String timeString) {
			m.setTimeStamp(timeString);
			return this;
		}

		public Builder setCurrentState(String description) {
			m.setCurrentState(description);
			return this;
		}

		public Builder setDirection(String name) {
			m.setDirection(name);
			return this;
		}

		public Builder setRefMessageId(String refToMessageId) {
			m.setRefMessageId(refToMessageId);
			return this;
		}

		public Builder setPmode(String pModeId) {
			m.setPmode(pModeId);
			return this;
		}

		public Builder setMessageUnitName(String messageUnitName) {
			m.setMessageUnitType(messageUnitName);
			return this;
		}

	}

}