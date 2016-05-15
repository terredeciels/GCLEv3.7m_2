package perft;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import position.FenToGPosition;
import position.GCoups;
import position.GPosition;
import position.ICodage;

public class PerftGCLETest {

    public PerftGCLETest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void perftTest() {
        String f = ICodage.fen_initiale;
//        String f = "r3k2r/p6p/8/B7/1pp1p3/3b4/P6P/R3K2R w KQkq - 0 1";
        GPosition gp = FenToGPosition.toGPosition(f);
        long miniMax = miniMax(gp, 1);
//        (!coupsvalides.isEmpty())
        System.out.println("depth 1: " + miniMax);// OK
        assert (miniMax == 20);
        miniMax = miniMax(gp, 2);
        System.out.println("depth 2: " + miniMax);//OK
        assert (miniMax == 400);
        miniMax = miniMax(gp, 3);
        System.out.println("depth 3: " + miniMax);//10874 aulieu de 8902 !
        assert (miniMax == 8902);
    }

    private long miniMax(GPosition gp, int depth) {
        long nodes = 0;
        if (depth == 0) {
            return 1;
        }
        ArrayList<GCoups> moves = gp.getCoupsValides();

        for (int i = 0; i < moves.size(); i++) {
            GCoups gcoups = moves.get(i);
            gp.exec(gcoups);
            nodes += miniMax(gp, depth - 1);
            gp.unexec();
        }
        return nodes;
    }

}
