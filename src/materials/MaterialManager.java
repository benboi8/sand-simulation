package materials;

import main.Colors;
import main.World;
import main.fileOperations.FileReader;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;

public class MaterialManager {
    public static final String materialFolder = "materials/";

    public static HashSet<String> uniqueMaterials = new HashSet<>();

    public static Grain empty(int x, int y, World world) {
        return new Grain(x, y, world);
    }

    public static String erase = "erase";

    public static String empty = "empty";

    public static Grain GetMaterial(String material, int x, int y, World world) {
        return CreateMaterial(material, x, y, world);
    }

    // todo go through each file in materials folder to get the list of available materials
    // todo should only get called once for each material and save those to a list to use later
    public static Grain CreateMaterial(String fileName, int x, int y, World world) {
        String jsonString = FileReader.ReadTextFile(materialFolder + fileName + ".json");

        JSONObject jsonObject = new JSONObject(jsonString);

        Grain material = new Grain(x, y, world);

        // All Material has
        material.name = jsonObject.getString(MaterialProperties.name.name());
        uniqueMaterials.add(material.name);

        material.movement = (ArrayList<Object>) jsonObject.getJSONArray(MaterialProperties.movement.name()).toList();

        material.mass = jsonObject.getInt(MaterialProperties.mass.name());

        String color = jsonObject.getString(MaterialProperties.color.name());
        material.color = Colors.FromRGBString(color);

        material.materialType = jsonObject.getString(MaterialProperties.material.name()).toLowerCase();


        // Some material has
        if (jsonObject.has(MaterialProperties.life_span.name())) {
            material.lifeSpan = jsonObject.getDouble(MaterialProperties.life_span.name());
        }
        if (jsonObject.has(MaterialProperties.ignition_products.name())) {
            material.ignitionProduct = jsonObject.getJSONArray(MaterialProperties.ignition_products.name());
        }
        if (jsonObject.has(MaterialProperties.fire_strength.name())) {
            material.setFireStrength(jsonObject.getInt(MaterialProperties.fire_strength.name()));
        }
        if (jsonObject.has(MaterialProperties.fire_resistance.name())) {
            material.fireResistance = jsonObject.getInt(MaterialProperties.fire_resistance.name());
        }
        if (jsonObject.has(MaterialProperties.corrosion_strength.name())) {
            material.corrosionStrength = jsonObject.getInt(MaterialProperties.corrosion_strength.name());
        }
        if (jsonObject.has(MaterialProperties.corrosion_resistance.name())) {
            material.corrosionResistance = jsonObject.getInt(MaterialProperties.corrosion_resistance.name());
        }
        if (jsonObject.has(MaterialProperties.displaceable.name())) {
            material.permanent = jsonObject.getBoolean(MaterialProperties.displaceable.name());
        }

        return material;
    }
}
