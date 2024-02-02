package materials;

import org.json.JSONArray;

import java.awt.*;
import java.util.ArrayList;

public class MaterialPropertiesSerial {
    public MaterialPropertiesSerial(
            String name,
            ArrayList<Object> movement,
            int mass,
            Color color,
            String materialType,
            double lifeSpan,
            JSONArray ignitionProduct,
            int setFireStrength,
            int fireResistance,
            int corrosionStrength,
            int corrosionResistance,
            boolean permanent
    ) {
        this.name = name;
        this.movement = movement;
        this.mass = mass;
        this.color = color;
        this.materialType = materialType;
        this.lifeSpan = lifeSpan;
        this.ignitionProduct = ignitionProduct;
        this.setFireStrength = setFireStrength;
        this.fireResistance = fireResistance;
        this.corrosionStrength = corrosionStrength;
        this.corrosionResistance = corrosionResistance;
        this.permanent = permanent;
    }
    String name;
    ArrayList<Object> movement;
    int mass;
    Color color;
    String materialType;
    double lifeSpan;
    JSONArray ignitionProduct;
    int setFireStrength;
    int fireResistance;
    int corrosionStrength;
    int corrosionResistance;
    boolean permanent;
}
