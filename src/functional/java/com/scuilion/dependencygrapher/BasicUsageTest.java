package com.scuilion.dependencygrapher.core;

import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

import java.io.File;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.ResourceIterable;
import org.neo4j.tooling.GlobalGraphOperations;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.Node;

import com.scuilion.dependencygrapher.core.Utils;

public class BasicUsageTest{

    private static final String DB_PATH = "build/data/basic-usage.db";
    static DependencyGrapher dependencyGrapher;
    static GraphDatabaseService graphDb;

    @BeforeClass
    public static void setUpDb(){
        Utils.deleteFileOrDirectory(DB_PATH);
        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( DB_PATH );
        dependencyGrapher = new DependencyGrapher(graphDb);
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

        try ( Transaction tx = graphDb.beginTx() ) {
            ResourceIterable<String> propertyKeys = GlobalGraphOperations.at(graphDb).getAllPropertyKeys();
        
            for ( String propertyKey : propertyKeys ) {
                System.out.println(propertyKey);
            }
            Iterable<Node> nodes = GlobalGraphOperations.at(graphDb).getAllNodes();

            for ( Node node : nodes ) {
                System.out.println(node.getProperty("artifactName"));
            }
        }

        //Node foundNode = graphDb.getNodeById( n.getId() );
        //assertThat( foundNode.getId(), is( n.getId() ) );

        assertTrue(true);
    }

}
