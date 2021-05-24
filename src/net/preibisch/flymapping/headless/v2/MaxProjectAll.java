package net.preibisch.flymapping.headless.v2;

import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.plugin.ZProjector;

import java.io.File;

public class MaxProjectAll implements PlugIn {

    private static final String outputFolderPath = "/Users/Marwan/Desktop/SingleCellProject/GeneratedImages";

    public static void main(String[] args) {
        new ImageJ();
        new MaxProjectAll().run(outputFolderPath);

    }

    public void run(String arg) {
        File outputFolder = new File(arg);
        processFolder(outputFolder);
    }

    private void processFolder(File folder) {
        for (File f : folder.listFiles()) {
            if (f.isDirectory()) {
                processFolder(f);
            } else {
                if (f.getName().endsWith(".tiff") || f.getName().endsWith(".tif")) {
                    processFile(f);
                }

            }
        }
    }


    public static void processFile(File file) {
        File outputFile = getOutputName(file, "MAX_", ".jpg");
        ImagePlus imp = IJ.openImage(file.getAbsolutePath());
        imp.setDisplayRange(0, 44);
        imp = ZProjector.run(imp, "max");
        IJ.run(imp, "Enhance Contrast", "saturated=0.35");
        IJ.saveAs(imp, "Jpeg", outputFile.getAbsolutePath());

    }

    private static File getOutputName(File file, String prefix, String extension) {
        int i = file.getName().lastIndexOf('.');
        String name = file.getName().substring(0,i);
        String fileName = prefix + name + extension;
        return new File(file.getParent(), fileName);
    }
}
