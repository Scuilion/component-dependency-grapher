package com.scuilion.dependencygrapher.core;

import java.util.ArrayList;

public class Artifact {
    private final String artifactName;
    private final String identifier;
    private final String language;
    private final String type;
    private final ArrayList<String> dependencyIdentifiers;

    private Artifact(ArtifactBuilder builder) {
        this.artifactName = builder.artifactName;
        this.identifier = builder.identifier;
        this.language = builder.language;
        this.type = builder.type;
        this.dependencyIdentifiers = builder.dependencyIdentifiers;
    }

    public String getArtifactName(){
        return artifactName;
    }
    public String getIdentifier(){
        return identifier;
    }
    public String getLanguage(){
        return language;
    }
    public String getType(){
        return type;
    }
    public ArrayList<String> getDependencyIdentifiers(){
        return dependencyIdentifiers;
    }

    public static class ArtifactBuilder {
        private final String artifactName;
        private final String identifier;
        private String language = "";
        private String type = "";
        private ArrayList<String> dependencyIdentifiers = new ArrayList<String>();

        public ArtifactBuilder(String artifactName, String identifier) {
            this.artifactName = artifactName;
            this.identifier = identifier;
        }

	public ArtifactBuilder language(String language) {
            this.language = language;
            return this;
	}

	public ArtifactBuilder type(String type) {
            this.type = type;
            return this;
	}

	public ArtifactBuilder dependencyIdentifiers(ArrayList<String> dependencyIdentifiers) {
            this.dependencyIdentifiers = dependencyIdentifiers;
            return this;
	}

	public Artifact build() {
            return new Artifact(this);
	}
    }
}


