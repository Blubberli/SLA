package de.ws.server;

import de.ws.client.GreetingService;
import de.ws.shared.FieldVerifier;
import de.ws.shared.Person;
import de.ws.shared.Text;
import de.ws.shared.TokenizedText;
import de.ws.shared.Translation;
import de.ws.shared.User;
import java.io.*;
import java.net.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;

import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {
	static HashMap<String, String[]> finnish_dictionary = new HashMap<String, String[]>();
	static Word2Vec word2Vec_model;
	
	
	public String checkPassword1(String name, String psw1, String psw2) throws IllegalArgumentException {
		if(!FieldVerifier.isValidName(name)) {
			throw new IllegalArgumentException("Name must be at least 3 characters");
		}else if(getUserList().contains(name)) {
			throw new IllegalArgumentException("This user name is already taken. Try a different one.");
		} else if (!FieldVerifier.isValidPassword1(psw1)) {
			throw new IllegalArgumentException("Password must contain at least 7 characters long!");
		} else if(!FieldVerifier.isValidPassword2(psw1)) {
			throw new IllegalArgumentException("Password must contain at least one digit!");
		} else if(!FieldVerifier.isValidPassword3(psw1)) {
			throw new IllegalArgumentException("Password must contain at least one letter!");
		} else if(!FieldVerifier.passwordRepeatedCorrectly(psw1, psw2)) {
			throw new IllegalArgumentException("Passwords are not the same, repeat again!");
		} else {
			return "";
		}
	}
	
	@Override
	public TokenizedText createText(String input){
		ArrayList<String> tokens = tokenize(input);
		TokenizedText t = new TokenizedText(tokens);
		t.setTranslationMap(getTextTranslation(tokens));
		return t;
	}

	/**
	 * Takes the input String from the user and tokenizes it. returns an ArrayList of Strings
	 * that represent the words. Adds chars to a Stringbuilder until the word boundary is reached by 
	 * a delimiter or a punctiation symbol. then the word is added to the stringbuilder.
	 * @param input
	 * @return
	 */
	public ArrayList<String> tokenize(String input){
		ArrayList<String> tokenList = new ArrayList<String>();
		StringReader reader = new StringReader(input);
		List<String> perferct_forms = Arrays.asList(new String [] {"lienen", "lienet", "lienee", "lienemme", "lienette", "lienevät", "lienee","ole", "olkoon", "olkaamme", "olkaa", "olkoot", "olkoon","olisin", "olisit", "olisi", "olisimme", "olisitte", "olisivat", "olisi","olen", "olet", "on", "olemme", "olete", "ovat", "on", "olin", "olit", "oli", "olimme", "olitte", "olivat", "oli"});		
		PTBTokenizer ptbt = new PTBTokenizer(reader, new CoreLabelTokenFactory(), "");
		while (ptbt.hasNext()) {
			String token = ptbt.next().toString().trim();
			if (ptbt.hasNext() && perferct_forms.contains(token.toLowerCase())) {
				String nextToken = ptbt.next().toString().trim();
				Translation nextTranslation = createTranslation(nextToken);
				String grammar = nextTranslation.getGrammaticalInfo();
				String potentialword = token + " " + nextToken;
				if (finnish_dictionary.containsKey(potentialword.toLowerCase())) {
					tokenList.add(potentialword+" ");
					if (ptbt.hasNext()) {
						ptbt.next();
					}
					continue;
				} else {
					tokenList.add(token+" ");
					tokenList.add(nextToken+" ");
					continue;
				}
			}
			tokenList.add(token+" ");
		}
		return tokenList;

	}
	public HashSet<String> getLemma(HashMap<String, Translation> translationmap){
		HashSet<String> lemmas = new HashSet<String>();
		for (String k : translationmap.keySet()) {
			lemmas.add(translationmap.get(k).getLemma());
		}
		return lemmas;
	}
	
	public HashMap<String, Translation> getTextTranslation(List<String> arr){
		HashMap<String, Translation> map = new HashMap<>();
		Translation tra;
		for(String t : arr) {
			//t = t.toLowerCase();
			tra = createTranslation(t);
			map.put(t, tra);
		}
		
		return map;
	}
	
	public String removeEnklitiks(String word) {
		List<String> enklitika = Arrays.asList(new String [] {"ka", "kä", "kö", "ko", "kan", "kän", "kin", "kaan", "kään", "han", "hän", "pa", "pä", "pas", "päs"});
			if (finnish_dictionary.containsKey(word)) {
				return word;
			}
			else if (word.equals("")) {
				return word;
			}
			boolean containsEnklitika = false;
			for (String en: enklitika) {
				if (word.endsWith(en)) {
					containsEnklitika = true;
					word = word.replace(en, "");
				}
			}
			if (containsEnklitika == false) {
				return word;
			}
			else {
				removeEnklitiks(word);
			}
			return word;
	}

	public Person getPerson(String name, String password){
		return DatabaseStorage.select(name, password);
	}
	
	public ArrayList<String> getUserList(){
		ArrayList<String> list = new ArrayList<>();
		list = DatabaseStorage.getUsersList();
		return list;

	}
	
	public String addUser(String name, String password){
		DatabaseStorage.insert(name, password);
		return "sucess";
	}
	
	public void deleteUser(String name){
		DatabaseStorage.delete(name);
	}
	
	public void deleteWord(String word, int id){
		DatabaseStorage.deleteWord(word, id);
	}

	@Override
	public void saveWords(String word, User user) {
		DatabaseStorage.saveWords(word, user);
	}
	
	@Override
	public void saveTexts(Text text, User user) {
		DatabaseStorage.saveTexts(text, user);
	}
	
	
	public static String getHTML(String urlToRead) throws Exception {
	      StringBuilder result = new StringBuilder();
	      URL url = new URL(urlToRead);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setRequestMethod("GET");
	      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	      String line;
	      while ((line = rd.readLine()) != null) {
	         result.append(line);
	      }
	      rd.close();
	      return result.toString();
	   }
	
	public Translation createTranslation(String token) {
		Translation tra = new Translation(finnish_dictionary.getOrDefault(token.trim().toLowerCase(), new String [] {"unknown", "unknown"}));
		if (finnish_dictionary.containsKey(token.trim())){
			tra = new Translation(finnish_dictionary.getOrDefault(token.trim(), new String [] {"unknown", "unknown"}));
		}
		String translation = tra.getTranslation();
		if (translation.equals("unknown")){
			token = removeEnklitiks(token.trim());
			tra = new Translation(finnish_dictionary.getOrDefault(token.trim().toLowerCase(), new String [] {"unknown", "unknown"}));
		}	
		return tra;
	}
	public ArrayList<String> getCluster(List<String> userlist, String word, int clustersize){
		String embedding_path = "./WEB-INF/lib/embeddings.txt";
		System.out.println("try to read");
		return (WordEmbddings.getClusteredLists(userlist, embedding_path, word, clustersize));
	}
	
	public void read_embeddings() {
		word2Vec_model = WordVectorSerializer.readWord2VecModel("./WEB-INF/lib/embeddings.txt");
		System.out.println("embeddings have been read");
	}

	/**
	 * read in the dictionary object and save it into static variable once (in on module load). 
	 */
	@SuppressWarnings("unchecked")
	public void load_Dictionary() {
		//InputStream stream = getServletContext().getResourceAsStream("/WEB-INF/lib/FinnishDictionary");
			 
		ObjectInputStream  objectInputStream = null;
			 HashMap<String, String[]> data = null;
		      try {
		        RandomAccessFile raf = new RandomAccessFile("./WEB-INF/lib/FinnishDictionary", "rw");
		        FileInputStream fos = new FileInputStream(raf.getFD());
		        objectInputStream = new ObjectInputStream(fos);
		        Object obj = objectInputStream.readObject();
		        if (obj instanceof HashMap<?, ?>){
		        	System.out.println("successfully loaded finnish database");
		        	data = (HashMap<String, String []>) obj;  
		        	System.out.println(data.size());
		        	}
		      } catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
		    	  try {
					objectInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		      }
		      finnish_dictionary =  data;
		}


}