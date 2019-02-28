package net.preibisch.flymapping.headless;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

import net.preibisch.flymapping.sequencingProc.Dros_lines;
import net.preibisch.flymapping.sequencingProc.MyPaths;
import net.preibisch.flymapping.sequencingProc.TxtProcess;
import picocli.CommandLine;
import picocli.CommandLine.Option;

public class TopDrosLines implements Callable<Void>{

	@Option(names = { "-x" }, required = false, description = "the threshold")
	private double x;

	@Option(names = { "-n" }, required = false, description = "The threshold for more than X")
	private Integer n;
	
	public static void main(String[] args) throws IOException {
		CommandLine.call(new TopDrosLines(), args);
	}
	
	@Override
	public Void call() throws IOException{
		if(n!=null) {
		System.out.println("Start get Top "+n);
		File dros_lines_expr_values_2083_genes_raw_path = MyPaths.File(MyPaths.dros_lines_expr_values_2083_genes);
		File iDGenespath = MyPaths.File(MyPaths.dros_linesIDtoJaneliaID);
		System.out.println("Get infos..");
		TxtProcess.infos(dros_lines_expr_values_2083_genes_raw_path);
		TxtProcess.infos(iDGenespath);
		File resultFile = MyPaths.ResultFile(MyPaths.dros_lines_expr_values_2083_genes_top);
		Dros_lines.getTopNFile(n, dros_lines_expr_values_2083_genes_raw_path, iDGenespath, resultFile);
		System.out.println("Finish get Top "+n);
		}
		else if(x!=0.0) {
			System.out.println("Not yet implimented");
		}else {
			throw new IllegalArgumentException("Give top N or threshold");
		}
		return null;
	}
}
