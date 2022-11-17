package dazaram.eureka.recipe.domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import lombok.Getter;

@Component
@Getter
public class Ingr2Vec {

	public final HashMap<String, List<Double>> ingr2Vec;

	public Ingr2Vec() throws FileNotFoundException {
		File file = new File("./src/main/resources/model/Model.json");
		Gson gson = new Gson();

		FileReader reader = new FileReader(file);
		gson.newJsonReader(reader);

		ingr2Vec = gson.fromJson(reader, HashMap.class);
	}

	public Set<String> getKeyset() {
		return ingr2Vec.keySet();
	}

	public List<Double> getValue(String key) {
		return ingr2Vec.get(key);
	}
}
