package electronique;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

public abstract class Composant {

    public Composant() {
    }


    public Composant lireCircuit(JsonNode node) {
        String type = node.get("type").asText();

        if ("resistance".equals(type)) {
            return new Resistance(node.get("valeur").asDouble());

        } else if ("serie".equals(type)) {
            List<Composant> composants = new ArrayList<>();
            for (JsonNode composantNode : node.get("composants")) {
                composants.add(lireCircuit(composantNode));
            }
            return new CircuitSerie(composants);

        } else if ("parallele".equals(type)) {
            List<Composant> composants = new ArrayList<>();
            for (JsonNode composantNode : node.get("composants")) {
                composants.add(lireCircuit(composantNode));
            }
            return new CircuitParallele(composants);
        }
        throw new IllegalArgumentException("Type de composant inconnu : " + type);
    }


    public abstract double calculerResistance();
}
