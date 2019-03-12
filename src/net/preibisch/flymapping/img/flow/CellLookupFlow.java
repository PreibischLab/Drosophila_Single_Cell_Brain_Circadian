package net.preibisch.flymapping.img.flow;

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
import net.preibisch.flymapping.img.ImgPaths;
import net.preibisch.flymapping.img.JanilaId;
import net.preibisch.flymapping.seq.ClustersExamples;
import net.preibisch.flymapping.seq.ResultsPaths;
import net.preibisch.flymapping.seq.aerts.AertsPaths;
import net.preibisch.flymapping.tools.GsonIO;
import net.preibisch.flymapping.tools.ImgUtils;
import net.preibisch.flymapping.tools.PathsUtils;

public class CellLookupFlow {
	public static void main(String[] args) throws IOException {

		// For CellLokup
		List<String> concatFoundGenes = GsonIO.read(PathsUtils.ResultFile(ResultsPaths.concat_genes),
				List.class);

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

		// For GenerateImg
		long[] dims = ImgUtils.getDims(ImgUtils.openImg(PathsUtils.File(ImgPaths.SUPER_VOXEL)));

		System.out.println("Supervoxel dims :" + dims.length);

		Map<Integer, List<List<Integer>>> supervoxelHashMap = GsonIO.read(
				PathsUtils.ResultFile(ImgPaths.SUPER_VOXEL_HASHMAP),
				new TypeToken<Map<Integer, List<List<Integer>>>>() {
				}.getType());

		System.out.println("supervoxel HashMap Size :" + supervoxelHashMap.size());

		for (int i = 0; i < ClustersExamples.clusters.length; i++)
			for (String cellExample : ClustersExamples.clusters[i]) {

			HashMap<String, Double> cellGenes = CellLookup.lookForCellGenes(concatFoundGenes, input, cellExample);

//			GsonIO.save(PathsUtils.ResultFileFromString(cellExample), cellGenes);
			System.out.println("Finish get expressed cells " + cellExample);

//			File cellFile = PathsUtils.ResultFile(cellExample + ".json");
//			Map<String, Double> cellGenes = GsonIO.read(cellFile, new TypeToken<Map<String, Double>>() {
//			}.getType());

			System.out.println("Start supervoxel HashMap generating..");

			Map<Integer, Double> supervoxelExpression = JanilaId.generateSuperVoxelIDPerCell(cellGenes,
					janilaIDsPerGenes, janilaIDsForSuperVoxels);

//			GsonIO.save(PathsUtils.ResultFileFromString("SV_" + cellExample), supervoxelExpression);

			System.out.println("Finish supervoxel HashMap generating..");

//			ImageJFunctions.show(supervoxels);

			System.out.println("Start generating imgage .. ");

//			Map<Integer, Double> supervoxelExpression = GsonIO.read(PathsUtils.ResultFile("SV_" + file + ".json"), new TypeToken<Map<Integer, Double>>() {}.getType());

			RandomAccessibleInterval<FloatType> resultImg = GenerateImg.generateImg(supervoxelExpression, elm2, dims);

			System.out.println("Finish generating img");
			ImagePlus resultImgPlus = ImageJFunctions.wrap(resultImg, "");
				String imgPath = PathsUtils.ResultFile("SV_" + i + "_" + cellExample + ".tiff").getAbsolutePath();
			ij.IJ.save(resultImgPlus, imgPath);

			System.out.println("Img saved : " + imgPath);

		}

	}
}
