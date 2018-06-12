package de.ws.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.ws.shared.Person;
import de.ws.shared.Text;
import de.ws.shared.TokenizedText;
import de.ws.shared.Translation;
import de.ws.shared.User;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	TokenizedText createText(String input);
	Person getPerson(String name, String password);
	String addUser(String name, String passwd);
	void saveWords(String word, User user);
	ArrayList<String> getUserList();
	void deleteUser(String name);
	String checkPassword1(String name, String psw1, String psw2) throws IllegalArgumentException;
	Translation createTranslation(String token);
	HashMap<String, Translation> getTextTranslation(List<String> words);
	ArrayList<String>getCluster(List<String>userlist, String word, int clustersize);
	void load_Dictionary();
	void read_embeddings();
	void saveTexts(Text text, User user);
	void deleteWord(String word, int id);
}