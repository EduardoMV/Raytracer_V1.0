package edu.up.isgc.cg.raytracer.objects;

/**
 * @author Eduardo Martínez
 * @coauthor Jafet Rodriguez
 */

import edu.up.isgc.cg.raytracer.Intersection;
import edu.up.isgc.cg.raytracer.Ray;
import edu.up.isgc.cg.raytracer.Vector3D;
import edu.up.isgc.cg.raytracer.tools.BarycentricCalculator;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a 3D model composed of triangles in a 3D scene.
 */

public class Model3D extends Object3D{
    private List<Triangle> triangles;

    /**
     * Constructs a Model3D object with the specified parameters.
     *
     * @param position       The position of the model.
     * @param triangles      An array of triangles composing the model.
     * @param color          The color of the model.
     * @param reflection     The reflection coefficient of the model.
     * @param refraction     The refraction coefficient of the model.
     * @param specularPower  The specular power of the model.
     */

    public Model3D(Vector3D position, Triangle[] triangles, Color color, double reflection, double refraction, double specularPower) {
        super(position, color, reflection, refraction, specularPower);
        setTriangles(triangles);
    }

    public List<Triangle> getTriangles() {
        return triangles;
    }

    /**
     * Sets the triangles of the model and adjusts their vertices to their respective positions in 3D space.
     *
     * @param triangles  An array of triangles composing the model.
     */

    public void setTriangles(Triangle[] triangles) {
        Vector3D position = getPosition();
        Set<Vector3D> uniqueVertices = new HashSet<>();
        for(Triangle triangle : triangles){
            uniqueVertices.addAll(Arrays.asList(triangle.getVertices()));
        }

        for(Vector3D vertex : uniqueVertices){
            vertex.setX(vertex.getX() + position.getX());
            vertex.setY(vertex.getY() + position.getY());
            vertex.setZ(vertex.getZ() + position.getZ());
        }

        this.triangles = Arrays.asList(triangles);
    }

    @Override
    public Intersection getIntersection(Ray ray) {
        double distance = -1;
        Vector3D normal = Vector3D.ZERO();
        Vector3D position = Vector3D.ZERO();

        for(Triangle triangle : getTriangles()){
            Intersection intersection = triangle.getIntersection(ray);
            double intersectionDistance = intersection.getDistance();
            if(intersection != null && intersectionDistance > 0 && (intersectionDistance < distance ||distance < 0)){
                distance = intersectionDistance;
                position = Vector3D.add(ray.getOrigin(), Vector3D.scalarMultiplication(ray.getDirection(), distance));
                normal = Vector3D.ZERO();
                double[] uVw = BarycentricCalculator.Coordinates(position, triangle);
                Vector3D[] normals = triangle.getNormals();
                for(int i = 0; i < uVw.length; i++) {
                    normal = Vector3D.add(normal, Vector3D.scalarMultiplication(normals[i], uVw[i]));
                }
            }
        }

        if(distance == -1){
            return null;
        }

        return new Intersection(position, distance, normal, this);
    }
}
