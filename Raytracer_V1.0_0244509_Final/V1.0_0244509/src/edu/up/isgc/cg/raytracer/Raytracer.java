package edu.up.isgc.cg.raytracer;

/**
 * @author Eduardo Martínez
 * @coauthor Jafet Rodriguez
 */


import edu.up.isgc.cg.raytracer.lights.Light;
import edu.up.isgc.cg.raytracer.lights.PointLight;
import edu.up.isgc.cg.raytracer.lights.SpotLight;
import edu.up.isgc.cg.raytracer.objects.*;
import edu.up.isgc.cg.raytracer.tools.OBJReader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class Raytracer {

    public static BufferedImage image;
    public static double ambient = 100;
    public static double shadowBias = 1e-2;

    /**
     * The principal method of the code containing all the execution of the raytracer, creates an image.
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(new Date());

        Scene scene01 = new Scene();
        scene01.setCamera(new Camera(new Vector3D(0, 0, -6), 60, 60, 1920, 1080, 0.6, 50.0));
        scene01.addObject(OBJReader.getModel3D("Raytracer_V1.0_0244509_Final\\V1.0_0244509\\Scene1\\stairsS.obj", new Vector3D(0.5, -1, 1), new Color(195, 42, 42), 0.0, 0.0 , 500, 0, 0.35, 0,1));
        scene01.addObject(OBJReader.getModel3D("Raytracer_V1.0_0244509_Final\\V1.0_0244509\\Scene1\\shoe2.obj", new Vector3D(0.5, -1, 1), new Color(75, 221, 241), 0.8, 0, 5, 0, 0.10, 0,1));
        scene01.addObject(OBJReader.getModel3D("Raytracer_V1.0_0244509_Final\\V1.0_0244509\\Scene1\\pumpk.obj", new Vector3D(1, -1, 1), new Color(255, 117, 24), 0.0, 0, 50, 0, 0, 0,1));
        scene01.addObject(OBJReader.getModel3D("Raytracer_V1.0_0244509_Final\\V1.0_0244509\\Scene1\\rat.obj", new Vector3D(0.5, -1.05, 1), new Color(108, 110, 107), 0.0, 0, 500, 0, 0, 0,1));
        scene01.addObject(OBJReader.getModel3D("Raytracer_V1.0_0244509_Final\\V1.0_0244509\\Scene1\\mirror.obj", new Vector3D(0.5, -1, 1), new Color(168, 176, 178), 0.8, 0, 5, 0, 0, 0,1));
        scene01.addObject(OBJReader.getModel3D("Raytracer_V1.0_0244509_Final\\V1.0_0244509\\Scene1\\frame.obj", new Vector3D(0.5, -1, 1), new Color(204, 179, 74), 0.01, 0, 5, 0, 0, 0,1));
        scene01.addObject(OBJReader.getModel3D("Raytracer_V1.0_0244509_Final\\V1.0_0244509\\Scene1\\room.obj", new Vector3D(0.5, -1, 1.2), new Color(232, 213, 199), 0.0, 0, 5, 0, 0, 0,1));
        scene01.addObject(OBJReader.getModel3D("Raytracer_V1.0_0244509_Final\\V1.0_0244509\\Scene1\\floor.obj", new Vector3D(0.5, 0.05, 1.2), new Color(232, 213, 199), 0.08, 0, 15, 0, 0, 0,1));
        scene01.addObject(new Sphere(new Vector3D(-2.2, -1, -2), 0.8, Color.red, 0.0, 1.3, 25));
        scene01.addLight(new PointLight(new Vector3D(1.0,2.0,-2.0),new Color(229, 204, 204),2));
        scene01.addLight(new PointLight(new Vector3D(-1.0,2.0,-2.0),new Color(229, 204, 204),2));
        scene01.addLight(new PointLight(new Vector3D(0.1,0.5,-3.7),new Color(229, 204, 204),1));
        scene01.addLight(new PointLight(new Vector3D(1,-0.5,-3.0),new Color(183, 0, 0),0.5));
        scene01.addLight(new PointLight(new Vector3D(-1,-0.5,-3.0),new Color(183, 0, 0),0.5));

        Scene scene02 = new Scene();
        scene02.setCamera(new Camera(new Vector3D(-0.2, 2.3, -7), 60, 60, 1920, 1080, 0.6, 50.0));
        scene02.addObject(OBJReader.getModel3D("Raytracer_V1.0_0244509_Final\\V1.0_0244509\\Scene2\\TBox.obj", new Vector3D(0, 0, 0), new Color(61, 65, 219), 0.8, 0, 50, 0, 0, 0, 1));
        scene02.addObject(OBJReader.getModel3D("Raytracer_V1.0_0244509_Final\\V1.0_0244509\\Scene2\\happyM.obj", new Vector3D(0, 0, 0), new Color(255, 255, 255), 0.0, 0, 5, 0, 0, 0, 1));
        scene02.addObject(OBJReader.getModel3D("Raytracer_V1.0_0244509_Final\\V1.0_0244509\\Scene2\\sadM.obj", new Vector3D(0, 0, 0), new Color(54, 53, 53), 0.0, 0, 5, 0, 0, 0, 1));
        scene02.addLight(new PointLight(new Vector3D(1.0,2.0,-2.5),new Color(255, 0, 0),3));
        scene02.addLight(new PointLight(new Vector3D(-1.0,2.0,-2.5),new Color(0, 255, 0),2));
        scene02.addLight(new SpotLight(new Vector3D(0,0.5,-3.7), new Vector3D(0,-1,-3.7), new Color(255, 255, 255),2, 0.35,0.20));
        scene02.addLight(new PointLight(new Vector3D(1,-0.5,-3.5),new Color(255, 255, 255),0.5));
        scene02.addLight(new PointLight(new Vector3D(-1,-0.5,-3.5),new Color(183, 0, 0),0.5));

        Scene scene03 = new Scene();
        scene03.setCamera(new Camera(new Vector3D(0, 4, -5.5), 130, 110, 1920, 1080, 0.6, 50.0));
        scene03.addObject(OBJReader.getModel3D("Raytracer_V1.0_0244509_Final\\V1.0_0244509\\Scene3\\box1.obj", new Vector3D(0.0, 0.0, 0.0), new Color(75, 55, 51), 0.0, 0, 15, 0, 0, 0, 1));
        scene03.addObject(OBJReader.getModel3D("Raytracer_V1.0_0244509_Final\\V1.0_0244509\\Scene3\\box2.obj", new Vector3D(0.0, 0.0, 0.0), new Color(75, 55, 51), 0.0, 0, 15, 0, 0, 0, 1));
        scene03.addObject(OBJReader.getModel3D("Raytracer_V1.0_0244509_Final\\V1.0_0244509\\Scene3\\hand1.obj", new Vector3D(0.0, 0.0, 0.0), new Color(247, 234, 208), 0.0, 0, 15, 0, 0.15,0,1));
        scene03.addObject(OBJReader.getModel3D("Raytracer_V1.0_0244509_Final\\V1.0_0244509\\Scene3\\hand2.obj", new Vector3D(0.0, 0.0, 0.0), new Color(247, 234, 208), 0.0, 0, 15, 0, 0, 0, 1));
        scene03.addObject(OBJReader.getModel3D("Raytracer_V1.0_0244509_Final\\V1.0_0244509\\Scene3\\heart.obj", new Vector3D(0.0, 0.0, 0.0), new Color(168, 3, 3), 0.8, 0, 15, 0, 0, 0,1 ));
        scene03.addObject(OBJReader.getModel3D("Raytracer_V1.0_0244509_Final\\V1.0_0244509\\Scene3\\brain.obj", new Vector3D(0.0, 0.0, 0.0), new Color(220, 149, 167), 0.0, 0, 15, 0, 0, 0,1 ));
        scene03.addObject(OBJReader.getModel3D("Raytracer_V1.0_0244509_Final\\V1.0_0244509\\Scene3\\tapa.obj", new Vector3D(0.0, 0.0, 0.0), new Color(25, 83, 255), 0.0, 1.7, 15, 0, 0, 0,1));
        scene03.addLight(new PointLight(new Vector3D(-1.0,2.0,-2.0),new Color(229, 204, 204),4));
        scene03.addLight(new PointLight(new Vector3D(0,0.5,-3.7),new Color(229, 204, 204),1));
        scene03.addLight(new PointLight(new Vector3D(1,-0.5,-3.0),new Color(183, 0, 0),0.5));
        scene03.addLight(new PointLight(new Vector3D(-1,-0.5,-3.0),new Color(183, 0, 0),0.5));

        image = raytrace(scene01);
        File outputImage = new File("scene01Mirror.png");
        try{
            ImageIO.write(image, "png", outputImage);
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Error on creation");
        }
        System.out.println(new Date());
    }

    /**
     *
     * @param scene
     * this method initializes variables and objects related to the scene.
     * The available processor cores are determined, and a thread pool is created for parallel execution.
     * Tasks are created to perform ray tracing calculations for each pixel of the image.
     * Positions in 3D space to be ray traced are determined using the main camera.
     * Rays are created for each position and tested for intersections with objects using the raycast method.
     * If an intersection is found, the pixel color is calculated based on lighting models.
     * The resulting pixel color is added to the image.
     * Finally, the tasks are executed using the thread pool, and the image is returned.
     *
     * Helper methods:
     * addColor: Adds two colors, clamping the resulting RGB values between 0 and 1.
     * merge: Blends two colors based on a given percentage.
     * raycast: Tests ray-object intersections and returns the closest intersection.
     * clamp: Clamps a value between a minimum and maximum range.
     * @return BufferedImage
     */
    public static BufferedImage raytrace(Scene scene) {
        Camera mainCamera = scene.getCamera();
        double[] nearFarPlanes = mainCamera.getNearFarPlanes();
        double cameraZ = mainCamera.getPosition().getZ();
        BufferedImage image = new BufferedImage(mainCamera.getResolutionWidth(), mainCamera.getResolutionHeight(), BufferedImage.TYPE_INT_RGB);
        List<Object3D> objects = scene.getObjects();
        List<Light> lights = scene.getLights();

        int numThreads = Runtime.getRuntime().availableProcessors(); // Number of available processor cores
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Callable<Void>> tasks = new ArrayList<>();

        Vector3D[][] positionsToRaytrace = mainCamera.calculatePositionsToRay();

        for (int i = 0; i < positionsToRaytrace.length; i++) {
            final int row = i;
            tasks.add(() -> {
                for (int j = 0; j < positionsToRaytrace[row].length; j++) {
                    double x = positionsToRaytrace[row][j].getX() + mainCamera.getPosition().getX();
                    double y = positionsToRaytrace[row][j].getY() + mainCamera.getPosition().getY();
                    double z = positionsToRaytrace[row][j].getZ() + mainCamera.getPosition().getZ();

                    Ray ray = new Ray(mainCamera.getPosition(), new Vector3D(x, y, z));
                    Intersection closestIntersection = raycast(ray, objects, null,
                            new double[] { cameraZ + nearFarPlanes[0], cameraZ + nearFarPlanes[1] });

                    Color pixelColor = Color.BLACK;
                    if (closestIntersection != null) {
                        Color object = closestIntersection.getObject().getColor();

                        //Refraction
                        if(closestIntersection.getObject().getRefraction() > 0.0){
                            Intersection refraction = LightInteractions.refraction(scene, ray, closestIntersection);
                            if(refraction != null){
                                closestIntersection = refraction;
                            }
                            Color after = closestIntersection.getObject().getColor();
                            object = merge(object, after, closestIntersection.getObject().getRefraction());
                        }

                        //Reflection
                        if(closestIntersection.getObject().getReflection() > 0.0){
                            Intersection reflection = LightInteractions.reflection(closestIntersection, objects, mainCamera, 0);
                            if(reflection != null){
                                closestIntersection = reflection;
                            }
                            object = closestIntersection.getObject().getColor();
                        }

                        for (Light light : lights) {
                            Vector3D lightOff = Vector3D.substract(closestIntersection.getPosition(), Vector3D.scalarMultiplication(closestIntersection.getNormal(), shadowBias));
                            Vector3D globalLight = Vector3D.substract(light.getPosition(), lightOff);
                            double lightDistance = Math.pow(Vector3D.magnitude(globalLight), 1);
                            Ray shadow = new Ray(lightOff, globalLight);

                            Color lightColor = light.getColor();
                            double[] lightColors = new double[]{lightColor.getRed() / 255.0, lightColor.getGreen() / 255.0, lightColor.getBlue() / 255.0};
                            Color ambientColor = new Color(clamp((float) (lightColors[0] / ambient), 0, 1), clamp((float) (lightColors[1] / ambient), 0, 1), clamp((float) (lightColors[2] / ambient), 0, 1));
                            pixelColor = addColor(pixelColor, ambientColor);
                            Intersection shadowIntersection = raycast(shadow, objects, closestIntersection.getObject(), new double[]{cameraZ + nearFarPlanes[0], cameraZ + nearFarPlanes[1]});

                            if(shadowIntersection == null || shadowIntersection.getObject().getRefraction() > 0.0){
                                // Specular
                                Vector3D viewDirection = Vector3D.normalize(Vector3D.substract(mainCamera.getPosition(), closestIntersection.getPosition()));
                                Vector3D lightDirection = Vector3D.normalize(Vector3D.substract(light.getPosition(), closestIntersection.getPosition()));
                                Vector3D halfVector = Vector3D.normalize(Vector3D.add(viewDirection, lightDirection));

                                double nDotH = Vector3D.dotProduct(closestIntersection.getNormal(), halfVector);
                                double specular = Math.pow(nDotH, closestIntersection.getObject().getSpecularPower());

                                //Diffuse
                                double nDotL = light.getNDotL(closestIntersection);
                                double intensity = (light.getIntensity()/lightDistance)  * nDotL;
                                //double intensity = light.getIntensity()  * nDotL;
                                double[] colorOBJ = new double[]{object.getRed()/255f, object.getGreen()/255f, object.getBlue()/255f};
                                for(int c = 0; c < colorOBJ.length; c++){
                                    //3 for RGB
                                    colorOBJ[c] *= (intensity + specular) * lightColors[c];
                                }
                                Color diffuse = new Color(clamp((float) colorOBJ[0], 0, 1),
                                        clamp((float) colorOBJ[1], 0, 1), clamp((float) colorOBJ[2], 0, 1));
                                pixelColor = addColor(pixelColor, diffuse);
                            } else {
                                pixelColor = addColor(pixelColor, Color.BLACK);
                            }
                        }
                    }
                    synchronized (image) {
                        image.setRGB(row, j, pixelColor.getRGB());
                    }
                }
                return null;
            });
        }

        try {
            List<Future<Void>> futures = executor.invokeAll(tasks);
            for (Future<Void> future : futures) {
                future.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdownNow();
        }

        return image;
    }

    public static Color addColor(Color original, Color otherColor) {
        float red = clamp((original.getRed() / 255f) + (otherColor.getRed() / 255f), 0, 1);
        float green = clamp((original.getGreen() / 255f) + (otherColor.getGreen() / 255f), 0, 1);
        float blue = clamp((original.getBlue() / 255f) + (otherColor.getBlue() / 255f), 0, 1);
        return new Color(red, green, blue);
    }

    public static Color merge(Color original, Color otherColor, double percentage) {
        float red = (float) ((original.getRed() * (1f-percentage) / 255f) + (otherColor.getRed() * percentage / 255f));
        float green = (float) ((original.getGreen() * (1f-percentage) / 255f) + (otherColor.getGreen() * percentage / 255f));
        float blue = (float) ((original.getBlue() * (1f-percentage) / 255f) + (otherColor.getBlue() * percentage / 255f));
        return new Color(red, green, blue);
    }

    public static Intersection raycast(Ray ray, List<Object3D> objects, Object3D caster, double[] clippingPlanes) {
        Intersection closestIntersection = null;

        for (int k = 0; k < objects.size(); k++) {
            Object3D currentObj = objects.get(k);
            if (caster == null || !currentObj.equals(caster)) {
                Intersection intersection = currentObj.getIntersection(ray);
                if (intersection != null) {
                    double distance = intersection.getDistance();
                    double intersectionZ = intersection.getPosition().getZ();
                    if (distance >= 0 &&
                            (closestIntersection == null || distance < closestIntersection.getDistance()) &&
                            (clippingPlanes == null || (intersectionZ >= clippingPlanes[0] && intersectionZ <= clippingPlanes[1]))) {
                        closestIntersection = intersection;
                    }
                }
            }
        }

        return closestIntersection;
    }

    public static float clamp(float value, float min, float max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }


}
