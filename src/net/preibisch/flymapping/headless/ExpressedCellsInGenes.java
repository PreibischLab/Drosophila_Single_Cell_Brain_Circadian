package net.preibisch.flymapping.headless;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.Callable;

import net.preibisch.flymapping.sequencingProc.Aerts_57k_cells;
import net.preibisch.flymapping.sequencingProc.MyPaths;
import picocli.CommandLine;

public class ExpressedCellsInGenes implements Callable<Void> {

	public static void main(String[] args) throws IOException {
		CommandLine.call(new ExpressedCellsInGenes(), args);
	}

	@Override
	public Void call() throws FileNotFoundException, IOException {
		System.out.println("Start get expressed cells ");
		Aerts_57k_cells.getExpressedCells(MyPaths.File(MyPaths.aerts_57k_cells_raw),
				MyPaths.ResultFile(MyPaths.aerts_57k_cells_result));
		System.out.println("Finish get expressed cells ");
		return null;
	}
}
