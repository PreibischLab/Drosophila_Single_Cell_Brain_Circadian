package net.preibisch.flymapping.sequencingProc;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

public class MyPaths {

	public static final String SEQUENCING_FOLDER = "single_cell_sequencing_data";

	public static final String RESULT_FOLDER = "result";
	
	public static final String[] aerts_57k_cells_metadata = {"id","nGene","nUMI","orig.ident","percent.mito","time","res.2","replicate"};
	
	public static final String aerts_57k_cells_raw = "aerts_57k_cells_raw.txt";
	public static final String aerts_57k_cells_raw_folder = "aerts_57k_cells_raw";
	
	public static final String aerts_57k_cells_scalesd = "aerts_57k_cells_scalesd.txt";
	
	public static final String aerts_57k_cells_test_json = "35_aerts_57k_cells_raw.json";
	
	public static final String aerts_57k_cells_result = "aerts_57k_cells_expressed_only.json";
	
	public static final String dros_linesIDtoJaneliaID = "dros_linesIDtoJaneliaID.txt";
	
	
	public static final String dros_lines_expr_values_2083_genes = "dros_lines_expr_values_2083_genes.txt";
	public static final String dros_lines_expr_values_2083_genes_folder = "dros_lines_expr_values_2083_genes";
	

	public static final String dros_lines_expr_values_2083_genes_top = "dros_lines_expr_values_2083_genes_top.json";


	public static File Data() throws FileNotFoundException {
		File f = new File(Paths.get("").toAbsolutePath().getParent().getParent().resolve(SEQUENCING_FOLDER).toString());
		if(!(f.isDirectory()&&(f.exists()))) throw new FileNotFoundException();
		return f;	
	}
	
	public static File Result() throws FileNotFoundException {
		File f = new File(Data(),RESULT_FOLDER);
		if(!(f.isDirectory()&&(f.exists()))) f.mkdir();
		return f;	
	}
	
	public static File ResultFolder(String file) throws FileNotFoundException {
		File f = new File(Result(),RESULT_FOLDER);
		if(!(f.isDirectory()&&(f.exists()))) f.mkdir();
		return f;	
	}
	
	
	public static File File(String file) throws FileNotFoundException {
		File f = new File(Data(), file);
		if(!(f.exists())) throw new FileNotFoundException();
		return f;
	}
	
	public static File Result(File folder, String file) throws FileNotFoundException {
		File f = new File(folder, file);
		return f;
	}

	public static File ResultFile(String file) throws FileNotFoundException {
		return Result(Result(), file);
	}
	
}
