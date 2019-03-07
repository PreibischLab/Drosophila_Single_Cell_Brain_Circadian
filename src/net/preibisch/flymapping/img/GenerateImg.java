package net.preibisch.flymapping.img;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

import ij.ImagePlus;
import net.imglib2.RandomAccess;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.Img;
import net.imglib2.img.ImgFactory;
import net.imglib2.img.cell.CellImgFactory;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.numeric.real.FloatType;
import net.preibisch.flymapping.seq.ClustersExamples;
import net.preibisch.flymapping.tools.GsonIO;
import net.preibisch.flymapping.tools.IOFunctions;
import net.preibisch.flymapping.tools.PathsUtils;

public class GenerateImg {
//	private static final String janilaIdsPerCellFile = "SV_ACACCAAAGGTTCCTA-DGRP-551_0d_r1.json";
//
//	private static final String resultImgName = "SV_ACACCAAAGGTTCCTA-DGRP-551_0d_r1.tiff";

	public static void main(String[] args) throws FileNotFoundException {

		System.out.println("Start supervoxel HashMap generating..");

//		ImageJFunctions.show(supervoxels);
		File imgFile = PathsUtils.File(ImgPaths.SUPER_VOXEL);
		long[] dims = getDims(openImg(imgFile));

//		File supervoxelExpressionPath = ;
//		Map<Integer, Double> supervoxelExpression = 
//		Map<Integer, List<long[]>> supervoxelMap = ;

		System.out.println("Start generating .. ");

		Type idsPerCell = new TypeToken<Map<Integer, Double>>() {
		}.getType();
		Type hashmapType = new TypeToken<Map<Integer, List<List<Integer>>>>() {
		}.getType();
		Map<Integer, List<List<Integer>>> elm2 = GsonIO.read(PathsUtils.ResultFile(ImgPaths.SUPER_VOXEL_HASHMAP),
				hashmapType);
		
		for (String file : ClustersExamples.cluster_2) {
		RandomAccessibleInterval<FloatType> resultImg = generateImg(
				GsonIO.read(PathsUtils.ResultFile("SV_"+file+".json"), idsPerCell),
				elm2, dims);

		System.out.println("Finish generating ");
		ImagePlus resultImgPlus = ImageJFunctions.wrap(resultImg, "");
			ij.IJ.save(resultImgPlus, PathsUtils.ResultFile("SV_" + file + ".tiff").getAbsolutePath());
		}

	}

	private static long[] getDims(RandomAccessibleInterval<FloatType> img) {
		int dims = img.numDimensions();
		long[] dimensions = new long[dims];
		for (int i = 0; i < dims; i++)
			dimensions[i] = img.dimension(i);
		return dimensions;
	}

	private static RandomAccessibleInterval<FloatType> openImg(File imgFile) {
		return IOFunctions.openAs32Bit(imgFile);
	}

	public static RandomAccessibleInterval<FloatType> generateImg(Map<Integer, Double> supervoxelExpression,
			Map<Integer, List<List<Integer>>> supervoxelMap, long[] dims) {

		// create the ImgFactory based on cells (cellsize = 5x5x5...x5) that will
		// instantiate the Img
		final ImgFactory<FloatType> imgFactory = new CellImgFactory<>(new FloatType(), 5);

		// create an 3d-Img with dimensions 20x30x40 (here cellsize is 5x5x5)Ø
		final Img<FloatType> result = imgFactory.create(dims);

		RandomAccess<FloatType> randomAccess = result.randomAccess();

		int i = 0;
		for (Map.Entry<Integer, Double> e : supervoxelExpression.entrySet()) {
			System.out.println(i + "-" + e.getKey() + "-" + e.getValue());
			List<List<Integer>> listPos = supervoxelMap.get(e.getKey());
			System.out.println(i + "-" + listPos.size());
			for (List<Integer> pos : listPos) {

				// set the output cursor to the position of the input cursor
				long[] p = convertPosition(pos);
				randomAccess.setPosition(p);

				// set the value of this pixel of the output image, every Type supports T.set( T
				// type )
				randomAccess.get().set(e.getValue().floatValue());

			}
			i++;
		}

		return result;
	}

	private static long[] convertPosition(List<Integer> pos) {
		long[] p = new long[pos.size()];
		for (int i = 0; i < pos.size(); i++)
			p[i] = pos.get(i);
		return p;
	}
}
