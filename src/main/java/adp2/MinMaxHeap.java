package adp2;

import java.util.ArrayList;
import java.util.List;

public class MinMaxHeap<T extends Comparable<T>> {
    private ArrayList<T> content = new ArrayList<>();

    public MinMaxHeap(T key) {
        content.add(key);
    }

    public MinMaxHeap(List<T> keys) {
        content.addAll(keys);
        build();
    }

    public void insert(T item){
        content.add(item);
        pushUp(content.size()-1);
    }

    public T min(){
        return content.get(0);
    }

    public T max(){
        T value;
        if (content.size() >= 3) {
            if (compare(content.get(1),content.get(2)) < 0) {
                return content.get(2);
            } else {
                return content.get(1);
            }
        } else if ( content.size() == 2) {
            return content.get(1);
        } else {
            return content.get(0);
        }
    }

    public T delMin(){
        T min = content.get(0);
        content.add(0,content.get(content.size()-1));
        content.remove(1);
        content.remove(content.size()-1);
        pushDown(0);
        return min;
    }

    private T delMax(){
        int indMax;
        if (compare(content.get(1),content.get(2))<0) {indMax = 2;}
        else {indMax = 1;}
        T max = content.get(indMax);
        content.add(indMax,content.get(content.size()-1));
        content.remove(indMax+1);
        content.remove(content.size()-1);
        pushDown(indMax);
        return max;
    }

    private void pushDown(int i) {
        if (onMaxLevel(i)) {
            pushDownMax(i);
        } else {
            pushDownMin(i);
        }
    }

    private void pushDownMin(int i) {
        T compare;
        int descendant = (i*2+1);
        int indCompare = descendant;
        int parent = i;

        if (hasChild(i)) {                                                                                                       // Children 1
            compare = content.get(descendant);
            if (hasMoreChild(i) && higher(compare,content.get(descendant + 1))) {                                                   // Children 2
                indCompare = descendant + 1;

                if (hasChild(descendant) && higher(compare,content.get(descendant * 2 + 1))) {                                      // Grandchildren 1.1
                    indCompare = descendant * 2 + 1;
                    parent = descendant;

                    if (hasMoreChild(descendant) && higher(compare,content.get(descendant * 2 + 2))) {                              // Grandchildren 1.2
                        indCompare = descendant * 2 + 2;
                        parent = descendant +1;

                        if (hasChild(descendant+1) && higher(compare,content.get((descendant + 1) * 2 + 1))) {                    // Grandchildren 2.1
                            indCompare = (descendant + 1) * 2 + 1;
                            parent = descendant;

                            if (hasMoreChild(descendant+1) && higher(compare,content.get((descendant + 1) * 2 + 2))) {             // Grandchildren 2.1
                                indCompare = (descendant + 1) * 2 + 2;
                                parent = descendant +1;
                            }
                        }
                    }
                }
            }
            if (parent > i) {
                if (higher(content.get(i),content.get(indCompare))) {
                    exch(i, indCompare);
                    if (higher(content.get(indCompare), content.get(parent))) {
                        exch(indCompare, parent);
                    }
                    pushDownMax(indCompare);
                }
            } else {
                if (higher(content.get(i),content.get(indCompare))) {
                    exch(i, indCompare);
                }
            }
        }
    }

    private boolean higher(T a,T b) {
        return a.compareTo(b) > 0;
    }

    public boolean less(T a,T b) {
        return a.compareTo(b)<0;
    }

    private boolean hasChild(int i) {
        return content.size()-1 >= (i*2+1);
    }

    private boolean hasMoreChild(int i) {
        return content.size()-1 >= (i*2+2);
    }

    private void pushDownMax(int i) {
        T compare;
        int descendant = (i*2+1);
        int indCompare = descendant;
        int parent = i;

        if (hasChild(i)) {                                                                                                       // Child 1
            compare = content.get(descendant);
            if (hasMoreChild(i) && less(compare,content.get(descendant + 1))) {                                                   // Child 2
                indCompare = descendant + 1;

                if (hasChild(descendant) && less(compare,content.get(descendant * 2 + 1))) {                                      // Grandchild 1.1
                    indCompare = descendant * 2 + 1;
                    parent = descendant;

                    if (hasMoreChild(descendant) && less(compare,content.get(descendant * 2 + 2))) {                              // Grandchild 1.2
                        indCompare = descendant * 2 + 2;
                        parent = descendant +1;

                        if (hasChild(descendant+1) && less(compare,content.get((descendant + 1) * 2 + 1))) {                    // Grandchild 2.1
                            indCompare = (descendant + 1) * 2 + 1;
                            parent = descendant;

                            if (hasMoreChild(descendant+1) && less(compare,content.get((descendant + 1) * 2 + 2))) {             // Grandchild 2.1
                                indCompare = (descendant + 1) * 2 + 2;
                                parent = descendant +1;
                            }
                        }
                    }
                }
            }
            if (parent > i) {
                if (less(content.get(i),content.get(indCompare))) {
                    exch(i, indCompare);
                    if (less(content.get(indCompare), content.get(parent))) {
                        exch(indCompare, parent);
                    }
                    pushDownMax(indCompare);
                }
            } else {
                if (less(content.get(i),content.get(indCompare))) {
                    exch(i, indCompare);
                }
            }
        }
    }

    private void pushUp(int i) {
        if (i != 0) {
            if (!onMaxLevel(i)) {
                if(higher(content.get(i),content.get(i/2))) {
                    exch(i,i/2);
                    pushUpMax(i/2);
                } else {
                    pushUpMin(i);
                }
            } else {
                if(less(content.get(i),content.get(i/2))) {
                    exch(i,i/2);
                    pushUpMin(i/2);
                } else {
                    pushUpMax(i);
                }
            }
        }
    }

    private void pushUpMin(int i) {
        if (i>= 3) { //has grandparent
            if (less(content.get(i),content.get(i/4))) {
                exch(i,i/4);
                pushUpMin(i/4);
            }
        }
    }

    private void pushUpMax(int i) {
        if (i>= 3) {
            if (higher(content.get(i),content.get(i/4))) {
                exch(i,i/4);
                pushUpMax(i/4);
            }
        }
    }

    private void exch(int x,int y) {
        T changer = content.get(x);
        content.add(x+1,content.get(y));
        content.remove(x);
        content.add(y,changer);
        content.remove(y+1);
    }

    private boolean onMaxLevel(int i) {
        if(i==0) {return false;}
        if(i==1||i==2) {return true;}
        double x = 1;
        double y = 2;
        int evod = 1;
        while(x < i && i > y) {
            x += Math.pow(2, evod);
            y += Math.pow(2, evod + 1);
            evod++;
        }
        return (evod/2)*2 != evod;
    }

    public String toString(){
        return content.toString();
    }

    private int compare(T one,T other) {
        return one.compareTo(other);
    }

    private void build() {
        for (int i = 0; i < content.size() ; i++) {
            pushDown(i);
        }
    }

    public static void main(String[] args) {
        MinMaxHeap<Integer> a = new MinMaxHeap<>(List.of(0, 8, 9, 1, 2, 3, 4, 10));

        System.out.println(a.toString());
        System.out.println("Min: " + a.min() + "  Max: " + a.max());

        System.out.println(a.toString());
        a.delMax();
        System.out.println(a.toString());
        a.delMax();
        System.out.println(a.toString());
        a.delMax();
        System.out.println(a.toString());
        a.delMax();
        System.out.println(a.toString());
        a.delMax();
        System.out.println(a.toString());
    }

}