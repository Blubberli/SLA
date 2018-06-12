package de.ws.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.ws.shared.Text;
import de.ws.shared.Translation;
import de.ws.shared.User;

public class InfoPanel extends Composite {

	private static InfoPanelUiBinder uiBinder = GWT.create(InfoPanelUiBinder.class);
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	@UiField
	HTMLPanel textconfig;
	@UiField
	HTMLPanel wordconfig;
	ArrayList<String> tokens;
	User user;
	String word;
	HTMLPanel dafaq;
	HTMLPanel cp;
	@UiField
	HTMLPanel infoPan;
	@UiField
	HTMLPanel wordinfo;
	HTML displayEnglishWord;
	HTML displayFinnishWord;
	String english;
	@UiField
	Anchor savewordbtn;
	@UiField
	Anchor trbtn;
	@UiField
	Anchor infobtn;
	String traview;
	String graview;
	String lemma;
	Translation tra;
	String inputText;
	@UiField
	Anchor saveText;
	Text text;
	@UiField
	HTMLPanel textName;
	@UiField 
	HTMLPanel saveTextPanel;
	@UiField
	TextBox insertTitle;
	@UiField 
	HTML saveIt;
	@UiField
	FocusPanel easy;
	@UiField
	FocusPanel medium;
	@UiField
	FocusPanel hard;
	@UiField
	HTML easyHTML;
	@UiField
	HTML mediumHTML;
	@UiField
	HTML hardHTML;
	String level;
	@UiField
	HTMLPanel coverage;
	@UiField
	HTMLPanel pan1;
	@UiField
	HTMLPanel pan2;


	interface InfoPanelUiBinder extends UiBinder<Widget, InfoPanel> {
	}
	
	abstract class LevelClickHandler implements ClickHandler {
		private Text text;

		public LevelClickHandler(Text t) {
			this.setText(t);
		}
		
		public abstract void onClick(ClickEvent event);

		public Text getText() {
			return text;
		}

		public void setText(Text text) {
			this.text = text;
		}
	}
	@UiConstructor
	public InfoPanel(Text text, User user) {
		initWidget(uiBinder.createAndBindUi(this));
		
		this.user = user;
		this.text = text;
		
		easy.getElement().setAttribute("class", "progress-bar easy");
		easy.getElement().setAttribute("role", "progressbar");
		easy.getElement().setAttribute("style", "width: 33.3%");
		easy.getElement().setAttribute("aria-valuenow", "30");
		easy.getElement().setAttribute("aria-valuemin", "0");
		easy.getElement().setAttribute("aria-valuemax", "90");
		
		
		medium.getElement().setAttribute("class", "progress-bar medium");
		medium.getElement().setAttribute("role", "progressbar");
		medium.getElement().setAttribute("style", "width: 33.3%");
		medium.getElement().setAttribute("aria-valuenow", "30");
		medium.getElement().setAttribute("aria-valuemin", "0");
		medium.getElement().setAttribute("aria-valuemax", "90");
		
		hard.getElement().setAttribute("class", "progress-bar hard");
		hard.getElement().setAttribute("role", "progressbar");
		hard.getElement().setAttribute("style", "width: 33.3%");
		hard.getElement().setAttribute("aria-valuenow", "30");
		hard.getElement().setAttribute("aria-valuemin", "0");
		hard.getElement().setAttribute("aria-valuemax", "90");
		
		
		
		if(text.isSaved()) {
			textName.add(new HTML(text.getTitle()));
			saveTextPanel.clear();
			if(text.getLevel().equals("easy")) {
				easy.getElement().addClassName("easyClicked");
			} else if(text.getLevel().equals("medium")) {
				medium.getElement().addClassName("mediumClicked");
			} else if(text.getLevel().equals("hard")) {
				hard.getElement().removeClassName("hardClicked");
			}
			
		} else {
			textName.add(new HTML("* Text"));
			coverage.clear();
			insertTitle.getElement().setAttribute("placeholder", "Text name");
			insertTitle.getElement().setAttribute("aria-label", "Text name");
			insertTitle.getElement().setAttribute("aria-describedby", "aria-describedby");
			easy.addClickHandler(new LevelClickHandler(text) {
				
				@Override
				public void onClick(ClickEvent event) {
					// TODO Auto-generated method stub
					easy.getElement().addClassName("easyClicked");
					medium.getElement().removeClassName("mediumClicked");
					hard.getElement().removeClassName("hardClicked");
					
					this.getText().setLevel("easy");
				}
			});
			medium.addClickHandler(new LevelClickHandler(text){
				
				@Override
				public void onClick(ClickEvent event) {
					// TODO Auto-generated method stub
					medium.getElement().addClassName("mediumClicked");
					hard.getElement().removeClassName("hardClicked");
					easy.getElement().removeClassName("easyClicked");
					
					this.getText().setLevel("medium");
				}
			});
			
			hard.addClickHandler(new LevelClickHandler(text)  {
				
				@Override
				public void onClick(ClickEvent event) {
					// TODO Auto-generated method stub
					hard.getElement().addClassName("hardClicked");
					medium.getElement().removeClassName("mediumClicked");
					easy.getElement().removeClassName("easyClicked");
					this.getText().setLevel("hard");
				}
			});
			
			saveText.addClickHandler(new SaveTextClickHandler());
		}
		textconfig.setStyleName("textconfig");
		wordconfig.setStyleName("wordconfig");
	}

	public void setWordToPanel(String word,Translation tra) {
		wordinfo.clear();
		displayFinnishWord = new HTML(word);
		displayFinnishWord.setStyleName("displayFinnishWord");
		
		traview = tra.getTranslation();
		traview = traview.replace("Translation:", "");
		graview = tra.getGrammaticalInfo();
		lemma = tra.getLemma();
		displayEnglishWord = new HTML(traview);
		displayEnglishWord.setStyleName("displayEnglishWord");
		
		wordinfo.add(displayFinnishWord);
		wordinfo.add(displayEnglishWord);
		infobtn.getElement().removeClassName("active");
		infobtn.getElement().addClassName("disabled");
		savewordbtn.getElement().removeClassName("active");
		savewordbtn.getElement().addClassName("disabled");
		trbtn.getElement().removeClassName("disabled");
		trbtn.getElement().addClassName("active");
		
		wordinfo.add(displayFinnishWord);
		wordinfo.add(displayEnglishWord);
		
		infobtn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				wordinfo.clear();
				trbtn.getElement().removeClassName("active");
				trbtn.getElement().addClassName("disabled");
				savewordbtn.getElement().removeClassName("active");
				savewordbtn.getElement().addClassName("disabled");
				infobtn.getElement().removeClassName("disabled");
				infobtn.getElement().addClassName("active");
				displayEnglishWord = new HTML(graview);
				displayEnglishWord.setStyleName("displayEnglishWord");
				wordinfo.add(displayFinnishWord);
				wordinfo.add(displayEnglishWord);
			}
		});
		
		trbtn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				wordinfo.clear();
				displayEnglishWord = new HTML(traview);
				displayEnglishWord.setStyleName("displayEnglishWord");
				infobtn.getElement().removeClassName("active");
				infobtn.getElement().addClassName("disabled");
				savewordbtn.getElement().removeClassName("active");
				savewordbtn.getElement().addClassName("disabled");
				trbtn.getElement().removeClassName("disabled");
				trbtn.getElement().addClassName("active");
				wordinfo.add(displayFinnishWord);
				wordinfo.add(displayEnglishWord);
			}
		});
		
		savewordbtn.addClickHandler(new SaveWordClickhandler());
	}
	
	private class SaveTextClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if(!insertTitle.getText().equals("")) {
				text.setTitle(insertTitle.getText());
				HTMLPanel success = new HTMLPanel("");
				saveTextPanel.clear();
				saveTextPanel.add(success);
				success.setStyleName("alert alert-success add-alert");
				success.getElement().setAttribute("role", "alert");
				success.add(new HTML("<strong><i class=\"far fa-check-circle\"></i>  Done!</strong> This text is now saved."));
				greetingService.saveTexts(text, user, new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}

					@Override
					public void onSuccess(Void result) {
						textName.clear();
						textName.add(new HTML(text.getTitle()));
						user.getTexts().add(text);
					}
				});
			} else {
				Window.alert("Title cannot be empty!");
			}
			
		}
		
	}
	
	private class SaveWordClickhandler implements ClickHandler {
		
		@Override
		public void onClick(ClickEvent event) {
			Anchor saveword = new Anchor("Save word");
			saveword.addStyleName("btn btn-primary save-word-btn");
			wordinfo.clear();
			infobtn.getElement().removeClassName("active");
			infobtn.getElement().addClassName("disabled");
			trbtn.getElement().removeClassName("active");
			trbtn.getElement().addClassName("disabled");
			savewordbtn.getElement().removeClassName("disabled");
			savewordbtn.getElement().addClassName("active");
			
			if((user.getWordList().contains(displayFinnishWord.getText()) || (user.getWordList().contains(lemma)))) {
				wordinfo.clear();
				HTMLPanel success = new HTMLPanel("");
				wordinfo.add(success);
				success.setStyleName("alert alert-danger add-alert");
				success.getElement().setAttribute("role", "alert");
				success.add(new HTML("<strong><i class=\"fas fa-exclamation-triangle\"></i>  Sorry! </strong> This word is already saved."));
				
			} else {
				wordconfig.add(saveword);
				saveword.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
							greetingService.saveWords(displayFinnishWord.getText(), user, new AsyncCallback<Void>() {    

								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub
									wordinfo.clear();
									HTMLPanel success = new HTMLPanel("");
									wordconfig.add(success);
									success.setStyleName("alert alert-danger add-alert");
									success.getElement().setAttribute("role", "alert");
									success.add(new HTML("<strong><i class=\"fas fa-exclamation-triangle\"></i>  Sorry! </strong> Something went wrong."));
									
								}

								@Override
								public void onSuccess(Void result) {
									if (lemma.equals("NaN")){
										user.getWordList().add(displayFinnishWord.getText());
									}
									else {
										user.getWordList().add(lemma);

									}
									wordinfo.clear();
									HTMLPanel success = new HTMLPanel("");
									wordinfo.add(success);
									success.setStyleName("alert alert-success add-alert");
									success.getElement().setAttribute("role", "alert");
									success.add(new HTML("<strong><i class=\"far fa-check-circle\"></i>  Done!</strong> Word saved successfully."));
								}
							});
					}
				});
				wordinfo.add(displayFinnishWord);
				wordinfo.add(saveword);
			}
		}


	}

}
