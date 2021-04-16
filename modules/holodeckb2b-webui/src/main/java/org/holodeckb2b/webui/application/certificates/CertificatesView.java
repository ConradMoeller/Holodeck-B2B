package org.holodeckb2b.webui.application.certificates;

import org.holodeckb2b.webui.application.main.MainView;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "certificates", layout = MainView.class)
@PageTitle("Certificates")
@CssImport("./views/about/about-view.css")
public class CertificatesView extends Div {

	public CertificatesView() {
		addClassName("about-view");
		add(new Text("Content placeholder"));
	}

}
