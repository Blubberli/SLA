package de.ws.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.ws.shared.Person;
import de.ws.shared.Text;
import de.ws.shared.TokenizedText;
import de.ws.shared.Translation;
import de.ws.shared.User;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void checkPassword1(String name, String psw1, String psw2, AsyncCallback<String> callback) throws IllegalArgumentException;
	void createText(String input, AsyncCallback<TokenizedText> callback);
	void getPerson(String name, String password, AsyncCallback<Person> callback);
	void addUser(String name, String passwd, AsyncCallback<String> callback);
	void deleteUser(String name, AsyncCallback<Void> callback);
	void saveWords(String word, User user, AsyncCallback<Void> callback);
	void getUserList(AsyncCallback<ArrayList<String>> callback);
	void createTranslation(String token, AsyncCallback<Translation> callback);
	void getCluster(List<String> userlist, String word, int clustersize, AsyncCallback<ArrayList<String>> callback);
	void load_Dictionary(AsyncCallback<Void> callback);
	void read_embeddings(AsyncCallback<Void> callback);
	void getTextTranslation(List<String> words, AsyncCallback<HashMap<String, Translation>> callback);
	void saveTexts(Text text, User user, AsyncCallback<Void> callback);
	void deleteWord(String word, int id, AsyncCallback<Void> callback);

}