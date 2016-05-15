package pgntofen;

import java.io.Serializable;
import java.util.ArrayList;
import main.Fen;
import main.Ui;

public class Pgn implements Serializable {

    private static final long serialVersionUID = 1L;
    private final ArrayList<String> FenListTest;

    public Pgn() {

        FenListTest = new ArrayList<>();
        initFenList1();
        for (String f : Fen.getFenList()) {
            FenListTest.add(f);
        }
        initFenList2();
        for (String f : Fen.getFenList()) {
            FenListTest.add(f);
        }
        initFenList3();
        for (String f : Fen.getFenList()) {
            FenListTest.add(f);
        }
//        initFenList4();
//        for (String f : Fen.getFenList()) {
//            FenListTest.add(f);
//        }
//        initFenList5();
//        for (String f : Fen.getFenList()) {
//            FenListTest.add(f);
//        }
    }

    private void initFenList1() {
        String[] command = new String[3];
        command[0] = "-cli";
        command[1] = "D:\\Documents\\CHESS\\MAVEN\\parties\\Bird.pgn";
//        command[1] = "D:\\Documents\\CHESS\\MAVEN\\parties\\Shirov.pgn";
//        command[1] = "D:\\Documents\\CHESS\\MAVEN\\parties\\Motylev.pgn";
//        command[1] = "D:\\Documents\\CHESS\\MAVEN\\parties\\Tartakower.pgn";
//        command[1] = "D:\\Documents\\CHESS\\MAVEN\\parties\\Nakamura.pgn";
        Ui.main(command);
    }

    private void initFenList2() {
        String[] command = new String[3];
        command[0] = "-cli";
//        command[1] = "D:\\Documents\\CHESS\\MAVEN\\parties\\Bird.pgn";
        command[1] = "D:\\Documents\\CHESS\\MAVEN\\parties\\Shirov.pgn";
//        command[1] = "D:\\Documents\\CHESS\\MAVEN\\parties\\Motylev.pgn";
//        command[1] = "D:\\Documents\\CHESS\\MAVEN\\parties\\Tartakower.pgn";
//        command[1] = "D:\\Documents\\CHESS\\MAVEN\\parties\\Nakamura.pgn";
        Ui.main(command);
    }

    private void initFenList3() {
        String[] command = new String[3];
        command[0] = "-cli";
//        command[1] = "D:\\Documents\\CHESS\\MAVEN\\parties\\Bird.pgn";
//        command[1] = "D:\\Documents\\CHESS\\MAVEN\\parties\\Shirov.pgn";
        command[1] = "D:\\Documents\\CHESS\\MAVEN\\parties\\Motylev.pgn";
//        command[1] = "D:\\Documents\\CHESS\\MAVEN\\parties\\Tartakower.pgn";
//        command[1] = "D:\\Documents\\CHESS\\MAVEN\\parties\\Nakamura.pgn";
        Ui.main(command);
    }

    private void initFenList4() {
        String[] command = new String[3];
        command[0] = "-cli";
//        command[1] = "D:\\Documents\\CHESS\\MAVEN\\parties\\Bird.pgn";
//        command[1] = "D:\\Documents\\CHESS\\MAVEN\\parties\\Shirov.pgn";
//        command[1] = "D:\\Documents\\CHESS\\MAVEN\\parties\\Motylev.pgn";
        command[1] = "D:\\Documents\\CHESS\\MAVEN\\parties\\Tartakower.pgn";
//        command[1] = "D:\\Documents\\CHESS\\MAVEN\\parties\\Nakamura.pgn";
        Ui.main(command);
    }

    private void initFenList5() {
        String[] command = new String[3];
        command[0] = "-cli";
//        command[1] = "D:\\Documents\\CHESS\\MAVEN\\parties\\Bird.pgn";
//        command[1] = "D:\\Documents\\CHESS\\MAVEN\\parties\\Shirov.pgn";
//        command[1] = "D:\\Documents\\CHESS\\MAVEN\\parties\\Motylev.pgn";
//        command[1] = "D:\\Documents\\CHESS\\MAVEN\\parties\\Tartakower.pgn";
        command[1] = "D:\\Documents\\CHESS\\MAVEN\\parties\\Nakamura.pgn";
        Ui.main(command);
    }

    public ArrayList<String> getFenListTest() {
        return FenListTest;
    }

}
