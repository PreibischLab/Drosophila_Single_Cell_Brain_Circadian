package net.preibisch.flymapping.seq.aerts;

import net.preibisch.flymapping.seq.ResultsPaths;
import net.preibisch.flymapping.tools.GsonIO;
import net.preibisch.flymapping.tools.PathsUtils;
import picocli.CommandLine;
import picocli.CommandLine.Option;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

public class ThresholdingExpressedCellsInGenes implements Callable<Void> {

	@Option(names = { "-n" }, required = false, description = "The threshold for more than X")
	private Integer n;
	
	public static void main(String[] args) throws IOException {
		CommandLine.call(new ThresholdingExpressedCellsInGenes(), args);
	}

	@Override
	public Void call() throws FileNotFoundException, IOException {
		System.out.println("Start get expressed cells ");
		
		File concat_genes_path = PathsUtils.ResultFile(ResultsPaths.concat_genes);
		List<String> genes = GsonIO.read(concat_genes_path, List.class);

		System.out.println("Size :" + genes.size());

//		File input = PathsUtils.File(AertsPaths.aerts_57k_cells_raw);

		File input = PathsUtils.File(ResultsPaths.Cluster5_Aerts);
		HashMap<String, HashMap<HashMap<Integer, String>, Double>> result = Aerts_57k_cells
				.getNormalisedExpressedCellsInFoundGenes(input, genes);

		System.out.println("Finished size :" + result.size());

//		File resultFile = PathsUtils.ResultFile(AertsPaths.aerts_57k_cells_normalised_foundGenes);
		File resultFile = PathsUtils.ResultFile(AertsPaths.aerts_cellCluster5_normalised_foundGenes);

		GsonIO.save(resultFile, result);
		System.out.println("Finish get expressed cells ");
		return null;
	}
}


