package org.holodeckb2b.webui.application.logging;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;

import javax.imageio.stream.FileImageInputStream;

import org.holodeckb2b.webui.application.UIUtil;
import org.holodeckb2b.webui.application.main.MainView;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

@Route(value = "logging", layout = MainView.class)
@PageTitle("Logging")
@CssImport("./views/content/content-view.css")
public class LoggingView extends Div {

	private Path LogFolder;

	private Grid<LogFileBean> grid;
	private ComboBox<Integer> size;
	private TextArea text;
	private Label info;
	private Anchor download;

	public LoggingView() {

		LogFolder = Paths.get(System.getProperty("hb2b.logging.dir", "./"));

		addClassName("content-view");
		// create components
		grid = new Grid(LogFileBean.class, true);
		grid.setItems(new ArrayList<LogFileBean>());
		grid.setColumns("fileName");
		grid.setPageSize(5);

		Button close = new Button("Close");

		size = new ComboBox<Integer>();
		size.setItems(10, 100, 250, 500, 1000, 5000, 10000);
		size.setValue(100);

		Button retrieve = new Button("Refresh");

		text = new TextArea();
		text.setWidthFull();
		text.setReadOnly(true);

		info = new Label();

		download = new Anchor();
		Button b = new Button(new Icon(VaadinIcon.DOWNLOAD_ALT));
		download.add(b);

		grid.addItemClickListener(l -> {
			if (grid.getSelectedItems().isEmpty()) {
				text.setValue("");
			} else {
				openLogFile();
				grid.setVisible(false);
				close.setVisible(true);
				size.setVisible(true);
				retrieve.setVisible(true);
				text.setVisible(true);
				info.setVisible(true);
				download.setVisible(true);
			}
		});

		close.addClickListener(l -> {
			text.setValue("");
			updateList();
			grid.setVisible(true);
			close.setVisible(false);
			size.setVisible(false);
			retrieve.setVisible(false);
			text.setVisible(false);
			info.setVisible(false);
			download.setVisible(false);
		});

		retrieve.addClickListener(l -> openLogFile());

		// build layout
		add(grid);
		HorizontalLayout h = new HorizontalLayout(close, size, retrieve);
		h.setAlignItems(Alignment.BASELINE);
		add(h);
		add(text);
		HorizontalLayout h2 = new HorizontalLayout(info, download);
		h.setAlignItems(Alignment.CENTER);
		add(h2);

		grid.setVisible(true);
		close.setVisible(false);
		size.setVisible(false);
		retrieve.setVisible(false);
		text.setVisible(false);
		info.setVisible(false);
		download.setVisible(false);

		text.setValue("");
		updateList();
	}

	private void updateList() {

		try {
			List<LogFileBean> list = new Vector<LogFileBean>();
			Files.list(LogFolder).filter(p -> !Files.isDirectory(p) && p.toString().endsWith("log"))
					.forEach(p -> list.add(new LogFileBean(p.getFileName())));
			grid.setItems(list);
			if (list.isEmpty()) {
				UIUtil.notify("no logfiles found");
			}
		} catch (Exception e) {
			UIUtil.notify(e.getMessage());
		}
	}

	private void openLogFile() {
		StringBuffer sb = new StringBuffer();
		String fileName = grid.getSelectedItems().iterator().next().getPath().toString();
		String logFile = LogFolder + File.separator + fileName;
		long totalLineCount = 0;
		long lineCount = 0;
		try {
			ArrayBlockingQueue<String> queu = new ArrayBlockingQueue<String>(size.getValue());
			totalLineCount = Files.lines(Paths.get(logFile)).count();
			Files.lines(Paths.get(logFile)).forEach(s -> {
				if (queu.size() < size.getValue()) {
					queu.add(s);
				} else {
					queu.poll();
					queu.add(s);
				}
			});
			lineCount = queu.size();
			queu.forEach(s -> sb.append(s).append("\n"));
		} catch (IOException e) {
			UIUtil.notify("could not open the file");
		}
		text.setValue(sb.toString());
		info.setText("Last " + lineCount + " of total " + totalLineCount + " lines. Download complete file ->");
		download.setHref(new StreamResource(fileName, () -> getFileInputStream(logFile)));
	}

	private InputStream getFileInputStream(String logFile) {
		try {
			return new FileInputStream(logFile);
		} catch (FileNotFoundException e) {
			return new ByteArrayInputStream(new byte[] {});
		}
	}

}
