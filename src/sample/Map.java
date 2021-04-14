package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Map {
    private final char[][] map;

    public Map(Scanner input) throws InvalidMapException {
        int size = Integer.parseInt(input.next());
        if (size == 0) {
            throw new InvalidMapException("Map size can not be zero");
        } else {
            map = new char[size][size];
            List<Character> list = new ArrayList<Character>();
            while(input.hasNext()){
                list.add(input.next().charAt(0));
            }
            if(list.size() != size * size){
                throw new InvalidMapException("Not enough map elements");
            }
            int counter = 0;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    char temp = list.get(counter);
                    map[i][j] = temp;
                    counter++;
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

    public void setElement(char value, int x, int y){
        map[x][y] = value;
    }
}
