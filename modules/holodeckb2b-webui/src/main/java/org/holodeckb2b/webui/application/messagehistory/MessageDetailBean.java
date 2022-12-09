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