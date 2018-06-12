package de.ws.shared;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.user.client.ui.HTMLPanel;

import de.ws.server.GreetingServiceImpl;

public class TokenizedText implements Serializable{
	
	private static final long serialVersionUID = 2764686765633329356L;
	
	ArrayList<String> tokens;
	HashMap<String, Translation> translationMap;

	private ArrayList<Boolean> contained;
	
	public TokenizedText() {
	}
	
	public TokenizedText(ArrayList<String> tokens) {
		this.tokens = tokens;
		
	}
	public ArrayList<String> getTokens() {
		return tokens;
	}
	public void setTokens(ArrayList<String> tokens) {
		this.tokens = tokens;
	}

	public void setTranslationMap(HashMap<String, Translation> textTranslation) {
		translationMap = textTranslation;
	}

	public HashMap<String, Translation> getTranslationMap() {
		return translationMap;
	}

	public ArrayList<Boolean> getBoolArray() {
		// TODO Auto-generated method stub
		return contained;
	}

}