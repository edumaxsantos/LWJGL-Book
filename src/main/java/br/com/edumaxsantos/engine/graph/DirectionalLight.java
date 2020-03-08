package br.com.edumaxsantos.engine.graph;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.joml.Vector3f;

@AllArgsConstructor
@Getter
@Setter
public class DirectionalLight {

    private Vector3f color;

    private Vector3f direction;

    private float intensity;

    public DirectionalLight(DirectionalLight light) {
        this(new Vector3f(light.getColor()), new Vector3f(light.getDirection()), light.getIntensity());
    }
}
