package br.com.edumaxsantos.game;

import br.com.edumaxsantos.engine.GameItem;
import br.com.edumaxsantos.engine.IGameLogic;
import br.com.edumaxsantos.engine.MouseInput;
import br.com.edumaxsantos.engine.Window;
import br.com.edumaxsantos.engine.graph.Camera;
import br.com.edumaxsantos.engine.graph.Mesh;
import br.com.edumaxsantos.engine.graph.OBJLoader;
import br.com.edumaxsantos.engine.graph.Texture;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;


public class DummyGame implements IGameLogic {

    private static final float MOUSE_SENSIBILITY = 0.02f;

    private final Vector3f cameraInc = new Vector3f();

    private final Renderer renderer = new Renderer();

    private final Camera camera = new Camera();

    private GameItem[] gameItems;

    private static final float CAMERA_POS_STEP = 0.05f;

    @Override
    public void init(Window window) throws Exception {
        renderer.init(window);

        Mesh mesh = OBJLoader.loadMesh("/models/cube.obj");
        Texture texture = new Texture("src/textures/grassblock.png");
        mesh.setTexture(texture);
        Mesh bunny = OBJLoader.loadMesh("/models/bunny.obj");
        GameItem gameItem = new GameItem(bunny);
        gameItem.setScale(1.5f);
        gameItem.setPosition(0, 0, -2);
        gameItems = new GameItem[] { gameItem };
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
    }

    @Override
    public void render(Window window) {
        renderer.render(window, camera, gameItems);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        for(GameItem gameItem: gameItems) {
            gameItem.getMesh().cleanup();
        }
    }


}
