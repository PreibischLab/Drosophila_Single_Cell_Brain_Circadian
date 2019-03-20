package net.preibisch.flymapping.img;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.preibisch.flymapping.seq.correlate.CorrelateGenes;

public class SuperVoxel {

	public static Map<Integer, Double> generateSVsPerCell(Map<String, Double> cellGenes,
			Map<String, List<Double>> concatGenesSV, Integer numberSV) {
		Map<Integer, Double> result = new HashMap<>();
		for (int i = 0; i < numberSV; i++)
			result.put(i, CorrelateGenes.diff(cellGenes, concatGenesSV, i));
		// TODO Auto-generated method stub
		return result;
	}

	public static Map<Integer, Double> normalise(Map<Integer, Double> map) {
		Double max = getMax(map);

		Map<Integer, Double> result = new HashMap<>();
		for (Map.Entry<Integer, Double> entry : map.entrySet()) {
			double x = max - entry.getValue();
			result.put(entry.getKey(), x);
		}
		return result;
	}

	private static Double getMax(Map<Integer, Double> map) {

		return Collections.max(map.values());
	}

}
