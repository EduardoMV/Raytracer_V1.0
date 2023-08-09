package edu.up.isgc.cg.raytracer.objects;

/**
 * @author Eduardo Martínez
 * @coauthor Jafet Rodriguez
 */

import edu.up.isgc.cg.raytracer.Intersection;
import edu.up.isgc.cg.raytracer.Ray;
import edu.up.isgc.cg.raytracer.Vector3D;

public class Triangle implements IIntersectable{
    public static final double EPSILON = 0.0000001;
    private Vector3D[] vertices;
    private Vector3D[] normals;

    /**
     * Constructs a Triangle object with the specified vertices and normals.
     *
     * @param v1 The first vertex of the triangle.
     * @param v2 The second vertex of the triangle.
     * @param v3 The third vertex of the triangle.
     * @param n1 The normal vector at the first vertex.
     * @param n2 The normal vector at the second vertex.
     * @param n3 The normal vector at the third vertex.
     */

    public Triangle(Vector3D v1, Vector3D v2, Vector3D v3, Vector3D n1, Vector3D n2, Vector3D n3) {
        setVertices(v1, v2, v3);
        setNormals(n1, n2, n3);
    }

    /**
     * Constructs a Triangle object with the specified vertices and shared normal.
     *
     * @param vertices The array of vertices representing the triangle.
     * @param normal   The shared normal vector for all vertices.
     */
    public Triangle(Vector3D[] vertices, Vector3D[] normal) {
        if (vertices.length == 3) {
            setVertices(vertices[0], vertices[1], vertices[2]);
        } else {
            setVertices(Vector3D.ZERO(), Vector3D.ZERO(), Vector3D.ZERO());
        }
        setNormals(normal);
    }

    public Vector3D[] getVertices() {
        return vertices;
    }

    public void setVertices(Vector3D v1, Vector3D v2, Vector3D v3) {
        Vector3D[] vertices = new Vector3D[]{v1, v2, v3};
        setVertices(vertices);
    }

    private void setVertices(Vector3D[] vertices) {
        this.vertices = vertices;
    }

    public Vector3D[] getNormals() {
        if(normals == null){
            Vector3D normal = getNormal();
            setNormals(new Vector3D[]{normal, normal, normal});
        }
        return normals;
    }

    public Vector3D getNormal(){
        Vector3D normal = Vector3D.ZERO();

        Vector3D[] normals = this.normals;
        if (normals == null) {
            Vector3D[] vertices = getVertices();
            Vector3D v = Vector3D.substract(vertices[1], vertices[0]);
            Vector3D w = Vector3D.substract(vertices[2], vertices[0]);

            normal = Vector3D.scalarMultiplication(Vector3D.normalize(Vector3D.crossProduct(v, w)), -1.0);
        } else {
            for(int i = 0; i < normals.length; i++){
                normal.setX(normal.getX() + normals[i].getX());
                normal.setY(normal.getY() + normals[i].getY());
                normal.setZ(normal.getZ() + normals[i].getZ());
            }
            normal.setX(normal.getX() / normals.length);
            normal.setY(normal.getY() / normals.length);
            normal.setZ(normal.getZ() / normals.length);
        }

        return normal;
    }

    public void setNormals(Vector3D n1, Vector3D n2, Vector3D n3) {
        Vector3D[] normals = new Vector3D[]{n1, n2, n3};
        setNormals(normals);
    }

    public void setNormals(Vector3D[] normals) {
        this.normals = normals;
    }

    @Override
    public Intersection getIntersection(Ray ray) {
        Intersection intersection = new Intersection(null, -1, null, null);

        Vector3D[] vert = getVertices();
        Vector3D v2v0 = Vector3D.substract(vert[2], vert[0]);
        Vector3D v1v0 = Vector3D.substract(vert[1], vert[0]);
        Vector3D vectorP = Vector3D.crossProduct(ray.getDirection(), v1v0);
        Vector3D vectorT = Vector3D.substract(ray.getOrigin(), vert[0]);

        double determinant = Vector3D.dotProduct(v2v0, vectorP);
        double invDet = 1.0 / determinant;

        double u = invDet * Vector3D.dotProduct(vectorT, vectorP);


        if(!(u < 0 || u > 1)){
            Vector3D vectorQ = Vector3D.crossProduct(vectorT, v2v0);
            double v = invDet * Vector3D.dotProduct(ray.getDirection(), vectorQ);
            if(!(v < 0 || (u + v) > (1.0 + EPSILON))){
                double t = invDet * Vector3D.dotProduct(vectorQ, v1v0);
                intersection.setDistance(t);
            }
        }

        return intersection;
    }
}
