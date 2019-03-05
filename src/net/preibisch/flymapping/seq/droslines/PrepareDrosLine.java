package net.preibisch.flymapping.seq.droslines;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

import net.preibisch.flymapping.config.MyPaths;
import net.preibisch.flymapping.config.PathUtils;
import picocli.CommandLine;

public class PrepareDrosLine implements Callable<Void> {

	public static void main(String[] args) throws IOException {
		CommandLine.call(new PrepareDrosLine(), args);
	}

	public Void call() throws IOException {
		System.out.println("Start prepare Dros Lines ");
		File dros_lines_expr_values_2083_genes_raw_path = PathUtils.File(MyPaths.dros_lines_expr_values_2083_genes);

		File dros_lines_expr_values_2083_genes_output_folder = PathUtils
				.ResultFolder(MyPaths.dros_lines_expr_values_2083_genes_folder);

		int chuncks = Dros_lines.toChuncks(dros_lines_expr_values_2083_genes_raw_path,
				dros_lines_expr_values_2083_genes_output_folder, MyPaths.dros_lines_expr_values_2083_genes_folder, 200);
		System.out.println("Chunks: " + chuncks);

		System.out.println("Finish prepare Dros Lines ");

		return null;
	}
}
