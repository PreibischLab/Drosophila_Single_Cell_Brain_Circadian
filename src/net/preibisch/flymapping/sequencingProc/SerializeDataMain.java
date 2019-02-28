package net.preibisch.flymapping.sequencingProc;

import java.io.File;
import java.io.IOException;

public class SerializeDataMain {
	public static void main(String[] args) throws IOException {
//		prepare_aerts_57k_cells_raw();
		
//		prepare_dros_lines();
//		getTop3DrosLines();
		
		getExpressedCellsInGenes();

//		Path aerts_57k_cells_raw_path = Paths.get(SeqPaths.aerts_57k_cells_test_json);
//		TxtProcess.infos(aerts_57k_cells_raw_path);
//		
//		Aerts_57k_cells aerts_57k_cells = Aerts_57k_cells.fromFile(SeqPaths.aerts_57k_cells_test_json);
		
//		System.out.println(aerts_57k_cells);
		
	}

	private static void getTop3DrosLines() throws IOException {
		
		System.out.println("Start get Top3");
		int n=3;
		File dros_lines_expr_values_2083_genes_raw_path = MyPaths.File(MyPaths.dros_lines_expr_values_2083_genes);
		File iDGenespath = MyPaths.File(MyPaths.dros_linesIDtoJaneliaID);
		System.out.println("Get infos..");
		TxtProcess.infos(dros_lines_expr_values_2083_genes_raw_path);
		TxtProcess.infos(iDGenespath);
		File resultFile = MyPaths.ResultFile(MyPaths.dros_lines_expr_values_2083_genes_top);
		Dros_lines.getTopNFile(n, dros_lines_expr_values_2083_genes_raw_path, iDGenespath,resultFile);
		System.out.println("Finish get Top3");
	}

	private static void prepare_aerts_57k_cells_raw() {
		try {
			System.out.println("Start prepare aerts 57k cells raw ");
			File aerts_57k_cells_raw_path = MyPaths.File(MyPaths.aerts_57k_cells_raw);
			File aerts_57k_cells_raw_output_folder = MyPaths.ResultFolder(MyPaths.aerts_57k_cells_raw_folder);
			int chuncks = Aerts_57k_cells.toChuncks(aerts_57k_cells_raw_path,aerts_57k_cells_raw_output_folder,MyPaths.aerts_57k_cells_raw_folder,1);
			System.out.println("Chunks: "+ chuncks );

			System.out.println("Finish prepare aerts 57k cells raw ");
//			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void getExpressedCellsInGenes() {
		try {
			System.out.println("Start get expressed cells ");
			Aerts_57k_cells.getExpressedCells(MyPaths.File(MyPaths.aerts_57k_cells_raw),MyPaths.ResultFile( MyPaths.aerts_57k_cells_result));
			System.out.println("Finish get expressed cells ");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void prepare_dros_lines() {
		try {

			System.out.println("Start prepare Dros Lines ");
			File dros_lines_expr_values_2083_genes_raw_path = MyPaths.File(MyPaths.dros_lines_expr_values_2083_genes);

			File dros_lines_expr_values_2083_genes_output_folder = MyPaths.ResultFolder(MyPaths.dros_lines_expr_values_2083_genes_folder);
			
			int chuncks = Dros_lines.toChuncks(dros_lines_expr_values_2083_genes_raw_path,dros_lines_expr_values_2083_genes_output_folder,MyPaths.dros_lines_expr_values_2083_genes_folder,200);
			System.out.println("Chunks: "+ chuncks );

			System.out.println("Finish prepare Dros Lines ");
//			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
