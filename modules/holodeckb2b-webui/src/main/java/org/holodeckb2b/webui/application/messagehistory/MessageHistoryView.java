package org.holodeckb2b.webui.application.messagehistory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.holodeckb2b.webui.application.Controller;
import org.holodeckb2b.webui.application.main.MainView;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "messagehistory", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Message History")
@CssImport("./views/content/content-view.css")
public class MessageHistoryView extends HorizontalLayout {

	public MessageHistoryView() {
		addClassName("content-view");

		Grid<MessageBean> grid = new Grid(MessageBean.class, true);
		grid.setItems(new ArrayList<MessageBean>());
		grid.setColumns("id", "timeStamp", "currentState", "messageUnitType", "direction", "refMessageId", "pmode");
		add(grid);

		Label label = new Label("messages after (UTC)");
		DateTimePicker time = new DateTimePicker();
		time.setValue(LocalDateTime.now());
		ComboBox<Integer> size = new ComboBox<Integer>();
		size.setItems(10, 25, 50);
		size.setValue(10);
		Label label2 = new Label("max items");
		Button apply = new Button("Apply");
		apply.addClickListener(l -> {
			try {
				List<MessageBean> list = Controller
						.retrieveMessageHistory(Date.from(time.getValue().toInstant(ZoneOffset.UTC)), size.getValue());
				grid.setItems(list);
				if (list.isEmpty()) {
					Notification.show("no messages found", 5000, Position.MIDDLE);
				}
			} catch (Exception e) {
				Notification.show(e.getMessage(), 5000, Position.MIDDLE);
			}
		});
		HorizontalLayout h = new HorizontalLayout(label, time, label2, size, apply);
		h.setJustifyContentMode(JustifyContentMode.CENTER);
		h.setAlignItems(Alignment.BASELINE);
		add(h);
	}

}
