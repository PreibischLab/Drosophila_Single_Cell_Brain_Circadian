package net.preibisch.flymapping.sequencingProc;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SerializeDataMain {
	public static void main(String[] args) throws IOException {
		prepare_aerts_57k_cells_raw();
		
//		prepare_dros_lines();
//		getTop3DrosLines();
		
		

//		Path aerts_57k_cells_raw_path = Paths.get(SeqPaths.aerts_57k_cells_test_json);
//		TxtProcess.infos(aerts_57k_cells_raw_path);
//		
//		Aerts_57k_cells aerts_57k_cells = Aerts_57k_cells.fromFile(SeqPaths.aerts_57k_cells_test_json);
		
//		System.out.println(aerts_57k_cells);
		
	}

	private static void getTop3DrosLines() throws IOException {
		
		System.out.println("Start get Top3");
		int n=3;
		Path dros_lines_expr_values_2083_genes_raw_path = Paths.get(SeqPaths.SEQUENCING_FOLDER,SeqPaths.dros_lines_expr_values_2083_genes);
		Path iDGenespath = Paths.get(SeqPaths.dros_linesIDtoJaneliaID);
		System.out.println("Get infos..");
		TxtProcess.infos(dros_lines_expr_values_2083_genes_raw_path);
		TxtProcess.infos(iDGenespath);
		Dros_lines.getTopNFile(n, dros_lines_expr_values_2083_genes_raw_path, iDGenespath,SeqPaths.dros_lines_expr_values_2083_genes_top3);
		System.out.println("Finish get Top3");
	}

	private static void prepare_aerts_57k_cells_raw() {
		try {
			System.out.println("Start prepare aerts 57k cells raw ");
			Path aerts_57k_cells_raw_path = Paths.get(SeqPaths.SEQUENCING_FOLDER,SeqPaths.aerts_57k_cells_raw);
			String aerts_57k_cells_raw_output_folder = Paths.get(SeqPaths.SEQUENCING_FOLDER,SeqPaths.aerts_57k_cells_raw_folder).toString();
			int chuncks = Aerts_57k_cells.toChuncks(aerts_57k_cells_raw_path,aerts_57k_cells_raw_output_folder,SeqPaths.aerts_57k_cells_raw_folder,100);
			System.out.println("Chunks: "+ chuncks );

			System.out.println("Finish prepare aerts 57k cells raw ");
//			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void prepare_dros_lines() {
		try {

			System.out.println("Start prepare Dros Lines ");
			Path dros_lines_expr_values_2083_genes_raw_path = Paths.get(SeqPaths.SEQUENCING_FOLDER,SeqPaths.dros_lines_expr_values_2083_genes);
			Path dros_lines_expr_values_2083_genes_output_folder = Paths.get(SeqPaths.SEQUENCING_FOLDER,SeqPaths.dros_lines_expr_values_2083_genes_folder);
			int chuncks = Dros_lines.toChuncks(dros_lines_expr_values_2083_genes_raw_path,dros_lines_expr_values_2083_genes_output_folder,SeqPaths.dros_lines_expr_values_2083_genes_folder,200);
			System.out.println("Chunks: "+ chuncks );

			System.out.println("Finish prepare Dros Lines ");
//			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
