package adp2;

import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VectorADTTest {
    @Test
    void euclideanSameTest() {
        VectorADT vector = new VectorADT(1, 2, 3, 4, 5);
        assertEquals(0, vector.euclideanDistanceTo(vector));
    }

    @Test
    void euclideanThrowsTest() {
        VectorADT two = new VectorADT(1, 2);
        VectorADT three = new VectorADT(1, 2, 3);

        assertThrows(InvalidParameterException.class, () -> two.euclideanDistanceTo(three));
    }

    @Test
    void euclideanTest() {
        VectorADT a = new VectorADT(1, 2, 3, 4);
        VectorADT b = new VectorADT(2, 3, 4, 5);
        VectorADT c = new VectorADT(13, 3, 8, 10);
        VectorADT d = new VectorADT(12, 1, 6, 6);

        double ab = 2;
        double cd = 5;

        assertEquals(ab, a.euclideanDistanceTo(b));
        assertEquals(cd, c.euclideanDistanceTo(d));
    }

    @Test
    void nearestMTest() {
        VectorADT a = new VectorADT(5, 5, 5, 5, 5);

        List<VectorADT> all = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            all.add(new VectorADT(5, 5, 5, 5, 5 + i));
        }
        Collections.shuffle(all);

        List<VectorADT> nearest3 = a.nearestM(all, 3);
        for (int i = 0; i < nearest3.size(); i++) {
            VectorADT other = nearest3.get(i);
            assertEquals(other.getValues()[4], a.getValues()[4] + i);
        }

        List<VectorADT> nearest5 = a.nearestM(all, 5);
        for (int i = 0; i < nearest5.size(); i++) {
            VectorADT other = nearest5.get(i);
            assertEquals(other.getValues()[4], a.getValues()[4] + i);
        }
    }
}
