import sun.plugin.dom.css.RGBColor;

import java.awt.*;
import java.util.List;

import static com.sun.javafx.iio.ImageStorage.ImageType.RGB;

public class PokemonModel {
    private int index;
    private String name;
    private int generation;
    private List<String> type;
    private Color color;
    private String colorText;

    public PokemonModel(){
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public int getGeneration() {
        return generation;
    }

    public List<String> getType() {
        return type;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = Color.decode(color);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getColorText() {
        return colorText;
    }

    public void setColorText(String colorText) {
        this.colorText = colorText;
    }

    @Override
    public String toString() {
        return "PokemonModel{" +
                "index=" + index +
                ", name='" + name + '\'' +
                ", generation=" + generation +
                ", type=" + type +
                ", color=" + color +
                ", colorText='" + colorText + '\'' +
                '}';
    }
}
