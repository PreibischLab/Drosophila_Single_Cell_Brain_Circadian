package net.preibisch.flymapping.headless.v2;

import ij.ImagePlus;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.numeric.real.FloatType;
import net.preibisch.flymapping.flow.GenerateImg;
import net.preibisch.flymapping.seq.correlate.CorrelateGenes;
import net.preibisch.flymapping.tools.ImgUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WorkflowV2 {

        private static final String[] cellExample = new String[]{"AAACGGGGTAGCCTAT.1"};
    private static final String[][] clusters = { cellExample};
    private static final String supervoxelImagePath = "/Users/Marwan/Desktop/SingleCellProject/img/180628_supervoxel.tif";
    private static final String outputFolderPath = "/Users/Marwan/Desktop/SingleCellProject/GeneratedImages";

    private static final boolean AERTS_LOW_MEMORY = true;
    private static final boolean SUPERVOXELS_LOW_MEMORY = false;

    public static void main(String[] args) throws IOException {


//        Load image supervoxel
        File imageSupervoxelFile = new File(supervoxelImagePath);
        System.out.println("Supervoxels: " + imageSupervoxelFile.getPath());
        RandomAccessibleInterval<FloatType> supervoxelImage = ImgUtils.openImg(imageSupervoxelFile);

        Refined_genes_vs_supervoxels genesVsSupervoxels = new Refined_genes_vs_supervoxels(new File(Refined_genes_vs_supervoxels.path),SUPERVOXELS_LOW_MEMORY);

        AertsCells_same_genes_as_supervoxels cellsVsGenes = new AertsCells_same_genes_as_supervoxels(new File(AertsCells_same_genes_as_supervoxels.path),AERTS_LOW_MEMORY);
        // For GenerateImg

        for (int k = 0; k < clusters.length; k++) {
            File outputFolder = new File(outputFolderPath,"Cluster_"+k);
            outputFolder.mkdirs();
            for (String cell : clusters[k]) {
                String cellExample = cell.split("-")[0];
                Map<String, Float> geneExpressionForCell = cellsVsGenes.getGenesExpressionForOneCell(cellExample);

                Map<Integer, Float> expressionPerSupervoxels = new HashMap<>();
                Integer numberSV = 7065;
                for (int i = 0; i < numberSV; i++) {
                    Map<String, Float> expressionForSupervoxel = genesVsSupervoxels.getExpressionForSuperVoxel(i);
                    Float expression = CorrelateGenes.correlate(geneExpressionForCell, expressionForSupervoxel);

//            System.out.println("sv: "+i+"-"+expression);
                    expressionPerSupervoxels.put(i + 1, expression);
                }


                RandomAccessibleInterval<FloatType> resultImg = GenerateImg.generateImgV2(supervoxelImage,
                        expressionPerSupervoxels);

                System.out.println("Finish generating img");
                ImagePlus resultImgPlus = ImageJFunctions.wrap(resultImg, "");
                resultImgPlus.setDimensions(1, (int) resultImg.dimension(2), 1);
//                ImageJFunctions.show(resultImg);

                String outputFileName = "SV_" + cellExample.replace(".", "_") + ".tiff";
                String imgPath = new File(outputFolder, outputFileName).getAbsolutePath();
                ij.IJ.save(resultImgPlus, imgPath);


                System.out.println("Img saved : " + imgPath);

            }
        }
    }

}
