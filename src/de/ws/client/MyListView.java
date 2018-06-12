package de.ws.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.TableElement;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.dom.client.TableSectionElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.ws.shared.Translation;
import de.ws.shared.User;

public class MyListView extends Composite implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2485781626483734624L;
	
	private static MyListViewUiBinder uiBinder = GWT.create(MyListViewUiBinder.class);
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

	interface MyListViewUiBinder extends UiBinder<Widget, MyListView> {
	}
	
	@UiField
	HTMLPanel contentPanel;
	@UiField 
	HTMLPanel wordListPanel;
	@UiField
	FlexTable table;
	
	List<String> words;
	ArrayList<String> translations;
	User user;
	final HashMap<String, Translation> map = new HashMap<>();

	public MyListView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public MyListView(HTMLPanel contentPanel, User user, List<String> words) {
		initWidget(uiBinder.createAndBindUi(this));
		this.user = user;
		this.words = words;

		this.contentPanel = contentPanel;
		contentPanel.clear();
		table.clear();
		greetingService.getTextTranslation(this.words, new TransCallback(this.contentPanel, map, this.words, table));

		
		//contentPanel.add(wordListPanel);
		
		
	}
	private class TransCallback implements AsyncCallback<HashMap<String,Translation>> {

		HashMap<String, Translation> map;
		List<String> tokens;
		HTMLPanel contentPanel;
		FlexTable table;
		public TransCallback(HTMLPanel contentPanel, HashMap<String, Translation> map, List<String> words, FlexTable table) {
			this.map = map;
			this.tokens = words;
			this.contentPanel = contentPanel;
			this.table = table;
		}

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("map wasn't created");
			
		}

		@Override
		public void onSuccess(HashMap<String, Translation> result) {
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
			row.insertCell(2).setInnerText("Delete");
			row.getCells().getItem(2).setAttribute("width", "10%");
			Button print = new Button();
			print.setHTML("print list");
			print.getElement().setAttribute("style", "margin-left:89%");
			print.addClickHandler(new ClickHandler() {
	            
	            @Override
	            public void onClick(ClickEvent event) {
	                Window.print();
	            }
	        });
			contentPanel.add(print);

			
			for (int i = 0; i< words.size(); i++) {
				String w = words.get(i);
				String t = result.get(w).getTranslation();
				table.setText(i, 0, w);
				table.setText(i, 1, t);
				Widget widget1 = new Anchor("");
				((Anchor) widget1).setHTML("<i class=\"fas fa-times\"></i>");
				widget1.setStyleName("delete-user-button");
				HTMLPanel panel = new HTMLPanel("");
				panel.add(widget1);
				// get the table
				table.setWidget(i, 2, panel);

				widget1.addDomHandler(new DeleteClickHandler(w, user, table), ClickEvent.getType());
				
				
			}
			
			contentPanel.add(wordListPanel);
			contentPanel.add(table);
			
		}
		private class DeleteWordCallback implements AsyncCallback<Void> {
			String token;
			User user;
			FlexTable table;

			public DeleteWordCallback(String token, User user, FlexTable table) {
				this.token = token;
				this.user = user;
				this.table = table;
			}
			@Override
			public void onSuccess(Void result) {
				user.getWordList().remove(user.getWordList().indexOf(token));
				for (int i = 0; i < table.getRowCount(); i++) {
					if (table.getText(i, 0).equals(token)) {
						table.removeRow(i);
						break;
					}
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("fail");
				Window.scrollTo(0, 0);
			}


		}
		private class DeleteClickHandler implements ClickHandler {
			String token;
			User user;
			FlexTable table;

			public DeleteClickHandler(String token, User user, FlexTable table) {
				this.token = token;
				this.user = user;
				this.table = table;
			}
			@Override
			public void onClick(ClickEvent event) {
				int id = user.getId();
				greetingService.deleteWord(token, id, new DeleteWordCallback(token, user, table));

			}
		}
		


	}
	

}
