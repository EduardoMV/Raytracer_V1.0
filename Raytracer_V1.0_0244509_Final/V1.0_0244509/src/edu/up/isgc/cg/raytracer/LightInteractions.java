package edu.up.isgc.cg.raytracer;

/**
 * @author Eduardo Martínez
 */

import edu.up.isgc.cg.raytracer.objects.Camera;
import edu.up.isgc.cg.raytracer.objects.Object3D;

import java.util.List;

import static edu.up.isgc.cg.raytracer.Raytracer.raycast;

public class LightInteractions {

    /**
     * Calculates the reflection of an intersection point, if the object at the
     * intersection has a positive reflection value and the replicas are not
     * exceeded it calculates the reflected ray recursively.
     * @param intersection The intersection point
     * @param objects List of objects in scene
     * @param camera The camera in the scene
     * @param replications Number of recursions
     * @return The intersection point after reflection,
     * or the original intersection if no reflection occurs.
     */
    public static Intersection reflection(Intersection intersection, List<Object3D> objects, Camera camera, int replications) {
        double reflectionValue = intersection.getObject().getReflection();
        if (reflectionValue > 0 && replications <= 2) {
            Vector3D normal = intersection.getNormal();
            Vector3D viewer = Vector3D.substract(intersection.getPosition(), camera.getPosition());
            Vector3D dNormal = Vector3D.scalarMultiplication(normal, -2);

            double nDotV = Vector3D.dotProduct(normal, viewer);

            Vector3D reflection = Vector3D.add(Vector3D.scalarMultiplication(dNormal, nDotV), viewer);
            Ray reflectionRay = new Ray(intersection.getPosition(), reflection);

            for (Object3D object : objects) {
                if (!object.equals(intersection.getObject())) {
                    Intersection intersectedReflection = raycast(reflectionRay, objects, intersection.getObject(), null);
                    if (intersectedReflection != null) {
                        return reflection(intersectedReflection, objects, camera, replications + 1);
                    }
                    return intersection;
                }
            }
        }
        return intersection;
    }

    /**
     * Calculates the refraction of a ray at an intersection point, it determines
     * the incident ray's refractive index, as well as the object's index;
     * Then it calculates the refraction direction based on Snell's law and checks
     * for intersections with other objects to reflect them.
     * @param scene The scene containing the image elements
     * @param ray The incident ray
     * @param intersection The intersection point
     * @return The intersection point after refraction, or null if total internal reflection occurs
     */
    public static Intersection refraction(Scene scene, Ray ray, Intersection intersection) {
        Vector3D incident = Vector3D.normalize(ray.getDirection());
        double n1 = 1.0; // Air's refractive index
        double n2 = intersection.getObject().getRefraction();

        Vector3D normal = intersection.getNormal();
        double cosTheta1 = -Vector3D.dotProduct(incident, normal);

        if (cosTheta1 < 0) {
            // Ray is inside the object, swap refractive indices and invert the normal
            double temp = n1;
            n1 = n2;
            n2 = temp;
            normal = Vector3D.scalarMultiplication(normal, -1);
            cosTheta1 = -cosTheta1;
        }

        double n = n1 / n2;
        double sinTheta2Squared = n * n * (1 - cosTheta1 * cosTheta1);

        if (sinTheta2Squared > 1.0) {
            return null;
        }

        double cosTheta2 = Math.sqrt(1 - sinTheta2Squared);
        Vector3D refractionDirection = Vector3D.add(Vector3D.scalarMultiplication(incident, n),
                Vector3D.scalarMultiplication(normal, n * cosTheta1 - cosTheta2));
        Ray refractionRay = new Ray(intersection.getPosition(), refractionDirection);

        return raycast(refractionRay, scene.getObjects(), intersection.getObject(), null);
    }




}
