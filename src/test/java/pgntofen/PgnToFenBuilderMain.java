package pgntofen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class PgnToFenBuilderMain {

    public static void main(String[] args) throws IOException {
        pgn2fen();
    }

    private static void pgn2fen() throws IOException {

        File fichier = new File("D:\\Documents\\CHESS\\MAVEN\\FINAL\\GCLEv3.7m_2\\FenListTest_reduit.ser");
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichier));
        Pgn m = new Pgn();
        oos.writeObject(m);

//        System.out.println(Motylev.size());
    }

}
