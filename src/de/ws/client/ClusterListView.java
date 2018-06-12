package de.ws.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.ws.shared.Text;
import de.ws.shared.TokenizedText;
import de.ws.shared.Translation;
import de.ws.shared.User;

public class ClusterListView extends Composite implements Serializable  {
	
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	
	private static ClusterListViewUiBinder uiBinder = GWT.create(ClusterListViewUiBinder.class);

	interface ClusterListViewUiBinder extends UiBinder<Widget, ClusterListView> {
	}

	public ClusterListView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	HTMLPanel inputPanel;
	@UiField
	HTMLPanel contentPanel;
	User user;
	@UiField
	Button submit;
	@UiField
	TextBox user_answer;

	

	public ClusterListView(HTMLPanel contentPanel, User user) {
		initWidget(uiBinder.createAndBindUi(this));
		
		this.contentPanel = contentPanel;
		this.user = user;
		contentPanel.clear();
		contentPanel.add(inputPanel);
		submit.removeStyleName("gwt-Button");
		submit.addStyleName("submit_b");
		submit.addStyleName("btn btn-primary");
		submit.addClickHandler(new SubmitClickHandler(this.contentPanel, inputPanel));
		
	}
	private class SubmitClickHandler implements ClickHandler {
		HTMLPanel cp;
		HTMLPanel ip;
		List<String> userlist;
		String inputword;
	

		public SubmitClickHandler(HTMLPanel cp, HTMLPanel ip) {
			this.cp = cp;
			this.ip = ip;
			this.userlist = user.getWordList();
			this.inputword = user_answer.getText();
			
			
		}
		@Override
		public void onClick(ClickEvent event) {
			//String word = ip.getElementById("inputPassword2").getTitle();
			//String w = ip.getElementById("inputPassword2").getInnerHTML();
			greetingService.getCluster(userlist, inputword, 4, new ClusterCallback());

		}
	}
private class ClusterCallback implements AsyncCallback<ArrayList<String>> {
		
	@Override
		public void onSuccess(ArrayList<String> result) {
			//contentPanel.clear();
			Window.alert("success");
			Window.alert(String.valueOf(result.size()));
		}

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("not");
			Window.scrollTo(0, 0);
		}
	}



	

}
