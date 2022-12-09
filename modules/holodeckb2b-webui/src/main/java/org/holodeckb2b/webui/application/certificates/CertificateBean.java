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
package org.holodeckb2b.webui.application.certificates;

import java.security.cert.X509Certificate;

public class CertificateBean {

	private String id;
	private String type;
	private String from;
	private String to;
	private X509Certificate certificate;

	public CertificateBean() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setValidFrom(String from) {
		this.from = from;
	}

	public String getValidFrom() {
		return from;
	}

	public void setValidTo(String to) {
		this.to = to;
	}

	public String getValidTo() {
		return to;
	}

	public X509Certificate getCertificate() {
		return certificate;
	}

	public void setCertificate(X509Certificate certificate) {
		this.certificate = certificate;
	}

	public String getSerial() {
		return certificate.getSerialNumber().toString();
	}

	public String getSubject() {
		return certificate.getSubjectDN().toString();
	}

	public String getIssuer() {
		return certificate.getIssuerDN().toString();
	}

	public static class Builder {

		private CertificateBean c;

		public Builder() {
			c = new CertificateBean();
		}

		public CertificateBean build() {
			return c;
		}

		public Builder setId(String id) {
			c.setId(id);
			return this;
		}

		public Builder setType(String type) {
			c.setType(type);
			return this;
		}

		public Builder setValidFrom(String from) {
			c.setValidFrom(from);
			return this;
		}

		public Builder setValidTo(String to) {
			c.setValidTo(to);
			return this;
		}

		public Builder setCertificate(X509Certificate x509cert) {
			c.setCertificate(x509cert);
			return this;
		}
	}
}
