package net.preibisch.flymapping.headless;

import static net.preibisch.flymapping.tools.GsonIO.read;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

import ij.ImagePlus;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.numeric.real.FloatType;
import net.preibisch.flymapping.flow.CellLookup;
import net.preibisch.flymapping.flow.GenerateImg;
import net.preibisch.flymapping.img.ImgPaths;
import net.preibisch.flymapping.img.SuperVoxel;
import net.preibisch.flymapping.seq.ClustersExamples;
import net.preibisch.flymapping.seq.ResultsPaths;
import net.preibisch.flymapping.seq.aerts.AertsPaths;
import net.preibisch.flymapping.seq.aerts.Aerts_57k_cells;
import net.preibisch.flymapping.tools.GsonIO;
import net.preibisch.flymapping.tools.ImgUtils;
import net.preibisch.flymapping.tools.PathsUtils;

public class FlowV2 {
	public static void main(String[] args) throws IOException {

//		

		// For CellLokup
		List<String> concatFoundGenes = GsonIO.read(PathsUtils.ResultFile(ResultsPaths.concat_genes), List.class);

		System.out.println("Concat Genes Size :" + concatFoundGenes.size());

		File input = PathsUtils.File(AertsPaths.aerts_57k_cells_raw);

		// For CellVoxels
		Map<String, List<String>> janilaIDsPerGenes = GsonIO.read(PathsUtils.ResultFile(ResultsPaths.JanilaIDsPerGenes),
				new TypeToken<Map<String, List<String>>>() {
				}.getType());

		System.out.println("Janila IDs Per Genes Size :" + janilaIDsPerGenes.size());

		List<String> janilaIDsForSuperVoxels = read(PathsUtils.ResultFile(ResultsPaths.JanilaIDsForSuperVoxels),
				new TypeToken<List<String>>() {
				}.getType());

		System.out.println("Janila IDs for supervoxels Size :" + janilaIDsForSuperVoxels.size());

		Integer max = Aerts_57k_cells.getMaxExpressed(input);
		System.out.println("Max val: " + max);

		// For GenerateImg
		long[] dims = ImgUtils.getDims(ImgUtils.openImg(PathsUtils.File(ImgPaths.SUPER_VOXEL)));

		System.out.println("Supervoxel dims :" + dims.length);

		Map<Integer, List<List<Integer>>> supervoxelHashMap = GsonIO.read(
				PathsUtils.ResultFile(ImgPaths.SUPER_VOXEL_HASHMAP),
				new TypeToken<Map<Integer, List<List<Integer>>>>() {
				}.getType());

		File file = PathsUtils.ResultFileFromString("ConcatGenesSV");

		HashMap<String, List<Double>> concatGenesSV = GsonIO.read(file, new TypeToken<HashMap<String, List<Double>>>() {
		}.getType());

//		String cellExample = ClustersExamples.clusters[0][0];
		for (int i = 0; i < ClustersExamples.clusters.length; i++)
			for (String cellExample : ClustersExamples.clusters[i]) {
				HashMap<String, Double> cellGenes = CellLookup.lookForCellGenes(concatFoundGenes, input, cellExample,
						max);

				System.out.println("Finish get expressed cells " + cellExample);

//			File cellFile = PathsUtils.ResultFile(cellExample + ".json");
//			Map<String, Double> cellGenes = GsonIO.read(cellFile, new TypeToken<Map<String, Double>>() {
//			}.getType());

				System.out.println("Start supervoxel HashMap generating..");

				// TODO fix error her
				// concat cellGenes got from Aerts with genes SV from DrosLines

				Integer numberSV = 7065;
				Map<Integer, Double> supervoxelExpression = SuperVoxel.generateSVsPerCell(cellGenes, concatGenesSV,
						numberSV);
				supervoxelExpression = SuperVoxel.normalise(supervoxelExpression);

//			GsonIO.save(PathsUtils.ResultFileFromString("SV_" + cellExample), supervoxelExpression);

				System.out.println("Finish supervoxel HashMap generating..");

//			ImageJFunctions.show(supervoxels);

				System.out.println("Start generating imgage .. ");

//			Map<Integer, Double> supervoxelExpression = GsonIO.read(PathsUtils.ResultFile("SV_" + file + ".json"), new TypeToken<Map<Integer, Double>>() {}.getType());

				System.out.println("supervoxel HashMap Size :" + supervoxelHashMap.size());

				RandomAccessibleInterval<FloatType> resultImg = GenerateImg.generateImg(supervoxelExpression,
						supervoxelHashMap, dims);

				System.out.println("Finish generating img");
				ImagePlus resultImgPlus = ImageJFunctions.wrap(resultImg, "");
				resultImgPlus.setDimensions(1, (int) resultImg.dimension(2), 1);

				String imgPath = PathsUtils.ResultFile("SV_" + i + "_" + cellExample + ".tiff").getAbsolutePath();
				ij.IJ.save(resultImgPlus, imgPath);

				System.out.println("Img saved : " + imgPath);
			}

	}
}
