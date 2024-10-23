# Raytracer_V1.0 awarded with SIGGRAPH'S EXHIBIT RIBBON
## Author: Eduardo Ulises Martínez Vaca

<p align="center">
 
 <img src="https://github.com/EduardoMV/EduardoMV/assets/81331013/39c9c3a2-1a0e-4cfb-8cc8-3a8eb270e434"/>
 
</p align="center">

## Overview

**Raytracer V1.0** is a Java-based 3D ray tracing engine developed as part of a computer graphics course at Universidad Panamericana. The engine simulates realistic lighting, shadows, reflections, and material interactions by calculating how light rays interact with 3D objects.

## Key Features

- **Ray Tracing Core**: Ray-sphere and ray-plane intersections.
- **Lighting and Shading**: Ambient, diffuse, and specular reflections.
- **Reflections**: Simulates reflective surfaces.
- **Scene Management**: Define complex scenes with objects, lights, and cameras.
- **Custom Materials**: Reflective and non-reflective material support.
- **Multithreading**: Leverages concurrency to improve performance.

## Project Structure

- **RayTracer.java**: Manages rendering, casting rays from the camera into the scene.
- **Scene.java**: Manages objects, lights, and cameras.
- **Ray.java**: Defines ray properties.
- **Camera.java**: Controls the camera and generates rays.
- **Sphere.java**: Handles ray-sphere intersections.
- **Plane.java**: Handles ray-plane intersections.
- **Light.java**: Manages light properties.
- **Material.java**: Defines material properties like color and reflectivity.

## Installation

### Prerequisites

- **Java JDK 17 or higher**

### Building the Project

1. Clone the repository:
   ```bash
   git clone https://github.com/EduardoMV/Raytracer_V1.0.git

2. Navigate to the project directory:
   ```bash
   cd Raytracer_V1.0_0244509_Final/V1.0_0244509

3. Compile the project:
   ```bash
   javac -d bin src/edu/up/isgc/cg/raytracer/*.java

4. Run the project:
   ```bash
   java -cp bin edu.up.isgc.cg.raytracer.RayTracer

