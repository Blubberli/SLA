package de.ws.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User extends Person implements Serializable {
	String name;
	List<String> wordList;
	int id;
	private List<Text> text;
	public void addText(Text tex) {
		if (text == null) {
			text = new ArrayList<>();
		}
		text.add(tex);
	}
	
	public List<Text> getTexts() {
		return text;
	}
	public void setTexts(List<Text> text) {
		this.text = text;
	}
	public User() {
		super.setRole("user");
		text = new ArrayList<>();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<String> getWordList() {
		return wordList;
	}
	public void setWordList(List<String> wordlist2) {
		this.wordList = wordlist2;
	}
	public List<String> addWordToList(String word) {
		wordList.add(word);
		return wordList;
	}
	

}
