package electronique;

import java.util.List;

public class CircuitParallele extends Circuit{

    public CircuitParallele(List<Composant> composants) {
        super(composants);
    }

    @Override
    public double calculerResistance() {
        return 0;
    }
}
