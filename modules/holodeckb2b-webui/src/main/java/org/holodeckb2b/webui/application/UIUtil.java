package org.holodeckb2b.webui.application;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;

public class UIUtil {

	private static int NOTIFICATION_TIME = 3000;
	private static Position NOTIFICATION_POSITION = Position.TOP_CENTER;

	public static void notify(String text) {
		Notification.show(text, NOTIFICATION_TIME, NOTIFICATION_POSITION);
	}

}
