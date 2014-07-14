package com.scuilion.dependencygrapher.neo4j.node;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;

import java.util.HashMap;
import java.util.Arrays;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.test.TestGraphDatabaseFactory;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterable;
import org.neo4j.tooling.GlobalGraphOperations;

public class CreatorTest {

    static protected GraphDatabaseService graphDb;
    static Node creatorNode = null;

    @BeforeClass
    static public void prepareTestDatabase()
    {
        graphDb = new TestGraphDatabaseFactory().newImpermanentDatabase();
        try ( Transaction tx = graphDb.beginTx() ) {
            HashMap<String, Object> setupInfo = new HashMap<>();
            setupInfo.put("artifactName", "jarA");
            setupInfo.put("identifier", "com.scuilion");
            setupInfo.put("lables", Arrays.asList("groovy", "internal"));
            
            creatorNode = Creator.createNode(graphDb, setupInfo);
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

    @Test 
    public void testAllPropertiesCreated() {
        try ( Transaction tx = graphDb.beginTx() ) {
            ResourceIterable<String> propertyKeys = GlobalGraphOperations.at(graphDb).getAllPropertyKeys();
            for ( String propertyKey : propertyKeys ) {
                System.out.println(propertyKey);
            }
 
            assertThat(propertyKeys, contains("artifactName","identifier","language","type", "dependencyIdentifiers"));
        }
    }

    @AfterClass
    static public void destroyTestDatabase()
    {
        graphDb.shutdown();
    }
}
