package net.preibisch.flymapping.sequencingProc;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

public class Dros_lines {
	int chunk;
	HashMap<Integer, List<Float>> elments;

	public Dros_lines(HashMap<Integer, List<Float>> elments, int chunk) {
		this.elments = elments;
		this.chunk = chunk;
	}

	public static Dros_lines fromFile(String path) throws FileNotFoundException {

		Gson gson = new Gson();

		JsonReader json = new JsonReader(new FileReader(path));
		return gson.fromJson(json, Dros_lines.class);

	}

	@Override
	public String toString() {
		Set<Entry<Integer, List<Float>>> entry = elments.entrySet();
		return "Size: " + elments.size() + "List: " + elments.values().iterator().next().size();
	}

	public static int toChuncks(Path input, Path output_folder, String output_name, int chunk) throws IOException {
		int index = 0, i = 0, n_chunk = 0;

		long col = TxtProcess.columns(input);
		long lines = TxtProcess.lines(input);

		TxtProcess.infos(input.toString(), col, lines);

		int totalChunks = ((int) (lines / chunk));

		System.out.println("Expected total chunks: " + totalChunks);

		Scanner sc = new Scanner(input, "UTF-8");
		HashMap<Integer, List<Float>> elements = new HashMap<Integer, List<Float>>();

		String outFile;
		Path out;
		while (sc.hasNextLine()) {
			if (i >= chunk) {
				outFile = n_chunk + "_" + output_name + ".json";
				out = Paths.get(output_folder.toString(), outFile);
				save(chunk, out, elements);

				System.out.println(n_chunk + "/" + totalChunks + " | " + out.toString());
				n_chunk++;
				elements.clear();
				i = 0;
			}
			String line = sc.nextLine();
			List<Float> elm = new LinkedList<String>(Arrays.asList(line.split("	"))).stream().map(Float::parseFloat)
					.collect(Collectors.toList());
		
			elements.put(index, elm);
			i++;
			index++;
		}

		outFile = n_chunk + "_" + output_name + ".json";
		out = Paths.get(output_folder.toString(), outFile);
		save(chunk, out, elements);
		return n_chunk;
	}
	
	public static void getTopNFile(int n, Path input,String output_file) throws IOException {
		int index = 0;
		
		Scanner sc = new Scanner(input, "UTF-8");
		HashMap<Integer, Map<Integer, Float>> elements = new HashMap<Integer, Map<Integer, Float>>();

	
		while (sc.hasNextLine()) {
			List<Float> elm = new LinkedList<String>(Arrays.asList(sc.nextLine().split("	"))).stream().map(Float::parseFloat)
					.collect(Collectors.toList());
	
			Map<Integer, Float> top = getTopN(n, elm); 
			elements.put(index, top);

			index++;
		}

		save(output_file, elements);
	}


	

	private static Map<Integer, Float> getTopN(int n, List<Float> list) {
		int[] topIndexes = new int[n];
		float[] topVal = new float[n];

		for (int i = 0; i < list.size(); i++) {
			int pos = getPosition(list.get(i), topVal);
			if (pos < n) {
				for (int k = n - 1; k > pos; k--) {
					// System.out.println("K:"+k+" N:"+n+" pos:"+pos);
					topIndexes[k] = topIndexes[k - 1];
					topVal[k] = topVal[k - 1];
				}
				topIndexes[pos] = i;
				topVal[pos] = list.get(i);
			}
		}

		Map<Integer, Float> map = new LinkedHashMap<Integer, Float>();
		
		for(int i =0; i<n ; i++){
			map.put(topIndexes[i], topVal[i]);
		}
		return map;
	}

	private static int getPosition(Float f, float[] array) {
		int pos = 0;
		for (float v : array)
			if (v > f)
				pos++;
		return pos;
	}

	 public static <K, V> void printMap(Map<K, V> map) {
	        for (Map.Entry<K, V> entry : map.entrySet()) {
	            System.out.println("Key : " + entry.getKey() 
					+ " Value : " + entry.getValue());
	        }
	    }
	private static void save(int chunk, Path path, HashMap<Integer, List<Float>> elements)
			throws UnsupportedEncodingException, FileNotFoundException {

		Writer writer = new OutputStreamWriter(new FileOutputStream(path.toString()), "UTF-8");
		Dros_lines dros_lines = new Dros_lines(elements, chunk);
		Gson gson = new GsonBuilder().create();
		gson.toJson(dros_lines, writer);
	}
	
	private static void save(String path, HashMap<Integer, Map<Integer, Float>> elements) throws UnsupportedEncodingException, FileNotFoundException {
		Writer writer = new OutputStreamWriter(new FileOutputStream(path), "UTF-8");
		Gson gson = new GsonBuilder().create();
		gson.toJson(elements, writer);
	}

	public static void main(String[] args) {
		// Test get top N
		int n = 3;

		List<Float> array = Arrays.asList(3f,  3f, 8f, 100f,9f, 0.1f, 85f);
		Map<Integer, Float> top = getTopN(n, array);

		printMap(top);
	}
}