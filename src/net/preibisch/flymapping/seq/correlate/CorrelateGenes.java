package net.preibisch.flymapping.seq.correlate;

import java.util.List;
import java.util.Map;

public class CorrelateGenes {
	public static double diff(Map<String, Double> cellGenes, Map<String, List<Double>> l2, Integer supervoxelid) {
		double result = 0.0;
		int n = cellGenes.size();
		for (String gene : cellGenes.keySet()) {
			Double genevalue = cellGenes.get(gene);
			Double x2 = l2.get(gene).get(supervoxelid);
			double tmp = Math.abs(genevalue - x2);
			result += tmp;

		}
		result = result / n;
		return result;
	}

	public static Float correlate(Map<String, Float> cellGenes,  Map<String,Float> svGenes) {
		Float result = 0.0f;
		int n = cellGenes.size();
		for (String gene : cellGenes.keySet()) {
			Float genevalue1 = cellGenes.get(gene);
			Float geneValue2 = svGenes.get(gene);
			Float tmp = genevalue1*geneValue2;
			result += tmp;

		}
//		result = result / n;
		return result;
	}
}
