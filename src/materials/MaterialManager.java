package materials;

import main.Colors;
import main.Constants;
import main.GuiButton;
import main.World;
import main.fileOperations.FileReader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.Hashtable;

public class MaterialManager {
    public static final String materialFolder = "materials/";

    public static Hashtable<String, MaterialPropertiesSerial> materials = new Hashtable<String, MaterialPropertiesSerial>();

    public static Grain empty(int x, int y, World world) {
        return new Grain(x, y, world);
    }

    public static String erase = "erase";

    public static String empty = "empty";

    public static Grain GetMaterial(String material, int x, int y, World world) {
        return CreateMaterial(material, x, y, world);
    }

    public static void CreateAllMaterials() {
        for (String fileName : FileReader.GetAllFilesInFolder(materialFolder)) {
            LoadMaterial(fileName.split("\\.")[0]);
        }
    }

    public static void LoadMaterial(String fileName) {
        String jsonString = FileReader.ReadTextFile(materialFolder + fileName + ".json");

        JSONObject jsonObject = new JSONObject(jsonString);

        String name = jsonObject.getString(MaterialProperties.name.name());

        ArrayList<Object> movement = (ArrayList<Object>) jsonObject.getJSONArray(MaterialProperties.movement.name()).toList();

        int mass = jsonObject.getInt(MaterialProperties.mass.name());

        String colorString = jsonObject.getString(MaterialProperties.color.name());
        Color color = Colors.FromRGBString(colorString);

        String materialType = jsonObject.getString(MaterialProperties.material.name()).toLowerCase();

        double lifeSpan = -1;
        JSONArray ignitionProduct = null;
        int setFireStrength = 0;
        int fireResistance = 0;
        int corrosionStrength = 0;
        int corrosionResistance = 0;
        boolean permanent = false;

        // Some material has
        if (jsonObject.has(MaterialProperties.life_span.name())) {
            lifeSpan = jsonObject.getDouble(MaterialProperties.life_span.name());
        }
        if (jsonObject.has(MaterialProperties.ignition_products.name())) {
            ignitionProduct = jsonObject.getJSONArray(MaterialProperties.ignition_products.name());
        }
        if (jsonObject.has(MaterialProperties.fire_strength.name())) {
            setFireStrength = jsonObject.getInt(MaterialProperties.fire_strength.name());
        }
        if (jsonObject.has(MaterialProperties.fire_resistance.name())) {
            fireResistance = jsonObject.getInt(MaterialProperties.fire_resistance.name());
        }
        if (jsonObject.has(MaterialProperties.corrosion_strength.name())) {
            corrosionStrength = jsonObject.getInt(MaterialProperties.corrosion_strength.name());
        }
        if (jsonObject.has(MaterialProperties.corrosion_resistance.name())) {
            corrosionResistance = jsonObject.getInt(MaterialProperties.corrosion_resistance.name());
        }
        if (jsonObject.has(MaterialProperties.displaceable.name())) {
            permanent = jsonObject.getBoolean(MaterialProperties.displaceable.name());
        }

        materials.put(
            name.toLowerCase(),
            new MaterialPropertiesSerial(
              name,
              movement,
              mass,
              color,
              materialType,
              lifeSpan,
              ignitionProduct,
              setFireStrength,
              fireResistance,
              corrosionStrength,
              corrosionResistance,
              permanent
            )
        );
        GuiButton button = new GuiButton(new Rectangle(15, 100 + (int) Constants.INSET_TOP + (55 * materials.size()), 85, 50), name, color);
    }

    public static Grain CreateMaterial(String materialName, int x, int y, World world) {
        if (!materialName.contains(materialName.toLowerCase())) {
            return empty(x, y, world);
        }

        MaterialPropertiesSerial materialProperties = materials.get(materialName.toLowerCase());

        Grain material = new Grain(x, y, world);

        // All Material has
        material.name = materialProperties.name;

        material.movement = (ArrayList<Object>) materialProperties.movement;

        material.mass = materialProperties.mass;

        material.color = materialProperties.color;

        material.materialType = materialProperties.materialType;


        // Some material has
        material.lifeSpan = materialProperties.lifeSpan;
        material.ignitionProduct = materialProperties.ignitionProduct;
        material.setFireStrength(materialProperties.setFireStrength);
        material.setFireResistance(materialProperties.fireResistance);
        material.setCorrosionStrength(materialProperties.corrosionStrength);
        material.setCorrosionResistance(materialProperties.corrosionResistance);
        material.permanent = materialProperties.permanent;

        return material;
    }
}
