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
 * A class representing a sphere object in a 3D scene.
 */

public class Sphere extends Object3D{
    private double radius;

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    /**
     * Constructs a Sphere object with the specified parameters.
     *
     * @param position       The position of the sphere in 3D space.
     * @param radius         The radius of the sphere.
     * @param color          The color of the sphere.
     * @param reflection     The reflection coefficient of the sphere.
     * @param refraction     The refraction coefficient of the sphere.
     * @param specularPower  The specular power of the sphere.
     */

    public Sphere(Vector3D position, double radius, Color color, double reflection, double refraction, double specularPower) {
        super(position, color, reflection, refraction, specularPower);
        setRadius(radius);
    }

    @Override
    public Intersection getIntersection(Ray ray) {

        double reflection = getReflection();
        double refraction = getRefraction();
        double distance = -1;
        Vector3D normal = Vector3D.ZERO();
        Vector3D position = Vector3D.ZERO();

        Vector3D L = Vector3D.substract(ray.getOrigin(), getPosition());
        double tca = Vector3D.dotProduct(ray.getDirection(), L);
        double L2 = Math.pow(Vector3D.magnitude(L), 2);
        //Intersection
        double intersection = Math.pow(tca, 2) - L2 + Math.pow(getRadius(), 2);

        if(intersection >= 0){
            double d = Math.sqrt(intersection);
            double t0 = -tca + d;
            double t1 = -tca - d;

            distance = Math.min(t0, t1);
            position = Vector3D.add(ray.getOrigin(), Vector3D.scalarMultiplication(ray.getDirection(), distance));
            normal = Vector3D.normalize(Vector3D.substract(position, getPosition()));
        } else {
            return null;
        }
        return new Intersection(position, distance, normal, this);
    }
}
