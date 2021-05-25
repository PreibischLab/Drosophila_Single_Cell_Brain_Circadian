package net.preibisch.flymapping.headless.v2;

import net.imglib2.RandomAccessibleInterval;
import net.preibisch.flymapping.tools.GsonIO;
import net.preibisch.flymapping.tools.ImgUtils;
import ij.ImagePlus;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.numeric.real.FloatType;
import net.preibisch.flymapping.flow.GenerateImg;

import java.io.File;
import java.util.*;
import java.io.IOException;

public class WorkflowHV1 {
    //    private static final String[] cellExample = new String[]{"AAACGGGGTAGCCTAT.1"};
    private static final String cellExample = "AACTCTTGTCAAAGAT.1.tsv";

    private static final String supervoxelImagePath = "/volumes/data/Bioinformatics_master/Fourth_semester/Thesis/" +
            "Drosophila_Single_Cell_Brain_Circadian/single_cell_sequencing_data/Img/180628_supervoxel.tif";
    private static final String outputFolderPath = "/volumes/data/Bioinformatics_master/Fourth_semester/Thesis/" +
            "Drosophila_Single_Cell_Brain_Circadian/single_cell_sequencing_data/GeneratedImages";
    private static final String path = "/volumes/data/Bioinformatics_master/Fourth_semester/Thesis/" +
            "Drosophila_Single_Cell_Brain_Circadian/Ines_data_working/Drosophila_brain_single_cells_mapping/cells_supervoxels";
    private static final String resultPath = "/volumes/data/Bioinformatics_master/Fourth_semester/Thesis/" +
            "Drosophila_Single_Cell_Brain_Circadian/single_cell_sequencing_data/GeneratedImages";

    public static void main(String[] args) throws IOException {
        // Here reading the file with the matrix of supervoxel expression in genes
        CellCorrelatedSupervoxelVals supervoxelexpression = new CellCorrelatedSupervoxelVals(
                new File(path, cellExample));

        List<List<Float>> matrix = new ArrayList<>();
        matrix = supervoxelexpression.getMatrix();
        Map<Integer, Float> expressionPerSupervoxels = new HashMap<>();
        Integer numberSV = 7065;
        for (int j = 0; j < numberSV ; j++) {
            expressionPerSupervoxels.put(j, matrix.get(j).get(0));
        }
          System.out.println(expressionPerSupervoxels.get(4));
//        File resultFile = new File(resultPath, "trialFile.json");
//        GsonIO.save(resultFile, expressionPerSupervoxels);

        // Load image to the file
        File imageSupervoxelFile = new File(supervoxelImagePath);
        RandomAccessibleInterval<FloatType> supervoxelImage = ImgUtils.openImg(imageSupervoxelFile);

        File outputFolder = new File(outputFolderPath, "Cell_" + cellExample.replace(".", "_"));
        outputFolder.mkdirs();

        System.out.println("Finished loading the image");

        RandomAccessibleInterval<FloatType> resultImg = GenerateImg.generateImgV2(supervoxelImage,
                expressionPerSupervoxels);

        System.out.println("Finish generating img");
        ImagePlus resultImgPlus = ImageJFunctions.wrap(resultImg, "");
        resultImgPlus.setDimensions(1, (int) resultImg.dimension(2), 1);
                ImageJFunctions.show(resultImg);

        String outputFileName = "SV_" + cellExample.replace(".", "_") + ".tiff";
        String imgPath = new File(outputFolder, outputFileName).getAbsolutePath();
        ij.IJ.save(resultImgPlus, imgPath);


        System.out.println("Img saved : " + imgPath);

    }
}


