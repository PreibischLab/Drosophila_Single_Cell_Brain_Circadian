package net.preibisch.flymapping.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

public class GsonIO<T> {

	public static void save(File path, Object obj) throws IOException {
		Writer writer = new OutputStreamWriter(new FileOutputStream(path), "UTF-8");
		Gson gson = new GsonBuilder().create();
		gson.toJson(obj, writer);
		writer.flush();
		writer.close();
		System.out.println("File saved: " + path.getAbsolutePath() );
	}

	public static <T extends Object> T read(File path, Class<T> type) throws FileNotFoundException {
		Gson gson = new Gson();
		JsonReader reader = new JsonReader(new FileReader(path));
		T data = gson.fromJson(reader, type);
		return data;
	}

	public static <T extends Object> T read(File path, Type type) throws FileNotFoundException {
		Gson gson = new Gson();
		JsonReader reader = new JsonReader(new FileReader(path));
		T data = gson.fromJson(reader, type);
		return data;
	}
}
