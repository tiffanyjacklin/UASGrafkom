package Engine;

import org.joml.Vector3f;
import org.joml.Vector4f;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.lang.Object;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Sphere extends Circle{
    Float radiusZ;
    int sectorCount;
    String path;
    List<Integer> index;
    List<Vector3f> normal;
    int nbo;
    int ibo;
    int depthFramebufferId; // ID of the depth framebuffer object
    int depthTextureId; // ID of the depth texture
    int depthTextureWidth; // Width of the depth texture
    int depthTextureHeight; // Height of the depth texture

    public Sphere(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color,
                  List<Float> centerPoint, Float radiusX, Float radiusY, Float radiusZ) {
        super(shaderModuleDataList, vertices, color, centerPoint, radiusX, radiusY);
        this.radiusZ = radiusZ;
        vertices.clear();
        createBox();
        setupVAOVBO();
    }
    public Sphere(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color, List<Vector3f> normal,
                  String path) {
        super(shaderModuleDataList, vertices, color);
        this.path = path;
        this.normal = normal;
        this.vertices = vertices;
        loadObject();
        setupVAOVBO();
    }


    public void loadObject(){
        vertices.clear();
        normal = new ArrayList<>();
        Vector3f temp = new Vector3f();
        ArrayList<Vector3f> tempVertices = new ArrayList<>();

        Model n = new Model();

        try {
            n = ObjLoader.loadModel(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }




        for (Face face : n.faces){
            Vector3f n1 = n.normals.get((int) face.normal.x - 1);
            normal.add(n1);
            Vector3f v1 = n.vertices.get((int) face.vertex.x - 1);
            vertices.add(v1);
            Vector3f n2 = n.normals.get((int) face.normal.y - 1);
            normal.add(n2);
            Vector3f v2 = n.vertices.get((int) face.vertex.y - 1);
            vertices.add(v2);

            Vector3f n3 = n.normals.get((int) face.normal.z - 1);
            normal.add(n3);
            Vector3f v3 = n.vertices.get((int) face.vertex.z - 1);
            vertices.add(v3);

        }

    }
    public Sphere() {
        super(Arrays.asList(
                //shaderFile lokasi menyesuaikan objectnya
                new ShaderProgram.ShaderModuleData
                        ("resources/shaders/scene.vert"
                                , GL_VERTEX_SHADER),
                new ShaderProgram.ShaderModuleData
                        ("resources/shaders/scene.frag"
                                , GL_FRAGMENT_SHADER)
        ));
    }

    public List<Vector3f> getNormal() {
        return normal;
    }

    public void setNormal(List<Vector3f> normal) {
        this.normal = normal;
        setupVAOVBO();
    }


    public List<Vector3f> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vector3f> vertices) {
        this.vertices = vertices;
        setupVAOVBO();
    }

    public void setIndicies(List<Integer> indicies){
        this.index = indicies;
        setupVAOVBO();
        //ibo
        ibo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,Utils.listoInt(index),GL_STATIC_DRAW);
    }


    public void createBox(){
        vertices.clear();
        Vector3f temp = new Vector3f();
        ArrayList<Vector3f> tempVertices = new ArrayList<>();
        //Titik 1 kiri atas belakang
        temp.x = centerPoint.get(0) - radiusX / 2;
        temp.y = centerPoint.get(1) + radiusY / 2;
        temp.z = centerPoint.get(2) - radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();
        //Titik 2 kiri bawah belakang
        temp.x = centerPoint.get(0) - radiusX / 2;
        temp.y = centerPoint.get(1) - radiusY / 2;
        temp.z = centerPoint.get(2) - radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();
        //Titik 3 kanan bawah belakang
        temp.x = centerPoint.get(0) + radiusX / 2;
        temp.y = centerPoint.get(1) - radiusY / 2;
        temp.z = centerPoint.get(2) - radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();
        //Titik 4 kanan atas belakang
        temp.x = centerPoint.get(0) + radiusX / 2;
        temp.y = centerPoint.get(1) + radiusY / 2;
        temp.z = centerPoint.get(2) - radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();
        //Titik 5 kiri atas depan
        temp.x = centerPoint.get(0) - radiusX / 2;
        temp.y = centerPoint.get(1) + radiusY / 2;
        temp.z = centerPoint.get(2) + radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();
        //Titik 6 kiri bawah depan
        temp.x = centerPoint.get(0) - radiusX / 2;
        temp.y = centerPoint.get(1) - radiusY / 2;
        temp.z = centerPoint.get(2) + radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();
        //Titik 7 kanan bawah depan
        temp.x = centerPoint.get(0) + radiusX / 2;
        temp.y = centerPoint.get(1) - radiusY / 2;
        temp.z = centerPoint.get(2) + radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();
        //Titik 8 kanan atas depan
        temp.x = centerPoint.get(0) + radiusX / 2;
        temp.y = centerPoint.get(1) + radiusY / 2;
        temp.z = centerPoint.get(2) + radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();

        //kotak belakang
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(2));

//        vertices.add(tempVertices.get(2));
        vertices.add(tempVertices.get(3));
        vertices.add(tempVertices.get(0));
        //kotak depan
        vertices.add(tempVertices.get(4));
        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(6));

        vertices.add(tempVertices.get(6));
        vertices.add(tempVertices.get(7));
        vertices.add(tempVertices.get(4));
        //kotak samping kiri
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(4));

        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(4));
        //kotak samping kanan
        vertices.add(tempVertices.get(7));
        vertices.add(tempVertices.get(6));
        vertices.add(tempVertices.get(2));

        vertices.add(tempVertices.get(2));
        vertices.add(tempVertices.get(3));
        vertices.add(tempVertices.get(7));
        //kotak atas
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(4));
        vertices.add(tempVertices.get(7));

        vertices.add(tempVertices.get(4));
        vertices.add(tempVertices.get(7));
        vertices.add(tempVertices.get(3));
        //kotak bawah
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(6));

        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(6));
        vertices.add(tempVertices.get(2));

        normal = new ArrayList<>(Arrays.asList(
                //belakang
                new Vector3f(0.0f,0.0f, -1.0f),
                new Vector3f(0.0f,0.0f, -1.0f),
                new Vector3f(0.0f,0.0f, -1.0f),
                new Vector3f(0.0f,0.0f, -1.0f),
                new Vector3f(0.0f,0.0f, -1.0f),
                new Vector3f(0.0f,0.0f, -1.0f),
                //depan
                new Vector3f(0.0f,0.0f, 1.0f),
                new Vector3f(0.0f,0.0f, 1.0f),
                new Vector3f(0.0f,0.0f, 1.0f),
                new Vector3f(0.0f,0.0f, 1.0f),
                new Vector3f(0.0f,0.0f, 1.0f),
                new Vector3f(0.0f,0.0f, 1.0f),
                // kiri
                new Vector3f(-1.0f,0.0f, 0.0f),
                new Vector3f(-1.0f,0.0f, 0.0f),
                new Vector3f(-1.0f,0.0f, 0.0f),
                new Vector3f(-1.0f,0.0f, 0.0f),
                new Vector3f(-1.0f,0.0f, 0.0f),
                new Vector3f(-1.0f,0.0f, 0.0f),
                // kanan
                new Vector3f(1.0f,0.0f, 0.0f),
                new Vector3f(1.0f,0.0f, 0.0f),
                new Vector3f(1.0f,0.0f, 0.0f),
                new Vector3f(1.0f,0.0f, 0.0f),
                new Vector3f(1.0f,0.0f, 0.0f),
                new Vector3f(1.0f,0.0f, 0.0f),
                // bawah
                new Vector3f(0.0f,-1.0f, 0.0f),
                new Vector3f(0.0f,-1.0f, 0.0f),
                new Vector3f(0.0f,-1.0f, 0.0f),
                new Vector3f(0.0f,-1.0f, 0.0f),
                new Vector3f(0.0f,-1.0f, 0.0f),
                new Vector3f(0.0f,-1.0f, 0.0f),
                // atas
                new Vector3f(0.0f,1.0f, 0.0f),
                new Vector3f(0.0f,1.0f, 0.0f),
                new Vector3f(0.0f,1.0f, 0.0f),
                new Vector3f(0.0f,1.0f, 0.0f),
                new Vector3f(0.0f,1.0f, 0.0f),
                new Vector3f(0.0f,1.0f, 0.0f)

        ));
    }
    public void createSphere(){
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();
        int stackCount = 18, sectorCount = 36;
        float x,y,z,xy,nx,ny,nz;
        float sectorStep = (float)(2* Math.PI )/ sectorCount; //sector count
        float stackStep = (float)Math.PI / stackCount; // stack count
        float sectorAngle, stackAngle;

        //titik persegi
        for(int i=0;i<=stackCount;i++){
            stackAngle = (float)Math.PI/2 - i * stackStep;
            xy = (float) (0.5f * Math.cos(stackAngle));
            z = (float) (0.5f * Math.sin(stackAngle));
            for(int j=0;j<sectorCount;j++){
                sectorAngle = j * sectorStep;
                x = (float) (xy * Math.cos(sectorAngle));
                y = (float) (xy * Math.sin(sectorAngle));
                temp.add(new Vector3f(x,y,z));
            }
        }
        vertices = temp;

        int k1, k2;
        ArrayList<Integer> temp_indices = new ArrayList<>();
        for(int i=0;i<stackCount;i++){
            k1 = i * (sectorCount + 1);
            k2 = k1 + sectorCount + 1;

            for(int j=0;j<sectorCount;++j, ++k1, ++k2){
                if(i != 0){
                    temp_indices.add(k1);
                    temp_indices.add(k2);
                    temp_indices.add(k1+1);
                }
                if(i!=(18-1)){
                    temp_indices.add(k1+1);
                    temp_indices.add(k2);
                    temp_indices.add(k2+1);
                }
            }
        }

        this.index = temp_indices;
        ibo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,
                ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,
                Utils.listoInt(index), GL_STATIC_DRAW);

    }

    public void createHalfEllipsoid(){
        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = -Math.PI/2; v<= Math.PI/2; v+=Math.PI/90){
            for(double u = -Math.PI/2; u<= Math.PI/1.99; u+=Math.PI/90){
                float x = 0.6f * (float)(Math.cos(v) * Math.cos(u));
                float y = 0.5f * (float)(Math.cos(v) * Math.sin(u));
                float z = 0.5f * (float)(Math.sin(v));
                temp.add(new Vector3f(x,y,z));
            }
        }
        vertices.clear();
        vertices = temp;
    }

    public void createEllipsoid(){
        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = -Math.PI/2; v<= Math.PI/2; v+=Math.PI/90){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/90){
                float x = 0.6f * (float)(Math.cos(v) * Math.cos(u));
                float y = 0.5f * (float)(Math.cos(v) * Math.sin(u));
                float z = 0.5f * (float)(Math.sin(v));
                temp.add(new Vector3f(x,y,z));
            }
        }
        vertices.clear();
        vertices = temp;
    }

    public void createElipticCylinder(){
        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = -Math.PI/2; v<= Math.PI/2; v+=Math.PI/90){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/90){
                float x = 0.5f * (float)(Math.sin(u));
                float y = 0.5f * (float)(Math.cos(u));
                float z = 0.5f * (float) v;
                temp.add(new Vector3f(x,y,z));
            }
        }
        vertices.clear();
        vertices = temp;
    }

    public void createHyeperboloid2side(){
        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = -Math.PI/2; v<= Math.PI/2; v+=Math.PI/60){
            for(double u = -Math.PI/2; u<= Math.PI/2; u+=Math.PI/60){
                float x = 0.1f * (float)(Math.tan(v) * Math.cos(u));
                float y = 0.1f * (float)(Math.tan(v) * Math.sin(u));
                float z = 0.1f * (float)(1/Math.cos(v));
                temp.add(new Vector3f(x,z,y));
            }

        }
        for(double v = -Math.PI/2; v<= Math.PI/2; v+=Math.PI/60) {
            for (double u = Math.PI / 2; u <= 3 * Math.PI / 2; u += Math.PI / 60) {
                float x = 0.3f * (float) (Math.tan(v) * Math.cos(u));
                float y = 0.3f * (float) (Math.tan(v) * Math.sin(u));
                float z = -0.3f * (float) (1 / Math.cos(v));
                temp.add(new Vector3f(x, z, y));
            }
        }
        vertices.clear();
        vertices = temp;
    }

    public void createElipticCone(){
        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = -Math.PI/2; v<= Math.PI/2; v+=Math.PI/90){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/90){
                float x = 0.5f * (float)(v * Math.cos(u));
                float y = 0.5f * (float)(v * Math.sin(u));
                float z = 0.5f * (float)(v);
                temp.add(new Vector3f(z,y,x));
            }
        }
        vertices.clear();
        vertices = temp;
    }

    public void createElipticParaboloid(){
        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = 0; v<= 1; v+=0.005){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/60){
                float x = 0.5f * (float)(v * Math.cos(u));
                float y = 0.5f * (float)(v * Math.sin(u));
                float z = (float) Math.pow(v,2);
                temp.add(new Vector3f(z,y,x));
            }
        }
        vertices.clear();
        vertices = temp;
    }

    public void createHalfElipticParaboloid(){
        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = 0; v<= 1; v+=0.005){
            for(double u = -Math.PI; u<= Math.PI/100; u+=Math.PI/60){
                float x = 0.5f * (float)(v * Math.cos(u));
                float y = 0.5f * (float)(v * Math.sin(u));
                float z = (float) Math.pow(v,2);
                temp.add(new Vector3f(z,y,x));
            }
        }
        vertices.clear();
        vertices = temp;
    }

    public void createHalfElipticParaboloid2(){
        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = 0; v<= 1; v+=0.005){
            for(double u = -Math.PI/200; u<= Math.PI; u+=Math.PI/60){
                float x = 0.5f * (float)(v * Math.cos(u));
                float y = 0.5f * (float)(v * Math.sin(u));
                float z = (float) Math.pow(v,2);
                temp.add(new Vector3f(z,y,x));
            }
        }
        vertices.clear();
        vertices = temp;
    }

    public void createHyperboloidParaboloid(){
        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = 0; v<= 1; v+=0.05){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/60){
                float x = 0.2f * (float)(v * Math.tan(u));
                float y = 0.2f * (float)(v * 1/Math.sin(u));
                float z = (float) Math.pow(v,2);
                temp.add(new Vector3f(x,y,z));
            }
        }
        vertices.clear();
        vertices = temp;
    }

    public void setupVAOVBO(){
        super.setupVAOVBO();

        //nbo
        //set nbo
        nbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, nbo);
        glBufferData(GL_ARRAY_BUFFER,
                Utils.listoFloat(normal),
                GL_STATIC_DRAW);

//        uniformsMap.createUniform(
//                "lightColor");
//        uniformsMap.createUniform(
//                "lightPos");
    }
    public void drawSetup(Camera camera, Projection projection){
        super.drawSetup(camera, projection);

        // Bind NBO
        glEnableVertexAttribArray(1);
        glBindBuffer(GL_ARRAY_BUFFER, nbo);
        glVertexAttribPointer(1,
                3, GL_FLOAT,
                false,
                0, 0);
//        uniformsMap.setUniform(
//                "lightColor",
//                new Vector3f(1.0f,1.0f,0.0f));
//        uniformsMap.setUniform(
//                "lightPos",
//                new Vector3f(1.0f,1.0f,0.0f));

        // Directional Light
//        uniformsMap.setUniform("dirLight.direction", new Vector3f(-0.2f,-1.0f, -0.3f));
//        uniformsMap.setUniform("dirLight.ambient", new Vector3f(0.0f,0.0f, 0.0f));
//        uniformsMap.setUniform("dirLight.diffuse", new Vector3f(0.0f,0.0f, 0.0f));
//        uniformsMap.setUniform("dirLight.specular", new Vector3f(0.0f,0.0f, 0.0f));

        // posisi pointLight
        Vector3f[] _pontLightPosisition = {
//                bedroom
                new Vector3f(0.0f, 3.0f, 1.0f),
//                kitchen & living room
                new Vector3f(8.0f, 3.0f, -4.0f),
//                treasure
                new Vector3f(0.0f, 3.0f, -15.0f),
//                pink
                new Vector3f(0.0f, 3.0f, -7.0f),
//                office
//                new Vector3f(6.924f, 1.670f, -17.35f),
//                new Vector3f(6.924f, 1.670f, -17.35f)
        };
        for (int i = 0; i < _pontLightPosisition.length; i++) {
            uniformsMap.setUniform("pointLights[" + i +"].position", _pontLightPosisition[i]);
            uniformsMap.setUniform("pointLights[" + i +"].ambient", new Vector3f(0.05f, 0.05f, 0.05f));
            uniformsMap.setUniform("pointLights[" + i +"].diffuse", new Vector3f(0.8f, 0.8f, 0.8f));
            uniformsMap.setUniform("pointLights[" + i +"].specular", new Vector3f(0.6f, 0.6f, 0.6f));
            uniformsMap.setUniform("pointLights[" + i +"].constant", 1.0f);
            uniformsMap.setUniform("pointLights[" + i +"].linear", 0.09f);
            uniformsMap.setUniform("pointLights[" + i +"].quadratic", 0.032f);
        }

        uniformsMap.setUniform("pointLights[4].position", new Vector3f(6.924f, 1.670f, -17.35f));
        uniformsMap.setUniform("pointLights[4].ambient", new Vector3f(0.05f, 0.05f, 0.05f));
        uniformsMap.setUniform("pointLights[4].diffuse", new Vector3f(0.8f, 0.8f, 0.8f));
        uniformsMap.setUniform("pointLights[4].specular", new Vector3f(0.6f, 0.6f, 0.6f));
        uniformsMap.setUniform("pointLights[4].constant", 1.5f);
        uniformsMap.setUniform("pointLights[4].linear", 0.09f);
        uniformsMap.setUniform("pointLights[4].quadratic", 0.0001f);
        uniformsMap.setUniform("pointLights[5].position", new Vector3f(6.924f, 1.670f, -17.35f));
        uniformsMap.setUniform("pointLights[5].ambient", new Vector3f(0.05f, 0.05f, 0.05f));
        uniformsMap.setUniform("pointLights[5].diffuse", new Vector3f(0.8f, 0.8f, 0.8f));
        uniformsMap.setUniform("pointLights[5].specular", new Vector3f(0.6f, 0.6f, 0.6f));
        uniformsMap.setUniform("pointLights[5].constant", 1.5f);
        uniformsMap.setUniform("pointLights[5].linear", 0.09f);
        uniformsMap.setUniform("pointLights[5].quadratic", 0.0001f);

        // SpotLight
        uniformsMap.setUniform("spotLight.position", camera.getPosition());
        uniformsMap.setUniform("spotLight.direction", camera.getDirection());
        uniformsMap.setUniform("spotLight.ambient", new Vector3f(0.0f, 0.0f, 0.0f));
        uniformsMap.setUniform("spotLight.diffuse", new Vector3f(1.0f,1.0f,1.0f));
        uniformsMap.setUniform("spotLight.specular", new Vector3f(1.0f,1.0f,1.0f));
        uniformsMap.setUniform("spotLight.constant", 1.0f);
        uniformsMap.setUniform("spotLight.linear", 0.09f);
        uniformsMap.setUniform("spotLight.quadratic", 0.032f);
        uniformsMap.setUniform("spotLight.cutOff", (float)Math.cos(Math.toRadians(5f)));
        uniformsMap.setUniform("spotLight.outerCutOff", (float)Math.cos(Math.toRadians(12.5f)));
        uniformsMap.setUniform("viewPos", camera.getPosition());


        // lampu di ruang kerja
        uniformsMap.setUniform("spotLight.position", new Vector3f(6.924f, 1.670f, -17.35f));
        uniformsMap.setUniform("spotLight.direction", new Vector3f(0.7042f, -0.6854f, 1.133f));
        uniformsMap.setUniform("spotLight.ambient", new Vector3f(0.0f, 0.0f, 0.0f));
        uniformsMap.setUniform("spotLight.diffuse", new Vector3f(1.0f,1.0f,1.0f));
        uniformsMap.setUniform("spotLight.specular", new Vector3f(1.0f,1.0f,1.0f));
        uniformsMap.setUniform("spotLight.constant", 1.0f);
        uniformsMap.setUniform("spotLight.linear", 0.09f);
        uniformsMap.setUniform("spotLight.quadratic", 0.032f);
        uniformsMap.setUniform("spotLight.cutOff", (float)Math.cos(Math.toRadians(5f)));
        uniformsMap.setUniform("spotLight.outerCutOff", (float)Math.cos(Math.toRadians(12.5f)));


    }
}
