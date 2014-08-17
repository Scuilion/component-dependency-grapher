package com.scuilion.dependencygrapher.neo4j.node;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Transaction;

import com.scuilion.dependencygrapher.core.Artifacts;
import com.scuilion.dependencygrapher.core.Artifact;

import java.util.List;
import java.util.HashMap;

public class Creator{

    static public void createMultipleNodes(GraphDatabaseService graphDb , Artifacts artifacts){
        try ( Transaction tx = graphDb.beginTx() ) {
            for(Artifact artifact : artifacts.getArtifacts().values()){
                createNode(graphDb, artifact);
            }
            tx.success();
        }catch(Exception e){
            System.out.println("exception thrown" + e.toString());
        }
    }

    @SuppressWarnings("unchecked")
    static public Node createNode(GraphDatabaseService graphDb , Artifact artifact){
        Node node = graphDb.createNode();
        node.setProperty("artifactName", artifact.getArtifactName());
        node.setProperty("identifier", artifact.getIdentifier());
        node.setProperty("language", artifact.getLanguage());
        node.setProperty("type", artifact.getType());
        /*
        Node node = graphDb.createNode();
        node.setProperty("artifactName", setupInfo.get("artifactName"));
        node.setProperty("identifier", setupInfo.get("identifier"));
        node = addLabels(node, (List)setupInfo.get("lables"));
        */
        return node;
    }

    //relationship = secondNode.createRelationshipTo( thirdNode, RelTypes.DEPENDS_ON );
    //relationship.setProperty( "becauseOf", "com.scuilion.provides.api.c.Method1" );
    //relationship.setProperty( "becauseOf", "com.scuilion.provides.api.c.Method2" );
    // END SNIPPET: addData
    
    static private Node addLabels(Node node, List<String> labels){
        for(String lable: labels){
            Label dynamicLable = DynamicLabel.label(lable);
            node.addLabel(dynamicLable);
        }
        return node;
    }
}
