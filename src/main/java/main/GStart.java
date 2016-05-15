package main;

import gui.ChessGui;
import gui.GameView;
import position.FenToGPosition;
import position.GPosition;
import position.ICodage;

class GStart {

    public GStart() {
//        String fen = "rnbqnrk1/pp1pb1pp/4p3/2pP4/2P1Pp2/1PN2N2/PB2BPPP/R2Q1RK1 b - - 0 11";
        String fen = ICodage.fen_initiale;
        GPosition gp = FenToGPosition.toGPosition(fen);
        GameView gameview = new GameView(gp);
        ChessGui gui = new ChessGui(gameview);
        gui.setGuiPosition();
    }

}
