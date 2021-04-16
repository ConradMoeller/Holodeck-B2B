package org.holodeckb2b.webui.application.pmodes;

import org.holodeckb2b.webui.application.main.MainView;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "pmodes", layout = MainView.class)
@PageTitle("P-Modes")
@CssImport("./views/about/about-view.css")
public class PModesView extends Div {

	public PModesView() {
		addClassName("about-view");
		add(new Text("Content placeholder"));
	}

}
