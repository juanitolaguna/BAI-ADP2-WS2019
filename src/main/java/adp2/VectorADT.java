package adp2;

import java.security.InvalidParameterException;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class VectorADT {

    public static final String REGEX = "\\((?<dimension>\\d)\\)\\s\\[(?<values>(\\s*|\\d|,)+)]";
    public static final String DIMENSION = "dimension";
    public static final String VALUES = "values";

    private final int[] values;

    public VectorADT(int... values) {
        this.values = values;
    }

    /**
     * Returns the euclidean distance from this vector to another.
     *
     * @param other Other vector to use for the calculation.
     * @return Euclidean distance between the two vectors.
     */
    public double euclideanDistanceTo(VectorADT other) {
        if (this.getDimension() != other.getDimension()) {
            String message = String.format("Other vector must be of same dimension (%s) but was %s.", this.getDimension(), other.getDimension());
            throw new InvalidParameterException(message);
        }

        double term = 0;
        int[] otherValues = other.getValues();
        for (int i = 0; i < this.values.length; i++) {
            term += Math.pow(this.values[i] - otherValues[i], 2);
        }

        return Math.sqrt(term);
    }

    /**
     * Finds m nearest vectors from a list, relative to the instance of this vector.
     *
     * @param all Other vectors to find nearest m in.
     * @param m   Amount to find.
     * @return M nearest vectors.
     */
    public List<VectorADT> nearestM(List<VectorADT> all, int m) {
        return all.stream().sorted(Comparator.comparingDouble(this::euclideanDistanceTo)).limit(m).collect(Collectors.toList());
    }

    /**
     * Returns the vector's dimension.
     *
     * @return Vector dimension.
     */
    public int getDimension() {
        return this.values.length;
    }

    public int[] getValues() {
        return this.values;
    }

    @Override
    public String toString() {
        String[] values = new String[getDimension()];
        for (int i = 0; i < values.length; i++) {
            values[i] = this.values[i] + "";
        }
        return "(" + this.getDimension() + ") [ " + String.join(", ", values) + " ]";
    }

    /**
     * Parses a Vector from a string.
     *
     * @param string String to parse Vector from.
     * @return Parsed Vector.
     * @throws InvalidParameterException If string has invalid pattern.
     */
    public static VectorADT fromString(String string) throws InvalidParameterException {
        if (string == null || string.length() == 0) {
            return null;
        }
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(string);

        if (!matcher.matches()) {
            String message = String.format("Invalid string: %s", string);
            throw new InvalidParameterException(message);
        }

        int dimension = Integer.parseInt(matcher.group(DIMENSION), 10);
        int[] values = new int[dimension];

        String valuesLine = matcher.group(VALUES).replaceAll(" ", "");
        String[] valuesStringArr = valuesLine.split(",");

        if (valuesStringArr.length != dimension) {
            String message = String.format("Invalid string (invalid dimension): %s", string);
            throw new InvalidParameterException(message);
        }

        for (int i = 0; i < valuesStringArr.length; i++) {
            values[i] = Integer.parseInt(valuesStringArr[i], 10);
        }

        return new VectorADT(values);
    }
}
