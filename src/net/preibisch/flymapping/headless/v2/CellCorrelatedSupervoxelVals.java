package net.preibisch.flymapping.headless.v2;

import net.preibisch.flymapping.tools.TxtProcess;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CellCorrelatedSupervoxelVals {

    private final HashMap<Integer, Float> supervoxelsValues;

    public CellCorrelatedSupervoxelVals(File input) throws IOException {

        long col = TxtProcess.columns(input);
        long lines = TxtProcess.lines(input);

        TxtProcess.infos(input.toString(), col, lines);


        Scanner sc = new Scanner(input, "UTF-8");
        supervoxelsValues = new HashMap<>();

        while (sc.hasNextLine()) {
            String currentLine = sc.nextLine();
            List<String> line = Arrays.asList(currentLine.split("\t"));
            Integer supervoxelId = Integer.parseInt(line.get(0).split("_")[1]);
            Float supervoxelValue = Float.parseFloat(line.get(1));
            supervoxelsValues.put(supervoxelId, supervoxelValue);
        }
    }

    public Float getExpressionForSuperVoxel(int supervoxelId) {
        return supervoxelsValues.get(supervoxelId);

    }

    public Map<Integer, Float> getAll() {
        return supervoxelsValues;
    }
}