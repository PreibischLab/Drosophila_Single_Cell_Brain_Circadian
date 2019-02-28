package net.preibisch.flymapping.headless;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

import net.preibisch.flymapping.sequencingProc.Aerts_57k_cells;
import net.preibisch.flymapping.sequencingProc.MyPaths;
import picocli.CommandLine;

public class Prepare_aerts_57k_cells_raw implements Callable<Void> {

	public static void main(String[] args) throws IOException {
		CommandLine.call(new Prepare_aerts_57k_cells_raw(), args);
	}

	@Override
	public Void call() throws IOException {

		System.out.println("Start prepare aerts 57k cells raw ");
		File aerts_57k_cells_raw_path = MyPaths.File(MyPaths.aerts_57k_cells_raw);
		File aerts_57k_cells_raw_output_folder = MyPaths.ResultFolder(MyPaths.aerts_57k_cells_raw_folder);
		int chuncks = Aerts_57k_cells.toChuncks(aerts_57k_cells_raw_path, aerts_57k_cells_raw_output_folder,
				MyPaths.aerts_57k_cells_raw_folder, 1);
		System.out.println("Chunks: " + chuncks);
		System.out.println("Finish prepare aerts 57k cells raw ");

		return null;
	}
}
