package TwentyFortyEight;

import java.util.ArrayList;
import java.util.Collections;

class Traversal {
    private ArrayList<Integer> x = new ArrayList<>();
    private ArrayList<Integer> y = new ArrayList<>();

    public void addX(int value) { x.add(value); }
    public void addY(int value) { y.add(value); }

    public void reverseX() {
        Collections.reverse(x);
    }

    public void reverseY() {
        Collections.reverse(y);
    }

    public ArrayList<Integer> getX() { return x; }
    public ArrayList<Integer> getY() { return y; }
}