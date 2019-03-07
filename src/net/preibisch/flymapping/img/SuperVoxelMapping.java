package net.preibisch.flymapping.img;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

import net.imglib2.Cursor;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.FloatType;
import net.imglib2.view.Views;
import net.preibisch.flymapping.tools.GsonIO;
import net.preibisch.flymapping.tools.IOFunctions;
import net.preibisch.flymapping.tools.PathsUtils;

public class SuperVoxelMapping {
	public static void main(String[] args) throws IOException {
//		generateMap();
		testMap();
	}

	private static void generateMap() throws IOException {
		System.out.println("Start supervoxel HashMap generating..");
		RandomAccessibleInterval<FloatType> supervoxels = IOFunctions
				.openAs32Bit(PathsUtils.File(ImgPaths.SUPER_VOXEL));
//		ImageJFunctions.show(supervoxels);

		Map<Integer, List<List<Integer>>> hashMap = getHashMap(supervoxels);

		System.out.println("Finish ! Size Map: " + hashMap.size());

		File resultHashMap = PathsUtils.ResultFile(ImgPaths.SUPER_VOXEL_HASHMAP);
		GsonIO.save(resultHashMap, hashMap);
	}

	private static void testMap() throws FileNotFoundException {
		System.out.println("load file");

		Type listType = new TypeToken<Map<Integer, List<List<Integer>>>>() {
		}.getType();


		Map<Integer, List<List<Integer>>> supervoxelMap = GsonIO.read(
				PathsUtils.ResultFile(ImgPaths.SUPER_VOXEL_HASHMAP),
				listType);
		System.out.println("loaded");
		System.out.println("Length: " + supervoxelMap.size());

		for (Integer key : supervoxelMap.keySet())
			System.out.print(key + "-");
//		List<List<Integer>> elm0 = supervoxelMap.get(1);
//		for (List<Integer> x : elm0) {
//			for (Integer n : x)
//				System.out.print(x + " ");
//			System.out.println();
//		}
	}

	public static <T extends RealType<T>> Map<Integer, List<List<Integer>>> getHashMap(
			RandomAccessibleInterval<T> supervoxel) {

		Map<Integer, List<List<Integer>>> voxelsMap = new HashMap<>();
		final Cursor<T> c = Views.iterable(supervoxel).cursor();
		int dims = supervoxel.numDimensions();
		while (c.hasNext()) {
			c.fwd();
			final List<Integer> pos = getPosition(c, dims);
			final Integer x = (int) (c.next().getRealDouble());
			if (x > 0) {
				if (voxelsMap.containsKey(x)) {
					voxelsMap.get(x).add(pos);
				} else {
					List<List<Integer>> listPos = new ArrayList<>();
					listPos.add(pos);
					voxelsMap.put(x, listPos);
			}

		}
		}
		return voxelsMap;
	}

	private static <T extends RealType<T>> List<Integer> getPosition(Cursor<T> c, int dims) {
		List<Integer> pos = new ArrayList<>();
		for (int i = 0; i < dims; i++) {
			pos.add((int) c.getLongPosition(i));
		}
		return pos;
	}

}
