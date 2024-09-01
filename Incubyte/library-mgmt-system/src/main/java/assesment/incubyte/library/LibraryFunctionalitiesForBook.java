package assesment.incubyte.library;

import java.util.List;
import assesment.incubyte.book.Book;
import assesment.incubyte.user.User;


public interface LibraryFunctionalitiesForBook {
    public Boolean addBook(Book book,User usr);
    public Boolean borrowBook(Object param, User usr);
    public Boolean returnBook(Book book, User usr);
    public List<Book> getAvlBooks();
}
