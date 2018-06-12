package de.ws.client;

import java.io.Serializable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import de.ws.shared.Text;
import de.ws.shared.TokenizedText;
import de.ws.shared.User;

public class InputTextView extends Composite implements Serializable {
	
	// UIBinder	
	private static InputTextViewUiBinder uiBinder = GWT.create(InputTextViewUiBinder.class);
	interface InputTextViewUiBinder extends UiBinder<Widget, InputTextView> {
	}
	
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	
	@UiField
	HTMLPanel contentPanel;
	@UiField
	HTMLPanel inputPanel;
	@UiField
	TextArea user_answer;
	@UiField
	HTMLPanel inputGroup;
	@UiField
	HTMLPanel inputField;
	@UiField
	Button submit;
	@UiField
	HTMLPanel warning;
	@UiField
	HTMLPanel topPanel;
	
	User user;
	
	public InputTextView() {

		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiConstructor
	public InputTextView(HTMLPanel contentPanel, User user) {
		initWidget(uiBinder.createAndBindUi(this));
		this.user = user;
				
		this.contentPanel = contentPanel;
		this.user = user;		
		contentPanel.clear();
		contentPanel.add(inputPanel);
		inputPanel.setWidth("80%");
		inputField.setHeight("400px");
		inputGroup.setHeight("400px");
		submit.removeStyleName("gwt-Button");
		submit.addStyleName("btn btn-success btn-sm");
		submit.addClickHandler(new TextClickHandler(user_answer, contentPanel, user));
		user_answer.addKeyDownHandler(new UserPressHandler(user_answer, contentPanel, user));

	}
	
	private class UserPressHandler implements KeyDownHandler {
		String text;
		HTMLPanel cp;
		TextArea ta;
		User user;
		public UserPressHandler (TextArea area, HTMLPanel cp, User user) {
			this.ta = area;
			this.cp = cp;
			this.user = user;
		}
		@Override
		public void onKeyDown(KeyDownEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				text = ta.getText();
				if (text.isEmpty()) {
					topPanel.add(warning);
				}
				else {
				greetingService.createText(text, new TokenizationCallback());
			}
			}
			
		}
		
	}
	private class TextClickHandler implements ClickHandler {
		String text;
		HTMLPanel cp;
		TextArea ta;
		User user;

		public TextClickHandler(TextArea area, HTMLPanel cp, User user) {
			this.ta = area;
			this.cp = cp;
			this.user = user;
		}
		@Override
		public void onClick(ClickEvent event) {
			text = ta.getText();
			if (text.isEmpty()) {
				topPanel.add(warning);
			}
			else {
			greetingService.createText(text, new TokenizationCallback());}
			//greetingService.saveWords(text, name, new SaveWordsCallback());
			

		}
	}
	private class TokenizationCallback implements AsyncCallback<TokenizedText> {
		
		@Override
		public void onSuccess(TokenizedText result) {
			//contentPanel.clear();
			Text text = new Text();
			text.setText(user_answer.getText());
			text.setTitle("");
			text.setLevel("");
			text.setSaved(false);
			TranslatorView tw = new TranslatorView(result.getTokens(),result.getTranslationMap(), contentPanel, user, text);
		}

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("not");
			Window.scrollTo(0, 0);
		}
	}
}