package electronique;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

public abstract class Composant {

    public Composant() {
    }


    public abstract double calculerResistance();
}
