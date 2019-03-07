package net.preibisch.flymapping.img;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import net.preibisch.flymapping.seq.ResultsPaths;
import net.preibisch.flymapping.seq.droslines.DrosLinesPaths;
import net.preibisch.flymapping.tools.GsonIO;
import net.preibisch.flymapping.tools.PathsUtils;
import net.preibisch.flymapping.tools.TxtProcess;

public class JanilaId {
	public static void main(String[] args) throws IOException {
		File iDSupervoxelspath = PathsUtils.File(DrosLinesPaths.dros_linesIDtoJaneliaID);

		System.out.println("Get infos..");
		TxtProcess.infos(iDSupervoxelspath);
		
		List<String> janilaIDsForSuperVoxels = convertToJanilaName(iDSupervoxelspath);
		System.out.println("Finish : Size : " + janilaIDsForSuperVoxels.size());
		
		File janilaIdsFile = PathsUtils.ResultFile(ResultsPaths.JanilaIDsForSuperVoxels);
		GsonIO.save(janilaIdsFile, janilaIDsForSuperVoxels);
	}

	public static Map<String, Double> generateJanilaIDPerCell(Map<String, Double> cellGenes,
			Map<String, List<String>> janilaIDsPerGenes) {
		Map<String, Double> JanilaIDs = new HashMap<>();
		for (Map.Entry<String, Double> entry : cellGenes.entrySet()) {
			if (janilaIDsPerGenes.containsKey(entry.getKey())) {
				for (String s : janilaIDsPerGenes.get(entry.getKey())) {
					JanilaIDs.put(s, entry.getValue());
				}
			} else {
				System.out.println("Gene: " + entry.getKey() + " NOT FOUND!");
			}
		}
		return JanilaIDs;
	}

	public static List<String> convertToJanilaName(File supervoxelIDs) throws FileNotFoundException {
		List<String> janilaNames = new ArrayList<>();
		List<String> ids = Arrays.asList(new Scanner(supervoxelIDs, "UTF-8").nextLine().split("	"));
		for (String id : ids) {
			String janilaId = getJanilaName(id);
			janilaNames.add(janilaId);
		}
		return janilaNames;
	}

	public static String getJanilaName(String input) {
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

	public static Map<Integer, Double> generateSuperVoxelIDPerCell(Map<String, Double> cellGenes,
			Map<String, List<String>> janilaIDsPerGenes, List<String> JanilaIds) {

		Integer errors = 0;
		int i = 0;
		Map<Integer, Double> JanilaIDs = new HashMap<>();
		System.out.println("Cells per gene size: " + cellGenes.size());
		for (Map.Entry<String, Double> entry : cellGenes.entrySet()) {
			if (janilaIDsPerGenes.containsKey(entry.getKey())) {
				for (String s : janilaIDsPerGenes.get(entry.getKey())) {
					if (JanilaIds.contains(s)) {
						int svId = JanilaIds.indexOf(s);

						JanilaIDs.put(svId, entry.getValue());
					} else {
						System.out.println(i + "-" + errors + "-JanileID: " + s + " Not Found!");
						errors++;
					}
					i++;
				}
			} else {
				System.out.println("Gene: " + entry.getKey() + " NOT FOUND!");
			}
		}
		return JanilaIDs;
	}
}
