package de.ws.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.sun.xml.internal.fastinfoset.vocab.Vocabulary;

import de.ws.shared.TokenizedText;
import de.ws.shared.Translation;
import de.ws.shared.User;

public class GamesView extends Composite implements Serializable  {
/**
	 * This view isn't done yet. it is the least important one.
	 * user should get 10 words from his list. question is if he should put in a translation which doesn't
	 * have to match 100% the original. we can count how many "correct" answers he gives and alert the score
	 */
	private static final long serialVersionUID = 1L;

private static GamesViewUiBinder uiBinder = GWT.create(GamesViewUiBinder.class);
	interface GamesViewUiBinder extends UiBinder<Widget, GamesView> {

	}
	
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	
	@UiField
	HTMLPanel contentPanel;
	
	
	@UiField
	TextBox user_answer;
	@UiField
	HTMLPanel inputGroup;
	@UiField
	HTMLPanel inputPassword;
	@UiField
	HTMLPanel questionPanel;
	@UiField
	Button submit;
	@UiField
	Button helpbutton;
	@UiField 
	HTMLPanel p1;
	@UiField 
	HTMLPanel p2;
	@UiField 
	HTMLPanel p3;
	@UiField 
	HTMLPanel p4;
	@UiField 
	HTMLPanel p5;
	@UiField 
	HTMLPanel p6;
	@UiField 
	HTMLPanel p7;
	@UiField 
	HTMLPanel p8;
	@UiField 
	HTMLPanel p9;
	@UiField 
	HTMLPanel p10;
	@UiField 
	HTMLPanel progress;
	@UiField
	HTMLPanel progresspanel;
	
	@UiField
	HTML vocabulary;
	
	ArrayList<String> words;
	HashMap<String, Translation> map;
	int correctvocabs;
	
	String answer;
	String progval;
	User user;
	String vocab;
	int points;
	int counter;
	ArrayList<String> correct;
	public GamesView() {

		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiConstructor
	public GamesView(HTMLPanel contentPanel, HashMap<String, Translation> map, User user, ArrayList<String>words, int points, int counter, ArrayList<String>correct) {
		
		initWidget(uiBinder.createAndBindUi(this));
		this.user = user;
		this.points = points;
		this.counter = counter;
		this.contentPanel = contentPanel;
		this.user = user;
		this.map = map;
		this.words = words;
		this.correct = correct;
		contentPanel.clear();
		progress.clear();
		switch (counter) {
		case 0:
			progresspanel.add(p1);
			break;
		case 1:
			progresspanel.add(p2);
			break;
		case 2:
			progresspanel.add(p3);
			break;
		case 3:
			progresspanel.add(p4);
			break;
		case 4:
			progresspanel.add(p5);
			break;
		case 5:
			progresspanel.add(p6);
			break;
		case 6:
			progresspanel.add(p7);
			break;
		case 7:
			progresspanel.add(p8);
			break;
		case 8:
			progresspanel.add(p9);
			break;
		case 9:
			progresspanel.add(p10);
			break;
		}
				
		submit.removeStyleName("gwt-Button");
		submit.addStyleName("btn btn-success btn-sm btn-block");
		
		helpbutton.removeStyleName("gwt-button");
		helpbutton.addStyleName("btn btn-secondary btn-sm btn-block");
		String w = words.get(counter);
		String t = map.get(w).getTranslation();
		t = t.replace("Translation: ", "");
		t = t.replace("1)", "");
		t = t.replace("2)", "");
		List <String> translation = Arrays.asList(t.split(","));
		String help = translation.get(0).trim().subSequence(0, 3).toString();
		helpbutton.getElement().setAttribute("title", help);
		helpbutton.setEnabled(false);
	    contentPanel.add(questionPanel);
	    questionPanel.setWidth("70%");
		vocabulary.removeStyleName("gwt-html");
		vocabulary.setStylePrimaryName("vocab-q");
		vocabulary.setText(" " + words.get(counter));
		vocabulary.getElement().setAttribute("font-family", "'Grand Hotel', cursive!important");
		submit.addClickHandler(new SubmitClickHandler(contentPanel, user_answer, this.points, this.counter, w, map));
		user_answer.addKeyDownHandler(new UserPressHandler(contentPanel, user_answer, this.points, this.counter, w, map));
	}
	private class SubmitClickHandler implements ClickHandler {
		TextBox answer;
		HTMLPanel cp;
		int points;
		int counter;
		private String word;
		private HashMap<String, Translation> map;

		public SubmitClickHandler(HTMLPanel cp, TextBox answer, int points, int counter, String word, HashMap<String, Translation>map) {
			this.answer = answer;
			this.cp = cp;
			this.points = points;
			this.counter = counter;
			this.word = word;
			this.map = map;
		}
		@Override
		public void onClick(ClickEvent event) {
			boolean c = false;
			String t = map.get(word).getTranslation();
			String aw = answer.getText();
			List<String> answer = Arrays.asList(aw.split(" "));
			t = t.replace("Translation: ", "");
			t = t.replace("1)", "");
			t = t.replace("2)", "");
			List <String> translation = Arrays.asList(t.split(","));
			for (String transl : answer) {
				if (translation.contains(transl)) {
					correct.add("correct");
					c = true;
					points+=1;
					break;
				}
			}
			if (c == false) {
				correct.add("false");
			}
			else {
				correct.add("true");
			}
			if (counter <words.size()-1) {
				GamesView gw = new GamesView(cp, map, user, words, points, counter+1, correct);
			}
			else {
				GamesFinalView gw = new GamesFinalView(cp, user, points, correct, words, map);
			}
			
		}
	}
	private class UserPressHandler implements KeyDownHandler {
		TextBox answer;
		HTMLPanel cp;
		int points;
		int counter;
		private String word;
		private HashMap<String, Translation> map;
		
		public UserPressHandler (HTMLPanel cp, TextBox answer, int points, int counter, String word, HashMap<String, Translation>map) {
			this.answer = answer;
			this.cp = cp;
			this.points = points;
			this.counter = counter;
			this.word = word;
			this.map = map;
		}
		@Override
		public void onKeyDown(KeyDownEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				String t = map.get(word).getTranslation();
				String aw = answer.getText();
				List<String> answer = Arrays.asList(aw.split(" "));
				t = t.replace("Translation: ", "");
				t = t.replace("1)", "");
				t = t.replace("2)", "");
				t = t.trim();
				List <String> translation = Arrays.asList(t.split(","));
				List<String> finaltranslation  = new ArrayList();
				for (String ta : translation) {
					finaltranslation.add(ta.trim());
				}
				boolean c = false;
				for (String transl : answer) {
					if (finaltranslation.contains(transl)) {
						c = true;
						points+=1;
						break;
					}
				}
				if (c == false) {
					correct.add("false");
				}
				else {
					correct.add("true");
				}
				if (counter <words.size()-1) {
					GamesView gw = new GamesView(cp, map, user, words, points, counter+1, correct);
				}
				else {
					GamesFinalView gw = new GamesFinalView(cp, user, points, correct, words, map);
				}
				
			}
			}
			
		}
		
	}
	
	

	

