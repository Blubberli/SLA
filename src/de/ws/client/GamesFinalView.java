package de.ws.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.TableElement;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.dom.client.TableSectionElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import de.ws.client.GamesView.GamesViewUiBinder;
import de.ws.shared.Translation;
import de.ws.shared.User;

public class GamesFinalView extends Composite implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1428686427805110011L;

	private static GamesFinalViewUiBinder uiBinder = GWT.create(GamesFinalViewUiBinder.class);
	interface GamesFinalViewUiBinder extends UiBinder<Widget, GamesFinalView> {

	}
	
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	
	@UiField
	HTMLPanel contentPanel;
	@UiField
	HTML finalpoints;
	@UiField
	HTMLPanel success;
	int points;
	
	@UiField 
	HTMLPanel wordListPanel;
	@UiField
	FlexTable table;
	
	ArrayList<String>correct;
	
	public GamesFinalView() {

		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiConstructor
	public GamesFinalView(HTMLPanel contentPanel, User userl, int points, ArrayList<String>correct, ArrayList<String> words, HashMap<String, Translation>map) {
		initWidget(uiBinder.createAndBindUi(this));
		this.correct = correct;
		this.points = points;
		this.contentPanel = contentPanel;
		contentPanel.clear();
		String p = String.valueOf(points);
		
		finalpoints.setText(p);
		contentPanel.add(success);
		
		com.google.gwt.user.client.Element oldElement = table.getElement();
		com.google.gwt.dom.client.Element element = (com.google.gwt.dom.client.Element) oldElement;
		TableElement tableElement = (TableElement) element;
		TableSectionElement tHead = tableElement.createTHead();
		tHead.setAttribute("class", "table-header");
		TableRowElement row = tHead.insertRow(0);
		row.insertCell(0).setInnerText("Finnish Word");
		row.getCells().getItem(0).setAttribute("width", "45%");
		row.insertCell(1).setInnerText("Translation");
		row.getCells().getItem(1).setAttribute("width", "45%");
		row.insertCell(2).setInnerText("correct answer");
		row.getCells().getItem(2).setAttribute("width", "10%");

		
		for (int i = 0; i< words.size(); i++) {
			String w = words.get(i);
			String t = map.get(w).getTranslation();
			String corr = correct.get(i);
			table.setText(i, 0, w);
			table.setText(i, 1, t);
			if (corr.equals("false")) {
				table.setText(i, 2, "incorrect");
			}
			else {
				table.setText(i, 2, "correct");

			}
						
		}
		contentPanel.add(wordListPanel);
		contentPanel.add(table);
		
	}
}
