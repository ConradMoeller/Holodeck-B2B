package org.holodeckb2b.webui.application.messagestatus;

import org.holodeckb2b.webui.application.main.MainView;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "messagestatus", layout = MainView.class)
@PageTitle("Message Status")
@CssImport("./views/about/about-view.css")
public class MessageStatusView extends Div {

	public MessageStatusView() {
		addClassName("about-view");
		add(new Text("Content placeholder"));
	}

}
