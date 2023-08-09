package edu.up.isgc.cg.raytracer.lights;

/**
 * @author Eduardo Martínez
 */

import edu.up.isgc.cg.raytracer.Intersection;
import edu.up.isgc.cg.raytracer.Vector3D;

import java.awt.*;

public class SpotLight extends Light {

    private Vector3D direction;
    private double cutoffAngle;
    private double fadeAngle;

    public SpotLight(Vector3D position, Vector3D direction, Color color, double intensity, double cutoffAngle, double fadeAngle) {
        super(position, color, intensity);
        setDirection(direction);
        setCutoffAngle(cutoffAngle);
        setFadeAngle(fadeAngle);
    }

    public Vector3D getDirection() {
        return direction;
    }

    public void setDirection(Vector3D direction) {
        this.direction = Vector3D.normalize(direction);
    }

    public double getCutoffAngle() {
        return cutoffAngle;
    }

    public void setCutoffAngle(double cutoffAngle) {
        this.cutoffAngle = cutoffAngle;
    }

    public double getFadeAngle() {
        return fadeAngle;
    }

    public void setFadeAngle(double fadeAngle) {
        this.fadeAngle = fadeAngle;
    }

    @Override
    public double getNDotL(Intersection intersection) {
        Vector3D lightDirection = Vector3D.normalize(Vector3D.substract(getPosition(), intersection.getPosition()));
        double nDotL = Vector3D.dotProduct(intersection.getNormal(), lightDirection);

        if (nDotL > Math.cos(cutoffAngle)) {
            // Calculate the falloff based on the angle between the light direction and spotlight direction
            double spotlightFactor;
            if (nDotL > Math.cos(fadeAngle)) {
                spotlightFactor = 1.0;
            } else {
                spotlightFactor = (nDotL - Math.cos(cutoffAngle)) / (Math.cos(fadeAngle) - Math.cos(cutoffAngle));
            }
            return Math.max(spotlightFactor * getIntensity(), 0.0);
        }
        return 0.0;
    }
}

