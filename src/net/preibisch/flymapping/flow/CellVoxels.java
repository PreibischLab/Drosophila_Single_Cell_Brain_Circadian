package net.preibisch.flymapping.flow;

import com.google.gson.reflect.TypeToken;
import net.preibisch.flymapping.img.JanilaId;
import net.preibisch.flymapping.seq.ClustersExamples;
import net.preibisch.flymapping.seq.ResultsPaths;
import net.preibisch.flymapping.tools.GsonIO;
import net.preibisch.flymapping.tools.PathsUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static net.preibisch.flymapping.tools.GsonIO.read;

/***
 * This Class would be used the first,
 */
public class CellVoxels {

	private static final String file = "ACACCAAAGGTTCCTA-DGRP-551_0d_r1.json";
	private static final String janilaIdsPerCellFile = "SV_ACACCAAAGGTTCCTA-DGRP-551_0d_r1.json";

	public static void main(String[] args) throws IOException {

		Map<String, List<String>> janilaIDsPerGenes = GsonIO.read(PathsUtils.ResultFile(ResultsPaths.JanilaIDsPerGenes),
				new TypeToken<Map<String, List<String>>>() {
				}.getType());

		List<String> janilaIDsForSuperVoxels = read(PathsUtils.ResultFile(ResultsPaths.JanilaIDsForSuperVoxels),
				new TypeToken<List<String>>() {
				}.getType());

		for (String file : ClustersExamples.cluster_2) {
			File cellFile = PathsUtils.ResultFile(file + ".json");
			Map<String, Double> cellGenes = GsonIO.read(cellFile, new TypeToken<Map<String, Double>>() {
			}.getType());

			Map<Integer, Double> supervoxelExpression = JanilaId.generateSuperVoxelIDPerCell(cellGenes,
					janilaIDsPerGenes, janilaIDsForSuperVoxels);

			File janilaIdsFile = PathsUtils.ResultFileFromString("SV_" + file);
			GsonIO.save(janilaIdsFile, supervoxelExpression);
		}
	}

	public static void generateJanilaIdsPerCell() throws IOException {
		File cellFile = PathsUtils.ResultFile(file);
		Map<String, Double> cellGenes = GsonIO.read(cellFile, new TypeToken<Map<String, Double>>() {
		}.getType());

		File concat_genes_path = PathsUtils.ResultFile(ResultsPaths.JanilaIDsPerGenes);
		Map<String, List<String>> janilaIDsPerGenes = GsonIO.read(concat_genes_path,
				new TypeToken<Map<String, List<String>>>() {
				}.getType());

		Map<String, Double> cellJanilaId = JanilaId.generateJanilaIDPerCell(cellGenes, janilaIDsPerGenes);

		File janilaIdsFile = PathsUtils.ResultFile(janilaIdsPerCellFile);
		GsonIO.save(janilaIdsFile, cellJanilaId);
	}
}
