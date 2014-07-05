package com.scuilion.dependencygrapher.core;

import com.scuilion.dependencygrapher.neo4j.node.Creator;

import java.io.File;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.Label;

import java.util.Arrays;
import java.util.List;
import java.util.HashMap;

public class DependencyGrapher{

    //private static final String DB_PATH = "/home/kevino/projects/data/neo4j-hello.db";
    private static final String DB_PATH = "build/data/neo4j-hello.db";

    GraphDatabaseService graphDb;
    Relationship relationship; 

    public String greeting;

    public boolean someLibraryMethod() {
        createDb();
        //removeData();
        shutDown();
        return true;
    }

    void createDb()//Artifacts artifacts)
    {
        //deleteFileOrDirectory( new File( DB_PATH ) );
        // START SNIPPET: startDb
        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( DB_PATH );
        registerShutdownHook( graphDb );
        // END SNIPPET: startDb

        // START SNIPPET: transaction
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
        System.out.println();
        System.out.println( "Shutting down database ..." );
        // START SNIPPET: shutdownServer
        graphDb.shutdown();
        // END SNIPPET: shutdownServer
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

    private static void deleteFileOrDirectory( File file )
    {
        if ( file.exists() )
        {
            if ( file.isDirectory() )
            {
                for ( File child : file.listFiles() )
                {
                    deleteFileOrDirectory( child );
                }
            }
            file.delete();
        }
    }

}
