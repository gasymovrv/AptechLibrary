package ru.aptech.library;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ru.aptech.library.controllers.BookControllerTest;
import ru.aptech.library.controllers.StandaloneBookControllerTest;
import ru.aptech.library.service.BookServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BookControllerTest.class,
        StandaloneBookControllerTest.class,
        BookServiceTest.class
})
public class TestSuite {
}
