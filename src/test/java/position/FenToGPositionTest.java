package position;

import ia.IAleatoire;
import java.util.ArrayList;
import org.chesspresso.move.IllegalMoveException;
import org.chesspresso.move.Move;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static position.ICodage.BLANC;
import static org.junit.Assert.assertEquals;

public class FenToGPositionTest {

    public FenToGPositionTest() {
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
    public void fenToGPosition() throws IllegalMoveException {

//        String f = ICodage.fen_initiale;
//        f="4k2r/3b1p2/p3pq1p/1p5Q/P1ppB2p/2b5/1P3PPP/2KR3R b k - 1 18";
        String f = "r3k2r/8/8/8/8/8/8/4K3 w kq - 0 1";
        f="r3kr2/8/8/8/8/8/6K1/8 b q - 3 2";
        GPosition gp = FenToGPosition.toGPosition(f);
        ArrayList<GCoups> coupsvalides = gp.getCoupsValides();
        assertEquals(gp.getCoupsvalides_lan(), gp.cp_coupsvalides_lan);
        affichage(gp);

        GCoups gcoups = new IAleatoire().evaluate(coupsvalides);
        UndoGCoups ui = new UndoGCoups();
        gp.exec(gcoups,ui);
        gp.getCoupsValides();

        short[] allMoves = gp.position.getAllMoves();
        short move = conv(allMoves, gcoups);
        gp.position.doMove(move);
        gp.cp_coupsvalides_lan = FenToGPosition.getStringCPCoupsValides(gp.position.getAllMoves());
        gp.fen = gp.position.getFEN();
        assertEquals(gp.getCoupsvalides_lan(), gp.cp_coupsvalides_lan);
//        assertEquals(gp.etats,conv(gp.position.))
        affichage(gp);

    }

    private void affichage(GPosition gp) {
        System.out.println(gp.fen);
        System.out.println(gp.trait == BLANC ? "trait aux Blancs" : "trait aux Noirs");
        System.out.println(gp.print());
        System.out.println(gp);
        System.out.println();
    }

    public short conv(short[] allMoves, GCoups gcoups) {
        String gcoupsS = GCoups.getString(gcoups);
        short ccoups = 0;
        for (short m : allMoves) {
            String mS = Move.getString(m);
            if (mS.equals(gcoupsS)) {
                ccoups = m;
                break;
            }
        }
        assert (ccoups != 0);
        assert (gcoupsS.equals(Move.getString(ccoups)));
        return ccoups;

    }
}
