package com.scuilion.dependencygrapher.core;

import org.junit.Test;
import static org.junit.Assert.*;

import static org.hamcrest.Matchers.is;

import java.util.ArrayList;

public class ArtifactsTest {
    Artifacts artifacts = new Artifacts();
 
    @Test
    public void twoGenericArtifacts(){
        artifacts.add(new Artifact
                .ArtifactBuilder("jar1", "com.scuilion.product.featureone")
                .type("internal")
                .language("groovy")
                .dependencyIdentifiers(new ArrayList<String>(){{ add("c.s.k"); add("c.s.o");}})
                .build()
        );
        artifacts.add(new Artifact
                .ArtifactBuilder("jar2", "com.scuilion.product.featuretwo")
                .type("internal")
                .language("groovy")
                .dependencyIdentifiers(new ArrayList<String>(){{ add("c.s.k"); add("c.s.o");}})
                .build()
        );
        assertEquals(artifacts.getArtifacts().size(), 2);
    }
    @Test
    public void lastOneWins(){
        artifacts.add(new Artifact
                .ArtifactBuilder("jar1", "com.scuilion.product.featureone")
                .type("internal")
                .build()
        );
        artifacts.add(new Artifact
                .ArtifactBuilder("jar1", "com.scuilion.product.featuretwo")
                .type("external")
                .build()
        );
        assertEquals(artifacts.getArtifacts().size(), 1);
        assertThat(artifacts.getArtifacts().get("jar1").getType(), is("external"));
    }

}
