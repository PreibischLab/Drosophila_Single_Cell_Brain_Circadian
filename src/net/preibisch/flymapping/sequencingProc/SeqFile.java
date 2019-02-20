package net.preibisch.flymapping.sequencingProc;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class SeqFile {
	int chunk;
	HashMap<String, List<String>> elments;
	
	public SeqFile(HashMap<String, List<String>> elments,int chunk) {
		this.elments = elments;
		this.chunk = chunk;
	}

	public static SeqFile fromFile(String path) throws FileNotFoundException {
		
		Gson gson = new Gson();
	
		JsonReader json = new JsonReader(new FileReader(path));
		return gson.fromJson(json, SeqFile.class);
		
	}
	
	@Override
	public String toString() {
		Set<Entry<String, List<String>>> entry = elments.entrySet();
		return "Size: "+ elments.size() + "List: " + elments.values().iterator().next().size();
	}
	


}
