package assesment.incubyte.library;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;

import assesment.incubyte.book.Book;
import assesment.incubyte.user.User;

public class LibraryDatabase implements LibraryDataBaseFunctionalities {
    private Map<Book, Integer> bookContainer = new HashMap<>();
    private Set<User> userCollection = new HashSet<>();
    private Map<User, List<Book>> borrowedBooksRecord = new HashMap<User, List<Book>>(); // legacy

    public Book getBookByISBN(String ISBN) {
        Book resultBook= bookContainer.keySet().stream().filter(bk -> bk.getISBN() == ISBN).findAny().orElse(null);
        return resultBook;
    }

    public void addBookToContainer(Book book) {
        bookContainer.put(book, 1);
    }

    public void incrementBookCount(Book book) {
        int avlBookCount = bookContainer.get(book);
        bookContainer.put(book, avlBookCount + 1);
    }

    public void decrementBookCount(Book book) {
        int avlBookCount = bookContainer.get(book);
        bookContainer.put(book, avlBookCount - 1);
    }

    public boolean doesBookHasEntryInContainer(Book book) {
        if (this.bookContainer.containsKey(book))
            return true;
        else
            return false;
    }

    public boolean isBookAvailableToBorrow(Book book) {
        if (doesBookHasEntryInContainer(book) == true) {
            int avlBookCount = bookContainer.get(book);
            if (avlBookCount > 0) {
                return true;
            }
        }
        return false;
    }

    public List<Book> getAvlBooks() {
        return this.bookContainer.keySet().stream().toList();
    }

    public void registerNewUser(User usr) {
        this.userCollection.add(usr);
    }

    public boolean isRegisteredUser(User usr) {
        return this.userCollection.contains(usr);
    }

    public void addBorrowEntry(User usr, Book book) {
        List<Book> borrowedBookList = borrowedBooksRecord.get(usr);
        if (borrowedBookList == null) {
            borrowedBookList = new ArrayList<>();
        }
        borrowedBookList.add(book);
        borrowedBooksRecord.put(usr, borrowedBookList);
    }

    public void removeBorrowEntry(Book book, User usr) {
        List<Book> borrowedBookList = borrowedBooksRecord.get(usr);
        borrowedBookList.remove(book);
    }

    public List<Book> getBorrowedBookListOfUser(User usr) {
        List<Book> borrowedList = borrowedBooksRecord.get(usr);
        if (borrowedList == null) {
            return null;
        } else {
            return Collections.unmodifiableList(borrowedList);
        }
    }
}
