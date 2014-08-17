package com.scuilion.dependencygrapher.core;

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
