package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CircuitApp {

    public CircuitApp(){
    }

    static void main(String[] args) {
        System.out.println("---Lancement Du Programme---\n");
        final char fSep = File.separatorChar;

        String cheminFichier = System.getProperty("user.dir") + fSep + "src" + fSep + "donnees";
        try {
            List<Path> fichiersJson = getFichiersJson(cheminFichier);

            if (fichiersJson.isEmpty()) {
                System.err.println("Erreur : aucun fichier Json trouvé dans -> " + cheminFichier);
            } else {
                for (int i = 0; i < fichiersJson.size(); i++) {
                    System.out.println(fichiersJson.get(i) + " [" + i + "]");
                }
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
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

        // Use try-with-resources to ensure the stream is closed
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
