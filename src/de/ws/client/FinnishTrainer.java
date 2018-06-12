package de.ws.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class FinnishTrainer implements EntryPoint, ValueChangeHandler<String> {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network " + "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

	
	HTMLPanel mainPanel;
	TextBox name;
	TextBox password;
	Button send;
	

	@Override
	public void onModuleLoad() {
		greetingService.load_Dictionary(new DictionaryCallback());
		greetingService.read_embeddings(new WordEmbeddingsCallback());
		mainPanel = new HTMLPanel("");
		
		RootPanel.get().setStyleName("haupt");
		RootPanel.get().add(mainPanel);
		Signin sign = new Signin(mainPanel);
		
	}
	
private class DictionaryCallback implements AsyncCallback<Void> {
		
		
		@Override
		public void onSuccess(Void result) {
			System.out.println("dic loaded");
		}

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("sth went wrong");
			Window.scrollTo(0, 0);
		}

	}
private class WordEmbeddingsCallback implements AsyncCallback<Void> {
	
	
	@Override
	public void onSuccess(Void result) {
		System.out.println("embeddings loaded");
	}

	@Override
	public void onFailure(Throwable caught) {
		Window.alert("sth went wrong");
		Window.scrollTo(0, 0);
	}

}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		// TODO Auto-generated method stub
		
	}
	
}
