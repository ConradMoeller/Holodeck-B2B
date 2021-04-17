package org.holodeckb2b.webui.application.pmodes;

import java.util.ArrayList;
import java.util.List;

import org.holodeckb2b.webui.application.Controller;
import org.holodeckb2b.webui.application.main.MainView;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "pmodes", layout = MainView.class)
@PageTitle("P-Modes")
@CssImport("./views/content/content-view.css")
public class PModesView extends Div {

	public PModesView() {
		addClassName("content-view");
		// create components
		Button retrieve = new Button("Retrieve");
		Grid<PModeBean> grid = new Grid(PModeBean.class, true);
		grid.setItems(new ArrayList<PModeBean>());
		grid.setColumns("id", "agreement", "mep", "party1", "party2");
		grid.setPageSize(5);
		TextArea text = new TextArea();
		text.setWidthFull();
		// add listener
		retrieve.addClickListener(l -> {
			try {
				List<PModeBean> list = Controller.retrievePModes();
				grid.setItems(list);
				if (list.isEmpty()) {
					Notification.show("no pmodes found", 5000, Position.MIDDLE);
				}
			} catch (Exception e) {
				Notification.show(e.getMessage(), 5000, Position.MIDDLE);
			}
		});
		grid.addItemClickListener(l -> {
			text.setValue(grid.getSelectedItems().iterator().next().getXml());
		});
		// build layout
		add(retrieve);
		add(grid);
		add(text);
	}

}
