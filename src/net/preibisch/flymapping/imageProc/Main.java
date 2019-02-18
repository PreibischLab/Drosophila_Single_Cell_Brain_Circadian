package net.preibisch.flymapping.imageProc;

import java.io.File;

import net.imglib2.img.Img;
import net.imglib2.type.numeric.real.FloatType;
import net.preibisch.flymapping.tools.IOFunctions;


public class Main {

	private static final String lsmPath = "";
	private static final String flyPath = "img/fly.tif";
	private static final String distributionPath = "";
	
	
	public static void main(String[] args) {
		System.out.println("img/fly.tif");
		Img<FloatType> image = IOFunctions.openAs32Bit(new File(flyPath));
	}
}
