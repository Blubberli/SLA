package de.ws.server;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.deeplearning4j.models.word2vec.Word2Vec;

import de.ws.shared.MapUtil;


public class WordEmbddings {
	
	
	public  static ArrayList<String> getClusteredLists(List<String> userlist, String embedding_path, String word, int clustersize){
		int maxlen = userlist.size();
		ArrayList<String>cluster = new ArrayList<String>();
		if (clustersize > maxlen) {
			clustersize = maxlen;
		}
		int counter = 0;
		System.out.println("try to read here");
	    Word2Vec word2Vec = WordVectorSerializer.readWord2VecModel(embedding_path);
		//WordVectors word2Vec = WordVectorSerializer.loadStaticModel(new File(embedding_path));

	    System.out.println("sucess");
	    Map<String, Double> sims = new HashMap<String, Double>();
        for (String w: userlist) {
            sims.put(w, word2Vec.similarity(word, w));

        }
        sims = MapUtil.sortByValue(sims);
        for (String key: sims.keySet()) {
        	cluster.add(key);
        	counter++;
        	if (counter == clustersize) {
        		break;
        	}
        }
        return cluster;
	}

	

}
