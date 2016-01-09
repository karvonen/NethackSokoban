package dev.nethacksokoban.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class is used to read files and parse them to form levels for the game.
 */
public class FileScanner {

    private boolean valid;

    public FileScanner() {
        this.valid = true;
    }

    /**
     * Method scans the src/levels/ directory for text files starting from
     * number one and continues to increment the file name by one until a file
     * with that name is not found. readAndParseFile is called after each file
     * found.
     *
     * @return ArrayList of the character arrays that will form the levels.
     */
    public ArrayList<char[][]> loadMaps() {
        ArrayList<char[][]> loadedMaps = new ArrayList<>();
        int counter = 1;
        while (true) {
            String fileName = "levels/" + counter + ".txt";
            counter++;
            char[][] loadedMap = readAndParseFile(fileName);
            if (loadedMap == null) {
                break;
            }
            MapValidator validator = new MapValidator(loadedMap);
            if (validator.validate() == false || !valid) {
                return null;
            }

            loadedMaps.add(loadedMap);
        }
        return loadedMaps;
    }

    /**
     * Method reads a file and parses it to return the file's contents as a
     * character array.
     *
     * @param fileName File name of the file being handled.
     *
     * @return ArrayList of the character arrays that will form the levels or
     * null if a file corresponding to the file name was not found.
     */
    public char[][] readAndParseFile(String fileName) {
        char[][] readMap = null;
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            Integer mapWidth = readNextInt(scanner);
            Integer mapHeight = readNextInt(scanner);
            if (mapHeight == null || mapWidth == null) {
                return null;
            }
            readMap = new char[mapHeight][mapWidth];
            for (int i = 0; i < mapHeight; i++) {
                readMap[i] = scanner.nextLine().toCharArray();
            }
        } catch (FileNotFoundException e) {
            return null;
        }
        return readMap;
    }

    private Integer readNextInt(Scanner scanner) {
        Integer readInt = null;
        try {
            String line = scanner.nextLine();
            readInt = Integer.parseInt(line);
        } catch (NumberFormatException ex) {
            valid = false;
            return null;
        }
        return readInt;
    }

}
