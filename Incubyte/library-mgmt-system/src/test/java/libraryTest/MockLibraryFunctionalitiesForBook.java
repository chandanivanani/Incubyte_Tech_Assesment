package libraryTest;
import java.util.List;
import java.util.ArrayList;

import assesment.incubyte.book.Book;
import assesment.incubyte.user.User;
import assesment.incubyte.library.LibraryFunctionalitiesForBook;

public class MockLibraryFunctionalitiesForBook implements LibraryFunctionalitiesForBook {

    @Override
    public Boolean addBook(Book book, User usr) {
        // Mocking to return true always
        return true;
    }

    @Override
    public Boolean borrowBook(Object param, User usr) {
        // Mocking to return true always
        return true;
    }

    @Override
    public Boolean returnBook(Book book, User usr) {
        // Mocking to return true always
        return true;
    }

    @Override
    public List<Book> getAvlBooks() {
        // Mocking to return empty list always
        List<Book> bookList = new ArrayList<>();
        return bookList;
    }
    
}
