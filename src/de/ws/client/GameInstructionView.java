package de.ws.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import de.ws.client.GamesView.GamesViewUiBinder;
import de.ws.shared.Translation;
import de.ws.shared.User;

public class GameInstructionView extends Composite implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1428686427805110011L;

	private static GameInstructionViewUiBinder uiBinder = GWT.create(GameInstructionViewUiBinder.class);
	interface GameInstructionViewUiBinder extends UiBinder<Widget, GameInstructionView> {

	}
	
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	
	@UiField
	HTMLPanel contentPanel;
	@UiField
	HTMLPanel instruction;
	@UiField
	Button go;
	
	
	List<String> words;
	ArrayList<String>correct ;
	User user;
	final HashMap<String, Translation> map = new HashMap<>();
	
	public GameInstructionView() {

		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiConstructor
	public GameInstructionView(HTMLPanel contentPanel, User user) {
		initWidget(uiBinder.createAndBindUi(this));
		this.words = user.getWordList();
		ArrayList<String> randomwords = new ArrayList<String>();
		correct = new ArrayList<String>();

		this.user = user;
		Random rnd = new Random();
		while (randomwords.size()!=10) {
			int i = rnd.nextInt(words.size());
			randomwords.add(words.get(i));
		}
		
		
		this.contentPanel = contentPanel;
		contentPanel.clear();
		contentPanel.add(instruction);
		go.removeStyleName("gwt-Button");
		go.addStyleName("btn btn-success btn-sm");
		go.addClickHandler(new GoClickHandler(this.contentPanel, this.user, this.words, randomwords, correct));
		
		

}
	private class GoClickHandler implements ClickHandler {
		User user;
		HTMLPanel cp;
		List<String> words;
		ArrayList<String> randomwords;
		ArrayList<String>correct;

		public GoClickHandler(HTMLPanel cp, User user, List<String> words2, ArrayList<String>randomwords, ArrayList<String>correct) {
			this.user = user;
			this.cp = cp;
			this.words = words2;
			this.randomwords = randomwords;
			this.correct = correct;
		}
		@Override
		public void onClick(ClickEvent event) {
			greetingService.getTextTranslation(this.words, new TransCallback(this.cp,this.user, this.randomwords, correct));

		}
	}
	private class TransCallback implements AsyncCallback<HashMap<String,Translation>> {

		HTMLPanel contentPanel;
		User user;
		ArrayList<String> randomwords;
		ArrayList<String> correct;

		public TransCallback(HTMLPanel contentPanel, User user, ArrayList<String>randomwords, ArrayList<String>correct) {
			this.contentPanel = contentPanel;
			this.user = user;
			this.randomwords = randomwords;
			this.correct = correct;

		}

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("map wasn't created");
			
		}

		@Override
		public void onSuccess(HashMap<String, Translation> result) {
			GamesView gw = new GamesView(contentPanel, result, user, randomwords, 0, 0, correct);
		}
		}
}
