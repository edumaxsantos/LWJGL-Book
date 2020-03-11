package br.com.edumaxsantos.engine;

import br.com.edumaxsantos.engine.graph.DirectionalLight;
import br.com.edumaxsantos.engine.graph.PointLight;
import br.com.edumaxsantos.engine.graph.SpotLight;
import lombok.Getter;
import lombok.Setter;
import org.joml.Vector3f;

@Getter
@Setter
public class SceneLight {


    private Vector3f ambientLight;

    private PointLight[] pointLightList;

    private SpotLight[] spotLightList;

    private DirectionalLight directionalLight;
}
