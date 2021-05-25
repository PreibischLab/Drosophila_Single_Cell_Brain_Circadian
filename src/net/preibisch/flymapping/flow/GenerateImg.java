package net.preibisch.flymapping.flow;

import com.google.gson.reflect.TypeToken;
import ij.ImagePlus;
import net.imglib2.Cursor;
import net.imglib2.RandomAccess;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.Img;
import net.imglib2.img.ImgFactory;
import net.imglib2.img.cell.CellImgFactory;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.numeric.real.FloatType;
import net.preibisch.flymapping.img.ImgPaths;
import net.preibisch.flymapping.seq.ClustersExamples;
import net.preibisch.flymapping.tools.GsonIO;
import net.preibisch.flymapping.tools.ImgUtils;
import net.preibisch.flymapping.tools.PathsUtils;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class GenerateImg {

    public static void main(String[] args) throws FileNotFoundException {

        System.out.println("Start supervoxel HashMap generating..");

//		ImageJFunctions.show(supervoxels);
        long[] dims = ImgUtils.getDims(ImgUtils.openImg(PathsUtils.File(ImgPaths.SUPER_VOXEL)));

        System.out.println("Start generating .. ");

        Map<Integer, List<List<Integer>>> elm2 = GsonIO.read(PathsUtils.ResultFile(ImgPaths.SUPER_VOXEL_HASHMAP),
                new TypeToken<Map<Integer, List<List<Integer>>>>() {
                }.getType());

        for (String file : ClustersExamples.cluster_2) {
            RandomAccessibleInterval<FloatType> resultImg = generateImg(
                    GsonIO.read(PathsUtils.ResultFile("SV_" + file + ".json"), new TypeToken<Map<Integer, Double>>() {
                    }.getType()),
                    elm2, dims);

            System.out.println("Finish generating ");
            ImagePlus resultImgPlus = ImageJFunctions.wrap(resultImg, "");
            ij.IJ.save(resultImgPlus, PathsUtils.ResultFile("SV_" + file + ".tiff").getAbsolutePath());
        }

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
            try {
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
            } catch (Exception ex) {
                System.out.println("Error pos: " + i);
                System.out.println(ex.getMessage());
            }
            i++;
        }

        return result;
    }


    public static RandomAccessibleInterval<FloatType> generateImgV2(Img<FloatType> supervoxelImage, Map<Integer, Float> supervoxelExpression) {

        // create the ImgFactory based on cells (cellsize = 5x5x5...x5) that will
        // instantiate the Img
        Img<FloatType> result = supervoxelImage.factory().imgFactory(new FloatType()).create(supervoxelImage, new FloatType());
        // create an 3d-Img with dimensions 20x30x40 (here cellsize is 5x5x5)Ø
//		final Img<FloatType> result = imgFactory.create(dims);

        Cursor<FloatType> targetCursor = result.localizingCursor();
        RandomAccess<FloatType> sourceRandomAccess = supervoxelImage.randomAccess();

        // iterate over the input cursor
        while (targetCursor.hasNext()) {
            // move input cursor forward
            targetCursor.fwd();

            // set the output cursor to the position of the input cursor
            sourceRandomAccess.setPosition(targetCursor);

            // set the value of this pixel of the output image, every Type supports T.set( T type )
            int sv = (int) sourceRandomAccess.get().get();
            if (sv > 0) {
                Float value = supervoxelExpression.get(sv);
                if (value != null)
                    targetCursor.get().set(value);
            }
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
