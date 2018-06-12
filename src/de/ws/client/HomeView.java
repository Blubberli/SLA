package de.ws.client;

import java.io.Serializable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import de.ws.shared.User;

public class HomeView extends Composite implements Serializable {

	// UIBinder
	private static HomeViewUiBinder uiBinder = GWT.create(HomeViewUiBinder.class);
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	interface HomeViewUiBinder extends UiBinder<Widget, HomeView> {
	}

	@UiField
	HTMLPanel mainPanel;
	@UiField
	HTMLPanel navPanel1;
	@UiField
	Anchor userTab;
	@UiField
	Anchor textTab;
	@UiField
	Anchor inputTextTab;
	@UiField
	Anchor homeTab;
	@UiField
	HTMLPanel contentPanel;
	@UiField
	Anchor gamesTab;
	@UiField
	Anchor myListTab;
	@UiField
	Anchor WordClusterTab;
	@UiField
	HTMLPanel aboutCourPanel;
	@UiField 
	Anchor logoutTab;
	HTML helloworld;
	TextBox name;
	Button sign;

	User user;

	public HomeView() {

		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiConstructor
	public HomeView(HTMLPanel mainPanel, User user) {

		initWidget(uiBinder.createAndBindUi(this));
		this.user = user;
		this.mainPanel = mainPanel;
		mainPanel.clear();
		navPanel1.setStyleName("navpan");
		contentPanel.clear();
		contentPanel.addStyleName("contentPanel");

		HTML words = new HTML("");
		for(String s : user.getWordList()) {
			words = new HTML(s + ", " + words.toString());
		}

		contentPanel.add(aboutCourPanel);

		userTab.getElement().setAttribute("data-toggle", "dropdown");
		userTab.getElement().setAttribute("role", "button");
		userTab.getElement().setAttribute("aria-haspopup", "true");
		userTab.getElement().setAttribute("expanded", "false");
		userTab.setHTML("<i class=\"fas fa-user-circle\" aria-hidden=\"true\"></i><span>&nbsp;&nbsp;" + user.getName() +  "</span>");
		homeTab.setHTML("<i class=\"fas fa-home\" aria-hidden=\"true\"></i><span>&nbsp; Home</span>");
		textTab.setHTML("<i class=\"fas fa-file-alt\" aria-hidden=\"true\"></i><span>&nbsp; Texts</span>");
		inputTextTab.setHTML("<i class=\"fas fa-edit\" aria-hidden=\"true\"></i><span>&nbsp; Insert Text</span>");
		gamesTab.setHTML("<i class=\"fas fa-gamepad\" aria-hidden=\"true\"></i><span>&nbsp; Game</span>");
		myListTab.setHTML("<i class=\"fas fa-book\" aria-hidden=\"true\"></i><span>&nbsp; My List</span>");
		WordClusterTab.setHTML("<i class=\"fa fa-quote-right\" aria-hidden=\"true\"></i><span>&nbsp; WordCluster</span>");


		homeTab.getElement().addClassName("navAnchorsActive");
		
		logoutTab.addClickHandler(new LogoutClickHandler());
		textTab.addClickHandler(new TextViewClickHandler(contentPanel, user));
		gamesTab.addClickHandler(new GamesViewClickHandler(contentPanel, user));
		myListTab.addClickHandler(new MyListViewClickHandler(contentPanel, user));
		homeTab.addClickHandler(new HomeViewClickHandler(mainPanel, user));
		WordClusterTab.addClickHandler(new ClusterClickHandler(contentPanel, user));

		mainPanel.add(navPanel1);
		mainPanel.add(contentPanel);



		inputTextTab.addClickHandler(new InputTextClickHandler(contentPanel, user));

	}

	private class InputTextClickHandler implements ClickHandler {

		HTMLPanel cp;
		User user;

		public InputTextClickHandler(HTMLPanel cp, User user) {
			this.cp = cp;
			this.user = user;
		}
		@Override
		public void onClick(ClickEvent event) {
			gamesTab.getElement().removeClassName("navAnchorsActive");
			myListTab.getElement().removeClassName("navAnchorsActive");
			homeTab.getElement().removeClassName("navAnchorsActive");
			textTab.getElement().removeClassName("navAnchorsActive");
			inputTextTab.getElement().addClassName("navAnchorsActive");
			
			InputTextView itw = new InputTextView(cp, user);
		}


	}



	private class LogoutClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Window.Location.reload();
		}

	}
	
	private class TextViewClickHandler implements ClickHandler {

		HTMLPanel cp;
		User user;

		public TextViewClickHandler(HTMLPanel cp, User user) {
			this.cp = cp;
			this.user = user;
		}
		@Override
		public void onClick(ClickEvent event) {
			gamesTab.getElement().removeClassName("navAnchorsActive");
			myListTab.getElement().removeClassName("navAnchorsActive");
			homeTab.getElement().removeClassName("navAnchorsActive");
			textTab.getElement().addClassName("navAnchorsActive");
			inputTextTab.getElement().removeClassName("navAnchorsActive");
			
			TextsView tw = new TextsView(cp, user);
		}
	}

	private class HomeViewClickHandler implements ClickHandler {

		HTMLPanel cp;
		User user;

		public HomeViewClickHandler(HTMLPanel cp, User user) {
			this.cp = cp;
			this.user = user;
		}
		@Override
		public void onClick(ClickEvent event) {
			gamesTab.getElement().removeClassName("navAnchorsActive");
			myListTab.getElement().removeClassName("navAnchorsActive");
			homeTab.getElement().addClassName("navAnchorsActive");
			textTab.getElement().removeClassName("navAnchorsActive");
			inputTextTab.getElement().removeClassName("navAnchorsActive");
			
			HomeView tw = new HomeView(cp, user);
		}
	}
	
	private class GamesViewClickHandler implements ClickHandler {

		HTMLPanel cp;
		User user;

		public GamesViewClickHandler(HTMLPanel cp, User user) {
			this.cp = cp;
			this.user = user;
		}
		@Override
		public void onClick(ClickEvent event) {
			gamesTab.getElement().addClassName("navAnchorsActive");
			myListTab.getElement().removeClassName("navAnchorsActive");
			homeTab.getElement().removeClassName("navAnchorsActive");
			textTab.getElement().removeClassName("navAnchorsActive");
			inputTextTab.getElement().removeClassName("navAnchorsActive");
			GameInstructionView gw = new GameInstructionView(cp, user);
		}
	}
	
	private class MyListViewClickHandler implements ClickHandler {

		HTMLPanel cp;
		User user;

		public MyListViewClickHandler(HTMLPanel cp, User user) {
			this.cp = cp;
			this.user = user;
		}
		@Override
		public void onClick(ClickEvent event) {
			gamesTab.getElement().removeClassName("navAnchorsActive");
			myListTab.getElement().addClassName("navAnchorsActive");
			homeTab.getElement().removeClassName("navAnchorsActive");
			textTab.getElement().removeClassName("navAnchorsActive");
			inputTextTab.getElement().removeClassName("navAnchorsActive");
			
			MyListView lw = new MyListView(cp, user, user.getWordList());
		}
	}
	
	private class ClusterClickHandler implements ClickHandler {

		HTMLPanel cp;
		User user;

		public ClusterClickHandler(HTMLPanel cp, User user) {
			this.cp = cp;
			this.user = user;
		}
		@Override
		public void onClick(ClickEvent event) {
			gamesTab.getElement().removeClassName("navAnchorsActive");
			myListTab.getElement().addClassName("navAnchorsActive");
			homeTab.getElement().removeClassName("navAnchorsActive");
			textTab.getElement().removeClassName("navAnchorsActive");
			inputTextTab.getElement().removeClassName("navAnchorsActive");
			
			ClusterListView w = new ClusterListView(cp, user);
		}
	}
}
