package org.holodeckb2b.webui.application.main;

import java.io.IOException;
import java.util.Optional;

import org.holodeckb2b.webui.application.about.AboutView;
import org.holodeckb2b.webui.application.certificates.CertificatesView;
import org.holodeckb2b.webui.application.logging.LoggingView;
import org.holodeckb2b.webui.application.messagehistory.MessageHistoryView;
import org.holodeckb2b.webui.application.pmodes.PModesView;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinServletService;

/**
 * The main view is a top-level placeholder for other views.
 */
@PWA(name = "HolodeckB2B", shortName = "HolodeckB2B", enableInstallPrompt = false)
@JsModule("./styles/shared-styles.js")
@CssImport("./views/main/main-view.css")
public class MainView extends AppLayout {

	private Tabs menu;
	private H1 viewTitle;

	private boolean tokenCheckPassed = true;

	public MainView() {
		String token = VaadinServletService.getCurrentServletRequest().getParameter("token");
		String tokenValue = System.getProperty("hb2b.webui.token");
		if (token == null || tokenValue == null || !token.equals(tokenValue)) {
			try {
				VaadinServletService.getCurrentResponse().sendError(403);
				tokenCheckPassed = false;
			} catch (IOException e) {
			}
		} else {
			setPrimarySection(Section.DRAWER);
			addToNavbar(true, createHeaderContent());
			menu = createMenu();
			addToDrawer(createDrawerContent(menu));
		}
	}

	private Component createHeaderContent() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setId("header");
		layout.getThemeList().set("dark", true);
		layout.setWidthFull();
		layout.setSpacing(false);
		layout.setAlignItems(FlexComponent.Alignment.CENTER);
		layout.add(new DrawerToggle());
		viewTitle = new H1();
		layout.add(viewTitle);
		return layout;
	}

	private Component createDrawerContent(Tabs menu) {
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		layout.setPadding(false);
		layout.setSpacing(false);
		layout.getThemeList().set("spacing-s", true);
		layout.setAlignItems(FlexComponent.Alignment.STRETCH);
		HorizontalLayout logoLayout = new HorizontalLayout();
		logoLayout.setId("logo");
		logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
		Image image = new Image("images/logo.png", "HolodeckB2B");
		logoLayout.add(image);
		logoLayout.add(new Label("HolodeckB2B"));
		layout.add(logoLayout, menu);
		return layout;
	}

	private Tabs createMenu() {
		final Tabs tabs = new Tabs();
		tabs.setOrientation(Tabs.Orientation.VERTICAL);
		tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
		tabs.setId("tabs");
		tabs.add(createMenuItems());
		return tabs;
	}

	private Component[] createMenuItems() {
		return new Tab[] { createTab("Message History", MessageHistoryView.class),
				createTab("Logging", LoggingView.class), createTab("P-Modes", PModesView.class),
				createTab("Certificates", CertificatesView.class), createTab("About", AboutView.class) };
	}

	private static Tab createTab(String text, Class<? extends Component> navigationTarget) {
		final Tab tab = new Tab();
		tab.add(new RouterLink(text, navigationTarget));
		ComponentUtil.setData(tab, Class.class, navigationTarget);
		return tab;
	}

	@Override
	protected void afterNavigation() {
		if (tokenCheckPassed) {
			super.afterNavigation();
			getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);
			viewTitle.setText(getCurrentPageTitle());
		}
	}

	private Optional<Tab> getTabForComponent(Component component) {
		return menu.getChildren().filter(tab -> ComponentUtil.getData(tab, Class.class).equals(component.getClass()))
				.findFirst().map(Tab.class::cast);
	}

	private String getCurrentPageTitle() {
		PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
		return title == null ? "" : title.value();
	}

}
