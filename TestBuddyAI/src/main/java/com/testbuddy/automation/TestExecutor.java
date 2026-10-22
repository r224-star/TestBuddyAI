package com.testbuddy.automation;

import org.testng.TestNG;

import java.util.ArrayList;
import java.util.List;

public class TestExecutor {

	public void runTestsByClassName(List<String> fullyQualifiedClassNames) throws Exception {
        List<Class<?>> classes = new ArrayList<>();
        for (String name : fullyQualifiedClassNames) {
            classes.add(Class.forName(name));
        }

        TestNG testng = new TestNG();
        testng.setTestClasses(classes.toArray(new Class<?>[0]));
        testng.addListener(new TestExecutionListener());
        testng.run();
    }
}