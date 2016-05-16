package perft;

import org.chesspresso.move.IllegalMoveException;
import org.chesspresso.position.Position;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import position.ICodage;

public class PerftChessPressoTest {

    public PerftChessPressoTest() {
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
    public void perftchesspresso() throws IllegalMoveException {

        String fen = ICodage.fen_initiale;
        fen = "r3k2r/8/8/8/8/8/8/4K3 w kq - 0 1";
        Position pos = new Position(fen);

        long miniMax = miniMax(pos, 1);
        System.out.println("depth 1: " + miniMax);// OK
//        assert (miniMax == 20);
        miniMax = miniMax(pos, 2);
        System.out.println("depth 2: " + miniMax);//OK
//        assert (miniMax == 400);
        miniMax = miniMax(pos, 3);
        System.out.println("depth 3: " + miniMax);//10874 aulieu de 8902 !
//        assert (miniMax == 8902);
        miniMax = miniMax(pos, 4);
        System.out.println("depth 4: " + miniMax);//10874 aulieu de 8902 !
//        assert (miniMax == 197281);
//        miniMax = miniMax(pos, 5);
//        System.out.println("depth 5: " + miniMax);//10874 aulieu de 8902 !
//        assert (miniMax == 4865609);
//        miniMax = miniMax(pos, 6);
//        System.out.println("depth 6: " + miniMax);//10874 aulieu de 8902 !
//        assert (miniMax == 119060324);
    }

    private long miniMax(Position pos, int depth) throws IllegalMoveException {
        long nodes = 0;
        if (depth == 0) {
            return 1;
        }

        short[] moves = pos.getAllMoves();

        for (int i = 0; i < moves.length; i++) {
            short move = moves[i];
            pos.doMove(move);
            nodes += miniMax(pos, depth - 1);
            pos.undoMove();
        }
        return nodes;
    }
}
