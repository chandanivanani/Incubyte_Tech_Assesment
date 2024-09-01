package libraryTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Year;
import assesment.incubyte.book.Book;
import assesment.incubyte.user.User;
import assesment.incubyte.library.LibraryFunctionalitiesForBook;

public class TestLibraryFunctionalitiesForBook {
    @Test
    @DisplayName("initial Test For Methods defined Library Functionalities Interface")
    public void testThat_LibraryFunctionalitiesMathodsWorkingAsPerIntended(){
        LibraryFunctionalitiesForBook testFunc = new MockLibraryFunctionalitiesForBook();
        User testUser = new User("Biswojit");
        String ISBN = "1234567890";
        Year publicationYear = Year.of(2000);
        Book testBook=new Book(ISBN,"Deep Learning","lan Goodfellow",publicationYear);

        assertTrue(testFunc.addBook(testBook,testUser));
        assertTrue(testFunc.borrowBook(testBook,testUser));
        assertTrue(testFunc.returnBook(testBook,testUser));
        assertEquals(0,testFunc.getAvlBooks().size());
    }
}
