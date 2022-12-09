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

import java.io.ByteArrayOutputStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.holodeckb2b.common.messagemodel.MessageUnit;
import org.holodeckb2b.common.pmode.PMode;
import org.holodeckb2b.common.util.MessageUnitUtils;
import org.holodeckb2b.commons.util.Utils;
import org.holodeckb2b.interfaces.general.IAgreement;
import org.holodeckb2b.interfaces.general.IPartyId;
import org.holodeckb2b.interfaces.pmode.ITradingPartnerConfiguration;
import org.holodeckb2b.interfaces.processingmodel.IMessageUnitProcessingState;
import org.holodeckb2b.ui.api.CertType;
import org.holodeckb2b.ui.api.CoreInfo;
import org.holodeckb2b.webui.application.certificates.CertificateBean;
import org.holodeckb2b.webui.application.messagehistory.MessageBean;
import org.holodeckb2b.webui.application.messagehistory.MessageDetailBean;
import org.holodeckb2b.webui.application.pmodes.PModeBean;

public class Controller {

	private static CoreInfo coreAPI;
	private static String hb2bHostName = "";
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

	static {
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	public static void connect() throws Exception {
		try {
			coreAPI = (CoreInfo) LocateRegistry.getRegistry(CoreInfo.DEFAULT_PORT).lookup(CoreInfo.RMI_SVC_NAME);
			hb2bHostName = coreAPI.getHostName();
		} catch (RemoteException | NotBoundException e) {
			throw new Exception("Could not connect to Holodeck B2B on port " + CoreInfo.DEFAULT_PORT, e);
		}
	}

	public String getHostName() {
		return hb2bHostName;
	}

	public static String formatDateTime(Date d) {
		return sdf.format(d);
	}

	public static List<MessageBean> retrieveMessageHistory(Date upto, int max) throws Exception {
		connect();
		ArrayList<MessageBean> result = new ArrayList<MessageBean>();
		MessageUnit[] messageUnitLog = coreAPI.getMessageUnitLog(upto, max);
		if (messageUnitLog == null) {
			return result;
		}
		for (int i = 0; i < messageUnitLog.length; i++) {
			MessageUnit log = messageUnitLog[i];
			result.add(new MessageBean.Builder() //
					.setId(log.getMessageId()) //
					.setTimestamp(sdf.format(log.getTimestamp())) //
					.setMessageUnitName(MessageUnitUtils.getMessageUnitName(log)) //
					.setCurrentState(log.getCurrentProcessingState().getState().name()) //
					.setDirection(log.getDirection().name()) //
					.setRefMessageId(log.getRefToMessageId()) //
					.setPmode(log.getPModeId()) //
					.build());
		}
		return result;
	}

	public static List<MessageDetailBean> retrieveMessageDetail(String messageId) throws Exception {
		connect();
		ArrayList<MessageDetailBean> result = new ArrayList<MessageDetailBean>();
		MessageUnit[] messageUnitInfo = coreAPI.getMessageUnitInfo(messageId);
		if (messageUnitInfo == null) {
			return result;
		}

		MessageUnit info = messageUnitInfo[0];
		List<IMessageUnitProcessingState> states = info.getProcessingStates();
		for (IMessageUnitProcessingState state : states) {
			result.add(new MessageDetailBean.Builder() //
					.setTimestamp(sdf.format(state.getStartTime())) //
					.setState(state.getState().name()) //
					.build());
		}
		return result;
	}

	public static List<PModeBean> retrievePModes() throws Exception {
		connect();
		ArrayList<PModeBean> result = new ArrayList<PModeBean>();
		PMode[] pModes = coreAPI.getPModes();
		if (pModes == null) {
			return result;
		}
		for (int i = 0; i < pModes.length; i++) {
			PMode p = pModes[i];
			result.add(new PModeBean.Builder() //
					.setId(p.getId()) //
					.setAgreement(getAgreement(p)) //
					.setMep(p.getMepBinding()) //
					.setParty1(getParty(p.getInitiator())) //
					.setParty2(getParty(p.getResponder())) //
					.setXML(getXml(p)) //
					.build());
		}
		return result;
	}

	public static List<CertificateBean> retrieveCertificates() throws Exception {
		connect();
		ArrayList<CertificateBean> result = new ArrayList<CertificateBean>();
		Map<String, X509Certificate> certs = coreAPI.getCertificates(CertType.Trusted);
		addCertificates(result, certs, CertType.Trusted);
		certs = coreAPI.getCertificates(CertType.Partner);
		addCertificates(result, certs, CertType.Partner);
		certs = coreAPI.getCertificates(CertType.Private);
		addCertificates(result, certs, CertType.Private);
		return result;
	}

	private static void addCertificates(ArrayList<CertificateBean> result, Map<String, X509Certificate> certs,
			CertType type) {
		if (certs == null) {
			return;
		}
		Set<String> keySet = certs.keySet();
		for (String key : keySet) {
			X509Certificate x509cert = certs.get(key);
			result.add(new CertificateBean.Builder() //
					.setId(key) //
					.setType(type.name()) //
					.setValidFrom(formatDateTime(x509cert.getNotBefore())) //
					.setValidTo(formatDateTime(x509cert.getNotAfter())) //
					.setCertificate(x509cert) //
					.build());
		}
	}

	private static String getAgreement(PMode p) {
		IAgreement a = p.getAgreement();
		if (a == null || Utils.isNullOrEmpty(a.getName())) {
			return "";
		} else {
			return (!Utils.isNullOrEmpty(a.getType()) ? a.getType() + "::" : "") + a.getName();
		}
	}

	private static String getParty(ITradingPartnerConfiguration p) {
		if (p == null || Utils.isNullOrEmpty(p.getPartyIds()))
			return "";
		else {
			IPartyId pid = p.getPartyIds().iterator().next();
			return (!Utils.isNullOrEmpty(pid.getType()) ? pid.getType() + "::" : "") + pid.getId();
		}
	}

	private static String getXml(PMode p) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			p.writeAsXMLTo(baos);
		} catch (Exception e) {
			return e.getMessage();
		}
		return baos.toString();
	}

}
