package net.preibisch.flymapping.seq;

import java.io.IOException;
import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.Option;

public class SerializeDataMain implements Callable<Void>{
	@Option(names = { "-t", "--task" }, required = true, description = "The path of the Data")
	private String dataPath;

	@Option(names = { "-m", "--meta" }, required = true, description = "The path of the MetaData file")
	private String metadataPath;

	@Option(names = { "-id" }, required = true, description = "The path of the MetaData file")
	private Integer id;

	@Option(names = { "-n" }, required = true, description = "The number of ")
	private Integer n;
	
	public static void main(String[] args) throws IOException {
		
		CommandLine.call(new SerializeDataMain(), args);
//		prepare_aerts_57k_cells_raw();
		
//		prepare_dros_lines();
//		getTop3DrosLines();
		
//		getExpressedCellsInGenes();

//		Path aerts_57k_cells_raw_path = Paths.get(SeqPaths.aerts_57k_cells_test_json);
//		TxtProcess.infos(aerts_57k_cells_raw_path);
//		
//		Aerts_57k_cells aerts_57k_cells = Aerts_57k_cells.fromFile(SeqPaths.aerts_57k_cells_test_json);
		
//		System.out.println(aerts_57k_cells);
		
	}
	
	@Override
	public Void call() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
