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
