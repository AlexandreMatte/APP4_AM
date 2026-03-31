package electronique;

import java.util.Collections;
import java.util.List;

import electronique.Resistance;

public class CircuitSerie extends Circuit {

    public CircuitSerie(List<Composant> composants) {
        super(composants);
    }

    @Override
    public double calculerResistance() {
        double resistance = 0;
        for (Composant composant : this.composants) {
            if (composant.getClass() == CircuitSerie.class) {
                CircuitSerie n = new CircuitSerie(Collections.singletonList(composant));
                resistance += n.calculerResistance();
            }
            if (composant.getClass() == CircuitParallele.class) {
                CircuitParallele n = new CircuitParallele(Collections.singletonList(composant));
                resistance += n.calculerResistance();
            }
            if (composant.getClass() == Resistance.class) {
                Resistance n = new Resistance(composant.calculerResistance());
                resistance += n.calculerResistance();
            }
        }
        return resistance;
    }
}
