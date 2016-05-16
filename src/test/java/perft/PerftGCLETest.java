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
import position.UndoGCoups;

public class PerftGCLETest {

    private int node_roque;
    private int node_ep;
    private int node_prise;
    private int node_promotion;
    private int e1_f1;

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
        //voir http://chessprogramming.wikispaces.com/Perft+Results
//        String f = ICodage.fen_initiale;
//        f = "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1";
//        f = "8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - - 0 1";
//        f = "r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq - 0 1";
        String f = "r3k2r/8/8/8/8/8/8/4K3 w kq - 0 1";
//        f = "1r2k2r/8/8/8/8/8/8/R3K2R w KQk - 0 1";

        GPosition gp = FenToGPosition.toGPosition(f);
        System.out.println(gp.getTrait());
        System.out.println(gp.isDroitPetitRoqueBlanc());
        System.out.println(gp.isDroitGrandRoqueBlanc());
        System.out.println(gp.isDroitPetitRoqueNoir());
        System.out.println(gp.isDroitGrandRoqueNoir());
        long miniMax = miniMax(gp, 1);
//        (!coupsvalides.isEmpty())
        System.out.println("depth 1: " + miniMax);//
        System.out.println("depth 2 roque: " + node_roque);//
        System.out.println("depth 2 ep: " + node_ep);// 
        System.out.println("depth 2 prise: " + node_prise);//
        System.out.println("depth 2 promotion: " + node_promotion);//
        System.out.println();
        miniMax = miniMax(gp, 2);
        System.out.println("depth 2: " + miniMax);//
        System.out.println("depth 2 roque: " + node_roque);//
        System.out.println("depth 2 ep: " + node_ep);// 
        System.out.println("depth 2 prise: " + node_prise);// 
        System.out.println("depth 2 promotion: " + node_promotion);//
        System.out.println();
        miniMax = miniMax(gp, 3);
        System.out.println("depth 3: " + miniMax);//
        System.out.println("depth 3 roque: " + node_roque); //
        System.out.println("depth 3 ep: " + node_ep);// 
        System.out.println("depth 3 prise: " + node_prise);
        System.out.println("depth 3 promotion: " + node_promotion);//
        System.out.println();
        miniMax = miniMax(gp, 4);
        System.out.println("depth 4: " + miniMax);//
//        System.out.println("depth 4 roque: " + node_roque);//
//        System.out.println("depth 4 ep: " + node_ep);//
//        System.out.println("depth 4 prise: " + node_prise);//
//        System.out.println("depth 4 promotion: " + node_promotion);//
        System.out.println(e1_f1);

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
            if (gp.exec(gcoups, ui)) {
//                if(GCoups.getString(gcoups).equals("O-O")){
//                    e1_f1 ++;
//                }
//                if (gcoups.getTypeDeCoups().equals(Roque)) {
//                    node_roque++;
//                }
//                if (gcoups.getTypeDeCoups().equals(EnPassant)) {
//                    node_ep++;
//                }
//                if (gcoups.getTypeDeCoups().equals(Prise)) {
//                    node_prise++;
//                }
//                if (gcoups.getTypeDeCoups().equals(Promotion)) {
//                    node_promotion++;
//                }
                nodes += miniMax(gp, depth - 1);
                gp.unexec(ui);
            }
        }
        return nodes;
    }

}
