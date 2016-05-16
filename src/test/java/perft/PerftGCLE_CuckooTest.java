package perft;

import chess.ChessParseError;
import chess.Move;
import chess.MoveGen;
import chess.Position;
import chess.TextIO;
import chess.UndoInfo;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import position.FenToGPosition;
import position.GCoups;
import position.GPosition;
import position.UndoGCoups;

public class PerftGCLE_CuckooTest {

    private int diff;
    private int diff_size;
    private String fen_last;

    public PerftGCLE_CuckooTest() {
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
    public void perftTest() throws ChessParseError {
        //voir http://chessprogramming.wikispaces.com/Perft+Results
//        String f = ICodage.fen_initiale;
        String f = "r3k2r/8/8/8/8/8/8/4K3 w kq - 0 1";

        GPosition gp = FenToGPosition.toGPosition(f);
        Position pos = TextIO.readFEN(f);
        long miniMax = miniMax(gp, pos, 4);
        System.out.println("depth 4: 22180");
        System.out.println("depth 4: " + miniMax);//22180
        System.out.println("diff 4: " + diff);
        System.out.println("diff size 4: " + diff_size);
//        System.out.println("miniMax + diff " + (miniMax + diff));
    }

    private long miniMax(GPosition gp, Position pos, int depth) {
        long nodes = 0;
        if (depth == 0) {
            return 1;
        }
        ArrayList<GCoups> coupsvalides = gp.getCoupsValides();
        ArrayList<String> coupsvalides_lan = getCoupsvalides_lanShort(coupsvalides);
        MoveGen.MoveList moves = new MoveGen().pseudoLegalMoves(pos);
        MoveGen.removeIllegal(pos, moves);
        ArrayList<String> moves_lan = getCoupsvalides_lanShort(moves);
        ArrayList<String> moves_lanC = (ArrayList<String>) moves_lan.clone();
        ArrayList<String> coupsvalides_lanC = (ArrayList<String>) coupsvalides_lan.clone();
        int size = coupsvalides_lan.size();
        int size1 = moves_lan.size();
        boolean suppr = false;
        if (size != size1) {
            diff_size++;
            if (size > size1) {
                coupsvalides_lanC.removeAll(moves_lanC);
                suppr = coupsvalides_lanC.isEmpty()||coupsvalides_lanC.contains("e8c8")||coupsvalides_lanC.contains("e8g8");
            } else {
                moves_lanC.removeAll(coupsvalides_lanC);
                suppr = moves_lanC.isEmpty()||moves_lanC.contains("e8c8")||moves_lanC.contains("e8g8");
            }
        }
        String fen_curr = TextIO.toFEN(pos);
        if (!suppr) {
            if (!coupsvalides_lan.equals(moves_lan)) {

                diff++;
                System.out.println(fen_curr);
                System.out.println("cuckoo " + size1 + " gcle " + size);
                System.out.println("cuckoo: " + moves_lan);
                System.out.println("gcle    : " + coupsvalides_lan);
                if (size > size1) {
                    System.out.println(coupsvalides_lanC);
                } else {
                    System.out.println(moves_lanC);
                }
                System.out.println();
            }
        }

        for (int i = 0; i < coupsvalides.size(); i++) {
            Move move = moves.m[i];
            GCoups gcoups = toGCoups(coupsvalides, move);
            UndoGCoups uig = new UndoGCoups();
            gp.exec(gcoups, uig);
            UndoInfo ui = new UndoInfo();
            pos.makeMove(moves.m[i], ui);
            nodes += miniMax(gp, pos, depth - 1);
            pos.unMakeMove(move, ui);
//            fen_last = TextIO.toFEN(pos);
            gp.unexec(uig);
        }
        return nodes;
    }

    private GCoups toGCoups(ArrayList<GCoups> coupsvalides, Move move) {
        String moveS = move.toString();
        GCoups gc = null;
        for (GCoups g : coupsvalides) {
            String gS = GCoups.getStringShort(g);
            if (gS.equals(moveS)) {
                gc = g;
                break;
            }
        }
        if (gc == null) {
            System.out.println(moveS);
        }
        assert (gc != null);
        assert (moveS.equals(GCoups.getStringShort(gc)));
        return gc;
    }

    private ArrayList<String> getCoupsvalides_lanShort(MoveGen.MoveList moves) {
        ArrayList<String> coupsvalides_lanShort = new ArrayList<>();
        for (int k = 0; k < moves.m.length; k++) {
            Move m = moves.m[k];
            String mS = m.toString();
            if (!mS.equals("a1a1")) {
                coupsvalides_lanShort.add(mS);
            }
        }
        Collections.sort(coupsvalides_lanShort);
        return coupsvalides_lanShort;
    }

    private ArrayList<String> getCoupsvalides_lanShort(ArrayList<GCoups> coupsvalides) {
        ArrayList<String> coupsvalides_lanShort = new ArrayList<>();
        for (GCoups c : coupsvalides) {
            coupsvalides_lanShort.add(GCoups.getStringShort(c));
        }
        Collections.sort(coupsvalides_lanShort);
        return coupsvalides_lanShort;
    }

}
