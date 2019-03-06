package net.preibisch.flymapping.seq.aerts;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.preibisch.flymapping.seq.ResultsPaths;
import net.preibisch.flymapping.tools.GsonIO;
import net.preibisch.flymapping.tools.PathsUtils;
import net.preibisch.flymapping.tools.TxtProcess;

public class CellClusters {
	public static void main(String[] args) throws IOException {
		File cellCluster5 = PathsUtils.File(ResultsPaths.Cluster5_Aerts);

		System.out.println("Get infos..");
		TxtProcess.infos(cellCluster5);

		List<String> cellsNamesCluster5 = Aerts_57k_cells.getCellsNames(cellCluster5);

		File cellNamesCluster5File = PathsUtils.ResultFile(ResultsPaths.cells_names_Cluster5_Aerts);
		GsonIO.save(cellNamesCluster5File, cellsNamesCluster5);
	}
}
