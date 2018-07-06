package ru.aptech.library;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;

public class BaseTest {
    @Rule
    public TestName name = new TestName();
    @Before
    public void printTestName(){
        System.out.println("------------------------------------------------------------------Test "+ name.getMethodName() +"------------------------------------------------------------------");
    }
}
