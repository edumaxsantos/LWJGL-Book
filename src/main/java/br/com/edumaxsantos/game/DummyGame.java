package br.com.edumaxsantos.game;

import br.com.edumaxsantos.engine.GameItem;
import br.com.edumaxsantos.engine.IGameLogic;
import br.com.edumaxsantos.engine.MouseInput;
import br.com.edumaxsantos.engine.Window;
import br.com.edumaxsantos.engine.graph.*;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;


public class DummyGame implements IGameLogic {

    private static final float MOUSE_SENSIBILITY = 0.05f;

    private final Vector3f cameraInc = new Vector3f();

    private final Renderer renderer = new Renderer();

    private final Camera camera = new Camera();

    private GameItem[] gameItems;

    private Vector3f ambientLight;

    private PointLight[] pointLightList;

    private SpotLight[] spotLightList;

    private DirectionalLight directionalLight;

    private float lightAngle = -90;

    private static final float CAMERA_POS_STEP = 0.05f;

    @Override
    public void init(Window window) throws Exception {
        renderer.init(window);

        float reflectance = 1f;

        Mesh mesh = OBJLoader.loadMesh("/models/cube.obj");
        Texture texture = new Texture("src/textures/grassblock.png");
        //mesh.setTexture(texture);
        //Mesh bunny = OBJLoader.loadMesh("/models/bunny.obj");
        Material material = new Material(texture, reflectance);
        mesh.setMaterial(material);
        GameItem gameItem = new GameItem(mesh);
        gameItem.setScale(1.5f);
        gameItem.setPosition(0, 0, -2);
        gameItems = new GameItem[] { gameItem };

        ambientLight = new Vector3f(0.3f, 0.3f, 0.3f);

        // Point Light
        Vector3f lightColor = new Vector3f(1, 1, 1);
        Vector3f lightPosition = new Vector3f(0, 0, 1);
        float lightIntensity = 1.0f;
        PointLight pointLight = new PointLight(lightColor, lightPosition, lightIntensity);
        PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f, 1.0f);
        pointLight.setAttenuation(att);
        pointLightList = new PointLight[]{pointLight};

        // Spot Light

        lightPosition = new Vector3f(0f, 0f, 10f);
        PointLight sl_pointLight = new PointLight(lightColor, lightPosition, lightIntensity);
        att = new PointLight.Attenuation(0f, 0f, 0.02f);
        sl_pointLight.setAttenuation(att);
        Vector3f coneDir = new Vector3f(0, 0, -1);
        float cutoff = (float) Math.cos(Math.toRadians(140));
        SpotLight spotLight = new SpotLight(sl_pointLight, coneDir, cutoff);
        spotLightList = new SpotLight[]{spotLight};

        lightColor = new Vector3f(-1, 0, 0);
        directionalLight = new DirectionalLight(lightColor, lightPosition, lightIntensity);
    }

    @Override
    public void input(Window window, MouseInput mouseInput) {
        cameraInc.set(0, 0, 0);
        if (window.isKeyPressed(GLFW_KEY_W)) {
            cameraInc.z = -1;
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            cameraInc.z = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            cameraInc.x = -1;
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            cameraInc.x = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_Z)) {
            cameraInc.y = -1;
        } else if (window.isKeyPressed(GLFW_KEY_X)) {
            cameraInc.y = 1;
        }
        float lightPos = spotLightList[0].getPointLight().getPosition().z;
        if (window.isKeyPressed(GLFW_KEY_N)) {
            this.spotLightList[0].getPointLight().getPosition().z = lightPos + 0.1f;
        } else if (window.isKeyPressed(GLFW_KEY_M)) {
            this.spotLightList[0].getPointLight().getPosition().z = lightPos - 0.1f;
        }
    }

    @Override
    public void update(float interval, MouseInput mouseInput) {
        // Update camera position
        camera.movePosition(cameraInc.x * CAMERA_POS_STEP,
                cameraInc.y * CAMERA_POS_STEP,
                cameraInc.z * CAMERA_POS_STEP);

        // Update camera based on mouse
        if (mouseInput.isRightButtonPressed()) {
            Vector2f rotVec = mouseInput.getDisplVec();
            camera.moveRotation(rotVec.x * MOUSE_SENSIBILITY, rotVec.y * MOUSE_SENSIBILITY, 0);
        }

        // Update directional light direction, intensity and color
        lightAngle += 1.1f;
        if (lightAngle > 90) {
            directionalLight.setIntensity(0);
            if (lightAngle >= 360) {
                lightAngle = -90;
            }
        } else if (lightAngle <= -80 || lightAngle >= 80) {
            float factor = 1 - (float) (Math.abs(lightAngle) - 80) / 10.0f;
            directionalLight.setIntensity(factor);
            directionalLight.getColor().y = Math.max(factor, 0.9f);
            directionalLight.getColor().z = Math.max(factor, 0.5f);
        } else {
            directionalLight.setIntensity(1);
            directionalLight.getColor().x = 1;
            directionalLight.getColor().y = 1;
            directionalLight.getColor().z = 1;
        }
        double angRad = Math.toRadians(lightAngle);
        directionalLight.getDirection().x = (float) Math.sin(angRad);
        directionalLight.getDirection().y = (float) Math.cos(angRad);
    }

    @Override
    public void render(Window window) {
        renderer.render(window, camera, gameItems, ambientLight,
                pointLightList, spotLightList, directionalLight);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        for(GameItem gameItem: gameItems) {
            gameItem.getMesh().cleanup();
        }
    }


}
