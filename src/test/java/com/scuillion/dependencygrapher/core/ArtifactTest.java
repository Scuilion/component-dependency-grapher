package com.scuilion.dependencygrapher.core;

import org.junit.Test;
import static org.junit.Assert.*;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.containsInAnyOrder;

import java.util.ArrayList;

public class ArtifactTest {
 
    @Test
    public void fullCreation(){
        Artifact artifact = new Artifact
                .ArtifactBuilder("jar1", "com.scuilion.product.featureone")
                .type("internal")
                .language("groovy")
                .dependencyIdentifiers(new ArrayList<String>(){{ add("c.s.k"); add("c.s.o");}})
                .build();
        assertThat(artifact.getArtifactName(), is("jar1") );
        assertThat(artifact.getIdentifier(), is("com.scuilion.product.featureone"));
        assertThat(artifact.getType(), is("internal"));
        assertThat(artifact.getLanguage(), is("groovy"));
        assertThat(artifact.getDependencyIdentifiers(), containsInAnyOrder( "c.s.o", "c.s.k"));
    }
    @Test
    public void defaultValues(){
        Artifact artifact = new Artifact
                .ArtifactBuilder("jar2", "com.scuilion.product.featuretwo")
                .build();
        assertThat(artifact.getArtifactName(), is("jar2") );
        assertThat(artifact.getIdentifier(), is("com.scuilion.product.featuretwo"));
        assertThat(artifact.getType(), is(""));
        assertThat(artifact.getLanguage(), is(""));
        assertEquals(artifact.getDependencyIdentifiers().size(), 0);
    }
}
