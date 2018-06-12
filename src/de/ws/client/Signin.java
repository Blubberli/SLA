package de.ws.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.ws.shared.Admin;
import de.ws.shared.Person;
import de.ws.shared.User;

public class Signin extends Composite {

	private static SigninUiBinder uiBinder = GWT.create(SigninUiBinder.class);
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

	interface SigninUiBinder extends UiBinder<Widget, Signin> {
	}

	public Signin() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	HTMLPanel mainPanel;
	@UiField
	HTMLPanel loginform;
	@UiField
	TextBox name;
	@UiField
	TextBox password;
	@UiField
	Anchor send;
	@UiField
	HTMLPanel inputName;
	@UiField
	HTMLPanel card;
	@UiField
	HTMLPanel cardBody;
	@UiField
	HTMLPanel inputGroup;
	@UiField
	HTMLPanel inputGroup2;
	@UiField
	HTMLPanel inputPassword;
	@UiField 
	HTMLPanel success;
	@UiField 
	HTMLPanel header;

	public Signin(HTMLPanel mainPanel) {
		initWidget(uiBinder.createAndBindUi(this));
		this.mainPanel = mainPanel;
		HTML na = new HTML("Please enter your login");
		HTML pa = new HTML("Please enter your password");

		mainPanel.setStyleName("conPan");

		name.getElement().setAttribute("placeholder", "Username");
		name.getElement().setAttribute("aria-label", "Username");
		name.getElement().setAttribute("aria-describedby", "basic-addon1");
		name.getElement().setAttribute("type", "text");

		password.getElement().setAttribute("placeholder", "Password");
		password.getElement().setAttribute("aria-label", "Password");
		password.getElement().setAttribute("aria-describedby", "basic-addon2");
		password.getElement().setAttribute("type", "password");

		send.addClickHandler(new UserClickHandler());
		password.addKeyDownHandler(new UserPressHandler());		

		mainPanel.add(loginform);

	}



	public void singin(HTMLPanel mainPanel, TextBox name, TextBox password) {
		// TODO Auto-generated method stub
		greetingService.getPerson(name.getText(), password.getText(), new PersonCallback());

	}

	private class UserPressHandler implements KeyDownHandler {
		@Override
		public void onKeyDown(KeyDownEvent event) {
			if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				singin(mainPanel, name, password);
			}
		}
	}



	private class UserClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			singin(mainPanel, name, password);
		}

	}

	private class PersonCallback implements AsyncCallback<Person> {


		@Override
		public void onSuccess(Person person) {
			if(person == null) {
				success.clear();

				success.setStyleName("alert alert-danger add-alert");
				success.getElement().setAttribute("role", "alert");
				success.add(new HTML("<strong><i class=\"fas fa-exclamation-triangle\"></i>  Error!</strong> You have entered a wrong name or password please try again"));
				name.setText("");
				password.setText("");
				//				card.add(success);

			} else if(person instanceof Admin) {
				AdminView aw = new AdminView(mainPanel);

			} else if(person instanceof User) { 
				User user = (User) person; 
				HomeView hm = new HomeView(mainPanel, user);
			} 
		}

		@Override
		public void onFailure(Throwable caught) {
			Window.scrollTo(0, 0);
		}
	}



}
