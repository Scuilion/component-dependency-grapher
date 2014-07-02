package com.scuilion.dependencygrapher.core;

import org.junit.Test;
import static org.junit.Assert.*;

public class DependencyGrapherTest {
    
    DependencyGrapher dependencyGrapher = new DependencyGrapher(); 
    @Test 
    public void testBlank() {
        dependencyGrapher.someLibraryMethod();
        assertTrue(true);
    }

}
