package luoyu.legendarytalent.Talent;

import java.util.Map;

public class Talent {
    private final String name;
    private final String description;
    private final Map<String, Double> effects;

    public Talent(String name, String description, Map<String, Double> effects) {
        this.name = name;
        this.description = description;
        this.effects = effects;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, Double> getEffects() {
        return effects;
    }
}
