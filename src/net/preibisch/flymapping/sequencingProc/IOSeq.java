package net.preibisch.flymapping.sequencingProc;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class IOSeq {
	public static void saveJson(aerts_57k_cells data, String path) {
		try(Writer writer = new OutputStreamWriter(new FileOutputStream(path) , "UTF-8")){
            Gson gson = new GsonBuilder().create();
            gson.toJson(data, writer);
        } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
