package net.preibisch.flymapping.headless.v2;

import net.preibisch.flymapping.tools.TxtProcess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Refined_genes_vs_supervoxels {
    public static final String path = "/Users/Marwan/Desktop/SingleCellProject/data_v2/refined_genes_vs_supervoxels.tsv";

    private final List<List<Float>> matrix;
    private final List<String> genes;
    private final List<String> supervoxels;
    private final File input;
    private final boolean lowMemory;

    public static void main(String[] args) throws IOException {

        File input = new File(path);
        new Refined_genes_vs_supervoxels(input, false);

    }

    public Refined_genes_vs_supervoxels(File input, boolean supervoxelsLowMemory) throws IOException {

        long col = TxtProcess.columns(input);
        long lines = TxtProcess.lines(input);

        TxtProcess.infos(input.toString(), col, lines);

        this.input = input;
        this.lowMemory = supervoxelsLowMemory;
        Scanner sc = new Scanner(input, "UTF-8");

        String[] lineone = sc.nextLine().split("\t");
        supervoxels = Arrays.asList(lineone).subList(1, lineone.length);
        matrix = new ArrayList<>();
        genes = new ArrayList<>();
        while (sc.hasNextLine()) {

            List<String> line = Arrays.asList(sc.nextLine().split("\t"));
            genes.add(line.get(0));
            if (!lowMemory) {
                List<Float> elm = line.subList(1, line.size()).stream().map(Float::parseFloat)
                        .collect(Collectors.toList());

                matrix.add(elm);
            }
        }
    }

//    public List<List<Float>> getMatrix() {
//        return matrix;
//    }

    public List<String> getGenes() {
        return genes;
    }

    public List<String> getSupervoxels() {
        return supervoxels;
    }

    public Map<String, Float> getExpressionForSuperVoxel(int supervoxelId) throws FileNotFoundException {
        String superVoxel = "V_" + supervoxelId;
        Integer index = supervoxels.indexOf(superVoxel);

//        System.out.println(superVoxel+" Index: "+index);
        Map<String, Float> genesExpression = new HashMap<>();


        if (lowMemory) {
            Scanner sc = new Scanner(input, "UTF-8");

            sc.nextLine();
            while (sc.hasNextLine()) {
                List<String> line = Arrays.asList(sc.nextLine().split("\t"));
                String currentGene = line.get(0);
                List<Float> elm = line.subList(1, line.size()).stream().map(Float::parseFloat)
                        .collect(Collectors.toList());
                Float currenExpression = elm.get(index);
                genesExpression.put(currentGene, currenExpression);
            }
            System.out.println("Got suppervoxels Genes expression");
        } else {
            for (int i = 0; i < genes.size(); i++) {
                genesExpression.put(genes.get(i), matrix.get(i).get(index));
            }

        }
        return genesExpression;
    }
}
