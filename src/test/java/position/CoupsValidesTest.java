package position;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import org.chesspresso.move.IllegalMoveException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pgntofen.Pgn;
import static position.ICodage.BLANC;
import static org.junit.Assert.assertEquals;

public class CoupsValidesTest {

    private GPosition gp;

    public CoupsValidesTest() {
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
//         File fichier = new File("D:\\Documents\\CHESS\\MAVEN\\FINAL\\GCLEv3.7m_2\\FenListTest.ser");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichier));
        Pgn m = (Pgn) ois.readObject();
        ArrayList<String> fenList = m.getFenListTest();
        System.out.println(fenList.size());

        for (String f : fenList) {
            gp = FenToGPosition.toGPosition(f);

            if (!gp.getCoupsValides().isEmpty()) {
                assertEquals(gp.getCoupsvalides_lan(), gp.cp_coupsvalides_lan);
                if (!gp.getCoupsvalides_lan().equals(gp.cp_coupsvalides_lan)) {
                    affichage(f);
                }
            }
        }
    }

    private void affichage(String fen) {
        System.out.println(fen);
        System.out.println(gp.trait == BLANC ? "trait aux Blancs" : "trait aux Noirs");
        if (gp.cp_coupsvalides_lan.size() > gp.getCoupsvalides_lan().size()) {
            gp.cp_coupsvalides_lan.removeAll(gp.getCoupsvalides_lan());
            System.out.println("cp_coups en plus :" + gp.cp_coupsvalides_lan);
        } else {
            gp.getCoupsvalides_lan().removeAll(gp.cp_coupsvalides_lan);
            System.out.println("g_coups en plus:" + gp.getCoupsvalides_lan());
        }
        System.out.println();
    }

}
