package net.preibisch.flymapping.headless;

import net.preibisch.flymapping.seq.aerts.AertsDataPreparator;
import net.preibisch.flymapping.seq.aerts.AertsPaths;
import net.preibisch.flymapping.tools.PathsUtils;
import picocli.CommandLine;
import picocli.CommandLine.Option;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.Callable;

/***
 * The aerts raw file is very big, so we created this class to:
 * 1- split raw in 1899 files
 * ech file represent one gene, and the expression of 57k cells
 * 7096 genes for 57k cells
 * 2- Create json file for only gene names
 * and json file for only cell names
 *
 * PS: there is other raw file expended with 157k cells
 */

@CommandLine.Command(name = "aerts")
public class AertsDataPreparation implements Callable<Void> {

    @Option(names = {"-i", "--input"}, required = false, description = "the path of input file aerts_57k_cells_raw.txt ")
    String input = PathsUtils.getInputPathForFile(AertsPaths.aerts_57k_cells_raw).getAbsolutePath();

    @Option(names = {"-o", "--output"}, required = false, description = "The output folder ")
    String output = PathsUtils.getOrCreateResultFolder().getAbsolutePath();

    @Override
    public Void call() throws Exception {
        File inputFile = new File(input);
        File outputFolder = new File(output);
        AertsDataPreparator aertsPreparator = new AertsDataPreparator(inputFile, outputFolder);
        aertsPreparator.showInfos();
//        aertsPreparator.generateGenesFile();
//        aertsPreparator.generateCellsFile();
//        aertsPreparator.splitInFiles();

        System.out.println("Finish preparing Aerts. ");
        return null;
    }

    public AertsDataPreparation() throws FileNotFoundException {
    }

    public static void main(String[] args) throws FileNotFoundException {
        CommandLine.call(new AertsDataPreparation(), args);
    }

}
