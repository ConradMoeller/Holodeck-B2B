package org.holodeckb2b.webui.application.certificates;

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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "certificates", layout = MainView.class)
@PageTitle("Certificates")
@CssImport("./views/about/about-view.css")
public class CertificatesView extends Div {

	public CertificatesView() {
		addClassName("content-view");
		// create components
		Button retrieve = new Button("Retrieve");
		Grid<CertificateBean> grid = new Grid<CertificateBean>(CertificateBean.class, true);
		grid.setItems(new ArrayList<CertificateBean>());
		grid.setColumns("id", "type", "serial", "validFrom", "validTo", "subject", "issuer");
		grid.setPageSize(5);
		// add listener
		retrieve.addClickListener(l -> {
			try {
				List<CertificateBean> list = Controller.retrieveCertificates();
				grid.setItems(list);
				if (list.isEmpty()) {
					Notification.show("no certificates found", 5000, Position.MIDDLE);
				}
			} catch (Exception e) {
				Notification.show(e.getMessage(), 5000, Position.MIDDLE);
			}
		});
		// build layout
		add(retrieve);
		add(grid);
	}

}
