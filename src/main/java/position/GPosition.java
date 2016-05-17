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

    public boolean exec(GCoups gcoups, UndoGCoups ui) {
        System.arraycopy(etats, 0, ui.etats, 0, NB_CELLULES);
        ui.droitPetitRoqueNoir = droitPetitRoqueNoir;
        ui.droitGrandRoqueNoir = droitGrandRoqueNoir;
        ui.droitPetitRoqueBlanc = droitPetitRoqueBlanc;
        ui.droitGrandRoqueBlanc = droitGrandRoqueBlanc;
        ui.caseEP = caseEP;

        int caseO = gcoups.getCaseO();
        int caseX = gcoups.getCaseX();
        caseEP = -1;
        if (gcoups.getPiece() == PION && Math.abs(caseX - caseO) == 24) {// avance de 2 cases
            caseEP = trait == NOIR ? caseX + 12 : caseX - 12;
        }
//        if (gcoups.getTypeDeCoups() != null) {
        switch (gcoups.getTypeDeCoups()) {
            case Deplacement:
                etats[caseX] = etats[caseO];
                etats[caseO] = VIDE;
                //piece deplacee = tour ou roi

                if (trait == BLANC) {
                    if (gcoups.getPiece() == ROI) {
                        droitPetitRoqueBlanc = false;
                        droitGrandRoqueBlanc = false;
                    } else if (gcoups.getPiece() == TOUR && caseO == h1) {
                        droitPetitRoqueBlanc = false;
                    } else if (gcoups.getPiece() == TOUR && caseO == a1) {
                        droitGrandRoqueBlanc = false;
                    }
                } else if (trait == NOIR) {
                    if (gcoups.getPiece() == ROI) {
                        droitPetitRoqueNoir = false;
                        droitGrandRoqueNoir = false;
                    } else if (gcoups.getPiece() == TOUR && caseO == h8) {
                        droitPetitRoqueNoir = false;
                    } else if (gcoups.getPiece() == TOUR && caseO == a8) {
                        droitGrandRoqueNoir = false;
                    }
                }
                //plus de roi ou tour
                if (trait == BLANC) {
                    if (etats[a1] != -TOUR || etats[e1] != -ROI) {
                        droitGrandRoqueBlanc = false;
                    }
                    if (etats[h1] != -TOUR || etats[e1] != -ROI) {
                        droitPetitRoqueBlanc = false;
                    }
                } else if (trait == NOIR) {
                    if (etats[a8] != TOUR || etats[e8] != ROI) {
                        droitGrandRoqueNoir = false;
                    }
                    if (etats[h8] != TOUR || etats[e8] != ROI) {
                        droitPetitRoqueNoir = false;
                    }
                }
                break;
            case Prise:
                etats[caseX] = etats[caseO];
                etats[caseO] = VIDE;
                //piece prise = tour
                if (trait == BLANC) {
                    if (gcoups.getPiece() == ROI) {
                        droitPetitRoqueBlanc = false;
                        droitGrandRoqueBlanc = false;
                    } else if (gcoups.getPiece() == TOUR && caseO == h1) {
                        droitPetitRoqueBlanc = false;
                    } else if (gcoups.getPiece() == TOUR && caseO == a1) {
                        droitGrandRoqueBlanc = false;
                    }
                } else if (trait == NOIR) {
                    if (gcoups.getPiece() == ROI) {
                        droitPetitRoqueNoir = false;
                        droitGrandRoqueNoir = false;
                    } else if (gcoups.getPiece() == TOUR && caseO == h8) {
                        droitPetitRoqueNoir = false;
                    } else if (gcoups.getPiece() == TOUR && caseO == a8) {
                        droitGrandRoqueNoir = false;
                    }
                }
                if (trait == BLANC) {
                    if (etats[a1] != -TOUR || etats[e1] != -ROI) {
                        droitGrandRoqueBlanc = false;
                    }
                    if (etats[h1] != -TOUR || etats[e1] != -ROI) {
                        droitPetitRoqueBlanc = false;
                    }
                } else if (trait == NOIR) {
                    if (etats[a8] != TOUR || etats[e8] != ROI) {
                        droitGrandRoqueNoir = false;
                    }
                    if (etats[h8] != TOUR || etats[e8] != ROI) {
                        droitPetitRoqueNoir = false;
                    }
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
//        }

        trait = -trait;

        return true;
    }

    public void unexec(UndoGCoups ui) {
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

    @Override
    public String toString() {
        return Main.DEBUG ? "CP_CoupsValides : " + '\n'
                + cp_coupsvalides_lan + '\n'
                + "G_CoupsValides : " + '\n'
                + coupsvalides_lan
                : "G_CoupsValides : " + '\n' + coupsvalides_lan;
//        return coupsvalides_lan.toString();
    }

    public boolean isDroitPetitRoqueNoir() {
        return droitPetitRoqueNoir;
    }

    public boolean isDroitGrandRoqueNoir() {
        return droitGrandRoqueNoir;
    }

    public boolean isDroitPetitRoqueBlanc() {
        return droitPetitRoqueBlanc;
    }

    public boolean isDroitGrandRoqueBlanc() {
        return droitGrandRoqueBlanc;
    }

    public Position getPosition() {
        return position;
    }

    public ArrayList<String> getCp_coupsvalides_lan() {
        return cp_coupsvalides_lan;
    }

}
