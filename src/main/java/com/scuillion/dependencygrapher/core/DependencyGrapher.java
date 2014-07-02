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
    Node firstNode;
    Node secondNode;
    Node thirdNode;
    Relationship relationship; 

    public String greeting;

    public boolean someLibraryMethod() {
        createDb();
        //removeData();
        shutDown();
        return true;
    }

    void createDb()
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

            HashMap<String, Object> setupInfo = new HashMap<>();
            setupInfo.put("artifactName", "jar1");
            setupInfo.put("identifier", "com.scuilion.jarJ");
            setupInfo.put("lables", Arrays.asList("groovy", "internal"));
            //firstNode = Creator.createNode(graphDb, setupInfo);
 
            //firstNode = graphDb.createNode();
            //firstNode.setProperty( "artifactName", "JarX" );
            //firstNode.setProperty( "identifier", "com.scuilion.product.feature.c" );
            //firstNode.addLabel(languageLabel);
            //firstNode.addLabel(scopeLabel);

            //HashMap<String, Object> setupInfo = new HashMap<>();
            setupInfo.put("artifactName", "jar2");
            setupInfo.put("identifier", "com.scuilion.jarK");
            setupInfo.put("lables", Arrays.asList("groovy", "internal"));
            //secondNode = Creator.createNode(graphDb, setupInfo);

            //secondNode = graphDb.createNode();
            //secondNode.setProperty( "artifactName", "JarY" );
            //secondNode.setProperty( "identifier", "com.scuilion.product.service.b" );
            //secondNode.addLabel(languageLabel);
            //secondNode.addLabel(scopeLabel);

            //thirdNode = graphDb.createNode();
            //thirdNode.setProperty( "artifactName", "JarZ" );
            //thirdNode.setProperty( "identifier", "com.scuilion.provides.api.c" );
            //thirdNode.addLabel(languageLabel);
            //thirdNode.addLabel(scopeLabel);

            setupInfo.put("artifactName", "jar3");
            setupInfo.put("identifier", "com.scuilion.jarI");
            setupInfo.put("lables", Arrays.asList("groovy", "internal"));
            //thirdNode = Creator.createNode(graphDb, setupInfo);
            { "jar3":{
                identifier:"com.scuilion.jarI",
                language:"groovy",
                type:"internal"
                }
            }
            { "jar3":{
                dependsOn:["jar2"],
                becauseOf:["com.scuilion.provides.api.c.Method1","com.scuilion.provides.api.c.Method2"]
            }

            relationship = secondNode.createRelationshipTo( thirdNode, RelTypes.DEPENDS_ON );
            relationship.setProperty( "becauseOf", "com.scuilion.provides.api.c.Method1" );
            relationship.setProperty( "becauseOf", "com.scuilion.provides.api.c.Method2" );
            // END SNIPPET: addData

            // START SNIPPET: readData
            System.out.print( firstNode.getProperty( "artifactName" ) );
            System.out.print( relationship.getProperty( "becauseOf" ) );
            System.out.print( secondNode.getProperty( "artifactName" ) );
            // END SNIPPET: readData

            greeting = ( (String) firstNode.getProperty( "artifactName" ) )
                       + ( (String) relationship.getProperty( "becauseOf" ) )
                       + ( (String) secondNode.getProperty( "artifactName" ) );

            // START SNIPPET: transaction
            tx.success();
        }catch(Exception e){
            System.out.println("exception thrown" + e.toString());
        }
        // END SNIPPET: transaction
    }


    void removeData()
    {
        try ( Transaction tx = graphDb.beginTx() )
        {
            // START SNIPPET: removingData
            // let's remove the data
            firstNode.getSingleRelationship( RelTypes.DEPENDS_ON, Direction.OUTGOING ).delete();
            firstNode.delete();
            secondNode.delete();
            // END SNIPPET: removingData

            tx.success();
        }
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
