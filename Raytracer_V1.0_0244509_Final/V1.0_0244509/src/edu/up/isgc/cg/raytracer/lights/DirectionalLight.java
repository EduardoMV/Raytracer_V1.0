package edu.up.isgc.cg.raytracer.lights;

/**
 * @author Eduardo Martínez
 * @coauthor Jafet Rodriguez
 */

import edu.up.isgc.cg.raytracer.Intersection;
import edu.up.isgc.cg.raytracer.Vector3D;

import java.awt.*;

public class DirectionalLight extends Light {
    //We need a specific type of light
    private Vector3D direction;

    /**
     * Constructs a DirectionalLight object with the specified direction, color, and intensity.
     *
     * @param direction The direction of the light source.
     * @param color     The color of the light.
     * @param intensity The intensity of the light.
     */

    public DirectionalLight(Vector3D direction, Color color, double intensity) {
        super(Vector3D.ZERO(), color, intensity);
        setDirection(Vector3D.normalize(direction));
    }

    @Override
    public double getNDotL(Intersection intersection) {
        return Math.max(Vector3D.dotProduct(intersection.getNormal(), Vector3D.scalarMultiplication(getDirection(), -1)),0f);
    }
    public Vector3D getDirection() {
        return direction;
    }

    public void setDirection(Vector3D direction) {
        this.direction = direction;
    }
}
