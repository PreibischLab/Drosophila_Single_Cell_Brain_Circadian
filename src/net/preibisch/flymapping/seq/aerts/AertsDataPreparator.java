package net.preibisch.flymapping.seq.aerts;

import net.preibisch.flymapping.tools.GsonIO;
import net.preibisch.flymapping.tools.PathsUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/***
 * 1
 * aerts_57k_cells_raw.txt is huge, This class will help you to split it in small files
 * 1899 cells for 57k genes
 * from Aerts single cell paper 2018. this is more deep and more cells so we should start with this data
 */

public class AertsDataPreparator {
	private final File input;
	private final File output;
	private final int chunk;

	public AertsDataPreparator(File input, File output,int chunk) {
		this.input = input;
		this.output = output;
		this.chunk = chunk;
	}

	public  AertsDataPreparator(File input,File output){
		this(input,output,1);
	}

	public static void main(String[] args) throws IOException {
		File input = PathsUtils.getInputPathForFile(AertsPaths.aerts_57k_cells_raw);
		File output = PathsUtils.getOrCreateResultFolder();
		 new AertsDataPreparator(input,output).splitInFiles();
	}

	public void splitInFiles() throws IOException {
		System.out.println("Start prepare aerts 57k cells raw ");
		int chuncks = Aerts_57k_cells.toChuncks(input, output,
				 chunk);
		System.out.println("Chunks: " + chuncks);
		System.out.println("Finish prepare aerts 57k cells raw files ");
	}

	public void generateCellsFile() throws IOException {
		System.out.println("Generating cells names file ");

		List<String> list = Aerts_57k_cells.getCellsNames(input);
		System.out.println("Cells Size : " + list.size());

		File resultFile = new File(output,AertsPaths.aerts_57k_cells_names);
		GsonIO.save(resultFile, list);

		System.out.println("Cells file generated : "+resultFile.getAbsolutePath());
	}

	public void generateGenesFile() throws IOException {
		System.out.println("Generating genes names file ");

		List<String> list = Aerts_57k_cells.getGenesNames(input);
		System.out.println("Genes Size : " + list.size());

		File resultFile = new File(output,AertsPaths.aerts_57k_genes_names);
		GsonIO.save(resultFile, list);

		System.out.println("Genes file generated : "+resultFile.getAbsolutePath());
	}
}
