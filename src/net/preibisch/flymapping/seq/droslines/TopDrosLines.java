package net.preibisch.flymapping.seq.droslines;

import net.preibisch.flymapping.tools.GsonIO;
import net.preibisch.flymapping.tools.PathsUtils;
import net.preibisch.flymapping.tools.TxtProcess;
import picocli.CommandLine;
import picocli.CommandLine.Option;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class TopDrosLines implements Callable<Void> {

	@Option(names = { "-x" }, required = false, description = "the threshold")
	private double x;

	@Option(names = { "-n" }, required = false, description = "The threshold for more than X")
	private Integer n;

	public static void main(String[] args) throws IOException {
		CommandLine.call(new TopDrosLines(), args);
	}

	@Override
	public Void call() throws Exception {
		x = 0.4f;

		System.out.println("Start get Top " + x);

		// This file is 7065 columns by 2083 lines
		// We will process it to get the more expressed / more than X = 0.4
		File dros_lines_expr_values_2083_genes_raw_path = PathsUtils
				.File(DrosLinesPaths.dros_lines_expr_values_2083_genes);

		// one line with 2083 columns contains the Janilla genes names
		// exmpl name GMR_10C06_AE_01
		// we convert it to Excel file name GMR_10C06_AE_01 => GMR10C06
		File iDGenespath = PathsUtils.File(DrosLinesPaths.dros_linesIDtoJaneliaID);

		System.out.println("Get infos..");
		TxtProcess.infos(dros_lines_expr_values_2083_genes_raw_path);
		TxtProcess.infos(iDGenespath);

//		File resultFile = MyPaths.ResultFile(MyPaths.dros_lines_expr_values_2083_genes_top);
//		Dros_lines.getTopNFile(n, dros_lines_expr_values_2083_genes_raw_path, iDGenespath, resultFile);

		// Getting Mapping name from Excel file
		// From janila name to gene name
		Map<String, String> janilaMapId = JaneliaIDtoGeneExcelReader.getJanilaGeneMap();

		// Result file
		// List 2083 genes Janila ids ( Exmpl: GMR10C06 ) with map of id of supervoxel
		// and probability of expression inside
		HashMap<String, Map<Integer, Float>> expressedGenesDrosLines = Dros_lines.getExpressedMoreThan(x,
				dros_lines_expr_values_2083_genes_raw_path, iDGenespath,janilaMapId);

		System.out.println("Finish get more than " + x);
		System.out.println("Final Size = " + expressedGenesDrosLines.size());

		// Save it to file
		File resultFile = PathsUtils.ResultFile(DrosLinesPaths.dros_lines_expr_values_2083_genes_more_than0_4);
		GsonIO.save(resultFile, expressedGenesDrosLines);

		expressedGenesDrosLines.clear();

		return null;
	}
}
