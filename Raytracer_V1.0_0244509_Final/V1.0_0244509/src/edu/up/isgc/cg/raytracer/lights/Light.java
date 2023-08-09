package edu.up.isgc.cg.raytracer.lights;

/**
 * @author Eduardo Martínez
 * @coauthor Jafet Rodriguez
 */

import edu.up.isgc.cg.raytracer.Intersection;
import edu.up.isgc.cg.raytracer.Ray;
import edu.up.isgc.cg.raytracer.Vector3D;
import edu.up.isgc.cg.raytracer.objects.Object3D;

import java.awt.*;

public abstract class Light extends Object3D {

    private double intensity;

    /**
     * Constructs a Light object with the specified position, color, and intensity.
     *
     * @param position  The position of the light source.
     * @param color     The color of the light.
     * @param intensity The intensity of the light.
     */

    public Light(Vector3D position, Color color, double intensity) {
        super(position, color, 0.0, 0.0, 0.0);
        setIntensity(intensity);
    }

    public double getIntensity() {
        return intensity;
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }

    /**
     * Calculates the dot product between the light direction and the surface normal at the given intersection.
     * This method must be implemented by subclasses to provide specific light calculations.
     *
     * @param intersection The intersection point.
     * @return The dot product between the light direction and the surface normal.
     */

    public abstract double getNDotL(Intersection intersection);

    /**
     * Returns a default intersection with no meaningful values.
     * This method is overridden from the Object3D class, but it is not used for lights.
     *
     * @param ray The ray to intersect with.
     * @return A default Intersection object with zero position, negative distance, zero normal, and null object reference.
     */

    public Intersection getIntersection(Ray ray) {
        return new Intersection(Vector3D.ZERO(), -1, Vector3D.ZERO(), null);
    }

}
