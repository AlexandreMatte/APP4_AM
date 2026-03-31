package electronique;

import java.util.Collections;
import java.util.List;

public class CircuitParallele extends Circuit{

    public CircuitParallele(List<Composant> composants) {
        super(composants);
    }

    @Override
    public double calculerResistance() {
        double resistance = 0;
        for (Composant composant : this.composants) {
            if (composant.getClass() == CircuitSerie.class) {
                CircuitSerie n = new CircuitSerie(((CircuitSerie) composant).composants);
                resistance += 1 / n.calculerResistance();
            }
            if (composant.getClass() == CircuitParallele.class) {
                CircuitParallele n = new CircuitParallele(((CircuitParallele) composant).composants);
                resistance += 1 / n.calculerResistance();
            }
            if (composant.getClass() == Resistance.class) {
                Resistance n = new Resistance(composant.calculerResistance());
                resistance += 1 / n.calculerResistance();
            }
        }
        return 1 / resistance;
    }
}
