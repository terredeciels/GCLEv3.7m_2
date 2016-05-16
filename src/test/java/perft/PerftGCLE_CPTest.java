package perft;

import java.util.ArrayList;
import org.chesspresso.move.IllegalMoveException;
import org.chesspresso.move.Move;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import position.FenToGPosition;
import position.GCoups;
import position.GPosition;
import position.ICodage;
import position.UndoGCoups;

public class PerftGCLE_CPTest {

    private int diff;
    private int diff_size;

    private int nb_e1_f1;
    private int nb_e1_f1_gcoups;

    public PerftGCLE_CPTest() {
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
    public void perftTest() throws IllegalMoveException {
        //voir http://chessprogramming.wikispaces.com/Perft+Results
//        String f = ICodage.fen_initiale;
        String f = "r3k2r/8/8/8/8/8/8/4K3 w kq - 0 1";
        // ROCE
//e1f1 3285
//e1d1 3287
//e1e2 5122
//e1f2 5241
//e1d2 5245
//
//Moves: 5
//Nodes: 22180
        GPosition gp = FenToGPosition.toGPosition(f);
        long miniMax = miniMax(gp, 4);
        System.out.println("depth 4: " + miniMax);//22180
//        System.out.println("diff size : " + diff_size);//
        System.out.println("diff : " + diff);//
//        System.out.println("nb_e1_f1 : " + nb_e1_f1);//
//        System.out.println("nb_e1_f1_gcoups : " + nb_e1_f1_gcoups);//
        assertEquals(22180, miniMax + diff);
    }
//

    private long miniMax(GPosition gp, int depth) throws IllegalMoveException {
        long nodes = 0;

        if (depth == 0) {
            return 1;
        }
        ArrayList<GCoups> moves = gp.getCoupsValides();
        short[] cp_moves = gp.getPosition().getAllMoves();
        if (!gp.getCoupsvalides_lan().equals(gp.getCp_coupsvalides_lan())) {
            diff++;
//            System.out.println(gp.getPosition().getFEN());
//            System.out.println(gp);
//             System.out.println();
        }
        for (int i = 0; i < moves.size(); i++) {
            short move = cp_moves[i];
            GCoups gcoups = toGCoups(moves, move);
            UndoGCoups ui = new UndoGCoups();
            gp.getPosition().doMove(move);
            gp.exec(gcoups, ui);
            
            nodes += miniMax(gp, depth - 1);

            gp.unexec(ui);
            gp.getPosition().undoMove();

        }
        return nodes;
    }
//if (Move.getString(move).equals("e1-f1") || Move.getString(move).equals("e1xf1")) {
//                nb_e1_f1++;
//            }
//            if (GCoups.getString(gcoups).equals("e1-f1") || GCoups.getString(gcoups).equals("e1xf1")) {
//                nb_e1_f1_gcoups++;
//            }

    private GCoups toGCoups(ArrayList<GCoups> coupsvalides, short move) {
        String moveS = Move.getString(move);
        GCoups gc = null;
        for (GCoups g : coupsvalides) {
            String gS = GCoups.getString(g);
            if (gS.equals(moveS)) {
                gc = g;
                break;
            }
        }

        assert (gc != null);
        assert (moveS.equals(GCoups.getString(gc)));
        return gc;
    }
}
