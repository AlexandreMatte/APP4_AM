package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CircuitApp {

    public CircuitApp(){
    }

    static void main(String[] args) {
        System.out.println("---Lancement Du Programme---");
        final char fSep = File.separatorChar;
        int i = 1;

        String cheminFichier = System.getProperty("user.dir") + fSep + "src" + fSep + "donnees" + fSep +"complexe_industriel_zone_nord.json";
        try (Scanner scanner = new Scanner(new File(cheminFichier), "UTF-8")) {

            while (scanner.hasNextLine()) {

                String ligne = scanner.nextLine();
                System.out.println(ligne);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Erreur : fichier introuvable -> " + cheminFichier);
        } catch (Exception e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
    }
}
