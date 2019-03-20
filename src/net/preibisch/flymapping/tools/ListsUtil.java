package net.preibisch.flymapping.tools;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ListsUtil {
	public static double getMaxExpressed(HashMap<String, List<Double>> genesSV) {
		double max = 0;
		for (List<Double> l : genesSV.values()) {
			max = Math.max(Collections.max(l), max);
		}
		return max;
	}

}
