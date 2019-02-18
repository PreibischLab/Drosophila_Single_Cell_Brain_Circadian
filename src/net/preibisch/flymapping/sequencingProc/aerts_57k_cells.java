package net.preibisch.flymapping.sequencingProc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class aerts_57k_cells {
	HashMap<String, List<String>> elments;
	
	public aerts_57k_cells(HashMap<String, List<String>> elments) {
		this.elments = elments;
	}

	public static aerts_57k_cells fromFile(String path) {
		HashMap<String, List<String>> elements = new HashMap<String, List<String>>();
		try (Scanner sc = new Scanner(new File(SeqPaths.aerts_57k_cells_raw), "UTF-8")) {
			String line = sc.nextLine();
			List<String> elm = Arrays.asList(line.split("	"));
			elements.put("id", elm);
	        while (sc.hasNextLine()) {
	            line = sc.nextLine();
				elm = new LinkedList<String>(Arrays.asList(line.split("	")));
				String index = elm.get(0);
				elm.remove(0);
				elements.put(index, elm);
	        }
	        if (sc.ioException() != null) {
	        	sc.ioException().printStackTrace();
	        }
	    } catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return new aerts_57k_cells(elements);
		
	}
	


}
