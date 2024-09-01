package assesment.incubyte.library;

import java.util.List;
import assesment.incubyte.book.Book;
import assesment.incubyte.user.User;

public interface LibraryDataBaseFunctionalities {

    Book getBookByISBN(String ISBN);
    void addBookToContainer(Book book);
    void incrementBookCount(Book book);
    void decrementBookCount(Book book);
    boolean doesBookHasEntryInContainer(Book book);
    boolean isBookAvailableToBorrow(Book book);
    List<Book> getAvlBooks();
    
    void registerNewUser(User usr);
    boolean isRegisteredUser(User usr);

    void addBorrowEntry(User usr,Book book);
    void removeBorrowEntry(Book book, User usr);
    List<Book> getBorrowedBookListOfUser(User usr);
}