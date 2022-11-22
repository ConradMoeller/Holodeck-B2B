package org.holodeckb2b.webui.application.certificates;

import java.util.ArrayList;
import java.util.List;

import org.holodeckb2b.webui.application.Controller;
import org.holodeckb2b.webui.application.UIUtil;
import org.holodeckb2b.webui.application.main.MainView;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "certificates", layout = MainView.class)
@PageTitle("Certificates")
@CssImport("./views/about/about-view.css")
public class CertificatesView extends Div {

	private Grid<CertificateBean> grid;

	public CertificatesView() {
		addClassName("content-view");
		// create components
		Button retrieve = new Button("Refresh");
		grid = new Grid<CertificateBean>(CertificateBean.class, true);
		grid.setItems(new ArrayList<CertificateBean>());
		grid.setColumns("id", "type", "serial", "validFrom", "validTo", "subject", "issuer");
		grid.setPageSize(5);
		// add listener
		retrieve.addClickListener(l -> updateList());
		// build layout
		add(retrieve);
		add(grid);
		updateList();
	}

	private void updateList() {
		try {
			List<CertificateBean> list = Controller.retrieveCertificates();
			grid.setItems(list);
			if (list.isEmpty()) {
				UIUtil.notify("no certificates found");
			}
		} catch (Exception e) {
			UIUtil.notify(e.getMessage());
		}
	}

}
