package net.preibisch.flymapping.tools;

import java.io.File;

import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.numeric.real.FloatType;

public class ImgUtils {
	public static long[] getDims(RandomAccessibleInterval<FloatType> img) {
		int dims = img.numDimensions();
		long[] dimensions = new long[dims];
		for (int i = 0; i < dims; i++)
			dimensions[i] = img.dimension(i);
		return dimensions;
	}

	public static RandomAccessibleInterval<FloatType> openImg(File imgFile) {
		return IOFunctions.openAs32Bit(imgFile);
	}
}
