package net.preibisch.flymapping.seq.correlate;

import net.preibisch.flymapping.seq.ResultsPaths;
import net.preibisch.flymapping.seq.aerts.AertsPaths;
import net.preibisch.flymapping.seq.aerts.Aerts_57k_cells;
import net.preibisch.flymapping.seq.droslines.DrosLinesPaths;
import net.preibisch.flymapping.seq.droslines.Dros_lines;
import net.preibisch.flymapping.seq.droslines.JaneliaIDtoGeneExcelReader;
import net.preibisch.flymapping.tools.GsonIO;
import net.preibisch.flymapping.tools.PathsUtils;
import net.preibisch.flymapping.tools.TxtProcess;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/***
 * 3
 * Correlate
 * In Aerts exist 7096 genes
 * In Drosline exists 2204 genetically targeted populations of neurons.
 * The goal of this class is to get the genes that exists in both of them, should be <= 2204
 */
public class CorrelateGenesNamesWhichExistsInAll {
    public static void main(String[] args) throws Exception {

        // 1- from Dros lines

        // one line with 2083 columns contains the Janilla genes names
        // exmpl name GMR_10C06_AE_01
        // we convert it to Excel file name GMR_10C06_AE_01 => GMR10C06
        File iDGenespath = PathsUtils.getInputPathForFile(DrosLinesPaths.dros_linesIDtoJaneliaID);

        System.out.println("Get infos..");
        TxtProcess.infos(iDGenespath);

        // Getting Mapping name from Excel file
        // From janila name to gene name
        Map<String, String> janilaMapId = JaneliaIDtoGeneExcelReader.getJanilaGeneMap();

        List<String> genes = findWithoutRepetitionGenes(janilaMapId.values());

        System.out.println("Genes without repetitions: " + genes.size());
        // Result file
        // List 2083 genes Janila ids ( Exmpl: GMR10C06 ) with map of id of supervoxel
        // and probability of expression inside
        // list of names of genes that exists in dros line
        List<String> drosLinesGenes = Dros_lines.getExpressedGenes(iDGenespath, janilaMapId);

        System.out.println("Final Dros lines genes = " + drosLinesGenes.size());

        // Save it to file
        File resultFile = PathsUtils.getPathForResultFile(DrosLinesPaths.dros_lines_expressed_genes_names);
        GsonIO.save(resultFile, drosLinesGenes);


        //2- from Aerts

        File aertsInput = PathsUtils.getInputPathForFile(AertsPaths.aerts_57k_cells_scalesd);
        System.out.println("Get infos..");
        TxtProcess.infos(aertsInput);
        // List of names of genes of Aerts
        List<String> aertsGenes = Aerts_57k_cells.getGenesNames(aertsInput);
        System.out.println("Final Aerts genes  : " + aertsGenes.size());

        // Save it to file
//		resultFile = PathsUtils.ResultFile(AertsPaths.aerts_57k_genes_names);
//		GsonIO.save(resultFile, aertsGenes);


        //3- Get similarity
        List<String> concat = concat(drosLinesGenes, aertsGenes);
        System.out.println("Final concat genes  : " + concat.size());

        // Save it to file
        resultFile = PathsUtils.getPathForResultFile(ResultsPaths.GenesNamesWhichExistsInAertsAndDrosLines);
        GsonIO.save(resultFile, concat);
    }

    private static List<String> findWithoutRepetitionGenes(Collection<String> values) {
        List<String> result = new ArrayList<>();
        for (String v : values)
            if (!result.contains(v))
                result.add(v);

        return result;
    }

    private static List<String> concat(List<String> drosLinesGenes, List<String> aertsGenes) {
        drosLinesGenes.retainAll(aertsGenes);
        return drosLinesGenes;
    }
}
