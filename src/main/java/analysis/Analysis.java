package analysis;

import java.util.ArrayList;
import position.FenToGPosition;
import position.GCoups;
import position.GPosition;
import position.ICodage;

public class Analysis implements ICodage {

    private final GPosition gp;
    private String message;

    public Analysis(String f) {
        gp = FenToGPosition.toGPosition(f);
    }

    public void start() {
        ArrayList<GCoups> coupsvalides = gp.getCoupsValides();
        message = toString(gp);
    }

    private String toString(GPosition gp) {
        String traitS = gp.getTrait() == BLANC ? "trait aux Blancs" : "trait aux Noirs";
        String str = gp.getFen() + '\n' + traitS + '\n' + gp.toString() + '\n';

        System.out.println(gp.getFen());
        System.out.println(gp.getTrait() == BLANC ? "trait aux Blancs" : "trait aux Noirs");
        System.out.println(gp.print());
        System.out.println(gp);
        System.out.println();

        return str;
    }

    public String getMessage() {
        return message;
    }

}
