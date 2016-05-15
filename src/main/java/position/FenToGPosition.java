package position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.collections.iterators.ArrayIterator;
import org.chesspresso.Chess;
import org.chesspresso.move.Move;
import org.chesspresso.position.Position;

public class FenToGPosition implements ICodage {

    public static GPosition toGPosition(String fen) {
        return toGPosition(new Position(fen));
    }

    private static GPosition toGPosition(Position position) {

        GPosition gp = new GPosition();
        gp.position = position;

        gp.fen = position.getFEN();
        gp.cp_coupsvalides_lan = getStringCPCoupsValides(position.getAllMoves());

        int[] cp_etats = new int[NB_CASES];
        for (final int caseO : CASES64) {
            cp_etats[caseO] = position.getStone(caseO);  // !! getStone et non getPiece
        }
        int[] etats = new int[NB_CELLULES];
        for (int caseO = 0; caseO < NB_CELLULES; caseO++) {
            etats[caseO] = OUT;
        }
        ArrayIterator itetats = new ArrayIterator(cp_etats);
        int indice = 0;
        while (itetats.hasNext()) {
            Integer e = (Integer) itetats.next();

            etats[CASES117[indice]] = e;
            indice++;
        }
        gp.etats = etats;

        gp.trait = position.getToPlay() == Chess.WHITE ? BLANC : NOIR;

        // roques - cf note_tad.txt
        int roques = position.getCastles();
        gp.droitPetitRoqueNoir = (8 & roques) == 8;
        gp.droitGrandRoqueNoir = (4 & roques) == 4;
        gp.droitPetitRoqueBlanc = (2 & roques) == 2;
        gp.droitGrandRoqueBlanc = (1 & roques) == 1;

        gp.caseEP = position.getSqiEP() == PAS_DE_CASE ? -1 : CASES117[position.getSqiEP()];

        return gp;
    }

    public static ArrayList<String> getStringCPCoupsValides(short[] cp_coupsvalides) {
        ArrayList<String> cp_coupsvalides_lan = new ArrayList<>();
        for (short m : cp_coupsvalides) {
            cp_coupsvalides_lan.add(Move.getString(m));
        }
        Collections.sort(cp_coupsvalides_lan);
        return cp_coupsvalides_lan;
    }

}
