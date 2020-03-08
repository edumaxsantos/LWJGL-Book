package br.com.edumaxsantos.engine.graph;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joml.Vector4f;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Material {

    private static final Vector4f DEFAULT_COLOR = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);

    private Vector4f ambientColor = DEFAULT_COLOR;

    private Vector4f diffuseColor = DEFAULT_COLOR;

    private Vector4f specularColor = DEFAULT_COLOR;

    private Texture texture = null;

    private float reflectance = 0;

    public Material(Vector4f color, float reflectance) {
        this(color, color, color, null, reflectance);
    }

    public Material(Texture texture) {
        this(DEFAULT_COLOR, DEFAULT_COLOR, DEFAULT_COLOR, texture, 0);
    }

    public Material(Texture texture, float reflectance) {
        this(DEFAULT_COLOR, DEFAULT_COLOR, DEFAULT_COLOR, texture, reflectance);
    }

    public boolean isTextured() {
        return this.texture != null;
    }
}
