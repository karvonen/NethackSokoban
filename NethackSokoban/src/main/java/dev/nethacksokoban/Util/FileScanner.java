package dev.nethacksokoban.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileScanner {

    public ArrayList<char[][]> loadMaps() {
        ArrayList<char[][]> loadedMaps = new ArrayList<>();
        int counter = 1;
        while (true) {
            String fileName = "src/levels/" + counter + ".txt";
            counter++;
            char[][] loadedMap = readAndParseFile(fileName);
            if (loadedMap == null) {
                break;
            }
            loadedMaps.add(loadedMap);
        }
        return loadedMaps;
    }

    public char[][] readAndParseFile(String fileName) {
        char[][] readMap = null;
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            int mapWidth = Integer.parseInt(scanner.nextLine());
            int mapHeight = Integer.parseInt(scanner.nextLine());
            readMap = new char[mapHeight][mapWidth];
            for (int i = 0; i < mapHeight; i++) {
                readMap[i] = scanner.nextLine().toCharArray();
            }
        } catch (FileNotFoundException e) {
            return null;
        }
        return readMap;
    }

}
