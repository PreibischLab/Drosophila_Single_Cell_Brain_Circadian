package net.preibisch.flymapping.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonIO {

	public static void save(File path, Object obj) throws IOException {
		Writer writer = new OutputStreamWriter(new FileOutputStream(path), "UTF-8");
		Gson gson = new GsonBuilder().create();
		gson.toJson(obj, writer);
		writer.flush();
		writer.close();
		System.out.println("File saved: " + path.getAbsolutePath() );
	}
}
