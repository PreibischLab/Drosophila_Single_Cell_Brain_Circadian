package net.preibisch.flymapping.seq.correlate;

import java.util.List;
import java.util.Map;

public class CorrelateGenes {
	public static double diff(Map<String, Double> cellGenes, Map<String, List<Double>> l2, Integer sv) {
		double result = 0.0;
		int n = cellGenes.size();
		for (String gene : cellGenes.keySet()) {
			Double x1 = cellGenes.get(gene);
			Double x2 = l2.get(gene).get(sv);
			double tmp = Math.abs(x1 - x2);
			result += tmp;

		}
		result = result / n;
		return result;

	}
}
