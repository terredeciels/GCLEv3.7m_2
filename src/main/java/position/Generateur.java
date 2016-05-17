package position;

import java.util.ArrayList;

public class Generateur extends AbstractGenerateur {

    private static final long serialVersionUID = 1L;
    private boolean estEnEchec;

    public Generateur(GPosition g_position) {
        super(g_position);
        this.couleur = g_position.trait;
    }

    public final ArrayList<GCoups> getCoups() {
        PGenerateur pGenerateur = new PGenerateur(g_position, couleur);
        pGenerateur.pseudoCoups();
        ArrayList<GCoups> pCoups = pGenerateur.getCoupsValides();
        // roques
        ajouterRoques(pCoups);
        coupsEp(pCoups, couleur);
        // Echecs
        ArrayList<GCoups> aRetirer = suprimerEchecs(pCoups);
        pCoups.removeAll(aRetirer);
        return pCoups;
    }

    private ArrayList<GCoups> suprimerEchecs(ArrayList<GCoups> fCoups) {
        ArrayList<GCoups> aRetirer = new ArrayList<>();
        int caseRoiCouleur;
//        boolean estEnEchec;
        for (GCoups coups : fCoups) {
            ArrayList<GCoups> pseudoCoupsPosSimul;
            GPosition positionSimul = fPositionSimul(g_position, coups, couleur);
            caseRoiCouleur = fCaseRoi(positionSimul, couleur);
            PGenerateur pGen = new PGenerateur(positionSimul, -couleur);    // attention: -couleur
            pGen.pseudoCoups();
            pseudoCoupsPosSimul = pGen.getCoupsValides();
            estEnEchec = fEstEnEchec(pseudoCoupsPosSimul, caseRoiCouleur);
            if (estEnEchec) {
                aRetirer.add(coups);
            }
        }

        return aRetirer;
    }

    private GPosition fPositionSimul(GPosition position, GCoups coups, int couleur) {
//        GPosition positionSimul = position.copieEtats();
        GPosition positionSimul = new GPosition();
        System.arraycopy(etats, 0, positionSimul.etats, 0, NB_CELLULES);

        int caseO = coups.getCaseO();
        int caseX = coups.getCaseX();
        if (coups.getTypeDeCoups() == TYPE_DE_COUPS.Deplacement
                || coups.getTypeDeCoups() == TYPE_DE_COUPS.Prise) {
            positionSimul.etats[caseX] = positionSimul.etats[caseO];
            positionSimul.etats[caseO] = VIDE;
        } else if (coups.getTypeDeCoups() == TYPE_DE_COUPS.EnPassant) {
            // caseX == caseEP
            positionSimul.etats[caseX] = positionSimul.etats[caseO];
            positionSimul.etats[caseO] = VIDE;
            if (couleur == BLANC) {
                positionSimul.etats[caseX + sud] = VIDE;
            } else if (couleur == NOIR) {
                positionSimul.etats[caseX + nord] = VIDE;
            }
        } else if (coups.getTypeDeCoups() == TYPE_DE_COUPS.Promotion) {
            positionSimul.etats[caseX] = coups.getPiecePromotion();
            positionSimul.etats[caseO] = VIDE;
        }

        return positionSimul;
    }

    private int fCaseRoi(GPosition position, int couleur) {
        int[] pEtats = position.etats;
        int caseRoi = OUT;
        int etatO;
        int typeO;
        for (int caseO : CASES117) {
            etatO = pEtats[caseO];
            typeO = Math.abs(etatO);
            if ((typeO == ROI) && (etatO * couleur > 0)) {
                caseRoi = caseO;
                break;
            }
        }
        return caseRoi;
    }

    private boolean fEstEnEchec(ArrayList<GCoups> pseudoCoupsPosSimul, int caseRoi) {
        boolean isCheck = false;
        for (GCoups coups : pseudoCoupsPosSimul) {
            if (coups.getCaseX() == caseRoi) {
                isCheck = true;
                break;
            }
        }
        return isCheck;
    }

    private void ajouterRoques(ArrayList<GCoups> fCoups) {
        // attention: -couleur
        PGenerateur pGen = new PGenerateur(g_position, -couleur);
        pGen.getPseudoCoupsAttaque();
        ArrayList<GCoups> coupsAttaque = pGen.getCoupsValides();

        boolean possible;
        final GPosition pgPosition = pGen.getGPosition();
        final int[] pgEtats = pgPosition.etats;
        if (couleur == BLANC) {
            if (pgPosition.droitPetitRoqueBlanc) {
                possible = ((pgEtats[f1] == VIDE)
                        && (pgEtats[h1] == -TOUR)
                        && (pgEtats[e1] == -ROI)
                        && (pgEtats[g1] == VIDE));
                possible &= !(attaqueRoque(e1, f1, g1, coupsAttaque));

                if (possible) {
                    fCoups.add(new GCoups(ROI, e1, g1, h1, f1, 0, TYPE_DE_COUPS.Roque));
                }
            }
            if (pgPosition.droitGrandRoqueBlanc) {
                possible = ((pgEtats[d1] == VIDE)
                        && (pgEtats[a1] == -TOUR)
                        && (pgEtats[e1] == -ROI)
                        && (pgEtats[c1] == VIDE)
                        && (pgEtats[b1] == VIDE));
                possible &= !(attaqueRoque(e1, d1, c1, coupsAttaque));

                if (possible) {
                    fCoups.add(new GCoups(ROI, e1, c1, a1, d1, 0, TYPE_DE_COUPS.Roque));
                }
            }
        } else {
            if (pgPosition.droitPetitRoqueNoir) {
                possible = ((pgEtats[f8] == VIDE)
                        && (pgEtats[h8] == TOUR)
                        && (pgEtats[e8] == ROI)
                        && (pgEtats[g8] == VIDE));
                possible &= !(attaqueRoque(e8, f8, g8, coupsAttaque));

                if (possible) {
                    fCoups.add(new GCoups(ROI, e8, g8, h8, f8, 0, TYPE_DE_COUPS.Roque));
                }
            }
            if (pgPosition.droitGrandRoqueNoir) {
                possible = ((pgEtats[d8] == VIDE)
                        && (pgEtats[a8] == TOUR)
                        && (pgEtats[e8] == ROI)
                        && (pgEtats[c8] == VIDE)
                        && (pgEtats[b8] == VIDE));
                possible &= !(attaqueRoque(e8, d8, c8, coupsAttaque));

                if (possible) {
                    fCoups.add(new GCoups(ROI, e8, c8, a8, d8, 0, TYPE_DE_COUPS.Roque));
                }
            }
        }
    }

    private boolean attaqueRoque(int E1ouE8, int F1ouF8, int G1ouG8, ArrayList<GCoups> coupsAttaque) {
        boolean attaque = false;
        int caseX;
        for (GCoups coups : coupsAttaque) {
            caseX = coups.getCaseX();
            if ((caseX == E1ouE8) || (caseX == F1ouF8) || (caseX == G1ouG8)) {
                attaque = true;

                break;
            }
        }
        return attaque;
    }

    private void coupsEp(final ArrayList<GCoups> pCoups, final int couleur) {
        final int caseEP = g_position.caseEP;
        // prise en passant (avant recherche d'échecs)
        if (caseEP != PAS_DE_CASE) {
            if (couleur == BLANC) {
                int caseX = caseEP + sudest;
                if (pionBlanc(caseX)) {
                    pCoups.add(new GCoups(PION, caseX, caseEP, 0, TYPE_DE_COUPS.EnPassant));
                }
                caseX = caseEP + sudouest;
                if (pionBlanc(caseX)) {
                    pCoups.add(new GCoups(PION, caseX, caseEP, 0, TYPE_DE_COUPS.EnPassant));
                }
            } else {
                int caseX = caseEP + nordest;
                if (pionNoir(caseX)) {
                    pCoups.add(new GCoups(PION, caseX, caseEP, 0, TYPE_DE_COUPS.EnPassant));
                }
                caseX = caseEP + nordouest;
                if (pionNoir(caseX)) {
                    pCoups.add(new GCoups(PION, caseX, caseEP, 0, TYPE_DE_COUPS.EnPassant));
                }
            }
        }
    }

    public boolean estEnEchec() {
        return estEnEchec;
    }

}
