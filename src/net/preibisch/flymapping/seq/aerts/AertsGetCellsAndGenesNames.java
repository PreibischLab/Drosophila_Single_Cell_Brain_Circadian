package net.preibisch.flymapping.seq.aerts;

import net.preibisch.flymapping.tools.GsonIO;
import net.preibisch.flymapping.tools.PathsUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/***
 * 2
 * Get only cells names from aerts files
 */
public class AertsGetCellsAndGenesNames {
	public static void main(String[] args) throws IOException {
		System.out.println("Start get expressed cells ");

		File input = PathsUtils.File(AertsPaths.aerts_57k_cells_raw);

		List<String> list = Aerts_57k_cells.getGenesNames(input);
		System.out.println("Genes Size : " + list.size());

		File resultFile = PathsUtils.ResultFile(AertsPaths.aerts_57k_genes_names);
		GsonIO.save(resultFile, list);

		list = Aerts_57k_cells.getCellsNames(input);
		System.out.println("Cells Size : " + list.size());

		resultFile = PathsUtils.ResultFile(AertsPaths.aerts_57k_cells_names);
		GsonIO.save(resultFile, list);

		System.out.println("Finish get expressed cells ");
	}
}
