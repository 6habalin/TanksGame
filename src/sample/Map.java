package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Map implements Serializable {
    private final char[][] map;

    Map(String input) throws InvalidMapException, FileNotFoundException {
        File fileMap = new File("src/sample/" + input);
        Scanner scan = new Scanner(fileMap);
        int counter = 0;
        int size = Integer.parseInt(scan.next());
        List<Character> list = new ArrayList<Character>();

        if (size != 0) {
            map = new char[size][size];
            while (scan.hasNext()) {
                list.add(scan.next().charAt(0));
            }
            if (list.size() != size * size) {
                throw new InvalidMapException("Not enough map elements");
            }

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    map[i][j] = list.get(counter);
                    counter++;
                }
            }

        } else {
            throw new InvalidMapException("Map size can not be zero");
        }
    }

    public int getSize() {
        return map.length;
    }

    public char getValueAt(int x, int y) {
        return map[x][y];
    }

    public void print() {
        for (char[] chars : map) {
            for (char aChar : chars) {
                System.out.print(aChar + " ");
            }
            System.out.println();
        }
    }

    public void setElement(char value, int x, int y) {
        map[x][y] = value;
    }
}
