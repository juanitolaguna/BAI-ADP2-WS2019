package adp2;

import java.util.Comparator;

public class VectorDistanceComparator implements Comparator<VectorADT> {

    private final VectorADT referenceVector;

    public VectorDistanceComparator(VectorADT referenceVector) {
        this.referenceVector = referenceVector;
    }

    @Override
    public int compare(VectorADT v1, VectorADT v2) {
        double distanceToV1 = referenceVector.euclideanDistanceTo(v1);
        double distanceToV2 = referenceVector.euclideanDistanceTo(v2);

        return Double.compare(distanceToV1, distanceToV2);
};
    }
