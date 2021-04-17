package org.holodeckb2b.webui.application;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.holodeckb2b.common.messagemodel.MessageUnit;
import org.holodeckb2b.common.util.MessageUnitUtils;
import org.holodeckb2b.ui.api.CoreInfo;
import org.holodeckb2b.webui.application.messagehistory.MessageBean;

public class Controller {

	private static CoreInfo coreAPI;
	private static String hb2bHostName = "";

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

	public static List<MessageBean> retrieveMessageHistory(Date upto, int max) throws Exception {
		connect();
		ArrayList<MessageBean> result = new ArrayList<MessageBean>();
		MessageUnit[] messageUnitLog = coreAPI.getMessageUnitLog(upto, max);
		if (messageUnitLog == null) {
			return result;
		}
		for (int i = 0; i < messageUnitLog.length; i++) {
			MessageUnit log = messageUnitLog[i];
			result.add(new MessageBean.Builder().setId(log.getMessageId()) //
					.setTimestamp(log.getTimestamp().toString()) //
					.setMessageUnitName(MessageUnitUtils.getMessageUnitName(log)) //
					.setCurrentState(log.getCurrentProcessingState().getState().name()) //
					.setDirection(log.getDirection().name()) //
					.setRefMessageId(log.getRefToMessageId()) //
					.setPmode(log.getPModeId()) //
					.build());
		}
		return result;
	}

}
