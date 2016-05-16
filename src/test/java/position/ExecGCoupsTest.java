package position;

import ia.IAleatoire;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import org.chesspresso.move.IllegalMoveException;
import org.chesspresso.move.Move;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pgntofen.Pgn;
import static position.ICodage.BLANC;

public class ExecGCoupsTest {

    private GCoups gcoups;
    private GPosition gp;
    private String fenI;

    public ExecGCoupsTest() {
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
    public void fenToGPosition() throws IllegalMoveException, IOException, ClassNotFoundException {

        File fichier = new File("D:\\Documents\\CHESS\\MAVEN\\FINAL\\GCLEv3.7m_2\\FenListTest_reduit.ser");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichier));
        Pgn m = (Pgn) ois.readObject();
        ArrayList<String> fenList = m.getFenListTest();

//         ArrayList<String> fenList =new ArrayList<>();
//        System.out.println(fenList.size());
//        String fen = "r3k2r/8/8/8/8/8/8/4K3 w kq - 0 1";
//        fenList.add(fen);
        for (String f : fenList) {
            fenI = f;
            gp = FenToGPosition.toGPosition(f);
            ArrayList<GCoups> coupsvalides = gp.getCoupsValides();
            if (!coupsvalides.isEmpty()) {

                if (!gp.getCoupsvalides_lan().equals(gp.cp_coupsvalides_lan)) {
                    affichage("before move");
                }

                short[] allMoves = gp.position.getAllMoves();
                    //                short move = allMoves[0];
                    // PRODUIRE ALEATOIREMENT
                short move = new IAleatoire().evaluate(allMoves);
                gp.position.doMove(move);

                gcoups = toGCoups(coupsvalides, move);
                if (gcoups != null) {
                    UndoGCoups ui = new UndoGCoups();
                    gp.exec(gcoups, ui);
                    gp.getCoupsValides();

                    gp.cp_coupsvalides_lan = FenToGPosition.getStringCPCoupsValides(gp.position.getAllMoves());
                    gp.fen = gp.position.getFEN();

                    if (!gp.getCoupsvalides_lan().equals(gp.cp_coupsvalides_lan)) {
                        affichage("after move");
                    }
                } else {
                    System.out.println("gcoups == null");
                }
            } else {
//                System.out.println("mat_ou_pat");
//                affichage();
            }
        }
    }

    private void affichage(String message) {

        if (gp.cp_coupsvalides_lan.size() > gp.getCoupsvalides_lan().size()) {
            gp.cp_coupsvalides_lan.removeAll(gp.getCoupsvalides_lan());
            System.out.println("CP_coups en plus :" + gp.cp_coupsvalides_lan);
            System.out.println(message);
            affichage();
            System.out.println();
        } else {
            gp.getCoupsvalides_lan().removeAll(gp.cp_coupsvalides_lan);
            System.out.println("G_coups en plus:" + gp.getCoupsvalides_lan());
            System.out.println(message);
            affichage();
            System.out.println();

        }

    }

    private void affichage() {

        System.out.println(fenI);
        System.out.println(gp.fen);
        if (gcoups != null) {
            String gcoupsS = GCoups.getString(gcoups);
            System.out.println(gcoupsS);
        } else {
            System.out.println("gcoups==null");
        }
        System.out.println(gp.trait == BLANC ? "trait aux Blancs" : "trait aux Noirs");
    }

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

//        assert (gc != null);
//        assert (moveS.equals(GCoups.getString(gc)));
        return gc;
    }
}
