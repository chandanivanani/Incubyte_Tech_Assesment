package assesment.incubyte.library;

import java.util.Set;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

import assesment.incubyte.book.Book;
import assesment.incubyte.user.User;
import assesment.incubyte.exception.InvalidUserException;
import assesment.incubyte.exception.InvalidBookException;
import assesment.incubyte.exception.BookNotAvailableException;
import assesment.incubyte.exception.BorrowLimitExceededException;
import assesment.incubyte.exception.InvalidReturnAttemptException;
import assesment.incubyte.exception.LibraryInitialisationException;


public class Library implements LibraryFunctionalitiesForBook {
    private String libName;
    private LibraryDatabase db;
    private final int MAX_BOOK_ALLOWED_TO_BORROW = 2;

    public Library() {
        throw new LibraryInitialisationException("Can't Create Library Without Name Parameter");
    }

    public Library(String libName) {
        if (isLibraryNameValid(libName) == true) {
            this.libName = libName;
            this.db = new LibraryDatabase();
        } else {
            throw new LibraryInitialisationException("Library name must contain atleast 4 characters");
        }
    }
    public Library(String libName, LibraryDatabase db){
        this(libName);
        this.db=db;
    }

    private boolean isLibraryNameValid(String libName) {
        if (libName != null) {
            if (libName.length() >= 4) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public Library(Object arg1, Object... args) {
        throw new LibraryInitialisationException(
                "Library Constructor should be called with exactly one argument(i:e Library name)");
    }

    public String getName() {
        return this.libName;
    }

    public int getMaxBooksAllowedToBorrow() {
        return this.MAX_BOOK_ALLOWED_TO_BORROW;
    }

    @Override
    public Boolean addBook(Book book, User usr) {
        validateUserAndBook(usr,book);
        processBookAdding(book,usr);
        return true;
    }
    private void processBookAdding(Book book, User usr){
        if (isNewUser(usr)) {
            db.registerNewUser(usr);
        }
        if (db.doesBookHasEntryInContainer(book)) {
            db.incrementBookCount(book);
        } else {
            db.addBookToContainer(book);
        }
    }
    
    @Override
    public Boolean borrowBook(Object param, User usr) {
        Book book = resolveBook(param);
        validateUserAndBook(usr, book);

        ensureBookCanBeBorrowed(usr, book);
        processBookBorrowing(usr, book);

        return true;
    }

    private Book resolveBook(Object param) {
        if (param == null) {
            throw new InvalidBookException("Book Parameter Is Either Null Or Invalid");
        }
        if (param instanceof Book) {
            return (Book) param;
        } else if (param instanceof String) { // Assuming ISBN is a String //TODO: infer type from book class
            return db.getBookByISBN((String) param);
        } else {
            throw new InvalidBookException("Invalid book parameter type");
        }
    }

    private void ensureBookCanBeBorrowed(User usr, Book book) {
        if (!db.isBookAvailableToBorrow(book)) {
            throw new BookNotAvailableException(book);
        }

        if (!isUserEligibleToBorrow(usr, book)) {
            throw new BorrowLimitExceededException(
                    "You have Excceded The Number Of Books Allowed To Borrow Please Return Any Of Your Previous Books To Continue");
        }
    }

    private void processBookBorrowing(User usr, Book book) {
        if (isNewUser(usr)) {
            db.registerNewUser(usr);
        }
        db.decrementBookCount(book);
        db.addBorrowEntry(usr, book);
    }

    private void validateUserAndBook(User usr, Book book) {
        if (!isValidUser(usr)) {
            throw new InvalidUserException("User Parameter Is Either Null Or Invalid");
        }
        if (!isValidBook(book)) {
            throw new InvalidBookException("Book Parameter Is Either Null Or Invalid");
        }
    }

    private boolean isValidBook(Book book) {
        if (book == null)
            return false;
        else
            return true;
    }

    private boolean isValidUser(User usr) {
        if (usr == null)
            return false;
        else
            return true;
    }

    private boolean isNewUser(User usr) {
        if (db.isRegisteredUser(usr) == true) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isUserEligibleToBorrow(User usr, Book book) {
        if (db.getBorrowedBookListOfUser(usr) == null) {
            return true;
        } 
        if(db.getBorrowedBookListOfUser(usr).size() >= MAX_BOOK_ALLOWED_TO_BORROW){
            return false;
        }
        if(db.getBorrowedBookListOfUser(usr).contains(book)){
            return false;
        }
        return true;
    }

    @Override
    public Boolean returnBook(Book book, User usr) {
        validateUserAndBook(usr, book);

        if (!isUserEligibleToReturnThisBook(usr, book)){
            throw new InvalidReturnAttemptException(book, usr);
        }
        db.incrementBookCount(book);
        db.removeBorrowEntry(book, usr);
        return true;
    }

    private boolean isUserEligibleToReturnThisBook(User usr, Book book){
        if(!db.doesBookHasEntryInContainer(book))
            return false;
        if(!db.isRegisteredUser(usr))
            return false;
        return db.getBorrowedBookListOfUser(usr).contains(book);
    }

    @Override
    public List<Book> getAvlBooks() {
        return db.getAvlBooks();
    }
}
