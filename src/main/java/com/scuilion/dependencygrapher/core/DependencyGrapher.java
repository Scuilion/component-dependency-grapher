package com.scuilion.dependencygrapher.core;

import com.scuilion.dependencygrapher.neo4j.node.Creator;
import com.scuilion.dependencygrapher.core.Utils;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.Label;

import java.util.Arrays;
import java.util.List;
import java.util.HashMap;

public class DependencyGrapher{

    GraphDatabaseService graphDb;

    public DependencyGrapher(GraphDatabaseService graphDatabaseService){
        graphDb = graphDatabaseService; 
    }

    public DependencyGrapher(String dbPath){
        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(dbPath);
        registerShutdownHook( graphDb );
    }

    public void store(Artifacts artifacts){
        try ( Transaction tx = graphDb.beginTx() ) {
            for(Artifact artifact : artifacts.getArtifacts().values()){
                Node node = graphDb.createNode();
                node.setProperty("artifactName", artifact.getArtifactName());
                node.setProperty("identifier", artifact.getIdentifier());
                node.setProperty("language", artifact.getLanguage());
                node.setProperty("type", artifact.getType());
            }
            tx.success();
        }catch(Exception e){
            System.out.println("exception thrown" + e.toString());
        }
    
    }

    void createDb()//Artifacts artifacts)
    {
        try ( Transaction tx = graphDb.beginTx() )
        {
            //Label languageLabel = DynamicLabel.label("java");
            //Label scopeLabel = DynamicLabel.label("internal");
            //
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

            /*{ "jar3":{
                identifier:"com.scuilion.jarI",
                language:"groovy",
                type:"internal"
                }
            }
            { "jar3":{
                dependsOn:["jar2"],
                becauseOf:["com.scuilion.provides.api.c.Method1","com.scuilion.provides.api.c.Method2"]
            }*/

            //relationship = secondNode.createRelationshipTo( thirdNode, RelTypes.DEPENDS_ON );
            //relationship.setProperty( "becauseOf", "com.scuilion.provides.api.c.Method1" );
            //relationship.setProperty( "becauseOf", "com.scuilion.provides.api.c.Method2" );
            // END SNIPPET: addData

            tx.success();
        }catch(Exception e){
            System.out.println("exception thrown" + e.toString());
        }
        // END SNIPPET: transaction
    }


    void shutDown()
    {
        graphDb.shutdown();
    }

    private static void registerShutdownHook( final GraphDatabaseService graphDb )
    {
        Runtime.getRuntime().addShutdownHook( new Thread()
        {
            @Override
            public void run()
            {
                graphDb.shutdown();
            }
        } );
    }

}
