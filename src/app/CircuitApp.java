package app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import electronique.Composant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CircuitApp {
    static final char fSep = File.separatorChar;
    static String cheminFichier = System.getProperty("user.dir") + fSep + "src" + fSep + "donnees";
    Scanner scanner = new Scanner(System.in);
    CircuitBuilder circuitBuilder = new CircuitBuilder();
    ObjectMapper mapper = new ObjectMapper();

    static void main(String[] args) throws IOException {
        new CircuitApp();
    }

    public CircuitApp() throws IOException {


        System.out.println("---Lancement Du Programme---\n");
        System.out.println("-Liste de fichiers-");


        try {
            List<Path> fichiersJson = getFichiersJson(cheminFichier);

            if (fichiersJson.isEmpty()) {
                System.err.println("Erreur : aucun fichier Json trouvé dans -> " + cheminFichier);
            } else {
                for (int i = 0; i < fichiersJson.size(); i++) {
                    System.out.println(fichiersJson.get(i) + " [" + (i + 1) + "]");
                }
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        System.out.println();

        int reponse = entree();
        System.out.println("Fichier numéro " + reponse + " sélectionné.\n");

        System.out.println("Construction du fichier...");

        String path = String.valueOf(getFichiersJson(cheminFichier).get(reponse-1));
        File jsonFile = new File(path);
        JsonNode node = mapper.readTree(jsonFile);
        Composant circuit = CircuitBuilder.lireComposant(node);
        System.out.println(circuit.calculerResistance());
    }

    public int entree(){

        while(true) {
            System.out.print("Entrez le numéro du circuit à modéliser: ");
            try {
                String reponse = scanner.nextLine();
                int i = Integer.parseInt(reponse);
                if (i <= getFichiersJson(cheminFichier).toArray().length && i >= 0) {
                    return i;
                } else {
                    System.out.println("Erreur: il faut entrez un numéro inclus dans la liste de fichiers.\n");
                }
            } catch (NumberFormatException | IOException e) {
                System.out.println("Erreur: il faut entrez un numéro.\n");
            }
        }
    }

    public static List<Path> getFichiersJson(String folderPath) throws IOException {
        List<Path> jsonFiles = new ArrayList<>();

        Path dir = Paths.get(folderPath);

        if (!Files.exists(dir)) {
            throw new IOException("Directory does not exist: " + folderPath);
        }
        if (!Files.isDirectory(dir)) {
            throw new IOException("Path is not a directory: " + folderPath);
        }


        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.json")) {
            for (Path entry : stream) {
                if (Files.isRegularFile(entry)) {
                    jsonFiles.add(entry.toAbsolutePath());
                }
            }
        }

        return jsonFiles;
    }
}
