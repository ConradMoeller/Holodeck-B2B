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
package org.holodeckb2b.webui.application.pmodes;

public class PModeBean {

	private String id;
	private String agreement;
	private String mep;
	private String party1;
	private String party2;
	private String xml;

	public PModeBean() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAgreement() {
		return agreement;
	}

	public void setAgreement(String agreement) {
		this.agreement = agreement;
	}

	public String getMep() {
		return mep;
	}

	public void setMep(String mep) {
		this.mep = mep;
	}

	public String getParty1() {
		return party1;
	}

	public void setParty1(String party1) {
		this.party1 = party1;
	}

	public String getParty2() {
		return party2;
	}

	public void setParty2(String party2) {
		this.party2 = party2;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public static class Builder {

		private PModeBean p;

		public Builder() {
			p = new PModeBean();
		}

		public PModeBean build() {
			return p;
		}

		public Builder setId(String id) {
			p.setId(id);
			return this;
		}

		public Builder setAgreement(String agreement) {
			p.setAgreement(agreement);
			return this;
		}

		public Builder setMep(String mep) {
			p.setMep(mep);
			return this;
		}

		public Builder setParty1(String party) {
			p.setParty1(party);
			return this;
		}

		public Builder setParty2(String party) {
			p.setParty2(party);
			return this;
		}

		public Builder setXML(String xml) {
			p.setXml(xml);
			return this;
		}
	}
}
