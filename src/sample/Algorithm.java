package sample;

import java.util.ArrayList;
import java.util.List;

public class Algorithm {

        private int x;
        private int y;

        private static List<Integer> marker = new ArrayList<Integer>();

        public Algorithm(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public static void addToMarker(int markerID) {
            marker.add(markerID);
        }

        public static void clearMarker() {
            marker.clear();
        }

        public static List<Integer> getMarker() {
            return marker;
        }

        public int getY() {
            return y;
        }

    public static double distance(Algorithm p1, Algorithm p2) {
        int a = p1.getX();
        int b = p1.getY();
        int c = p2.getX();
        int d = p2.getY();

        return Math.sqrt(Math.pow((c - a), 2) + Math.pow((d - b), 2));
    }


}
