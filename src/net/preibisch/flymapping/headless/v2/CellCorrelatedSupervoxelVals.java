package net.preibisch.flymapping.headless.v2;

import net.preibisch.flymapping.tools.TxtProcess;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CellCorrelatedSupervoxelVals {
    private static final String path = "/volumes/data/Bioinformatics_master/Fourth_semester/Thesis/" +
            "Drosophila_Single_Cell_Brain_Circadian/Ines_data_working/Drosophila_brain_single_cells_mapping/cells_supervoxels";
    private static final String cellExample = "AACTCTTGTCAAAGAT.1.tsv";

    private final List<List<Float>> matrix;
    private final List<String> genes;
    private final List<String> supervoxels;

    public static void main(String[] args) throws IOException {

        File input = new File(path, cellExample);
        new Refined_genes_vs_supervoxels(input);

    }

    public CellCorrelatedSupervoxelVals(File input) throws IOException {

        long col = TxtProcess.columns(input);
        long lines = TxtProcess.lines(input);

        TxtProcess.infos(input.toString(), col, lines);


        Scanner sc = new Scanner(input, "UTF-8");

        String[] lineone = sc.nextLine().split("\t");
        supervoxels = Arrays.asList(lineone).subList(1,lineone.length);
        matrix = new ArrayList<>();
        genes = new ArrayList<>();
        while (sc.hasNextLine()) {

            List<String> line = Arrays.asList(sc.nextLine().split("\t"));
            genes.add(line.get(0));

            List<Float> elm = line.subList(1,line.size()).stream().map(Float::parseFloat)
                    .collect(Collectors.toList());

            matrix.add(elm);
        }
    }

    public List<List<Float>> getMatrix() {
        return matrix;
    }

    public List<String> getGenes() {
        return genes;
    }

    public List<String> getSupervoxels() {
        return supervoxels;
    }

    public Map<String ,Float> getExpressionForSuperVoxel(int supervoxelId) {
        String superVoxel = "V_"+supervoxelId;
        Integer index = supervoxels.indexOf(superVoxel);

//        System.out.println(superVoxel+" Index: "+index);
        Map<String, Float> voxelsExpression = new HashMap<>();

        for(int i = 0; i<genes.size();i++){
            voxelsExpression.put(genes.get(i),matrix.get(i).get(index));
        }
        return voxelsExpression ;

    }
}