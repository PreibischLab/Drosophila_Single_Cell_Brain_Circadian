package net.preibisch.flymapping.seq.droslines;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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

import net.preibisch.flymapping.tools.TxtProcess;

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

	public static int toChuncks(File input, File output_folder, String output_name, int chunk) throws IOException {
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

	public static HashMap<HashMap<Integer, String>, Map<Integer, Float>> getTopNFile(int n, File input,
			File iDGenespath) throws IOException {
		int index = 0;

		List<String> ids = Arrays.asList(new Scanner(iDGenespath, "UTF-8").nextLine().split("	"));
		Scanner sc = new Scanner(input, "UTF-8");
		HashMap<HashMap<Integer, String>, Map<Integer, Float>> elements = new HashMap<HashMap<Integer, String>, Map<Integer, Float>>();

		while (sc.hasNextLine()) {
			List<Float> elm = new LinkedList<String>(Arrays.asList(sc.nextLine().split("	"))).stream()
					.map(Float::parseFloat).collect(Collectors.toList());

			Map<Integer, Float> top = getTopN(n, elm);

			HashMap<Integer, String> geneInfo = new HashMap<>();
			geneInfo.put(index, ids.get(index));

			elements.put(geneInfo, top);

			index++;
		}
		System.out.println("index: " + index);

		return elements;
	}

	public static HashMap<String, Map<Integer, Float>> getExpressedMoreThan(double prob, File input, File iDGenespath,
			Map<String, String> janilaMapIdGenes) throws IOException {
		int index = 0;

		List<String> ids = Arrays.asList(new Scanner(iDGenespath, "UTF-8").nextLine().split("	"));
		System.out.println("Ids size = " + ids.size());

		Scanner sc = new Scanner(input, "UTF-8");
		HashMap<String, Map<Integer, Float>> elements = new HashMap<String, Map<Integer, Float>>();
		List<String> notFound = new ArrayList<>();
		while (sc.hasNextLine()) {
			List<Float> elm = new LinkedList<String>(Arrays.asList(sc.nextLine().split("	"))).stream()
					.map(Float::parseFloat).collect(Collectors.toList());

			Map<Integer, Float> top = getMorethan(elm, prob);

//			HashMap<Integer, String> geneInfo = new HashMap<>();
//			geneInfo.put(index, getJanilaName(ids.get(index)));

			String janilaId = getJanilaName(ids.get(index));
			if (janilaMapIdGenes.containsKey(janilaId)) {
				String geneName = janilaMapIdGenes.get(janilaId);

				if (elements.containsKey(geneName)) {
//					System.out.println(index + "-" + geneName + " found before");
					top.putAll(elements.get(geneName));
				}
				elements.put(geneName, top);
			} else {
				notFound.add(janilaId);
			}

			index++;
		}
		System.out.println("index: " + index);
		System.out.println("NOT FOUND: "+String.join(" - ", notFound));

		return elements;
	}
	
	public static List<String> getExpressedGenes(File iDGenespath,
			Map<String, String> janilaMapIdGenes) throws IOException {
		
		List<String> expressedGenes = new ArrayList<>();
		List<String> ids = Arrays.asList(new Scanner(iDGenespath, "UTF-8").nextLine().split("	"));
		System.out.println("Ids size = " + ids.size());
		List<String> notFound = new ArrayList<>();
		for(String id : ids){
			String janilaId = getJanilaName(id);
			if (janilaMapIdGenes.containsKey(janilaId)) {
				String geneName = janilaMapIdGenes.get(janilaId);	
				if(!expressedGenes.contains(geneName)) expressedGenes.add(geneName);	
			} else {
				notFound.add(janilaId);
			}
		}
		System.out.println("NOT FOUND: "+String.join(" - ", notFound));

		return expressedGenes;
	}

	private static String getJanilaName(String input) {
		String[] parts = input.split("_");
		String name = input;
		try {
			name = parts[0] + parts[1];
		} catch (Exception e) {
			System.out.println("Error convert name: " + input);
		}
		name = name.replace("'", "");
		return name;
	}

	private static Map<Integer, Float> getMorethan(List<Float> list, double prob) {
		Map<Integer, Float> map = new LinkedHashMap<Integer, Float>();

		for (int i = 0; i < list.size(); i++) {
			if (prob < list.get(i)) {
				map.put(i, list.get(i));
			}
		}
		return map;

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

		for (int i = 0; i < n; i++) {
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
			System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
		}
	}

	private static void save(int chunk, Path path, HashMap<Integer, List<Float>> elements) throws IOException {

		Writer writer = new OutputStreamWriter(new FileOutputStream(path.toString()), "UTF-8");
		Dros_lines dros_lines = new Dros_lines(elements, chunk);
		Gson gson = new GsonBuilder().create();
		gson.toJson(dros_lines, writer);
		writer.flush();
		writer.close();
	}

	public static void main(String[] args) {
		// Test get top N
		int n = 3;

		List<Float> array = Arrays.asList(3f, 3f, 8f, 100f, 9f, 0.1f, 85f);
		Map<Integer, Float> top = getTopN(n, array);

		printMap(top);
	}
}
