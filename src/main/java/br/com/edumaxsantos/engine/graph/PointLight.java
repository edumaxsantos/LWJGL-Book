package br.com.edumaxsantos.engine.graph;

import lombok.*;
import org.joml.Vector3f;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PointLight {

    @NonNull
    private Vector3f color;

    @NonNull
    private Vector3f position;

    @NonNull
    protected float intensity;

    private Attenuation attenuation = new Attenuation(1, 0, 0);

    public PointLight(PointLight pointLight) {
        this(new Vector3f(pointLight.getColor()), new Vector3f(pointLight.getPosition()),
                pointLight.getIntensity(), pointLight.getAttenuation());
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class Attenuation {

        private float constant;
        private float linear;
        private float exponent;

    }
}
