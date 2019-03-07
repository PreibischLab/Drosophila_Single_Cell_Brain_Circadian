package net.preibisch.flymapping.seq;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import net.preibisch.flymapping.seq.aerts.AertsPaths;
import net.preibisch.flymapping.seq.aerts.Aerts_57k_cells;
import net.preibisch.flymapping.tools.GsonIO;
import net.preibisch.flymapping.tools.PathsUtils;

public class CellLookup {
	public static void main(String[] args) throws IOException {
		final String cellExample = ClustersExamples.cluster_2[0];
		System.out.println("Start get expressed cells ");

		File concat_genes_path = PathsUtils.ResultFile(ResultsPaths.concat_genes);
		List<String> genes = GsonIO.read(concat_genes_path, List.class);

		System.out.println("Size :" + genes.size());

		File input = PathsUtils.File(AertsPaths.aerts_57k_cells_raw);

		HashMap<String, Double> result = Aerts_57k_cells
				.getNormalisedGenesForCell(input, genes, cellExample);

		System.out.println("Finished size :" + result.size());

//		File resultFile = PathsUtils.ResultFile(AertsPaths.aerts_57k_cells_normalised_foundGenes);
		File resultFile = PathsUtils.ResultFileFromString(cellExample);

		GsonIO.save(resultFile, result);
		System.out.println("Finish get expressed cells ");

	}
}
