import Engine.*;
import Engine.Object;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL30.*;

public class Main {
    private Window window =
            new Window
                    (1800,900,"Bruh");
    private ArrayList<Object> objects
            = new ArrayList<>();

    private ArrayList<Object> drone
            = new ArrayList<>();

    Camera camera = new Camera();
    float test = 0.0f;
    int counter = 0;
    float rotate = 0.5f;
    float angle = 0;

    Projection projection = new Projection(window.getWidth(), window.getHeight());
    private MouseInput mouseInput;



    public void init(){
        window.init();
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        glDepthMask(true);
        glDepthFunc(GL_LEQUAL);
        glDepthRange(0.0f, 0.5f);

        mouseInput = window.getMouseInput();
        camera.setPosition(-1.5f, 4.0f, -8.5f);
        camera.setRotation((float)Math.toRadians(0.0f),(float)Math.toRadians(90f));

        Vector4f warnaTembok = new Vector4f(252/255f, 238/255f, 217/255f,1.f);

        List<ShaderProgram.ShaderModuleData> vertfrag = Arrays.asList(
                new ShaderProgram.ShaderModuleData
                        ("resources/shaders/scene.vert"
                                , GL_VERTEX_SHADER),
                new ShaderProgram.ShaderModuleData
                        ("resources/shaders/scene.frag"
                                , GL_FRAGMENT_SHADER)
        );

        // DRONE
        drone.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.677f, 0.707f, 0.720f, 1.0f),
                new ArrayList<>(), "resources/FIX BLEND/drone/tengah.obj"
        ));
        drone.get(0).getChildObject().add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.677f, 0.707f, 0.720f, 1.0f),
                new ArrayList<>(), "resources/FIX BLEND/drone/fan.obj"
        ));
        drone.get(0).getChildObject().add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.677f, 0.707f, 0.720f, 1.0f),
                new ArrayList<>(), "resources/FIX BLEND/drone/kakiBulat.obj"
        ));
        drone.get(0).getChildObject().add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.677f, 0.707f, 0.720f, 1.0f),
                new ArrayList<>(), "resources/FIX BLEND/drone/penghubungFan.obj"
        ));
        drone.get(0).getChildObject().add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(252 / 255f, 238 / 255f, 217 / 255f, 1.f),
                new ArrayList<>(), "resources/FIX BLEND/drone/garsiFan.obj"
        ));
        drone.get(0).getChildObject().add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.893f, 0.926f, 0.940f, 1.0f),
                new ArrayList<>(), "resources/FIX BLEND/drone/penghubungFanTengah.obj"
        ));

        drone.get(0).getChildObject().add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(252 / 255f, 238 / 255f, 217 / 255f, 1.f),
                new ArrayList<>(), "resources/FIX BLEND/drone/piringKamera.obj"
        ));
        drone.get(0).getChildObject().add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(1.00f, 1.0f, 1.0f, 1.0f),
                new ArrayList<>(), "resources/FIX BLEND/drone/kamera.obj"
        ));
        drone.get(0).getChildObject().add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(warnaTembok),
                new ArrayList<>(), "resources/FIX BLEND/drone/tengahFan.obj"
        ));
        drone.get(0).getChildObject().add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(252 / 255f, 238 / 255f, 217 / 255f, 1.f),
                new ArrayList<>(), "resources/FIX BLEND/drone/depanKamera.obj"
        ));

        drone.get(0).translateObject(-0.2f,23.0f,56.50f);
        drone.get(0).scaleObject(0.15f,0.15f,0.15f);
        drone.get(0).rotateObject((float) Math.toRadians(180),0.0f,1.0f,0.0f);

        // WALLS AND FLOORING
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.8f,1f,0f,1f),
                new ArrayList<>(),"resources/FIX BLEND/walls/wall_kiri.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(1f,0f,0f,1f),
                new ArrayList<>(),"resources/FIX BLEND/walls/wall_kanan.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
//                new Vector4f(0.87f,0.89f,0.87f,1f),
                new Vector4f(1f,1f,1f,1f),
                new ArrayList<>(),"resources/FIX BLEND/walls/dapur2.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(1f,0.93f,0.81f,1f),
                new ArrayList<>(),"resources/FIX BLEND/walls/floor.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(1f,1f, 1f,1f),
                new ArrayList<>(),"resources/FIX BLEND/walls/kitchen_floor.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(warnaTembok),
                new ArrayList<>(),"resources/FIX BLEND/walls/living.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.9f,1f,0.9f,1f),
                new ArrayList<>(),"resources/FIX BLEND/walls/living_floor.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.9f,1f,0.9f,1f),
                new ArrayList<>(),"resources/FIX BLEND/bedroom/wallbed1.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.9f,1f,0.9f,1f),
                new ArrayList<>(),"resources/FIX BLEND/bedroom/wallbed2.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.97f,0.88f,0.86f,1f),
                new ArrayList<>(),"resources/FIX BLEND/pinkRoom/dinding1.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.97f,0.88f,0.86f,1f),
                new ArrayList<>(),"resources/FIX BLEND/pinkRoom/dinding2.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(1f,0.23f,0.23f,1f),
                new ArrayList<>(),"resources/FIX BLEND/ruangKerja/dinding.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.59f, 0.32f, 0.12f,1f),
                new ArrayList<>(),"resources/FIX BLEND/treasure/dinding1.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.59f, 0.32f, 0.12f,1f),
                new ArrayList<>(),"resources/FIX BLEND/treasure/dinding2.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.59f, 0.32f, 0.12f,1f),
                new ArrayList<>(),"resources/FIX BLEND/treasure/dinding3.obj"
        ));

        //BEDROOM

        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.74f, 0.52f, 0.5f,1f),
                new ArrayList<>(),"resources/FIX BLEND/bedroom/bantaiLantai1.obj"
        ));

        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.74f, 0.52f, 0.5f,1f),
                new ArrayList<>(),"resources/FIX BLEND/bedroom/bantaiLantai2.obj"
        ));

        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.76f,0.76f,0.76f,1f),
                new ArrayList<>(),"resources/FIX BLEND/bedroom/bantalBed.obj"
        ));

        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.4f,0.4f,0.4f,1f),
                new ArrayList<>(),"resources/FIX BLEND/bedroom/bantalSofa1.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.8f,0.8f,0.6f,1f),
                new ArrayList<>(),"resources/FIX BLEND/bedroom/bantalSofa2.obj"
        ));

        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
//                new Vector4f(0.5f, 1f,0.5f,1.0f),
                new Vector4f(0.64f, 0.64f,0.64f,1.0f),
                new ArrayList<>(),"resources/FIX BLEND/bedroom/bed.obj"
        ));

        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.68f, 0.46f, 0.25f,1f),
                new ArrayList<>(),"resources/FIX BLEND/bedroom/boardDinding.obj"
        ));

        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.3f,0.4f,0.5f,1f),
                new ArrayList<>(),"resources/FIX BLEND/bedroom/bukuShelf1.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.6f,0.5f,0.4f,1f),
                new ArrayList<>(),"resources/FIX BLEND/bedroom/bukuShelf2.obj"
        ));

        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(1f,1f,0.7f,1f),
                new ArrayList<>(),"resources/FIX BLEND/bedroom/bukuShelf3.obj"
        ));

        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(1f, 0.3f, 0.4f, 1f),
                new ArrayList<>(),"resources/FIX BLEND/bedroom/bukuShelf4.obj"
        ));

        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.6f,0.5f,0.4f,1f),
                new ArrayList<>(),"resources/FIX BLEND/bedroom/bukuShelf5.obj"
        ));

        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.6f,0.5f,0.4f,1f),
                new ArrayList<>(),"resources/FIX BLEND/bedroom/bukuShelf6.obj"
        ));

        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.84f, 1f,0.87f,1f),
                new ArrayList<>(),"resources/FIX BLEND/bedroom/carpet.obj"
        ));

        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.81f,0.17f,0.17f,1f),
                new ArrayList<>(),"resources/FIX BLEND/bedroom/gelas1.obj"
        ));

        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.17f,0.47f,0.81f,1f),
                new ArrayList<>(),"resources/FIX BLEND/bedroom/gelas2.obj"
        ));

        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(1f,0.84f,1f,1f),
                new ArrayList<>(),"resources/FIX BLEND/bedroom/hiasanDinding.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.34f,0.34f,0.34f,1f),
                new ArrayList<>(),"resources/FIX BLEND/bedroom/keyboard.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.34f,0.34f,0.34f,1f),
                new ArrayList<>(),"resources/FIX BLEND/bedroom/komputer.obj"
        ));

        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.48f, 0.24f, 0f,1f),
                new ArrayList<>(),"resources/FIX BLEND/bedroom/kursi.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(1f, 0.8f,0.6f,1.0f),
                new ArrayList<>(),"resources/FIX BLEND/bedroom/meja.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(1f, 0.8f,0.6f,1.0f),
                new ArrayList<>(),"resources/FIX BLEND/bedroom/mejaBulat.obj"
        ));

        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
//                new Vector4f(0.8f, 0.7f,0.6f,1.0f),
                new Vector4f(1f, 0.8f,0.6f,1.0f),
                new ArrayList<>(),"resources/FIX BLEND/bedroom/pinggiranBed.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(1f, 0.8f,0.6f,1.0f),
                new ArrayList<>(),"resources/FIX BLEND/bedroom/shelf.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(1f, 0.8f,0.6f,1.0f),
                new ArrayList<>(),"resources/FIX BLEND/bedroom/sofa.obj"
        ));

        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.15f,0.85f,0.29f,1f),
                new ArrayList<>(),"resources/FIX BLEND/bedroom/tanaman.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.03f, 0.6f, 0.05f, 1f),
                new ArrayList<>(),"resources/FIX BLEND/bedroom/tanamanDinding1.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.03f, 0.6f, 0.05f, 1f),
                new ArrayList<>(),"resources/FIX BLEND/bedroom/tanamanDinding2.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(warnaTembok),
                new ArrayList<>(),"resources/FIX BLEND/bedroom/tempatSampah.obj"
        ));

        //PINK ROOM
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.87f,0.87f,0.87f,1f),
                new ArrayList<>(),"resources/FIX BLEND/pinkRoom/fan.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.34f,0.34f,0.34f,1f),
                new ArrayList<>(),"resources/FIX BLEND/pinkRoom/komputer.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.4f,0.4f,0.4f,1f),
                new ArrayList<>(),"resources/FIX BLEND/pinkRoom/kursi.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.59f, 0.39f,0.18f,1f),
                new ArrayList<>(),"resources/FIX BLEND/pinkRoom/lemari.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.68f, 0.43f, 0.56f,1f),
                new ArrayList<>(),"resources/FIX BLEND/pinkRoom/meja.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.56f,0.36f,0.64f,1f),
                new ArrayList<>(),"resources/FIX BLEND/pinkRoom/pigura.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.98f, 0.87f, 0.7f,1f),
                new ArrayList<>(),"resources/FIX BLEND/pinkRoom/sofa.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.98f, 0.87f, 0.7f,1f),
                new ArrayList<>(),"resources/FIX BLEND/pinkRoom/sofaKecil.obj"
        ));

//        // RUANG KERJA
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(1f,1f,1f,1f),
                new ArrayList<>(),"resources/FIX BLEND/ruangKerja/ac.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.84f, 0.53f, 0.13f,1f),
                new ArrayList<>(),"resources/FIX BLEND/ruangKerja/bookShelf.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.65f, 0.92f, 1f,1f),
                new ArrayList<>(),"resources/FIX BLEND/ruangKerja/buku.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.2f, 0.4f,0.2f,1f),
                new ArrayList<>(),"resources/FIX BLEND/ruangKerja/hiasanDinding1.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.2f, 0.4f,0.2f,1f),
                new ArrayList<>(),"resources/FIX BLEND/ruangKerja/hiasanDinding2.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.65f, 0.92f, 1f,1f),
                new ArrayList<>(),"resources/FIX BLEND/ruangKerja/isiShelf.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.98f, 0.98f,0.98f,1f ),
                new ArrayList<>(),"resources/FIX BLEND/ruangKerja/kursi.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(1f,1f,1f,1f  ),
                new ArrayList<>(),"resources/FIX BLEND/ruangKerja/lampu.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.34f,0.34f,0.34f,1f),
                new ArrayList<>(),"resources/FIX BLEND/ruangKerja/laptop.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.66f,0.74f, 0.57f,1f),
                new ArrayList<>(),"resources/FIX BLEND/ruangKerja/meja.obj"
        ));
        // TREASURE
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.29f, 0.18f, 0.05f,1f),
                new ArrayList<>(),"resources/FIX BLEND/treasure/hiasanDinding.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(warnaTembok),
                new ArrayList<>(),"resources/FIX BLEND/treasure/jendela.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(1f,1f,1f,1f),
                new ArrayList<>(),"resources/FIX BLEND/treasure/kertas.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.34f, 0.24f,  0.12f,1f),
                new ArrayList<>(),"resources/FIX BLEND/treasure/kursi.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.34f, 0.24f,  0.12f,1f),
                new ArrayList<>(),"resources/FIX BLEND/treasure/meja.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.8f, 0.2f,0f,1f),
                new ArrayList<>(),"resources/FIX BLEND/treasure/obor2.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.29f, 0.18f, 0.05f,1f),
                new ArrayList<>(),"resources/FIX BLEND/treasure/treasure.obj"
        ));

        // KITCHEN
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.2f, 0.4f,0.2f,1f),
                new ArrayList<>(),"resources/FIX BLEND/kitchen/cactus1.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.2f, 0.4f,0.2f,1f),
                new ArrayList<>(),"resources/FIX BLEND/kitchen/cactus2.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.51f, 0.35f,0.06f,1f),
                new ArrayList<>(),"resources/FIX BLEND/kitchen/donat.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(1f,0f,0f,1f),
                new ArrayList<>(),"resources/FIX BLEND/kitchen/karpet.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.6f, 0.56f, 0.47f,1f),
                new ArrayList<>(),"resources/FIX BLEND/kitchen/kitchenset1.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.6f, 0.56f, 0.47f,1f),
                new ArrayList<>(),"resources/FIX BLEND/kitchen/kitchenset2.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.6f, 0.56f, 0.47f,1f),
                new ArrayList<>(),"resources/FIX BLEND/kitchen/kitchenset3.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.6f, 0.56f, 0.47f,1f),
                new ArrayList<>(),"resources/FIX BLEND/kitchen/kompor.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.75f, 0.73f, 0.76f,1f),
                new ArrayList<>(),"resources/FIX BLEND/kitchen/kulkas.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.56f, 0.76f,0.22f,1f),
                new ArrayList<>(),"resources/FIX BLEND/kitchen/kursi1.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.56f, 0.76f,0.22f,1f),
                new ArrayList<>(),"resources/FIX BLEND/kitchen/kursi2.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.68f, 0.47f, 0.27f,1f),
                new ArrayList<>(),"resources/FIX BLEND/kitchen/lemari.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.68f, 0.47f, 0.27f,1f),
                new ArrayList<>(),"resources/FIX BLEND/kitchen/meja.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.98f, 0.84f, 1f,1f),
                new ArrayList<>(),"resources/FIX BLEND/kitchen/mug.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.55f,0.55f,0.55f,1f),
                new ArrayList<>(),"resources/FIX BLEND/kitchen/pan.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(warnaTembok),
                new ArrayList<>(),"resources/FIX BLEND/kitchen/plate.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(warnaTembok),
                new ArrayList<>(),"resources/FIX BLEND/kitchen/plates.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.2f, 0.4f,0.2f,1f),
                new ArrayList<>(),"resources/FIX BLEND/kitchen/pot.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.68f, 0.47f, 0.27f,1f),
                new ArrayList<>(),"resources/FIX BLEND/kitchen/rak_atas.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.68f, 0.47f, 0.27f,1f),
                new ArrayList<>(),"resources/FIX BLEND/kitchen/rak_bawah.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.52f, 0.95f, 0.96f,1f),
                new ArrayList<>(),"resources/FIX BLEND/kitchen/susu1.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.52f, 0.95f, 0.96f,1f),
                new ArrayList<>(),"resources/FIX BLEND/kitchen/susu2.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.6f, 0.4f, 0.2f,1f),
                new ArrayList<>(),"resources/FIX BLEND/kitchen/talenan.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(warnaTembok),
                new ArrayList<>(),"resources/FIX BLEND/kitchen/wadah1.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(warnaTembok),
                new ArrayList<>(),"resources/FIX BLEND/kitchen/wadah2.obj"
        ));

        // LIVING
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.8f,0.4f,0.2f,1f),
                new ArrayList<>(),"resources/FIX BLEND/living/book.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.8f,0.4f,0.2f,1f),
                new ArrayList<>(),"resources/FIX BLEND/living/karpet.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.34f,0.34f,0.34f,1f),
                new ArrayList<>(),"resources/FIX BLEND/living/phone.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.29f, 0.41f,0.1f,1f),
                new ArrayList<>(),"resources/FIX BLEND/living/plant1.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.29f, 0.41f,0.1f,1f),
                new ArrayList<>(),"resources/FIX BLEND/living/plant2.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.29f, 0.41f,0.1f,1f),
                new ArrayList<>(),"resources/FIX BLEND/living/plat.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.93f, 0.81f, 0.68f,1f),
                new ArrayList<>(),"resources/FIX BLEND/living/sofa.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.34f,0.34f,0.34f,1f),
                new ArrayList<>(),"resources/FIX BLEND/living/speaker1.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.61f, 0.68f, 0.53f,1f),
                new ArrayList<>(),"resources/FIX BLEND/living/tirai1.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.61f, 0.68f, 0.53f,1f),
                new ArrayList<>(),"resources/FIX BLEND/living/tirai2.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.34f,0.34f,0.34f,1f),
                new ArrayList<>(),"resources/FIX BLEND/living/tv.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(warnaTembok),
                new ArrayList<>(),"resources/FIX BLEND/living/tv_table.obj"
        ));
        objects.add(new Sphere(
                vertfrag,
                new ArrayList<>(),
                new Vector4f(0.8f,0.4f,0.2f,1f),
                new ArrayList<>(),"resources/FIX BLEND/living/what.obj"
        ));
    }

    public void loop(){
        while (window.isOpen()) {
            window.update();
            glClearColor(0.9f,
                    0.9f, 0.8f,
                    0.0f);
            GL.createCapabilities();
            input();


            for(Object object: objects){
                ((Sphere) object).draw(camera, projection);
            }
            for(Object object: drone){
                ((Sphere) object).draw(camera, projection);
            }

            // Restore state
            glDisableVertexAttribArray(0);

            // Poll for window events.
            // The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    public void input() {

        //muter 360 horizontal ke kiri 3rd
        if (window.isKeyPressed(GLFW_KEY_Q)) {
            if (counter == 1) {
                System.out.println("masuk1");
                camera.setPosition(camera.getPosition().x-1.4f, camera.getPosition().y + 0.6f, camera.getPosition().z);
                counter = 0;
            } else {
                Vector3f temp = drone.get(0).updateCenterPoint();
                drone.get(0).translateObject(-temp.x, -temp.y, -temp.z);
                drone.get(0).rotateObject((float) Math.toRadians(0.5f), 0f, 1f, 0f);
                drone.get(0).translateObject(temp.x, temp.y, temp.z);
                angle += rotate;

                camera.moveForward(1.5f);
                camera.addRotation(0f, -(float) Math.toRadians(0.5f));
                camera.moveBackwards(1.5f);

            }
        }

        //muter 360 horizontal ke kanan 3rd
        if (window.isKeyPressed(GLFW_KEY_W)) {
            if (counter == 1) {
                System.out.println("masuk2");
                camera.setPosition(camera.getPosition().x-1.4f, camera.getPosition().y + 0.6f, camera.getPosition().z);
                counter = 0;
            } else {
                Vector3f temp = drone.get(0).updateCenterPoint();
                drone.get(0).translateObject(-temp.x, -temp.y, -temp.z);
                drone.get(0).rotateObject(-(float) Math.toRadians(0.5f), 0f, 1f, 0f);
                drone.get(0).translateObject(temp.x, temp.y, temp.z);
                angle -= rotate;

                camera.moveForward(1.5f);
                camera.addRotation(0f, (float) Math.toRadians(0.5f));
                camera.moveBackwards(1.5f);
            }
        }

        //muteri drone secara horizontal ke kiri
        if (window.isKeyPressed(GLFW_KEY_E)) {
            camera.moveForward(1.5f);
            camera.addRotation(0f, (float) Math.toRadians(0.5f));
            camera.moveBackwards(1.5f);
        }

        //muteri drone secara horizontal ke kanan
        if (window.isKeyPressed(GLFW_KEY_R)) {
            camera.moveForward(1.5f);
            camera.addRotation(0f, -(float) Math.toRadians(0.5f));
            camera.moveBackwards(1.5f);
        }

        //muter 360 horizontal ke kiri 1st SEK ANEH
        if (window.isKeyPressed(GLFW_KEY_Y)) {
            if (counter == 0) {
                camera.setPosition(camera.getPosition().x, camera.getPosition().y - 0.5f, camera.getPosition().z + 1.65f);
                counter = 1;
            } else {
                Vector3f temp = drone.get(0).updateCenterPoint();
                drone.get(0).translateObject(-temp.x, -temp.y, -temp.z);
                drone.get(0).rotateObject((float) Math.toRadians(0.5f), 0f, 1f, 0f);
                drone.get(0).translateObject(temp.x, temp.y, temp.z);

                camera.moveForward(-0.15f);
                camera.addRotation(0f, -(float) Math.toRadians(0.5f));
                camera.moveBackwards(-0.15f);

            }
        }

        float deltax = (float) (0.05f * Math.sin(Math.toRadians(angle)));
        float deltaz = (float) (0.05f * Math.cos(Math.toRadians(angle)));

        // third point of view
        //geser kiri
        if (window.isKeyPressed(GLFW_KEY_1)) {
            if (counter == 1) {
                camera.setPosition(camera.getPosition().x - 1.4f, camera.getPosition().y + 0.6f, camera.getPosition().z);
                counter = 0;
            } else {
                drone.get(0).translateObject(-deltax, 0.0f, -deltaz);
                if(!CheckCollision()) {
                    camera.setPosition(camera.getPosition().x - deltax, camera.getPosition().y, camera.getPosition().z - deltaz);
                } else {
                    drone.get(0).translateObject(deltax, 0.0f, deltaz);
                }
            }
        }
        //geser kanan
        if (window.isKeyPressed(GLFW_KEY_2)) {
            if (counter == 1) {
                camera.setPosition(camera.getPosition().x - 1.4f, camera.getPosition().y + 0.6f, camera.getPosition().z);
                counter = 0;
            } else {
                drone.get(0).translateObject(deltax, 0.0f, deltaz);
                if (!CheckCollision()) {
                    camera.setPosition(camera.getPosition().x + deltax, camera.getPosition().y, camera.getPosition().z + deltaz);
                } else {
                    drone.get(0).translateObject(-deltax, 0.0f, -deltaz);
                }
            }
        }
        //geser naik
        if (window.isKeyPressed(GLFW_KEY_3)) {
            if (counter == 1) {
                camera.setPosition(camera.getPosition().x - 1.4f, camera.getPosition().y + 0.6f, camera.getPosition().z);
                counter = 0;
            } else {
                drone.get(0).translateObject(0f, test + 0.05f, 0f);
                if (!CheckCollision()) {
                    camera.setPosition(camera.getPosition().x, camera.getPosition().y + 0.05f, camera.getPosition().z);
                }else {
                    drone.get(0).translateObject(0f, -(test + 0.05f), 0f);
                }
            }
        }
        //geser turun
        if (window.isKeyPressed(GLFW_KEY_4)) {
            if (counter == 1) {
                camera.setPosition(camera.getPosition().x - 1.4f, camera.getPosition().y + 0.6f, camera.getPosition().z);
                counter = 0;
            } else {
                drone.get(0).translateObject(0f, test - 0.05f, 0f);
                if (!CheckCollision()) {
                    camera.setPosition(camera.getPosition().x, camera.getPosition().y - 0.05f, camera.getPosition().z);
                }else {
                    drone.get(0).translateObject(0f, -(test - 0.05f), 0f);
                }
            }
        }

        float deltax2 = (float) (0.05f * Math.cos(Math.toRadians(angle)));
        float deltaz2 = (float) (-0.05f * Math.sin(Math.toRadians(angle)));

        //geser maju
        if (window.isKeyPressed(GLFW_KEY_5)) {
            if (counter == 1) {
                camera.setPosition(camera.getPosition().x - 1.4f, camera.getPosition().y + 0.6f, camera.getPosition().z);
                counter = 0;
            } else {
                drone.get(0).translateObject(deltax2, 0.0f, deltaz2);
                if(!CheckCollision()) {
                    camera.setPosition(camera.getPosition().x + deltax2, camera.getPosition().y, camera.getPosition().z + deltaz2);

                }else {
                    drone.get(0).translateObject(-deltax2, 0.0f, -deltaz2);
                }
            }
        }
        //geser mundur
        if (window.isKeyPressed(GLFW_KEY_6)) {
            if (counter == 1) {
                camera.setPosition(camera.getPosition().x - 1.4f, camera.getPosition().y + 0.6f, camera.getPosition().z);
                counter = 0;
            } else {
                drone.get(0).translateObject(-deltax2, 0.0f, -deltaz2);
                if (!CheckCollision()) {
                    camera.setPosition(camera.getPosition().x - deltax2, camera.getPosition().y, camera.getPosition().z - deltaz2);
                }else {
                    drone.get(0).translateObject(deltax2, 0.0f, deltaz2);
                }
            }

        }
        //first point of view
        //geser kiri
        if (window.isKeyPressed(GLFW_KEY_7)) {
            if (counter == 0) {
                camera.setPosition(camera.getPosition().x+1.4f, camera.getPosition().y - 0.6f, camera.getPosition().z);
                counter = 1;
            } else {
                drone.get(0).translateObject(-deltax, 0.0f, -deltaz);
                camera.setPosition(camera.getPosition().x -deltax, camera.getPosition().y, camera.getPosition().z -deltaz);
            }
        }
        //geser kanan
        if (window.isKeyPressed(GLFW_KEY_8)) {
            if (counter == 0) {
                camera.setPosition(camera.getPosition().x+1.4f, camera.getPosition().y - 0.6f, camera.getPosition().z);
                counter = 1;
            } else {
                drone.get(0).translateObject(deltax, 0.0f, deltaz);
                camera.setPosition(camera.getPosition().x +deltax, camera.getPosition().y, camera.getPosition().z +deltaz);
            }
        }
        //geser naik
        if (window.isKeyPressed(GLFW_KEY_9)) {
            if (counter == 0) {
                camera.setPosition(camera.getPosition().x+1.4f, camera.getPosition().y - 0.6f, camera.getPosition().z);
                counter = 1;
            } else {
                drone.get(0).translateObject(0f, test + 0.05f, 0f);
                camera.setPosition(camera.getPosition().x, camera.getPosition().y + 0.05f, camera.getPosition().z);
            }

        }
        //geser turun
        if (window.isKeyPressed(GLFW_KEY_0)) {
            if (counter == 0) {
                camera.setPosition(camera.getPosition().x+1.4f, camera.getPosition().y - 0.6f, camera.getPosition().z);
                counter = 1;
            } else {
                drone.get(0).translateObject(0f, test - 0.05f, 0f);
                camera.setPosition(camera.getPosition().x, camera.getPosition().y - 0.05f, camera.getPosition().z);
            }
        }

        //geser maju
        if (window.isKeyPressed(GLFW_KEY_P)) {
            if (counter == 0) {
                camera.setPosition(camera.getPosition().x+1.4f, camera.getPosition().y - 0.6f, camera.getPosition().z);
                counter = 1;
            } else {
                drone.get(0).translateObject(deltax2, 0.0f, deltaz2);
                camera.setPosition(camera.getPosition().x +deltax2, camera.getPosition().y, camera.getPosition().z +deltaz2);
            }
        }
        //geser mundur
        if (window.isKeyPressed(GLFW_KEY_O)) {
            if (counter == 0) {
                camera.setPosition(camera.getPosition().x+1.4f, camera.getPosition().y - 0.6f, camera.getPosition().z);
                counter = 1;
            } else {
                drone.get(0).translateObject(-deltax2, 0.0f, -deltaz2);
                camera.setPosition(camera.getPosition().x -deltax2, camera.getPosition().y, camera.getPosition().z -deltaz2);
            }
        }
        if (window.isKeyPressed(GLFW_KEY_M)) {
            camera.setPosition(camera.getPosition().x, camera.getPosition().y + 0.2f, camera.getPosition().z);
        }
        if (window.isKeyPressed(GLFW_KEY_N)) {
            camera.setPosition(camera.getPosition().x, camera.getPosition().y - 0.2f, camera.getPosition().z);
        }
        if (window.isKeyPressed(GLFW_KEY_UP)) {
            camera.setPosition(camera.getPosition().x - 0.2f, camera.getPosition().y, camera.getPosition().z);
        }
        if (window.isKeyPressed(GLFW_KEY_DOWN)) {
            camera.setPosition(camera.getPosition().x  + 0.2f, camera.getPosition().y, camera.getPosition().z);
        }
        if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
            camera.setPosition(camera.getPosition().x, camera.getPosition().y, camera.getPosition().z-0.2f);
        }
        if (window.isKeyPressed(GLFW_KEY_LEFT)) {
            camera.setPosition(camera.getPosition().x, camera.getPosition().y, camera.getPosition().z+0.2f);
        }
        if (window.isKeyPressed(GLFW_KEY_Z)) {
            camera.moveUp(0.025f);
            camera.addRotation(0.025f, 0.0f);
            camera.addRotation(0.025f, 0.0f);
        }
        if (window.isKeyPressed(GLFW_KEY_X)) {
            camera.moveDown(0.025f);
            camera.addRotation(-0.025f, 0.0f);
        }
        if (window.isKeyPressed(GLFW_KEY_C)) {
            camera.moveLeft(0.025f);
            camera.addRotation(0.0f, 0.025f);
        }
        if (window.isKeyPressed(GLFW_KEY_V)) {
            camera.moveRight(0.025f);
            camera.addRotation(0.0f, -0.025f);
        }
        if (window.getMouseInput().isLeftButtonPressed()) {
            Vector2f displayVector = window.getMouseInput().getDisplVec();
            camera.addRotation((float) Math.toRadians(displayVector.x * 0.1f), (float) Math.toRadians(displayVector.y * 0.1f));

        }
        if (window.getMouseInput().getScroll().y != 0) {
            projection.setFOV(projection.getFOV() - (window.getMouseInput().getScroll().y * 0.01f));
            window.getMouseInput().setScroll(new Vector2f());
        }
    }

    public boolean CheckCollision(){
        Vector3f dronePos = new Vector3f(drone.get(0).getCenterPoint().get(0) + 0.15f, drone.get(0).getCenterPoint().get(1) + 0.15f,
                drone.get(0).getCenterPoint().get(2) + 0.15f);
        for (Object object : objects) {
            Vector3f objPos = new Vector3f(object.getAllVertices().get(0).x , object.getAllVertices().get(0).y ,object.getAllVertices().get(0).z);
            float distance = (float) Math.sqrt(Math.pow(objPos.x - dronePos.x, 2) + Math.pow(objPos.y - dronePos.y, 2) + Math.pow(objPos.z - dronePos.z, 2));
//            System.out.println(distance);
            if (distance < 1f){
//                System.out.println("nabrak");
//                System.out.println("y = " + distance);
                return true;
            }
            System.out.println("n = " + distance);

        }
        return false;
    }


    public void run() {

        init();
        loop();

        // Terminate GLFW and
        // free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
    public static void main(String[] args) {
        new Main().run();
    }
}
