package net.preibisch.flymapping.seq.droslines;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import net.preibisch.flymapping.config.MyPaths;
import net.preibisch.flymapping.config.PathUtils;
import net.preibisch.flymapping.tools.PrettyPrintingMap;

public class JaneliaIDtoGeneExcelReader {

	public static Map<String,String> getJanilaGeneMap() throws EncryptedDocumentException, InvalidFormatException, IOException{
		File f = PathUtils.File(MyPaths.Janelia_map_janelia_IDtoGene_name);
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
	
	public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, IOException {
		
		Map<String, String> janilaMapId = getJanilaGeneMap();
		System.out.println(new PrettyPrintingMap<String, String>(janilaMapId));
//		System.out.println(janilaMapId.get("GMR19G07"));
	}
	
}
