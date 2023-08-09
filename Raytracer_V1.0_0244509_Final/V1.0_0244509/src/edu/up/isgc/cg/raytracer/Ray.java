package edu.up.isgc.cg.raytracer;

/**
 * @author Eduardo Martínez
 * @coauthor Jafet Rodriguez
 */

public class Ray {
    private Vector3D origin;
    private Vector3D direction;

    /**
     * Constructor of the Scene ray's
     * @param origin The starting point
     * @param direction The point where is directed
     */
    public Ray(Vector3D origin, Vector3D direction) {
        setOrigin(origin);
        setDirection(direction);
    }

    public Vector3D getOrigin() {
        return origin;
    }

    public void setOrigin(Vector3D origin) {
        this.origin = origin;
    }

    public Vector3D getDirection() {
        return Vector3D.normalize(direction);
    }

    public void setDirection(Vector3D direction) {
        this.direction = direction;
    }
}
