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

		public MessageBean build() {
			return m;
		}

		public Builder setId(String id) {
			m.id = id;
			return this;
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