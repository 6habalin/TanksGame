package sample;

import java.util.Scanner;

public class Map {
    private final char[][] map;

    public Map(Scanner input) throws InvalidMapException {
        int size = Integer.parseInt(input.next());
        if (size == 0) {
            throw new InvalidMapException("Map size can not be zero");
        } else {
            map = new char[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    char temp = input.next().charAt(0);
                    switch (temp) {
                        case 'R':
                        case 'L':
                        case 'U':
                        case 'D':
                            if (i < size - 1) {
                                throw new InvalidMapException("Not enough map elements");
                            }
                            break;
                        case '0':
                        case '1':
                        case 'P':
                            map[i][j] = temp;
                            break;
                        default:
                            throw new InvalidMapException("Something went wrong");
                    }
                }
            }
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
}
