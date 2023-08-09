package edu.up.isgc.cg.raytracer;

/**
 * @author Eduardo Martínez
 * @coauthor Jafet Rodriguez
 */
public class Vector3D {
    private static final Vector3D ZERO = new Vector3D(0.0, 0.0, 0.0);
    private double x, y, z;

    /**
     * Constructor of the Vector's positions
     * @param x point at x
     * @param y point at y
     * @param z point at z
     */

    public Vector3D(double x, double y, double z) {
        setX(x);
        setY(y);
        setZ(z);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public Vector3D clone() {
        return new Vector3D(getX(), getY(), getZ());
    }

    public static Vector3D ZERO() {
        return ZERO.clone();
    }

    /**
     * Method to convert a Vector3D to string
     * @return A string summarizing the vector's positions
     */
    @Override
    public String toString() {
        return "Vector3D{" +
                "x=" + getX() +
                ", y=" + getY() +
                ", z=" + getZ() +
                "}";
    }

    /**
     * Dot product between two vectors, by multiplying each position
     * @param vectorA First vector
     * @param vectorB Second vector
     * @return A double as a result of addition of all multiplications
     */
    public static double dotProduct(Vector3D vectorA, Vector3D vectorB) {
        return (vectorA.getX() * vectorB.getX()) + (vectorA.getY() * vectorB.getY()) + (vectorA.getZ() * vectorB.getZ());
    }

    /**
     * Cross product between two vectors, by multiplying each position
     * @param vectorA First vector
     * @param vectorB Second vector
     * @return A vector as a result of multiplying and substracting positions.
     */
    public static Vector3D crossProduct(Vector3D vectorA, Vector3D vectorB){
        return new Vector3D((vectorA.getY() * vectorB.getZ()) - (vectorA.getZ() * vectorB.getY()),
                (vectorA.getZ() * vectorB.getX()) - (vectorA.getX() * vectorB.getZ()),
                (vectorA.getX() * vectorB.getY()) - (vectorA.getY() * vectorB.getX()));
    }

    /**
     * Size of a vector
     * @param vectorA Three positions summarized in a vector
     * @return a double indicating the size of a vector.
     */
    public static double magnitude (Vector3D vectorA){
        return Math.sqrt(dotProduct(vectorA, vectorA));
    }

    /**
     * Addition between two Vectors
     * @param vectorA First vector
     * @param vectorB Second Vector
     * @return A vector as a result of the addition of each position
     */
    public static Vector3D add(Vector3D vectorA, Vector3D vectorB){
        return new Vector3D(vectorA.getX() + vectorB.getX(), vectorA.getY() + vectorB.getY(), vectorA.getZ() + vectorB.getZ());
    }

    /**
     * Subtraction between two Vectors
     * @param vectorA First vector
     * @param vectorB Second Vector
     * @return A vector as a result of the subtraction of each position
     */
    public static Vector3D substract(Vector3D vectorA, Vector3D vectorB){
        return new Vector3D(vectorA.getX() - vectorB.getX(), vectorA.getY() - vectorB.getY(), vectorA.getZ() - vectorB.getZ());
    }

    /**
     * Change a Vectors length by 1
     * @param vectorA Vector
     * @return It returns a vector with the same direction but with length of 1
     */
    public static Vector3D normalize(Vector3D vectorA){
        double mag = Vector3D.magnitude(vectorA);
        return new Vector3D(vectorA.getX() / mag, vectorA.getY() / mag, vectorA.getZ() / mag);
    }

    /**
     * Multiplication of two vectors
     * @param vectorA Vector
     * @param scalar Value
     * @return The multiplication of each position for a value.
     */
    public static Vector3D scalarMultiplication(Vector3D vectorA, double scalar){
        return new Vector3D(vectorA.getX() * scalar, vectorA.getY() * scalar, vectorA.getZ() * scalar);
    }
}
