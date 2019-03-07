package net.preibisch.flymapping.img;

import static net.preibisch.flymapping.tools.GsonIO.read;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

import net.preibisch.flymapping.seq.ClustersExamples;
import net.preibisch.flymapping.seq.ResultsPaths;
import net.preibisch.flymapping.tools.GsonIO;
import net.preibisch.flymapping.tools.PathsUtils;

public class CellVoxels {

	private static final String file = "ACACCAAAGGTTCCTA-DGRP-551_0d_r1.json";
	private static final String janilaIdsPerCellFile = "SV_ACACCAAAGGTTCCTA-DGRP-551_0d_r1.json";

	public static void main(String[] args) throws IOException {

//		generateJanilaIdsPerCell();
		// We have cell expression pre janilaID superVoxol
//		Map<String, Double> cellJanilaId = read(PathsUtils.ResultFile(janilaIdsPerCellFile), Map.class);

		File concat_genes_path = PathsUtils.ResultFile(ResultsPaths.JanilaIDsPerGenes);
		Type type1 = new TypeToken<Map<String, List<String>>>() {
		}.getType();
		Map<String, List<String>> janilaIDsPerGenes = GsonIO.read(concat_genes_path, type1);

		Type type2 = new TypeToken<List<String>>() {
		}.getType();
		List<String> janilaIDsForSuperVoxels = read(PathsUtils.ResultFile(ResultsPaths.JanilaIDsForSuperVoxels),
				type2);

		Type type3 = new TypeToken<Map<String, Double>>() {
		}.getType();
		for (String file : ClustersExamples.cluster_2) {
			File cellFile = PathsUtils.ResultFile(file + ".json");
			Map<String, Double> cellGenes = GsonIO.read(cellFile, type3);

		Map<Integer, Double> supervoxelExpression = JanilaId.generateSuperVoxelIDPerCell(cellGenes, janilaIDsPerGenes,
				janilaIDsForSuperVoxels);

			File janilaIdsFile = PathsUtils.ResultFileFromString("SV_" + file);
		GsonIO.save(janilaIdsFile, supervoxelExpression);

		}

//		Map<Integer, List<List<Integer>>> hashMapVoxels 
	}

	private static void generateJanilaIdsPerCell() throws IOException {
		File cellFile = PathsUtils.ResultFile(file);
		Map<String, Double> cellGenes = GsonIO.read(cellFile, HashMap.class);

		File concat_genes_path = PathsUtils.ResultFile(ResultsPaths.JanilaIDsPerGenes);
		Map<String, List<String>> janilaIDsPerGenes = GsonIO.read(concat_genes_path, Map.class);

		Map<String, Double> cellJanilaId = JanilaId.generateJanilaIDPerCell(cellGenes, janilaIDsPerGenes);

		File janilaIdsFile = PathsUtils.ResultFile(janilaIdsPerCellFile);
		GsonIO.save(janilaIdsFile, cellJanilaId);
	}
}
