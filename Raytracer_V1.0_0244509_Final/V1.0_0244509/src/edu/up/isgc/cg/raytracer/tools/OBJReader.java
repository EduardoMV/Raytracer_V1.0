/**
 * [1968] - [2022] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package edu.up.isgc.cg.raytracer.tools;

/**
 * @author Eduardo Martínez
 * @coauthor Jafet Rodriguez
 */

import edu.up.isgc.cg.raytracer.Vector3D;
import edu.up.isgc.cg.raytracer.objects.Model3D;
import edu.up.isgc.cg.raytracer.objects.Triangle;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class OBJReader {

    /**
     * Reads a model file and constructs a 3D model object.
     *
     * @param path           The path to the model file.
     * @param origin         The origin point of the model.
     * @param color          The color of the model.
     * @param reflection     The reflection coefficient of the model.
     * @param refraction     The refraction coefficient of the model.
     * @param specularPower  The specular power of the model.
     * @param alpha          The alpha angle for rotation.
     * @param beta           The beta angle for rotation.
     * @param theta          The theta angle for rotation.
     * @param scale          The scaling factor of the model.
     * @return The constructed Model3D object.
     */

    public static Model3D getModel3D(String path, Vector3D origin, Color color, double reflection, double refraction, double specularPower, double alpha, double beta, double theta, double scale) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));

            List<Triangle> triangles = new ArrayList<Triangle>();
            List<Vector3D> vertices = new ArrayList<Vector3D>();
            List<Vector3D> normals = new ArrayList<Vector3D>();
            String line;
            int defaultSmoothingGroup = -1;
            int smoothingGroup = defaultSmoothingGroup;
            Map<Integer, List<Triangle>> smoothingMap = new HashMap<>();
            alpha = Math.toRadians(alpha);
            beta = Math.toRadians(beta);
            theta = Math.toRadians(theta);


            while ((line = reader.readLine()) != null) {
                if (line.startsWith("v ") || line.startsWith("vn ")) {
                    String[] vertexComponents = line.split("(\\s)+");
                    if (vertexComponents.length >= 4) {
                        double x = Double.parseDouble(vertexComponents[1]) * scale;
                        double y = Double.parseDouble(vertexComponents[2]) * scale;
                        double z = Double.parseDouble(vertexComponents[3]) * scale;

                        if (line.startsWith("v ")) {
                            // Rotate around X-axis
                            double rotatedX = x;
                            double rotatedY = Math.cos(alpha) * y + Math.sin(alpha) * z;
                            double rotatedZ = -Math.sin(alpha) * y + Math.cos(alpha) * z;

                            // Rotate around Y-axis
                            double tempX = Math.cos(beta) * rotatedX + Math.sin(beta) * rotatedZ;
                            double tempY = rotatedY;
                            double tempZ = -Math.sin(beta) * rotatedX + Math.cos(beta) * rotatedZ;

                            // Rotate around Z-axis
                            double finalX = Math.cos(theta) * tempX - Math.sin(theta) * tempY;
                            double finalY = Math.sin(theta) * tempX + Math.cos(theta) * tempY;
                            double finalZ = tempZ;

                            vertices.add(new Vector3D(finalX * scale, finalY * scale, finalZ * scale));
                        } else {
                            normals.add(new Vector3D(x, y, z));
                        }
                    }
                } else if (line.startsWith("f ")) {
                    String[] faceComponents = line.split("(\\s)+");
                    List<Integer> faceVertex = new ArrayList<Integer>();
                    List<Integer> faceNormal = new ArrayList<Integer>();

                    for (int i = 1; i < faceComponents.length; i++) {
                        String[] infoVertex = faceComponents[i].split("/");
                        if (infoVertex.length >= 3) {
                            int vertexIndex = Integer.parseInt(infoVertex[0]);
                            int normalIndex = Integer.parseInt(infoVertex[2]);
                            faceVertex.add(vertexIndex);
                            faceNormal.add(normalIndex);
                        }
                    }

                    if (faceVertex.size() >= 3) {
                        Vector3D[] triangleVertices = new Vector3D[faceVertex.size()];
                        Vector3D[] triangleVerticesNormals = new Vector3D[faceNormal.size()];

                        for (int i = 0; i < faceVertex.size(); i++) {
                            triangleVertices[i] = (vertices.get(faceVertex.get(i) - 1));
                        }

                        Vector3D[] arrangedTriangleVertices = null;
                        Vector3D[] arrangedTriangleNormals = null;
                        if (normals.size() > 0) {
                            for (int i = 0; i < faceNormal.size(); i++) {
                                triangleVerticesNormals[i] = (normals.get(faceNormal.get(i) - 1));
                            }
                            arrangedTriangleNormals = new Vector3D[]{triangleVerticesNormals[1], triangleVerticesNormals[0], triangleVerticesNormals[2]};
                        }
                        arrangedTriangleVertices = new Vector3D[]{triangleVertices[1], triangleVertices[0], triangleVertices[2]};


                        Triangle tmpTriangle = new Triangle(arrangedTriangleVertices, arrangedTriangleNormals);
                        triangles.add(tmpTriangle);

                        List<Triangle> trianglesInMap = smoothingMap.get(smoothingGroup);
                        if (trianglesInMap == null) {
                            trianglesInMap = new ArrayList<>();
                        }
                        trianglesInMap.add(tmpTriangle);

                        if (faceVertex.size() == 4) {
                            arrangedTriangleVertices = new Vector3D[]{triangleVertices[2], triangleVertices[0], triangleVertices[3]};
                            if (arrangedTriangleNormals != null) {
                                arrangedTriangleNormals = new Vector3D[]{triangleVerticesNormals[2], triangleVerticesNormals[0], triangleVerticesNormals[3]};
                            }
                            tmpTriangle = new Triangle(arrangedTriangleVertices, arrangedTriangleNormals);
                            triangles.add(tmpTriangle);
                            trianglesInMap.add(tmpTriangle);
                        }

                        if (smoothingGroup != defaultSmoothingGroup) {
                            smoothingMap.put(smoothingGroup, trianglesInMap);
                        }
                    }
                } else if (line.startsWith("s ")) {
                    String[] smoothingComponents = line.split("(\\s)+");
                    if (smoothingComponents.length > 1) {
                        if (smoothingComponents[1].equals("off")) {
                            smoothingGroup = defaultSmoothingGroup;
                        } else {
                            try {
                                smoothingGroup = Integer.parseInt(smoothingComponents[1]);
                            } catch (NumberFormatException nfe) {
                                smoothingGroup = defaultSmoothingGroup;
                            }
                        }
                    }
                }
            }
            reader.close();

            class NormalPair {
                Vector3D normal;
                int count;

                public NormalPair() {
                    normal = Vector3D.ZERO();
                    count = 0;
                }
            }

            //Smooth vertices normals
            for (Integer key : smoothingMap.keySet()) {
                Map<Vector3D, NormalPair> vertexMap = new HashMap<>();
                List<Triangle> trianglesInMap = smoothingMap.get(key);
                for (Triangle triangle : trianglesInMap) {
                    Vector3D[] triangleVertices = triangle.getVertices();
                    Vector3D[] triangleNormals = triangle.getNormals();
                    for (int i = 0; i < triangleVertices.length; i++) {
                        NormalPair normalsVertex = vertexMap.get(triangleVertices[i]);
                        if (normalsVertex == null) {
                            normalsVertex = new NormalPair();
                        }
                        if (triangleNormals.length > 0 && i < triangleNormals.length) {
                            normalsVertex.normal = Vector3D.add(normalsVertex.normal, triangleNormals[i]);
                            normalsVertex.count++;
                        }
                        vertexMap.put(triangleVertices[i], normalsVertex);
                    }
                }
                for (Triangle triangle : trianglesInMap) {
                    Vector3D[] triangleVertices = triangle.getVertices();
                    Vector3D[] triangleNormals = triangle.getNormals();
                    for (int i = 0; i < triangleVertices.length; i++) {
                        NormalPair normalsVertex = vertexMap.get(triangleVertices[i]);
                        triangleNormals[i] = Vector3D.scalarMultiplication(normalsVertex.normal, 1.0 / (double) normalsVertex.count);
                    }
                    triangle.setNormals(triangleNormals);
                }
            }
            origin = new Vector3D(origin.getX() * scale, origin.getY() * scale, origin.getZ() * scale);

            return new Model3D(origin, triangles.toArray(new Triangle[triangles.size()]), color, reflection, refraction, specularPower);
        } catch (FileNotFoundException ex) {
            System.err.println("File not found");
        } catch (IOException ex) {
            System.err.println("Exception found");
        }
        return null;
    }

}
