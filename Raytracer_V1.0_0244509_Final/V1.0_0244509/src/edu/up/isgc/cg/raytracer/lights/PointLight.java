package edu.up.isgc.cg.raytracer.lights;

/**
 * @author Eduardo Martínez
 */

import edu.up.isgc.cg.raytracer.Intersection;
import edu.up.isgc.cg.raytracer.Vector3D;

import java.awt.*;

public class PointLight extends Light{

    public PointLight(Vector3D position, Color color, double intensity) {
        super(position, color, intensity);
    }
    @Override
    public double getNDotL(Intersection intersection) {
        double nDotL = Vector3D.dotProduct(intersection.getNormal(), Vector3D.normalize(Vector3D.substract(getPosition(), intersection.getPosition())));
        return Math.max(nDotL, 0.0);
    }
}
