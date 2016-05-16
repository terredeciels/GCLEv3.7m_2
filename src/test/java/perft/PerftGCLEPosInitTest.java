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
import static position.ICodage.TYPE_DE_COUPS.EnPassant;
import static position.ICodage.TYPE_DE_COUPS.Prise;
import static position.ICodage.TYPE_DE_COUPS.Promotion;
import static position.ICodage.TYPE_DE_COUPS.Roque;
import position.UndoGCoups;

public class PerftGCLEPosInitTest {

    private int node_roque;
    private int node_ep;
    private int node_prise;
    private int node_promotion;

    public PerftGCLEPosInitTest() {
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
        //voir http://chessprogramming.wikispaces.com/Perft+Results
        String f = ICodage.fen_initiale;

        GPosition gp = FenToGPosition.toGPosition(f);
        long miniMax = miniMax(gp, 1);
//        (!coupsvalides.isEmpty())
        System.out.println("depth 1: " + miniMax);// OK
        assert (miniMax == 20);

        miniMax = miniMax(gp, 2);
        System.out.println("depth 2: " + miniMax);//OK
        assert (miniMax == 400);

        miniMax = miniMax(gp, 3);
        System.out.println("depth 3: " + miniMax);//OK
        System.out.println("depth 3 roque: " + node_roque);//OK
        System.out.println("depth 3 ep: " + node_ep);//OK
        System.out.println("depth 3 prise: " + node_prise);// OK  34
        assert (miniMax == 8902);

        miniMax = miniMax(gp, 4);
        System.out.println("depth 4: " + miniMax);//OK
        System.out.println("depth 4 roque: " + node_roque);//OK 0
        System.out.println("depth 4 ep: " + node_ep);// OK 0 
        System.out.println("depth 4 prise: " + node_prise);// 1640 vs 1576
        System.out.println("depth 4 promotion: " + node_promotion);//  vs 0
        assert (miniMax == 197281);

//        miniMax = miniMax(gp, 5);
//        System.out.println("depth 5: " + miniMax + "  () sec");//OK
//        assert (miniMax == 4865609);
//        miniMax = miniMax(gp, 6);
//        System.out.println("depth 6: " + miniMax + "  () sec");//OK plus long > 4 min
//        assert (miniMax == 119060324);
        // integer too large :
//        miniMax = miniMax(gp, 7);
//        System.out.println("depth 7: " + miniMax +"  () sec");
//        assert (miniMax == 3195901860);
    }

    private long miniMax(GPosition gp, int depth) {
        long nodes = 0;

        if (depth == 0) {
            return 1;
        }
        ArrayList<GCoups> moves = gp.getCoupsValides();
        for (int i = 0; i < moves.size(); i++) {
            GCoups gcoups = moves.get(i);
            UndoGCoups ui = new UndoGCoups();
            gp.exec(gcoups, ui);
            if (gcoups.getTypeDeCoups().equals(Roque)) {
                node_roque++;
            }
            if (gcoups.getTypeDeCoups().equals(EnPassant)) {
                node_ep++;
            }
            if (gcoups.getTypeDeCoups().equals(Prise)) {
                node_prise++;
            }
            if (gcoups.getTypeDeCoups().equals(Promotion)) {
                node_promotion++;
            }
            nodes += miniMax(gp, depth - 1);
            gp.unexec(ui);
        }
        return nodes;
    }

}
