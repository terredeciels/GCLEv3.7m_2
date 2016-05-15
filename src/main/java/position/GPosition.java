package position;

import java.util.ArrayList;
import java.util.Collections;
import main.Main;
import org.chesspresso.position.Position;
import static position.ICodage.*;

public class GPosition implements ICodage {

    Position position;
    String fen;
    int trait;
    boolean droitPetitRoqueNoir;
    boolean droitGrandRoqueNoir;
    boolean droitPetitRoqueBlanc;
    boolean droitGrandRoqueBlanc;
    int caseEP;
    int[] etats;
    ArrayList<String> cp_coupsvalides_lan;

    ArrayList<GCoups> coupsvalides;
    private ArrayList<String> coupsvalides_lan;

    private UndoGCoups ui;

    public GPosition() {
        etats = new int[NB_CELLULES];
    }

    public ArrayList<GCoups> getCoupsValides() {
        coupsvalides = new Generateur(this).getCoups();
//        assert (!coupsvalides.isEmpty());
        coupsvalides_lan = new ArrayList<>();
        for (GCoups c : coupsvalides) {
            coupsvalides_lan.add(GCoups.getString(c));
        }
        Collections.sort(coupsvalides_lan);
        return coupsvalides;
    }

    public void exec(GCoups gcoups) {
        gposition_part_copy();

        int caseO = gcoups.getCaseO();
        int caseX = gcoups.getCaseX();
        caseEP = -1;
        if (gcoups.getPiece() == PION && Math.abs(caseX - caseO) == 24) {// avance de 2 cases
            caseEP = trait == NOIR ? caseX + 12 : caseX - 12;
        }
        if (gcoups.getTypeDeCoups() != null) {
            switch (gcoups.getTypeDeCoups()) {
                case Deplacement:
                    etats[caseX] = etats[caseO];
                    etats[caseO] = VIDE;
                    //piece deplacee = tour ou roi
                    if (trait == BLANC) {
                        if (gcoups.getPiece() == ROI || gcoups.getPiece() == TOUR) {
                            droitPetitRoqueBlanc = false;
                            droitGrandRoqueBlanc = false;
                        }
                    } else if (trait == NOIR) {
                        if (gcoups.getPiece() == ROI || gcoups.getPiece() == TOUR) {
                            droitPetitRoqueNoir = false;
                            droitGrandRoqueNoir = false;
                        }
                    }
                    break;
                case Prise:
                    etats[caseX] = etats[caseO];
                    etats[caseO] = VIDE;
                    //piece prise = tour
                    if (trait == BLANC) {
                        if (caseX == a1) {
                            droitGrandRoqueBlanc = false;
                        } else if (caseX == h1) {
                            droitPetitRoqueBlanc = false;
                        }
                    } else if (trait == NOIR) {
                        if (caseX == a8) {
                            droitGrandRoqueNoir = false;
                        } else if (caseX == h8) {
                            droitPetitRoqueNoir = false;
                        }
                    }   //plus de tour en a1, h1 ...
                    if (etats[h1] != -TOUR) {
                        droitPetitRoqueBlanc = false;
                    }
                    if (etats[a1] != -TOUR) {
                        droitGrandRoqueBlanc = false;
                    }
                    if (etats[h8] != TOUR) {
                        droitPetitRoqueNoir = false;
                    }
                    if (etats[a8] != TOUR) {
                        droitGrandRoqueNoir = false;
                    }
                    break;
                case EnPassant:
                    // caseX == caseEP
                    etats[caseX] = etats[caseO];
                    etats[caseO] = VIDE;
                    if (trait == BLANC) {
                        etats[caseX + sud] = VIDE;
                    } else if (trait == NOIR) {
                        etats[caseX + nord] = VIDE;
                    }
                    break;
                case Promotion:
                    etats[caseX] = gcoups.getPiecePromotion();
                    etats[caseO] = VIDE;
                    break;
                case Roque:
                    // System.out.println("//@TODO roques ?");
                    etats[caseX] = etats[caseO];//ROI
                    etats[caseO] = VIDE;
                    etats[gcoups.getCaseXTour()] = etats[gcoups.getCaseOTour()];//TOUR
                    etats[gcoups.getCaseOTour()] = VIDE;
                    if (trait == BLANC) {
                        droitPetitRoqueBlanc = false;
                        droitGrandRoqueBlanc = false;
                    } else {
                        droitPetitRoqueNoir = false;
                        droitGrandRoqueNoir = false;
                    }
                    break;
                default:
                    break;
            }
        }

        //
//        validerRoques();
        //
        trait = -trait;
    }

    private void gposition_part_copy() {
        ui = new UndoGCoups();
        System.arraycopy(etats, 0, ui.etats, 0, NB_CELLULES);
        ui.droitPetitRoqueNoir = droitPetitRoqueNoir;
        ui.droitGrandRoqueNoir = droitGrandRoqueNoir;
        ui.droitPetitRoqueBlanc = droitPetitRoqueBlanc;
        ui.droitGrandRoqueBlanc = droitGrandRoqueBlanc;
        ui.caseEP = caseEP;
    }

    public void unexec() {
        System.arraycopy(ui.etats, 0, etats, 0, NB_CELLULES);
        droitPetitRoqueNoir = ui.droitPetitRoqueNoir;
        droitGrandRoqueNoir = ui.droitGrandRoqueNoir;
        droitPetitRoqueBlanc = ui.droitPetitRoqueBlanc;
        droitGrandRoqueBlanc = ui.droitGrandRoqueBlanc;
        caseEP = ui.caseEP;
        trait = -trait;
    }

    public ArrayList<String> getCoupsvalides_lan() {
        return coupsvalides_lan;
    }

    public String print() {
        String str = "";
        String e_str;
        String Clr_str;
        int rg = 0;
        for (int e : etats) {
            int piecetype = Math.abs(e);
            Clr_str = piecetype == e ? "N" : "B";
            switch (piecetype) {
                case PION:
                    e_str = "P";
                    break;
                case ROI:
                    e_str = "K";
                    break;
                case VIDE:
                    e_str = " ";
                    break;
                case OUT:
                    e_str = "X";
                    break;
                default:
                    e_str = STRING_PIECE[piecetype];
                    break;
            }
            Clr_str = "X".equals(e_str) ? "X" : Clr_str;
            Clr_str = " ".equals(e_str) ? "_" : Clr_str;
            str += e_str + Clr_str + " ";
            rg++;
            if (rg == 12) {
                str += '\n';
                rg = 0;
            }
        }
        return str;
    }

    public int[] getEtats() {
        return etats;
    }

    public String getFen() {
        return fen;
    }

    public int getTrait() {
        return trait;
    }

    public void setTrait(int trait) {
        this.trait = trait;
    }

    @Override
    public String toString() {

//        return Main.DEBUG ? "CP_CoupsValides : " + '\n'
//                + cp_coupsvalides_lan + '\n'
//                + "G_CoupsValides : " + '\n'
//                + coupsvalides_lan
//                : "G_CoupsValides : " + '\n' + coupsvalides_lan;
        return coupsvalides_lan.toString();
    }

    private static class UndoGCoups {

        private boolean droitPetitRoqueNoir;
        private int[] etats;
        private boolean droitGrandRoqueNoir;
        private boolean droitPetitRoqueBlanc;
        private boolean droitGrandRoqueBlanc;
        private int caseEP;

        public UndoGCoups() {
            etats = new int[NB_CELLULES];
        }

    }
//
//    private void validerRoques() {
//        int couleur = trait;
//        // attention: -couleur
//        PGenerateur pGen = new PGenerateur(this, -couleur);
//        pGen.getPseudoCoupsAttaque();
//        ArrayList<GCoups> coupsAttaque = pGen.getCoupsValides();
//        boolean possible;
//        if (couleur == BLANC) {
//            possible = ((etats[f1] == VIDE)
//                    && (etats[h1] == -TOUR)
//                    && (etats[g1] == VIDE));
//            possible &= !(attaqueRoque(e1, f1, g1, coupsAttaque));
//            droitPetitRoqueBlanc = possible & droitPetitRoqueBlanc;
//
//            possible = ((etats[d1] == VIDE)
//                    && (etats[a1] == -TOUR)
//                    && (etats[c1] == VIDE)
//                    && (etats[b1] == VIDE));
//            possible &= !(attaqueRoque(e1, d1, c1, coupsAttaque));
//            droitGrandRoqueBlanc = possible & droitGrandRoqueBlanc;
//        } else {
//            possible = ((etats[f8] == VIDE)
//                    && (etats[h8] == TOUR)
//                    && (etats[g8] == VIDE));
//            possible &= !(attaqueRoque(e8, f8, g8, coupsAttaque));
//            droitPetitRoqueNoir = possible & droitPetitRoqueNoir;
//            possible = ((etats[d8] == VIDE)
//                    && (etats[a8] == TOUR)
//                    && (etats[c8] == VIDE)
//                    && (etats[b8] == VIDE));
//            possible &= !(attaqueRoque(e8, d8, c8, coupsAttaque));
//            droitGrandRoqueNoir = possible & droitGrandRoqueNoir;
//        }
//    }
//
//    private boolean attaqueRoque(int E1ouE8, int F1ouF8, int G1ouG8, ArrayList<GCoups> coupsAttaque) {
//        boolean attaque = false;
//        int caseX;
//        for (GCoups coups : coupsAttaque) {
//            caseX = coups.getCaseX();
//            if ((caseX == E1ouE8) || (caseX == F1ouF8) || (caseX == G1ouG8)) {
//                attaque = true;
//
//                break;
//            }
//        }
//        return attaque;
//    }

}
