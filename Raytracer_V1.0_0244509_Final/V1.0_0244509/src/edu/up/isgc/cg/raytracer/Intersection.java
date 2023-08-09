package edu.up.isgc.cg.raytracer;

/**
 * @author Eduardo Martínez
 * @coauthor Jafet Rodriguez
 */

import edu.up.isgc.cg.raytracer.objects.Object3D;

public class Intersection {
    /**
     *
     */
    private double distance;
    private Vector3D normal;
    private Vector3D position;
    private Object3D object;

    /**
     * Constructor that determines the scene intersection parameters
     * @param position The location of the object
     * @param distance The difference between the original object and the intersection
     * @param normal The direction of the vector
     * @param object The object being intersected
     */
    public Intersection(Vector3D position, double distance, Vector3D normal, Object3D object) {
        setPosition(position);
        setDistance(distance);
        setNormal(normal);
        setObject(object);
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Vector3D getNormal() {
        return normal;
    }

    public void setNormal(Vector3D normal) {
        this.normal = normal;
    }

    public Vector3D getPosition() {
        return position;
    }

    public void setPosition(Vector3D position) {
        this.position = position;
    }

    public Object3D getObject() {
        return object;
    }

    public void setObject(Object3D object) {
        this.object = object;
    }

}
