package net.preibisch.flymapping.seq.droslines;

import com.google.gson.reflect.TypeToken;
import net.preibisch.flymapping.seq.ResultsPaths;
import net.preibisch.flymapping.tools.GsonIO;
import net.preibisch.flymapping.tools.ListsUtil;
import net.preibisch.flymapping.tools.PathsUtils;
import net.preibisch.flymapping.tools.TxtProcess;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GenesSuperVoxels {
	private final static String ConcatGenesSV = "ConcatGenesSV";
	public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, IOException {

//		generateGenesSV();

//		getOnlyConcatFromGeneratedGenesSV();

		check();

	}

	private static void check() throws FileNotFoundException {

		File file = PathsUtils.ResultFileFromString(ConcatGenesSV);


		HashMap<String, List<Double>> concatGenesSV = GsonIO.read(file, new TypeToken<HashMap<String, List<Double>>>() {
		}.getType());
		Integer svs = concatGenesSV.get(concatGenesSV.keySet().iterator().next()).size();
		System.out.println("SVs: " + svs);
		double max = ListsUtil.getMaxExpressed(concatGenesSV);
		System.out.println("Max val: " + max);
	}


	private static void getOnlyConcatFromGeneratedGenesSV() throws IOException {
		File file = PathsUtils.ResultFileFromString("GenesSV");

		HashMap<String, List<Double>> genesSV = GsonIO.read(file, new TypeToken<HashMap<String, List<Double>>>() {
		}.getType());
		System.out.println("File size : " + genesSV.size());

		List<String> concatFoundGenes = GsonIO.read(PathsUtils.ResultFile(ResultsPaths.concat_genes), List.class);
		HashMap<String, List<Double>> concatGenesSV = getFrom(genesSV, concatFoundGenes);
		System.out.println("File size : " + concatGenesSV.size());

		File out = PathsUtils.ResultFileFromString(ConcatGenesSV);
		GsonIO.save(out, concatGenesSV);

	}

	private static HashMap<String, List<Double>> getFrom(HashMap<String, List<Double>> genesSV,
			List<String> concatFoundGenes) {
		HashMap<String, List<Double>> elements = new HashMap<String, List<Double>>();
		for (String gene : concatFoundGenes) {
			if (genesSV.containsKey(gene)) {
				elements.put(gene, genesSV.get(gene));
			} else {
				System.out.println("Gene : " + gene + " NOT FOUND in concat !");
			}
		}
		return elements;
	}

	private static void generateGenesSV() throws Exception {
		File dros_lines_expr_values_2083_genes_raw_path = PathsUtils
				.File(DrosLinesPaths.dros_lines_expr_values_2083_genes);
		TxtProcess.infos(dros_lines_expr_values_2083_genes_raw_path);

		HashMap<String, List<Double>> genesSV = Dros_lines.getFoundConcat(dros_lines_expr_values_2083_genes_raw_path,
				Dros_lines.getIDs(PathsUtils.File(DrosLinesPaths.dros_linesIDtoJaneliaID)),
				JaneliaIDtoGeneExcelReader.getJanilaGeneMap());

		File out = PathsUtils.ResultFileFromString("GenesSV");
		GsonIO.save(out, genesSV);
		System.out.println("File saved : " + out.getAbsolutePath());
	}
}
