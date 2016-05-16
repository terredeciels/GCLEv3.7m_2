package perft.external;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import position.GCoups;
import position.GPosition;
import position.ICodage.TYPE_DE_COUPS;
import position.UndoGCoups;

public class Perft {

    public static void main(String[] args) {

    }

    public static PerftResult perft(GPosition gp, int depth) {

        PerftResult result = new PerftResult();
        if (depth == 0) {
            result.moveCount++;
            return result;
        }
        ArrayList<GCoups> moves = gp.getCoupsValides();
        for (int i = 0; i < moves.size(); i++) {
            UndoGCoups ui = new UndoGCoups();
            if (gp.exec(moves.get(i), ui)) {
                PerftResult subPerft = perft(gp, depth - 1);
                gp.unexec(ui);
                result.moveCount += subPerft.moveCount;
            }
        }
        return result;
    }

    public static Map<String, Long> divide(GPosition gp, int depth) {
        HashMap<String, Long> result = new HashMap<>();
        ArrayList<GCoups> moves = gp.getCoupsValides();
        for (int i = 0; i < moves.size(); i++) {
            UndoGCoups ui = new UndoGCoups();
            if (gp.exec(moves.get(i), ui)) {
                PerftResult subPerft = perft(gp, depth - 1);
                gp.unexec(ui);
                result.put(toString(moves.get(i)), subPerft.moveCount);
            }
        }
        return result;
    }

    public static String toString(GCoups gc) {
        int fromSquare = gc.getCaseO();
        int toSquare = gc.getCaseX();
        int promotedPiece = gc.getPiecePromotion();
//		boolean isQueening = MoveUtils.isQueening(move);
        boolean isEnPassentCapture = gc.getTypeDeCoups() == TYPE_DE_COUPS.EnPassant;
        StringBuilder result = new StringBuilder();
        result.append(GCoups.getString(gc));
//		if (isQueening && (promotedPiece == 0)) {
//			// The move is just a generated queening move without a specific piece
//			throw new NotationException("Unable to determine promoted piece");
//		}
//		if (isQueening) {
//			result.append(fromPiece(promotedPiece));
//		}
        return result.toString();
    }
}
