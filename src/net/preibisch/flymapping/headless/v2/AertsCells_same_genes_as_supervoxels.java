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

public class AertsCells_same_genes_as_supervoxels {

    public static final String path = "/Users/Marwan/Desktop/SingleCellProject/data_v2/aertsCells_same_genes_as_supervoxels.tsv";
    private final List<List<Float>> matrix;
    private final List<String> genes;
    private final List<String> cells;
    private final File input;
    private final boolean lowMemory;

    public static void main(String[] args) throws IOException {

        File input = new File(path);
        new AertsCells_same_genes_as_supervoxels(input, false);

    }

    public AertsCells_same_genes_as_supervoxels(File input, boolean aertsLowMemory) throws IOException {

        long col = TxtProcess.columns(input);
        long lines = TxtProcess.lines(input);

        TxtProcess.infos(input.toString(), col, lines);
        this.lowMemory = aertsLowMemory;

        Scanner sc = new Scanner(input, "UTF-8");

        String[] lineone = sc.nextLine().split("\t");

        cells = Arrays.asList(lineone).subList(1, lineone.length);
        this.input = input;

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

    public List<String> getCells() {
        return cells;
    }

    public Map<String, Float> getGenesExpressionForOneCell(String cellExample) throws FileNotFoundException {

        System.out.println("Looking for cell: " + cellExample);
        Map<String, Float> genesExpression = new HashMap<>();
//        int index = -1;
//        for (int i = 0; i < cells.size(); i++) {
//            if (cells.get(i).contains(cellExample)) {
//                index = i;
//                break;
//            }
//        }
        int index = cells.indexOf(cellExample);
        if (index <= 0) {
            throw new IllegalArgumentException("Cell " + cellExample + " not found !");
        }

        System.out.println("Cell found at position: " + index);

        if (lowMemory) {
            Scanner sc = new Scanner(input, "UTF-8");

            sc.nextLine();
            while (sc.hasNextLine()) {
                List<String> line = Arrays.asList(sc.nextLine().split("\t"));
                String currentGenes = line.get(0);

                List<Float> elm = line.subList(1, line.size()).stream().map(Float::parseFloat)
                        .collect(Collectors.toList());

                Float expressionForGene = elm.get(index);
                genesExpression.put(currentGenes, expressionForGene);
            }
            System.out.println("Got cell genes expression>");
        } else {
            for (int i = 0; i < genes.size(); i++) {
                genesExpression.put(genes.get(i), matrix.get(i).get(index));

            }
        }
        return genesExpression;
    }
}
