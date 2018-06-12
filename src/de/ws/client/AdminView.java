package de.ws.client;

import java.util.ArrayList;
import java.util.HashMap;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.TableElement;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.dom.client.TableSectionElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;



public class AdminView extends Composite{

	private static AdminViewUiBinder uiBinder = GWT.create(AdminViewUiBinder.class);
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

	interface AdminViewUiBinder extends UiBinder<Widget, AdminView> {
	}

	public AdminView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	HTMLPanel contentPanel;
	HTMLPanel hp;
	Button addUser;
	HTML nameInstr;
	HTML passwdInstr;
	@UiField
	HTMLPanel navPanel1;
	@UiField
	HTMLPanel inside;
	@UiField
	Anchor logout;
	@UiField
	FlexTable table;
	HashMap<Integer, String> userList;
	@UiField
	HTMLPanel addUserPanel;
	@UiField
	TextBox name;
	@UiField
	TextBox password;
	@UiField
	TextBox password2;
	@UiField
	HTMLPanel card;
	@UiField 
	HTMLPanel success;
	@UiField
	Anchor addbtn;

	public AdminView(HTMLPanel contentPanel) {
		initWidget(uiBinder.createAndBindUi(this));
		this.contentPanel = contentPanel;

		contentPanel.clear();
		success.clear();
		navPanel1.setStyleName("navpan");

		name.getElement().setAttribute("placeholder", "Username");
		name.getElement().setAttribute("aria-label", "Username");
		name.getElement().setAttribute("aria-describedby", "basic-addon1");
		name.getElement().setAttribute("type", "text");

		password.getElement().setAttribute("placeholder", "Password");
		password.getElement().setAttribute("aria-label", "Password");
		password.getElement().setAttribute("aria-describedby", "basic-addon2");
		password.getElement().setAttribute("type", "password");

		password2.getElement().setAttribute("placeholder", "Repeat Password");
		password2.getElement().setAttribute("aria-label", "Repeat Password");
		password2.getElement().setAttribute("aria-describedby", "basic-addon2");
		password2.getElement().setAttribute("type", "password");

		inside.add(addUserPanel);
		addUserPanel.add(card);

		logout.addClickHandler(new LogoutClickHandler());
		addbtn.addClickHandler(new NewUserClickHandler(name, password, password2, table));

		table.clear();
		greetingService.getUserList(new UserListCallback(table));

		com.google.gwt.user.client.Element oldElement = table.getElement();
		com.google.gwt.dom.client.Element element = (com.google.gwt.dom.client.Element) oldElement;
		TableElement tableElement = (TableElement) element;
		TableSectionElement tHead = tableElement.createTHead();
		tHead.setAttribute("class", "table-header");
		TableRowElement row = tHead.insertRow(0);

		row.insertCell(0).setInnerText("#");
		row.getCells().getItem(0).setAttribute("width", "30%");
		row.insertCell(1).setInnerText("Username");
		row.getCells().getItem(1).setAttribute("width", "40%");
		row.insertCell(2).setInnerText("Delete");
		row.getCells().getItem(2).setAttribute("width", "30%");

		inside.add(table);
		inside.addStyleName("contentPanel");
		contentPanel.add(navPanel1);
		contentPanel.add(inside);
	}

	public HashMap<Integer, String> getUserList() {
		return userList;
	}

	public void setUserList(HashMap<Integer, String> userList) {
		this.userList = userList;
	}

	private class NewUserClickHandler implements ClickHandler {
		TextBox name;
		TextBox passwd;
		TextBox passwd2;
		FlexTable table;

		public NewUserClickHandler(TextBox name, TextBox passwd, TextBox passwd2, FlexTable table) {
			this.name = name;
			this.passwd = passwd;
			this.passwd2 = passwd2;
			this.table = table;
		}

		@Override
		public void onClick(ClickEvent event) {
			greetingService.checkPassword1(name.getText(), passwd.getText(), passwd2.getText(), new AsyncCallback<String>() {

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					success.clear();
					success.setStyleName("alert alert-danger add-alert");
					success.getElement().setAttribute("role", "alert");
					success.add(new HTML("<strong><i class=\"fas fa-exclamation-triangle\"></i>  Error!</strong> " + caught.getMessage()));

				}

				@Override
				public void onSuccess(String result) {
					greetingService.addUser(name.getText(), passwd.getText(), new NewUserCallback(name, passwd, passwd2));
					greetingService.getUserList(new UserListCallback(table));


				}
			});

			greetingService.getUserList(new UserListCallback(table));
		}

	}

	private class NewUserCallback implements AsyncCallback<String> {
		TextBox name;
		TextBox psw1;
		TextBox psw2;
		public NewUserCallback(TextBox name, TextBox psw1, TextBox psw2) {
			this.name = name;
			this.psw1 = psw1;
			this.psw2 = psw2;
		}

		@Override
		public void onSuccess(String message) {

			success.clear();
			success.setStyleName("alert alert-success add-alert");
			success.getElement().setAttribute("role", "alert");
			success.add(new HTML("<strong><i class=\"far fa-check-circle\"></i>  Done!</strong> User added successfully."));
			Timer timer = new Timer() {
				@Override
				public void run() {
					success.clear();
					success.removeStyleName("alert");
				}
			};
			timer.schedule(300000);
			name.setText(null);
			psw1.setText(null);
			psw2.setText(null);
		}

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Something didn't work, please pay some money!");

		}
	}

	private class UserListCallback implements AsyncCallback<ArrayList<String>> {

		FlexTable table;

		public UserListCallback(FlexTable table) {
			this.table = table;
		}

		@Override
		public void onSuccess(final ArrayList<String> userList) {
			table.clear();
			for(int i=0; i<userList.size(); i++) {
				final int idx = i;

				table.setText(i, 0, i+1+"");
				table.setText(i, 1, userList.get(i));

				if(!userList.get(i).equals("admin")) {
					Widget widget1 = new Anchor("");
					((Anchor) widget1).setHTML("<i class=\"fas fa-times\"></i>");
					widget1.setStyleName("delete-user-button");
					HTMLPanel panel = new HTMLPanel("");
					panel.add(widget1);
					// get the table
					table.setWidget(i, 2, panel);
					widget1.addDomHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							greetingService.deleteUser(userList.get(idx), new DeleteUserCallback(userList.get(idx), idx));
						}
					}, ClickEvent.getType());
				} else {
					table.setText(i, 2, "");
				}

			}
		}

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Din't work");

		}
	}

	private class DeleteUserCallback implements AsyncCallback<Void> {

		String name;
		int idx;

		public DeleteUserCallback(String name, int idx) {
			this.name = name;
			this.idx = idx;
		}
		@Override
		public void onSuccess(Void result) {

			for (int i = 0; i < table.getRowCount(); i++) {
				if (table.getText(i, 1).equals(name)) {
					table.removeRow(i);
					break;
				}
			}

		}

		@Override
		public void onFailure(Throwable caught) {
			Window.scrollTo(0, 0);
		}


	}

	private class LogoutClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Window.Location.reload();
		}

	}

}
