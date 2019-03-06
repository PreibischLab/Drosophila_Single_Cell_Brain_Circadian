package net.preibisch.flymapping.seq.aerts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import net.preibisch.flymapping.config.MyPaths;
import net.preibisch.flymapping.config.PathUtils;
import net.preibisch.flymapping.tools.GsonIO;
import picocli.CommandLine;
import picocli.CommandLine.Option;

public class ExpressedCellsInGenes implements Callable<Void> {

	@Option(names = { "-n" }, required = false, description = "The threshold for more than X")
	private Integer n;
	
	public static void main(String[] args) throws IOException {
		CommandLine.call(new ExpressedCellsInGenes(), args);
	}

	@Override
	public Void call() throws FileNotFoundException, IOException {
		System.out.println("Start get expressed cells ");
		
		File concat_genes_path = PathUtils.ResultFile(MyPaths.concat_genes);
		List<String> genes = GsonIO.read(concat_genes_path, List.class);

		System.out.println("Size :" + genes.size());

		File input = PathUtils.File(MyPaths.aerts_57k_cells_raw);

		HashMap<String, HashMap<HashMap<Integer, String>, Double>> result = Aerts_57k_cells
				.getNormalisedExpressedCellsInFoundGenes(input, genes);

		System.out.println("Finished size :" + result.size());

		File resultFile = PathUtils.ResultFile(MyPaths.aerts_57k_cells_normalised_foundGenes);
		GsonIO.save(resultFile, result);
		System.out.println("Finish get expressed cells ");
		return null;
	}
}


