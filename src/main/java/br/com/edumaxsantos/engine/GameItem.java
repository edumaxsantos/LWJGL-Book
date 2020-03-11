package br.com.edumaxsantos.engine;

import br.com.edumaxsantos.engine.graph.Mesh;
import lombok.*;
import org.joml.Vector3f;

@RequiredArgsConstructor
@NoArgsConstructor
public class GameItem {

    @Getter
    @NonNull
    @Setter
    private Mesh mesh;

    @Getter
    private final Vector3f position = new Vector3f();

    @Getter
    @Setter
    private float scale = 1;

    @Getter
    private final Vector3f rotation = new Vector3f();

    public void setPosition(float x, float y, float z) {
        this.position.set(x, y, z);
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.set(x, y, z);
    }

}
