package perft;

import chess.ChessParseError;
import chess.Move;
import chess.MoveGen;
import chess.Position;
import chess.TextIO;
import chess.UndoInfo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PerftCuckooChessTest {

    public PerftCuckooChessTest() {
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
    public void preftCuckooChess() throws ChessParseError {

        String fen = TextIO.startPosFEN;
        fen = "r3k2r/8/8/8/8/8/8/4K3 w kq - 0 1";
        Position pos = TextIO.readFEN(fen);

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

    private long miniMax(Position pos, int depth) {
        long nodes = 0;
        if (depth == 0) {
            return 1;
        }
        MoveGen.MoveList moves = new MoveGen().pseudoLegalMoves(pos);
        MoveGen.removeIllegal(pos, moves);
//        System.out.println(pos);

        for (int k = 0; k < moves.size; k++) {
            Move move = moves.m[k];
            UndoInfo ui = new UndoInfo();
            pos.makeMove(moves.m[k], ui);
            nodes += miniMax(pos, depth - 1);
            pos.unMakeMove(move, ui);
        }
        return nodes;
    }

}
