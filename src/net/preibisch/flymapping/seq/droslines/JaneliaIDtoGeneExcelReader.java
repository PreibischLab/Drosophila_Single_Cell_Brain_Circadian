package net.preibisch.flymapping.seq.droslines;

import net.preibisch.flymapping.seq.ResultsPaths;
import net.preibisch.flymapping.tools.GsonIO;
import net.preibisch.flymapping.tools.PathsUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JaneliaIDtoGeneExcelReader {

	public static void main(String[] args) throws Exception {

		Map<String, List<String>> janilaIDsPerGenes = getJanilaIDsPerGene();

		File resultFile = PathsUtils.ResultFile(ResultsPaths.JanilaIDsPerGenes);

		GsonIO.save(resultFile, janilaIDsPerGenes);
		System.out.println("Finish get expressed cells ");

//		System.out.println(new PrettyPrintingMap<String, String>(janilaMapId));
//		System.out.println(janilaMapId.get("GMR19G07"));
	}

	public static Map<String,String> getJanilaGeneMap() throws Exception{
		File f = PathsUtils.File(DrosLinesPaths.Janelia_map_janelia_IDtoGene_name);
		Workbook workbook = WorkbookFactory.create(f);
		HashedMap<String, String> janilaMapId = new HashedMap();
		for(Sheet sheet: workbook) {
			DataFormatter dataFormatter = new DataFormatter();
            System.out.println("=> " + sheet.getSheetName());
            System.out.println("\nIterating over Rows and Columns using Iterator\n");
            Iterator<Row> rowIterator = sheet.rowIterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                janilaMapId.put(dataFormatter.formatCellValue(row.getCell(0)), dataFormatter.formatCellValue(row.getCell(1)));
            }
        }
		return janilaMapId;
	}
	
	public static Map<String, List<String>> getJanilaIDsPerGene()
			throws Exception {

		File f = PathsUtils.File(DrosLinesPaths.Janelia_map_janelia_IDtoGene_name);
		Workbook workbook = WorkbookFactory.create(f);
		Map<String, List<String>> janilaIDsPerGenes = new HashedMap();
		for (Sheet sheet : workbook) {
			DataFormatter dataFormatter = new DataFormatter();
			System.out.println("=> " + sheet.getSheetName());
			System.out.println("\nIterating over Rows and Columns using Iterator\n");
			Iterator<Row> rowIterator = sheet.rowIterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				String janilaID = dataFormatter.formatCellValue(row.getCell(0));
				String geneName = dataFormatter.formatCellValue(row.getCell(1));
				if (janilaIDsPerGenes.containsKey(geneName)) {
					janilaIDsPerGenes.get(geneName).add(janilaID);
				} else {
					List<String> listJanilaIds = new ArrayList<>();
					listJanilaIds.add(janilaID);
					janilaIDsPerGenes.put(geneName, listJanilaIds);
				}
			}
		}
		return janilaIDsPerGenes;

	}
	
}
