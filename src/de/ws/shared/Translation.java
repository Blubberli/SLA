package de.ws.shared;

import java.io.Serializable;
import java.util.ArrayList;

public class Translation implements Serializable {
	
	private static final long serialVersionUID = 2733329356L;
	
	String translation;
	String grammatical_info;
	String lemma;
	
	public Translation() {
	}
	public Translation(String[] strings) {
		this.translation = strings[1];
		this.grammatical_info = strings[0];
		if (this.grammatical_info.contains("// Lemma")) {
			this.lemma = this.grammatical_info.split("// Lemma:")[1].trim();
		}
		else {
			this.lemma = "NaN";
		}
	}

	public String getTranslation(){
		return translation;
	}
	public String getGrammaticalInfo(){
		return grammatical_info;
	}
	public String getLemma() {
		return lemma;
	}


}
