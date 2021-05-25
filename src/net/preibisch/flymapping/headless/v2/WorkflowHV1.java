package net.preibisch.flymapping.headless.v2;

import ij.ImageJ;
import ij.ImagePlus;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.Img;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.numeric.real.FloatType;
import net.preibisch.flymapping.flow.GenerateImg;
import net.preibisch.flymapping.tools.IOFunctions;

import java.io.File;
import java.io.IOException;

public class WorkflowHV1 {
    private static final String supervoxelsValuesFile = "AACTCTTGTCAAAGAT.1.tsv";

    private static final String supervoxelImagePath = "/Users/Marwan/Desktop/SingleCellProject/img/180628_supervoxel.tif";
    private static final String outputFolderPath = "/Users/Marwan/Desktop/SingleCellProject/GeneratedImages";

    public static void main(String[] args) throws IOException {
        new ImageJ();
        // Here reading the file with the matrix of supervoxel expression in genes
        CellCorrelatedSupervoxelVals supervoxelexpression = new CellCorrelatedSupervoxelVals(
                new File(System.getProperty("user.dir"), supervoxelsValuesFile));

        // Load image to the file
        File imageSupervoxelFile = new File(supervoxelImagePath);
        Img<FloatType> supervoxelImage = IOFunctions.openAs32Bit(imageSupervoxelFile);
        ImageJFunctions.show(supervoxelImage);
        File imgPath = new File(outputFolderPath, "Cell_" + supervoxelsValuesFile.replace(".", "_") + ".tiff");

        System.out.println("Finished loading the image");

        RandomAccessibleInterval<FloatType> resultImg = GenerateImg.generateImgV2(supervoxelImage,
                supervoxelexpression.getAll());

        System.out.println("Finish generating img");
        ImagePlus resultImgPlus = ImageJFunctions.wrap(resultImg, "");
        resultImgPlus.setDimensions(1, (int) resultImg.dimension(2), 1);
        ImageJFunctions.show(resultImg);
        ij.IJ.save(resultImgPlus, imgPath.getAbsolutePath());


        System.out.println("Img saved : " + imgPath);

    }
}


