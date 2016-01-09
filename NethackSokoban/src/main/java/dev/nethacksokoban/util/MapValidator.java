package dev.nethacksokoban.util;

/**
 * Class provides basic validation methods for the game's maps.
 */
public class MapValidator {

    private char[][] map;

    /**
     * Constructs a new MapValidator for the given character array.
     * 
     * @param map The map which is going to be validated.
     */
    public MapValidator(char[][] map) {
        this.map = map;
    }

    public boolean validate() {
        return (validateWalls() && validatePlayerStartAndGoal());
    }

    private boolean validateWalls() {
        try {
            for (int i = 0; i < map[0].length; i++) {
                if (map[0][i] != '#' || map[map.length - 1][i] != '#') {
                    return false;
                }
            }
            for (int i = 0; i < map.length; i++) {
                if (map[i][0] != '#' || map[i][map[0].length - 1] != '#') {
                    return false;
                }
            }
        } catch (NullPointerException ex) {
            return false;
        }
        return true;
    }

    private boolean validatePlayerStartAndGoal() {
        int startingPositions = 0;
        int goals = 0;
        try {
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++) {
                    if (map[i][j] == '@') {
                        startingPositions++;
                    }
                    if (map[i][j] == '<') {
                        goals++;
                    }
                }
            }
        } catch (NullPointerException ex) {
            return false;
        }
        return (startingPositions == 1 && goals == 1);
    }
}
