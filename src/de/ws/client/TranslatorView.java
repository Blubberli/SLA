package de.ws.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import de.ws.shared.Text;
import de.ws.shared.Translation;
import de.ws.shared.User;



public class TranslatorView extends Composite {

	private static TranslatorViewUiBinder uiBinder = GWT.create(TranslatorViewUiBinder.class);
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

	interface TranslatorViewUiBinder extends UiBinder<Widget, TranslatorView> {
	}

	public TranslatorView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	HTMLPanel contentPanel;
	HTML hello;
	ArrayList<String> tokens;
	@UiField
	HTMLPanel textPanel;
	HTMLPanel trwindow;
	HTMLPanel mainPanel;
	InfoPanel infoPanel;
	String inputText;
	User user;
	String title;
	String level;
	Text text;
	final HashMap<String, Translation> map = new HashMap<>();
	public HTMLPanel cover;
	public HTML uniqueCov;
	public HTML generalCov;
	public HTMLPanel percentages;
	private HTMLPanel instr;
	private HTMLPanel btns;
	private Button b;
	private Button c;


	public TranslatorView(ArrayList<String> tokens, HashMap<String, Translation> translationMap,
			HTMLPanel contentPanel, User user, Text text) {
		initWidget(uiBinder.createAndBindUi(this));
		this.contentPanel= contentPanel;
		this.tokens = tokens;
		this.user = user;
		this.text = text;


		//map = new HashMap<>();
		contentPanel.clear();
		infoPanel = new InfoPanel(text, user);
		infoPanel.setStyleName("infoPanel");

		textPanel.clear();
		textPanel.setStyleName("textField");
		Set<HTML> knowns = new HashSet<>();
		HashSet<String> knownTypes = new HashSet<>();
		for (int i = 0; i < tokens.size(); i++) {
			String token = tokens.get(i);
			HTML t = new HTML(token);
			if(!t.equals("")) {
				t.setStyleName("token");
				if (!translationMap.get(token).getTranslation().equals("unknown")) {
					knowns.add(t);
					knownTypes.add(token);
				} 
				textPanel.add(t);

				t.addClickHandler(new TokenClickHandler(token, infoPanel, user, contentPanel, translationMap.get(token)));
				t.getElement().setAttribute("tabIndex", "0");
			}
		}

		percentages = new HTMLPanel("");
		instr = new HTMLPanel("");
		btns = new HTMLPanel("");
		b = new Button("ON");
		c = new Button("OFF");
		b.setStyleName("on");
		c.setStyleName("off");
		if(text.isSaved()) {

			instr.add(new HTML("<h6 class=\"card-title grr\">Switch the toggle to hightlight known tokens </h6>"));
			instr.setStyleName("cov-instr");
			infoPanel.coverage.setStyleName("cov-panel");
			btns.setStyleName("my-btn-block");
			btns.add(b);
			btns.add(c);
			infoPanel.coverage.add(instr);
			infoPanel.coverage.add(btns);


			int uniqTypes = knownTypes.size();
			int unknownTokens = knowns.size();
			int uniqueCount = (uniqTypes *100) / translationMap.size() ;
			int generalCount = (unknownTokens * 100) / tokens.size();

			uniqueCov = new HTML("<h6>Unique token coverage: <span class=\"badge badge-default prplr\">" + uniqueCount + "%</span></h6>");
			generalCov = new HTML("<h6>Total token coverage: <span class=\"badge badge-default prplr\">" + generalCount + "%</span></h6>");

			percentages.add(uniqueCov);
			percentages.add(generalCov);
			infoPanel.coverage.add(percentages);

		}
		c.addClickHandler(new CoverageOffClickhandler(knowns));
		b.addClickHandler(new CoverageClickhandler(knowns, translationMap.size(), tokens.size()));

		contentPanel.add(textPanel);
		contentPanel.add(infoPanel);

	}

	private class CoverageClickhandler implements ClickHandler {
		private Set<HTML> known;
		private boolean isActive;
		private int unCount;
		private int genCount;


		public CoverageClickhandler(Set<HTML> known, int unCount, int genCount)  {
			this.known = known;
			this.isActive = false;
			this.unCount = unCount;
			this.genCount = genCount;
		}

		@Override
		public void onClick(ClickEvent event) {
			b.getElement().addClassName("onClicked");
			c.getElement().removeClassName("offClicked");
			for (HTML h : known) {
				if (!isActive) {
					h.getElement().addClassName("showUnkown");
				}

			}
		}

	}

	private class CoverageOffClickhandler implements ClickHandler {
		private Set<HTML> known;


		public CoverageOffClickhandler(Set<HTML> known)  {
			this.known = known;
		}

		@Override
		public void onClick(ClickEvent event) {

			c.getElement().addClassName("offClicked");
			b.getElement().removeClassName("onClicked");

			for (HTML h : known) {
				h.getElement().removeClassName("showUnkown");
			}
		}

	}


	private class TokenClickHandler implements ClickHandler {
		String token;
		HashMap<String, Translation> map;
		InfoPanel ip;
		User user;
		HTMLPanel cp;
		Translation tra;

		public TokenClickHandler(String token, InfoPanel ip, User user, HTMLPanel cp, Translation tra) {
			this.token = token;
			this.ip = ip;
			this.user = user;
			this.cp = cp;
			this.tra = tra;
		}
		@Override
		public void onClick(ClickEvent event) {
			ip.setWordToPanel(token, tra);

		}
	}

}
