package org.holodeckb2b.webui.application.messagehistory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.holodeckb2b.webui.application.Controller;
import org.holodeckb2b.webui.application.UIUtil;
import org.holodeckb2b.webui.application.main.MainView;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "messagehistory", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Message History")
@CssImport("./views/content/content-view.css")
public class MessageHistoryView extends HorizontalLayout {

	private Grid<MessageBean> grid;
	private DateTimePicker time;
	private ComboBox<Integer> size;
	private TextField txt;
	private Grid<MessageDetailBean> details;

	public MessageHistoryView() {
		addClassName("content-view");
		// create components
		grid = new Grid<MessageBean>(MessageBean.class, true);
		grid.setPageSize(5);
		grid.setItems(new ArrayList<MessageBean>());
		grid.setColumns("timeStamp", "currentState", "messageUnitType", "direction", "id", "refMessageId", "pmode");
		Label label = new Label("messages before (UTC)");
		time = new DateTimePicker();
		time.setLocale(Locale.UK);
		time.setValue(LocalDateTime.now(ZoneId.of("Z")));
		size = new ComboBox<Integer>();
		size.setItems(10, 25, 50);
		size.setValue(10);
		Label label2 = new Label("max items");
		Button apply = new Button("Apply");
		txt = new TextField();
		Button show = new Button("Show");
		details = new Grid<MessageDetailBean>(MessageDetailBean.class, true);
		details.setItems(new ArrayList<MessageDetailBean>());
		details.setColumns("state", "timeStamp");
		// add listener
		grid.addItemClickListener(l -> {
			String id = grid.getSelectedItems().iterator().next().getId();
			txt.setValue(id);
			showDetails(details, id);
		});
		apply.addClickListener(l -> updateList());
		show.addClickListener(l -> {
			showDetails(details, txt.getValue());
		});
		// build layout
		HorizontalLayout h = new HorizontalLayout(label, time, label2, size, apply);
		h.setJustifyContentMode(JustifyContentMode.START);
		h.setAlignItems(Alignment.BASELINE);
		add(h);
		add(grid);
		add(new Label("select a list entry or type a message id"));
		HorizontalLayout h2 = new HorizontalLayout(txt, show);
		h2.setJustifyContentMode(JustifyContentMode.START);
		h2.setAlignItems(Alignment.BASELINE);
		add(h2);
		add(details);
		updateList();
	}

	private void updateList() {
		try {
			List<MessageBean> list = Controller
					.retrieveMessageHistory(Date.from(time.getValue().toInstant(ZoneOffset.UTC)), size.getValue());
			grid.setItems(list);
			if (list.isEmpty()) {
				UIUtil.notify("no messages found");
			}
			txt.setValue("");
			details.setItems(new ArrayList<MessageDetailBean>());
		} catch (Exception e) {
			UIUtil.notify(e.getMessage());
		}
	}

	private void showDetails(Grid<MessageDetailBean> details, String id) {
		List<MessageDetailBean> detail;
		try {
			detail = Controller.retrieveMessageDetail(id);
			details.setItems(detail);
			if (detail.isEmpty()) {
				UIUtil.notify("message not found");
			}
		} catch (Exception e) {
			UIUtil.notify(e.getMessage());
		}
	}
}
