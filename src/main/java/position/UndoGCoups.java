package position;

public class UndoGCoups {

    boolean droitPetitRoqueNoir;
    final int[] etats;
    boolean droitGrandRoqueNoir;
    boolean droitPetitRoqueBlanc;
    boolean droitGrandRoqueBlanc;
    int caseEP;

    public UndoGCoups() {

        etats = new int[ICodage.NB_CELLULES];
    }

}
