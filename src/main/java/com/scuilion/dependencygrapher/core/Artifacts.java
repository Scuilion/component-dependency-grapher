package com.scuilion.dependencygrapher.core;

import java.util.HashMap;

public class Artifacts {
    private HashMap<String, Artifact> artifacts = new HashMap<>();

    public void add(Artifact artifact){
        artifacts.put(artifact.getArtifactName(), artifact);
    }
    public HashMap<String, Artifact> getArtifacts(){
        return this.artifacts;
    }
}
