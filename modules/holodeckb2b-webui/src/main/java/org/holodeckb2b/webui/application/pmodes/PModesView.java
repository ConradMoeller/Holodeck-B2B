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

import java.util.ArrayList;
import java.util.List;

import org.holodeckb2b.webui.application.Controller;
import org.holodeckb2b.webui.application.UIUtil;
import org.holodeckb2b.webui.application.main.MainView;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "pmodes", layout = MainView.class)
@PageTitle("P-Modes")
@CssImport("./views/content/content-view.css")
public class PModesView extends Div {

	private Grid<PModeBean> grid;

	public PModesView() {
		addClassName("content-view");
		// create components
		Button retrieve = new Button("Refresh");
		grid = new Grid(PModeBean.class, true);
		grid.setItems(new ArrayList<PModeBean>());
		grid.setColumns("id", "agreement", "mep", "party1", "party2");
		grid.setPageSize(5);
		TextArea text = new TextArea();
		text.setWidthFull();
		text.setReadOnly(true);
		// add listener
		retrieve.addClickListener(l -> {
			updateList();
			text.setValue("");
		});
		grid.addItemClickListener(l -> {
			if (grid.getSelectedItems().isEmpty()) {
				text.setValue("");
			} else {
				text.setValue(grid.getSelectedItems().iterator().next().getXml());
			}
		});
		// build layout
		add(retrieve);
		add(grid);
		add(text);
		updateList();
	}

	private void updateList() {

		try {
			List<PModeBean> list = Controller.retrievePModes();
			grid.setItems(list);
			if (list.isEmpty()) {
				UIUtil.notify("no pmodes found");
			}
		} catch (Exception e) {
			UIUtil.notify(e.getMessage());
		}
	}

}
