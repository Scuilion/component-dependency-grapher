package com.scuilion.dependencygrapher.neo4j.node;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;

import java.util.HashMap;

import java.util.Arrays;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.test.TestGraphDatabaseFactory;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.Node;

public class CreatorTest {

    static protected GraphDatabaseService graphDb;
    static Node creatorNode = null;
    //static Creator classUnderTest = null; 

    @BeforeClass
    static public void prepareTestDatabase()
    {
        //classUnderTest = new Library();
        graphDb = new TestGraphDatabaseFactory().newImpermanentDatabase();
        try ( Transaction tx = graphDb.beginTx() ) {
            HashMap<String, Object> setupInfo = new HashMap<>();
            setupInfo.put("artifactName", "jarA");
            setupInfo.put("identifier", "com.scuilion");
            setupInfo.put("lables", Arrays.asList("groovy", "internal"));
            
            creatorNode = Creator.nodeCreator(graphDb, setupInfo);
            tx.success();
        }
    }

    @Test 
    public void testNodeCreator() {
        assertThat(creatorNode.getId(), is(greaterThan(-1L)));
    }

    @Test 
    public void testForNodeProperties() {
        
        try ( Transaction tx = graphDb.beginTx() ) {
            Node foundNode = graphDb.getNodeById( creatorNode.getId() );
            assertThat( foundNode.getId(), is( creatorNode.getId() ) );
            assertThat( (String) foundNode.getProperty( "artifactName" ), is( "jarA" ) );
        }
    }

    @Test 
    public void testForNodeLabels() {
        try ( Transaction tx = graphDb.beginTx() ) {
            Node foundNode = graphDb.getNodeById( creatorNode.getId() );
            assertTrue(foundNode.hasLabel(DynamicLabel.label("groovy")));
            assertTrue(foundNode.hasLabel(DynamicLabel.label("internal")));
        }
    }

    @AfterClass
    static public void destroyTestDatabase()
    {
        graphDb.shutdown();
    }
}
