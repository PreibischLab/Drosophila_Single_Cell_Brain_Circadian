package net.preibisch.flymapping.seq.aerts;

import net.preibisch.flymapping.tools.PathsUtils;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

/***
 * 1
 * aerts_57k_cells_raw.txt is huge, This class will help you to split it in small files
 * 1899 cells for 57k genes
 * from Aerts single cell paper 2018. this is more deep and more cells so we should start with this data
 */
@CommandLine.Command
public class Prepare_aerts_57k_cells_raw implements Callable<Void> {

	public static void main(String[] args) throws IOException {
		CommandLine.call(new Prepare_aerts_57k_cells_raw(), args);
	}

	public Void call() throws IOException {

		System.out.println("Start prepare aerts 57k cells raw ");
		File aerts_57k_cells_raw_path = PathsUtils.File(AertsPaths.aerts_57k_cells_raw);
		File aerts_57k_cells_raw_output_folder = PathsUtils.ResultFolder(AertsPaths.aerts_57k_cells_raw_folder);
		int chuncks = Aerts_57k_cells.toChuncks(aerts_57k_cells_raw_path, aerts_57k_cells_raw_output_folder,
				AertsPaths.aerts_57k_cells_raw_folder, 1);
		System.out.println("Chunks: " + chuncks);
		System.out.println("Finish prepare aerts 57k cells raw ");

		return null;
	}
}
