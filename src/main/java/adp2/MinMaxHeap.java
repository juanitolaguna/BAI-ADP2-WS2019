package adp2;

import java.util.*;
import java.util.stream.Collectors;

public class MinMaxHeap<T extends Comparable> {
    private final List<T> values;

    public MinMaxHeap() {
        this.values = new ArrayList<>();
    }

    public MinMaxHeap(List<T> items) {
        this.values = items;
        this.build(items);
    }

    private void build(List<T> items) {
        for (int i = items.size() / 2; i >= 0; i--) {
            pushDown(i);
        }
    }

    private void pushDown(int index) {
        if (isMinLevel(index)) {
            pushDownMin(index);
        } else {
            pushDownMax(index);
        }
    }

    private void pushDownMin(int index) {
        boolean indexHasChildren = hasChildren(index);
        if (indexHasChildren) {
            int m = indexOfXChildOrGrandChild(index, true);
            boolean mIsGrandChild = m > 2 * index + 2;
            if (mIsGrandChild) {
                if (get(m).compareTo(get(index)) < 0) {
                    swap(m, index);
                    if (get(m).compareTo(getParent(m)) > 0) {
                        swap(m, getParentIndex(m));
                    }
                    pushDownMin(m);
                }
            } else if (get(m).compareTo(get(index)) < 0) {
                swap(m, index);
            }
        }
    }

    private void pushDownMax(int index) {
        boolean indexHasChildren = hasChildren(index);
        if (indexHasChildren) {
            int m = indexOfXChildOrGrandChild(index, false);
            boolean mIsGrandChild = m > 2 * index + 2;
            if (mIsGrandChild) {
                if (get(m).compareTo(get(index)) > 0) {
                    swap(m, index);
                    if (get(m).compareTo(getParent(m)) < 0) {
                        swap(m, getParentIndex(m));
                    }
                    pushDownMax(m);
                }
            } else if (get(m).compareTo(get(index)) > 0) {
                swap(m, index);
            }
        }
    }

    private boolean isMinLevel(int index) {
        return Math.floor(Math.log(index + 1) / Math.log(2)) % 2 == 0;
    }

    private void swap(int a, int b) {
        T temp = this.values.get(a);
        this.values.set(a, this.values.get(b));
        this.values.set(b, temp);
    }

    private T get(int index) {
        if (this.values.size() <= index) {
            String message = String.format("No node at index %s.", index);
            throw new NoSuchElementException(message);
        }

        return this.values.get(index);
    }


    private int getParentIndex(int index) {
        boolean isEven = index % 2 == 0;
        return index / 2 - (isEven ? 1 : 0);
    }

    private int getLeftChildIndex(int index) {
        return 2 * index + 1;
    }

    private int getRightChildIndex(int index) {
        return 2 * index + 2;
    }

    private boolean hasLeftChild(int index) {
        return this.values.size() > getLeftChildIndex(index);
    }


    private boolean hasRightChild(int index) {
        return this.values.size() > getRightChildIndex(index);
    }

    private boolean hasChildren(int index) {
        return hasLeftChild(index);
    }

    private boolean hasParent(int index) {
        return index != 0;
    }

    private T getLeftChild(int index) {
        if (!hasLeftChild(index)) {
            String message = String.format("No left child for node %s at index %s.", get(index), index);
            throw new NoSuchElementException(message);
        }

        return this.values.get(getLeftChildIndex(index));
    }

    private T getRightChild(int index) {
        if (!hasRightChild(index)) {
            String message = String.format("No right child for node %s at index %s.", get(index), index);
            throw new NoSuchElementException(message);
        }

        return this.values.get(getRightChildIndex(index));
    }

    private List<T> getChildren(int index) {
        List<T> children = new ArrayList<>();
        if (hasLeftChild(index)) children.add(getLeftChild(index));
        if (hasRightChild(index)) children.add(getRightChild(index));
        return children;
    }

    private T getParent(int index) {
        if (!hasParent(index)) {
            String message = String.format("Node %s at index %s has no parent.", get(index), index);
            throw new NoSuchElementException(message);
        }

        return get(getParentIndex(index));
    }

    private int indexOfXChildOrGrandChild(int index, boolean smallest) {
        Map<T, Integer> map = new HashMap<>();
        List<T> all = new ArrayList<>();

        List<T> children = getChildren(index);
        for (int i = 0; i < children.size(); i++) {
            int childIndex = i % 2 == 0 ? getLeftChildIndex(index) : getRightChildIndex(index);
            T child = children.get(i);
            map.put(child, childIndex);
            all.add(child);
            List<T> grandChildren = getChildren(childIndex);
            for (int j = 0; j < grandChildren.size(); j++) {
                int grandChildIndex = j % 2 == 0 ? getLeftChildIndex(childIndex) : getRightChildIndex(childIndex);
                T grandChild = grandChildren.get(j);
                map.put(grandChild, grandChildIndex);
                all.add(grandChild);
            }
        }

        if (smallest) {
            all.sort(Comparable::compareTo);
        } else {
            all.sort((a, b) -> a.compareTo(b) * -1);
        }
        return map.get(all.get(0));
    }

    public void insert(T element) {
        this.values.add(element);
        pushUp(this.values.size() - 1);
    }

    private void pushUp(int index) {
        if (index == 0) return;
        if (isMinLevel(index)) {
            if (get(index).compareTo(getParent(index)) > 0) {
                swap(index, getParentIndex(index));
                pushUpMax(getParentIndex(index));
            } else {
                pushUpMin(index);
            }
        } else {
            if (get(index).compareTo(getParent(index)) < 0) {
                swap(index, getParentIndex(index));
                pushUpMin(getParentIndex(index));
            } else {
                pushUpMax(index);
            }
        }
    }

    private void pushUpMin(int index) {
        boolean hasGrandParent = hasParent(index) && hasParent(getParentIndex(index));
        if (hasGrandParent) {
            int grandParentIndex = getParentIndex(getParentIndex(index));
            T grandParent = get(grandParentIndex);
            if (get(index).compareTo(grandParent) < 0) {
                swap(index, grandParentIndex);
                pushUpMin(grandParentIndex);
            }
        }
    }

    private void pushUpMax(int index) {
        boolean hasGrandParent = hasParent(index) && hasParent(getParentIndex(index));
        if (hasGrandParent) {
            int grandParentIndex = getParentIndex(getParentIndex(index));
            T grandParent = get(grandParentIndex);
            if (get(index).compareTo(grandParent) > 0) {
                swap(index, grandParentIndex);
                pushUpMax(grandParentIndex);
            }
        }
    }

    public T delMin() {
        if (this.values.size() == 0) {
            String message = String.format("Cannot remove min from heap of size %s", 0);
            throw new NoSuchElementException(message);
        }

        T nextRoot = this.values.remove(this.values.size() - 1);
        T root = this.values.set(0, nextRoot);
        pushDown(0);
        return root;
    }

    public T delMax() {
        if (this.values.size() == 0) {
            String message = String.format("Cannot remove max from heap of size %s", 0);
            throw new NoSuchElementException(message);
        }

        if (!hasChildren(0)) {
            return this.values.remove(0);
        }

        T left = get(1);
        T right = null;

        int maxIndex;

        if (hasRightChild(0)) {
            right = get(2);
        }

        if (right == null) {
            maxIndex = 1;
        } else {
            maxIndex = left.compareTo(right) > 0 ? 1 : 2;
        }

        T max = this.values.remove(maxIndex);
        pushDown(maxIndex);
        return max;
    }

    public T min() {
        if (this.values.size() == 0) {
            String message = String.format("No min found for heap of size %s", 0);
            throw new NoSuchElementException(message);
        }

        return get(0);
    }

    public T max() {
        if (this.values.size() == 0) {
            String message = String.format("No max found for heap of size %s", 0);
            throw new NoSuchElementException(message);
        }

        if (!hasChildren(0)) {
            return get(0);
        }
        T left = get(1);
        T right = null;

        if (hasRightChild(0)) {
            right = get(2);
        }

        if (right == null) return left;
        return left.compareTo(right) < 0 ? right : left;

    }

    @Override
    public String toString() {
        return "(" + this.values.size() + ") [ " + this.values.stream().map(T::toString).collect(Collectors.joining(", ")) +
                " ]";
    }


    public static void main(String[] args) {
        MinMaxHeap<Integer> heap = new MinMaxHeap<>();
        heap.insert(0);
        heap.insert(8);
        heap.insert(9);
        heap.insert(1);
        heap.insert(2);
        heap.insert(3);
        heap.insert(4);
        heap.insert(10);

        System.out.println(heap);

//heap.delMin();

        heap.delMax();
        System.out.println(heap);
        heap.delMax();
        System.out.println(heap);
        heap.delMax();
        System.out.println(heap);
        heap.delMax();

        System.out.println(heap);

    }
}
