package app;

import com.fasterxml.jackson.databind.JsonNode;
import electronique.CircuitParallele;
import electronique.CircuitSerie;
import electronique.Composant;
import electronique.Resistance;

import java.util.ArrayList;
import java.util.List;

public class CircuitBuilder {

    public CircuitBuilder() {
    }

    //public Composant construireCircuit(String a){
    //}


    public static Composant lireComposant(JsonNode node) {
        if (node == null || !node.has("type")) {
            throw new IllegalArgumentException("Le nœud JSON est nul ou ne contient pas de champ 'type'.");
        }

        String type = node.get("type").asText(null);
        if (type == null) {
            throw new IllegalArgumentException("Le champ 'type' est vide ou invalide.");
        }

        switch (type) {
            case "resistance":
                if (!node.has("valeur") || !node.get("valeur").isNumber()) {
                    throw new IllegalArgumentException("Une résistance doit avoir un champ numérique 'valeur'.");
                }
                return new Resistance(node.get("valeur").asDouble());

            case "serie":
                return new CircuitSerie(lireListeComposants(node));

            case "parallele":
                return new CircuitParallele(lireListeComposants(node));

            default:
                throw new IllegalArgumentException("Type de composant inconnu : " + type);
        }
    }

    private static List<Composant> lireListeComposants(JsonNode node) {
        if (!node.has("composants") || !node.get("composants").isArray()) {
            throw new IllegalArgumentException("Le champ 'composants' doit être un tableau JSON.");
        }

        List<Composant> composants = new ArrayList<>();
        for (JsonNode composantNode : node.get("composants")) {
            composants.add(lireComposant(composantNode)); // récursif
        }
        return composants;
    }
}
