package org.holodeckb2b.webui.application.about;

import org.holodeckb2b.webui.application.main.MainView;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "about", layout = MainView.class)
@PageTitle("About")
@CssImport("./views/about/about-view.css")
public class AboutView extends Div {

	public AboutView() {
		addClassName("about-view");
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		add(layout);
		Image image = new Image("images/logo.png", "HolodeckB2B");
		layout.add(image);
		layout.add(new H1("HolodeckB2B Web-UI"));
		layout.add(new Label("(c) 2021 Holodeck B2B contributed by C.Moeller"));
		layout.setAlignItems(Alignment.CENTER);
	}

}
