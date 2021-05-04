package net.preibisch.flymapping.flow;

import net.preibisch.flymapping.seq.ClustersExamples;
import net.preibisch.flymapping.seq.ResultsPaths;
import net.preibisch.flymapping.seq.aerts.AertsPaths;
import net.preibisch.flymapping.seq.aerts.Aerts_57k_cells;
import net.preibisch.flymapping.tools.GsonIO;
import net.preibisch.flymapping.tools.PathsUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class CellLookup {
	public static void main(String[] args) throws IOException {

		List<String> concatFoundGenes = GsonIO.read(PathsUtils.getPathForResultFile(ResultsPaths.GenesNamesWhichExistsInAertsAndDrosLines),
				List.class);

		File input = PathsUtils.File(AertsPaths.aerts_57k_cells_raw);

		System.out.println("Concat Genes Size :" + concatFoundGenes.size());
		Integer max = Aerts_57k_cells.getMaxExpressed(input);
		System.out.println("Max val: " + max);

		for (String cellExample : ClustersExamples.cluster_2) {

			HashMap<String, Double> result = lookForCellGenes(concatFoundGenes, input, cellExample, max);

			GsonIO.save(PathsUtils.ResultFileFromString(cellExample), result);
			System.out.println("Finish get expressed cells " + cellExample);
		}
	}

	public static HashMap<String, Double> lookForCellGenes(List<String> genes, File input, String cellExample,
			Integer max)
			throws IOException {
		System.out.println("Start get expressed cells " + cellExample);

		HashMap<String, Double> result = Aerts_57k_cells.getNormalisedGenesForCell(input, genes, cellExample, max);

		System.out.println("Finished Normalised Genes For Cell " + cellExample + " size :" + result.size());
		return result;
	}
}
