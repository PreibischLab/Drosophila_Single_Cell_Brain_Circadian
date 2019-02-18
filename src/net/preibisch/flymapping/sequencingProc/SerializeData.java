package net.preibisch.flymapping.sequencingProc;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SerializeData {
	public static void main(String[] args) {
		prepare_aerts_57k_cells_raw();
	}

	private static void prepare_aerts_57k_cells_raw() {
		try {
			Path aerts_57k_cells_raw_path = Paths.get(SeqPaths.SEQUENCING_FOLDER,SeqPaths.aerts_57k_cells_raw);
			Path aerts_57k_cells_raw_output_folder = Paths.get(SeqPaths.SEQUENCING_FOLDER,SeqPaths.aerts_57k_cells_raw_folder);
			int chuncks = TxtProcess.toChuncks(aerts_57k_cells_raw_path,aerts_57k_cells_raw_output_folder,SeqPaths.aerts_57k_cells_raw_folder,200);
			System.out.println("Chunks: "+ chuncks );
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
