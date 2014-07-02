package com.scuilion.dependencygrapher.neo4j.node;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.Label;

import java.util.List;
import java.util.HashMap;

public class Creator{

    @SuppressWarnings("unchecked")
    static public Node createNode(GraphDatabaseService graphDb , HashMap<String, Object> setupInfo){
        Node node = graphDb.createNode();
        node.setProperty("artifactName", setupInfo.get("artifactName"));
        node.setProperty("identifier", setupInfo.get("identifier"));
        node = addLabels(node, (List)setupInfo.get("lables"));
        return node;
    }

    static private Node addLabels(Node node, List<String> labels){
        for(String lable: labels){
            Label dynamicLable = DynamicLabel.label(lable);
            node.addLabel(dynamicLable);
        }
        return node;
    }
}
