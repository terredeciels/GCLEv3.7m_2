package perft.external;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import position.FenToGPosition;
import position.GPosition;
import position.ICodage;

public class PerftTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testInitPos() {
        GPosition position = FenToGPosition.toGPosition(ICodage.fen_initiale);
        PerftResult p = Perft.perft(position, 1);
        assertEquals(20, p.moveCount);
        PerftResult p2 = Perft.perft(position, 2);
        assertEquals(400, p2.moveCount);
        PerftResult p3 = Perft.perft(position, 3);
        assertEquals(8902, p3.moveCount);
        // PerftResult p4 = Perft.perft(position, 4);
        // assertEquals(197281, p4.moveCount);
        // PerftResult p5 = Perft.perft(position, 5);
        // assertEquals(4865609, p5.moveCount);
    }

//    @Test
//    public void testKiwiPete() {
//        GPosition position = FenToGPosition.toGPosition("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -");
//        PerftResult p = Perft.perft(position, 1);
//        assertEquals(48, p.moveCount);
//        PerftResult p2 = Perft.perft(position, 2);
//        assertEquals(2039, p2.moveCount);
//        PerftResult p3 = Perft.perft(position, 3);
//        assertEquals(97862, p3.moveCount);
//        // PerftResult p4 = Perft.perft(position, 4);
//        // assertEquals(4085603, p4.moveCount);
//    }
    @Test
    public void testPerft() {
//        GPosition position = FenToGPosition.toGPosition("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -");
//        PerftResult p = Perft.perft(position, 1);
//        assertEquals(14, p.moveCount);
        GPosition position = FenToGPosition.toGPosition("rnbqkbnr/pp2pppp/2p5/3pP3/8/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1");
        PerftResult p = Perft.perft(position, 1);
        assertEquals(30, p.moveCount);

        position = FenToGPosition.toGPosition("4k3/8/8/2pP4/8/8/8/4K3 w - c6 0 2");
        p = Perft.perft(position, 1);
        assertEquals(7, p.moveCount);
    }

    @Test
    public void testWeirdPosition() {

        GPosition position = FenToGPosition.toGPosition("4k3/2p5/8/3P4/8/8/8/4K3 b - - 0 1");
        Map<String, Long> divide1 = Perft.divide(position, 1);
        Map<String, Long> divide2 = Perft.divide(position, 2);
        assertEquals(divide1.size(), divide2.size());
    }

    @Test
    public void testDivide() {
        // Position position = Position.fromFEN("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R b KQkq -");
        // Position position = Position.fromFEN("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R4RK1 b kq - 1 1");
        // Position position = Position.fromFEN("r3k2r/p1ppqpb1/bn2pnp1/1B1PN3/1p2P3/P1N2Q2/1PPB1PpP/R3K2R b KQkq - 1 1");
        // Position position = Position.fromFEN("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -");
        GPosition position = FenToGPosition.toGPosition("r3k2N/p1ppq1b1/bn2pnp1/3P4/4P3/1pN2Q1p/PPPBBPPP/R3K2R b KQq - 0 2");
        Map<String, Long> divide = Perft.divide(position, 1);
        long nodes = 0L;
        for (String key : divide.keySet()) {
            Long moveCount = divide.get(key);
            System.out.println(key + " " + moveCount);
            nodes += moveCount;
        }
        System.out.println("Moves: " + divide.size());
        System.out.println("Nodes: " + nodes);
    }

    @Test
    public void testPerft8() {
        GPosition position = FenToGPosition.toGPosition("r3k2r/8/8/8/8/8/8/4K3 w kq - 0 1");
        PerftResult p = Perft.perft(position, 1);
        assertEquals(5, p.moveCount);

        p = Perft.perft(position, 2);
        assertEquals(130, p.moveCount);

        p = Perft.perft(position, 3);
        assertEquals(782, p.moveCount);

        p = Perft.perft(position, 4);
        assertEquals(22180, p.moveCount);

        /*p = Perft.perft(position, 5);
		assertEquals(118882, p.moveCount);
		
		p = Perft.perft(position, 6);
		assertEquals(3517770, p.moveCount);*/
    }

//    @Test
//    public void testDivideAfterMove() {
//        GPosition position = FenToGPosition.toGPosition("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1");
//        position.move(create(4, 6));
//        // position.move(MoveUtils.create(40, 49));
//        Map<String, Long> divide = Perft.divide(position, 2);
//        long nodes = 0L;
//        for (String key : divide.keySet()) {
//            Long moveCount = divide.get(key);
//            System.out.println(key + " " + moveCount);
//            nodes += moveCount;
//        }
//        System.out.println("Moves: " + divide.size());
//        System.out.println("Nodes: " + nodes);
//
//    }
}
