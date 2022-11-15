package dazaram.eureka.recipe.domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class Ingr2Vec {

	public static final HashMap<String, List<Double>> ingr2Vec;

	static {
		try {
			ingr2Vec = ingr2Vec();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static HashMap<String, List<Double>> ingr2Vec() throws FileNotFoundException {
		JsonParser parser = new JsonParser();
		File file = new File("./src/main/resources/model/Model.json");
		Gson gson = new Gson();

		FileReader reader = new FileReader(file);
		JsonReader jsonReader = new JsonReader(reader);
		gson.newJsonReader(reader);

		HashMap<String, List<Double>> ret = gson.fromJson(reader, HashMap.class);

		return ret;
	}
}
