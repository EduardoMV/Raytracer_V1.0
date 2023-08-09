package edu.up.isgc.cg.raytracer;

/**
 * @author Eduardo Martínez
 * @coauthor Jafet Rodriguez
 */

import edu.up.isgc.cg.raytracer.objects.Camera;
import edu.up.isgc.cg.raytracer.lights.Light;
import edu.up.isgc.cg.raytracer.objects.Object3D;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    private Camera camera;
    private List<Object3D> objects;
    private List<Light> lights;

    /**
     * Constructor containing the list of objects and lights of the scene
     */
    public Scene() {
        setObjects(new ArrayList<>());
        setLights(new ArrayList<>());
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void addObject(Object3D object){
        getObjects().add(object);
    }

    public void addLight(Light light){
        getLights().add(light);
    }

    public List<Object3D> getObjects() {
        if(objects == null){
            objects = new ArrayList<>();
        }
        return objects;
    }

    public void setObjects(List<Object3D> objects) {
        this.objects = objects;
    }

    public List<Light> getLights() {
        return lights;
    }

    public void setLights(List<Light> lights) {
        this.lights = lights;
    }
}

