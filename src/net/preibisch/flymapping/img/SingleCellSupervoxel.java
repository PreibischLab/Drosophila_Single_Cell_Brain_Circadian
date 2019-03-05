package net.preibisch.flymapping.img;

import java.io.File;

import net.imglib2.img.Img;
import net.imglib2.type.numeric.real.FloatType;
import net.preibisch.flymapping.tools.IOFunctions;

public class SingleCellSupervoxel {
public static void main(String[] args) {
	String flyPath = "img/fly.tif";
	System.out.println(flyPath);
	Img<FloatType> image = IOFunctions.openAs32Bit(new File(flyPath));
}
}
