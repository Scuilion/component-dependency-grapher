package com.scuilion.dependencygrapher.core;

import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import static com.scuilion.dependencygrapher.core.Utils;

public class BasicUsageTest{

    private static final String DB_PATH = "build/data/basic-usage.db";
    static DependencyGrapher dependencyGrapher;

    @BeforeClass
    public static void setUpDb(){
        dependencyGrapher = new DependencyGrapher(DB_PATH);
    }

    @AfterClass
    public static void shutDownAndDeleteData(){
        Utils.deleteFileOrDirectory(DB_PATH);
    }

    @Test
    public void firstTest(){
        Artifacts artifacts = new Artifacts();
        Artifact artifactOne = new Artifact
            .ArtifactBuilder("jar1", "com.scuilion.product.featureone")
            .type("internal")
            .language("groovy")
            .build();
        Artifact artifactTwo = new Artifact
            .ArtifactBuilder("jarX", "com.scuilion.product.feature.c")
            .type("internal")
            .language("java")
            .build();
        Artifact artifactThree = new Artifact
            .ArtifactBuilder("three", "org.thridparty.some.other.thing")
            .type("external")
            .language("scala")
            .build();

        artifacts.add(artifactOne); 
        artifacts.add(artifactTwo); 
        artifacts.add(artifactThree); 

        dependencyGrapher.store(artifacts);

        assertTrue(true);
    }

}
