package edu.up.isgc.cg.raytracer.objects;

/**
 * @author Eduardo Martínez
 * @coauthor Jafet Rodriguez
 */

import edu.up.isgc.cg.raytracer.Vector3D;

import java.awt.*;

public abstract class Object3D implements IIntersectable{
    private Color color;
    private Vector3D position;
    private double reflection;
    private double refraction;
    private double specularPower;

    /**
     * Constructs an Object3D with the specified parameters.
     *
     * @param position       The position of the object in 3D space.
     * @param color          The color of the object.
     * @param reflection     The reflection coefficient of the object.
     * @param refraction     The refraction coefficient of the object.
     * @param specularPower  The specular power of the object.
     */

    public Object3D(Vector3D position, Color color, double reflection, double refraction, double specularPower) {
        setPosition(position);
        setColor(color);
        setReflection(reflection);
        setRefraction(refraction);
        setSpecularPower(specularPower);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Vector3D getPosition() {
        return position;
    }

    public void setPosition(Vector3D position) {
        this.position = position;
    }

    public double getReflection() {
        return reflection;
    }

    public void setReflection(double reflection) {
        this.reflection = reflection;
    }

    public double getRefraction() {
        return refraction;
    }

    public void setRefraction(double refraction) {
        this.refraction = refraction;
    }

    public double getSpecularPower() {
        return specularPower;
    }

    public void setSpecularPower(double specularPower) {
        this.specularPower = specularPower;
    }
}
