package net.preibisch.flymapping.seq.aerts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import net.preibisch.flymapping.config.PathUtils;
import net.preibisch.flymapping.tools.TxtProcess;

public class Aerts_57k_cells {
	int chunk;
	HashMap<String, List<String>> elments;

	public Aerts_57k_cells(HashMap<String, List<String>> elments, int chunk) {
		this.elments = elments;
		this.chunk = chunk;
	}

	public static Aerts_57k_cells fromFile(String path) throws FileNotFoundException {

		Gson gson = new Gson();

		JsonReader json = new JsonReader(new FileReader(path));
		return gson.fromJson(json, Aerts_57k_cells.class);

	}

	@Override
	public String toString() {
		Set<Entry<String, List<String>>> entry = elments.entrySet();
		return "Size: " + elments.size() + "List: " + elments.values().iterator().next().size();
	}

	public static int toChuncks(File input, File output_folder, String output_name, int chunk) throws IOException {
		int i = 0, n_chunk = 0;

		long col = TxtProcess.columns(input);
		long lines = TxtProcess.lines(input);
		List<String> linesNames = new ArrayList<>();

		TxtProcess.infos(input.toString(), col, lines);

		int totalChunks = ((int) (lines / chunk));

		System.out.println("Expected total chunks: " + totalChunks);

		Scanner sc = new Scanner(input, "UTF-8");

		HashMap<String, List<String>> elements = new HashMap<String, List<String>>();

		String outName = "head_" + output_name + ".json";
		File outFile = PathUtils.Result(output_folder, outName);

		createHeadFile(outFile, sc.nextLine());

		while (sc.hasNextLine()) {
			if (i >= chunk) {
				outName = n_chunk + "_" + output_name + ".json";
				outFile = PathUtils.Result(output_folder, outName);
				save(chunk, outFile, elements);

				System.out.println(n_chunk + "/" + totalChunks + " | " + outFile);
				n_chunk++;
				elements.clear();
				i = 0;
			}
			String line = sc.nextLine();
			LinkedList<String> elm = new LinkedList<String>(Arrays.asList(line.split("	")));
			String index = elm.get(0);
			index = index.replace("\"", "");
			linesNames.add(index);
			elm.remove(0);
			elements.put(index, elm);
			i++;
		}

		outName = "rows_" + output_name + ".json";
		outFile = PathUtils.Result(output_folder, outName);
		save(outFile, linesNames);

		outName = n_chunk + "_" + output_name + ".json";
		outFile = PathUtils.Result(output_folder, outName);
		save(chunk, outFile, elements);
		return n_chunk;
	}

	private static void createHeadFile(File outFile, String line) throws IOException {

		List<String> elm = new LinkedList<String>(Arrays.asList(line.replace("\"", "").split("	")));

		save(outFile, elm);

		elm.clear();
	}

	private static void save(File file, Object object) throws IOException {
		Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
		Gson gson = new GsonBuilder().create();
		gson.toJson(object, writer);
		writer.flush();
		writer.close();
	}

	private static void save(int chunk,  File file, HashMap<String, List<String>> elements)
			throws IOException {
		Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
		Aerts_57k_cells aerts_57k_cells = new Aerts_57k_cells(elements, chunk);
		Gson gson = new GsonBuilder().create();
		gson.toJson(aerts_57k_cells, writer);
		writer.flush();
		writer.close();
	}

	public static HashMap<String, HashMap<HashMap<Integer, String>, Integer>> getExpressedCells(File input) throws IOException {
		int i = 0;
		long col = TxtProcess.columns(input);
		long lines = TxtProcess.lines(input);

		TxtProcess.infos(input.toString(), col, lines);

		Scanner sc = new Scanner(input, "UTF-8");

		List<String> cellsNames = new LinkedList<String>(Arrays.asList(sc.nextLine().replace("\"", "").split("	")));

		HashMap<String, HashMap<HashMap<Integer, String>, Integer>> elements = new HashMap<>();

		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			LinkedList<String> elm = new LinkedList<String>(Arrays.asList(line.split("	")));
			String index = elm.get(0).replace("\"", "");

			elm.remove(0);
			System.out.println(i + " -" + "Looking for Expressed Cells");
			HashMap<HashMap<Integer, String>, Integer> expressedCells = getExpressedCellsInGene(elm, cellsNames);
			System.out.println(i + " -" + "Got Expressed Cells");
			elements.put(index, expressedCells);
			i++;
		}

		return elements;

	}

	private static HashMap<HashMap<Integer, String>, Integer> getExpressedCellsInGene(LinkedList<String> elm,
			List<String> cellsNames) {
		HashMap<HashMap<Integer, String>, Integer> result = new HashMap<>();

		List<Integer> vals = elm.stream().map(Integer::parseInt).collect(Collectors.toList());
		for (int i = 0; i < vals.size(); i++) {
			if (vals.get(i) > 0) {
				String cellName = cellsNames.get(i);
				HashMap<Integer, String> cellInfo = new HashMap<>();
				cellInfo.put(i, cellName);
				result.put(cellInfo, vals.get(i));
			}
		}
		return result;
	}
	
	public static List<String> getCellsNames(File input) throws FileNotFoundException {
		Scanner sc = new Scanner(input, "UTF-8");
		List<String> cellsNames = new LinkedList<String>(Arrays.asList(sc.nextLine().replace("\"", "").split("	")));
		return cellsNames;
	}

	public static List<String> getGenesNames(File input) throws FileNotFoundException {
		
		Scanner sc = new Scanner(input, "UTF-8");

		List<String> elements = new ArrayList<>();

		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			LinkedList<String> elm = new LinkedList<String>(Arrays.asList(line.split("	")));
			String index = elm.get(0).replace("\"", "");
			elements.add(index);
		}

		return elements;
	}

}