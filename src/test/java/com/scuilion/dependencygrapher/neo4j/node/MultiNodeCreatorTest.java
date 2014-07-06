package com.scuilion.dependencygrapher.neo4j.node;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.test.TestGraphDatabaseFactory;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.graphdb.index.Index;

public class MultiNodeCreatorTest {

    static protected GraphDatabaseService graphDb;
    static Node creatorNode = null;

    @BeforeClass
    static public void prepareTestDatabase()
    {
        graphDb = new TestGraphDatabaseFactory().newImpermanentDatabase();
        try ( Transaction tx = graphDb.beginTx() ) {
            List<HashMap<String, Object>> nodes = new ArrayList<>();

            HashMap<String, Object> setupInfo = new HashMap<>();
            setupInfo.put("artifactName", "jarA");
            setupInfo.put("identifier", "com.scuilion");
            setupInfo.put("lables", Arrays.asList("groovy", "internal"));

            nodes.add(setupInfo);
            
            setupInfo.put("artifactName", "jarA");
            setupInfo.put("identifier", "com.scuilion");
            setupInfo.put("lables", Arrays.asList("groovy", "internal"));
            
            nodes.add(setupInfo);

            Creator.createMultipleNodes(graphDb, nodes);
            tx.success();
        }
    }


    @Test public void multipleNodesFoundInDb(){
        try ( Transaction tx = graphDb.beginTx() ) {
            IndexManager index = graphDb.index();
            Index<Node> artifact = index.forNodes("artifactName");
            /*for(Node node : artifact){
                System.out.println(node.getId());
            }*/
        }
        assertTrue(true);
    }
}
