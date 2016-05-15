package ia;

import com.googlecode.jctree.Tree;
import java.util.ArrayList;
import java.util.Random;
import position.GCoups;
import position.GPosition;

public class IAleatoire {

    private final Random randomGenerator;

    public IAleatoire() {
        randomGenerator = new Random();
    }

    public GCoups evaluate(ArrayList<GCoups> coupsvalides) {

        int randomInt = randomGenerator.nextInt(coupsvalides.size());
        GCoups gcoups = coupsvalides.get(randomInt);

        return gcoups;
    }

    public short evaluate(short[] allMoves) {
        int randomInt = randomGenerator.nextInt(allMoves.length);
        short move = allMoves[randomInt];
        return move;
    }
//    public GCoups evaluate(Tree<GPosition> t) {
//
//        ArrayList<GCoups> coupsvalides = t.root().getCoupsValides().get();
//        int randomInt = randomGenerator.nextInt(coupsvalides.size());
//        GCoups gcoups = coupsvalides.get(randomInt);
//        return gcoups;
//    }

}
