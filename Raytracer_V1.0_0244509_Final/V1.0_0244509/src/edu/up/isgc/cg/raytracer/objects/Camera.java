package edu.up.isgc.cg.raytracer.objects;

/**
 * @author Eduardo Martínez
 * @coauthor Jafet Rodriguez
 */

import edu.up.isgc.cg.raytracer.Intersection;
import edu.up.isgc.cg.raytracer.Ray;
import edu.up.isgc.cg.raytracer.Vector3D;

import java.awt.*;
/**
 * Represents a camera in a 3D scene.
 */

public class Camera extends Object3D {
    //FOV[0] = Horizontal | FOV[1] = Vertical
    private double[] fieldOfView = new double[2];
    private double defaultZ = 15.0;
    //0 is width
    //1 is height
    private int[] resolution;
    private double[] nearFarPlanes = new double[2];

    /**
     * Constructs a Camera object with the specified parameters.
     *
     * @param position            The position of the camera.
     * @param fieldOfViewHorizontal  The horizontal field of view angle in degrees.
     * @param fieldOfViewVertical    The vertical field of view angle in degrees.
     * @param widthResolution     The width resolution of the camera.
     * @param heightResolution    The height resolution of the camera.
     * @param nearPlane           The distance to the near plane.
     * @param farPlane            The distance to the far plane.
     */

    public Camera(Vector3D position, double fieldOfViewHorizontal, double fieldOfViewVertical,
                  int widthResolution, int heightResolution, double nearPlane, double farPlane) {
        super(position, Color.BLACK, 0.0, 0, 0);
        setFieldOfViewHorizontal(fieldOfViewHorizontal);
        setFieldOfViewVertical(fieldOfViewVertical);
        setResolution(new int[]{widthResolution, heightResolution});
        setNearFarPlanes(new double[]{nearPlane, farPlane});
    }

    public double[] getNearFarPlanes() {
        return nearFarPlanes;
    }

    private void setNearFarPlanes(double[] nearFarPlanes) {
        this.nearFarPlanes = nearFarPlanes;
    }

    public double[] getFieldOfView() {
        return fieldOfView;
    }

    private void setFieldOfView(double[] fieldOfView) {
        this.fieldOfView = fieldOfView;
    }

    public double getFieldOfViewHorizontal() {
        return fieldOfView[0];
    }

    public void setFieldOfViewHorizontal(double fieldOfViewHorizontal) {
        fieldOfView[0] = fieldOfViewHorizontal;
    }

    public double getFieldOfViewVertical() {
        return fieldOfView[1];
    }

    public void setFieldOfViewVertical(double fieldOfViewVertical) {
        fieldOfView[1] = fieldOfViewVertical;
    }

    public double getDefaultZ() {
        return defaultZ;
    }

    public void setDefaultZ(double defaultZ) {
        this.defaultZ = defaultZ;
    }

    public int[] getResolution() {
        return resolution;
    }

    public int getResolutionWidth() {
        return getResolution()[0];
    }

    public int getResolutionHeight() {
        return getResolution()[1];
    }

    private void setResolution(int[] resolution) {
        this.resolution = resolution;
    }

    /**
     * Calculates the positions in 3D space corresponding to each pixel of the camera's resolution.
     *
     * @return A 2D array of Vector3D representing the positions for each pixel.
     */

    public Vector3D[][] calculatePositionsToRay() {
        double angleMaxX = getFieldOfViewHorizontal() / 2.0;
        double radiusMaxX = getDefaultZ() / Math.cos(Math.toRadians(angleMaxX));

        double maxX = Math.sin(Math.toRadians(angleMaxX)) * radiusMaxX;
        double minX = -maxX;

        double angleMaxY = getFieldOfViewVertical() / 2.0;
        double radiusMaxY = getDefaultZ() / Math.cos(Math.toRadians(angleMaxY));

        double maxY = Math.sin(Math.toRadians(angleMaxY)) * radiusMaxY;
        double minY = -maxY;

        Vector3D[][] positions = new Vector3D[getResolutionWidth()][getResolutionHeight()];
        double posZ = getDefaultZ();

        for (int x = 0; x < positions.length; x++) {
            for (int y = 0; y < positions[x].length; y++) {
                double posX = minX + (((maxX - minX) / (double) getResolutionWidth()) * x);
                double posY = maxY - (((maxY - minY) / (double) getResolutionHeight()) * y);
                positions[x][y] = new Vector3D(posX, posY, posZ);
            }
        }

        return positions;
    }

    @Override
    public Intersection getIntersection(Ray ray) {
        return new Intersection(Vector3D.ZERO(), -1, Vector3D.ZERO(), null);
    }
}
