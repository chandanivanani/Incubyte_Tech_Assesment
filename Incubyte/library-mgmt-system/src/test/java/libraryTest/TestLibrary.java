package libraryTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.lang.reflect.Field;

import assesment.incubyte.user.User;
import assesment.incubyte.book.Book;
import bookTest.DemoTestBookObjectStore;
import userTest.DemoTestUserObjectStore;
import assesment.incubyte.library.Library;
import assesment.incubyte.library.LibraryDatabase;
import assesment.incubyte.exception.InvalidBookException;
import assesment.incubyte.exception.InvalidUserException;
import assesment.incubyte.exception.BookNotAvailableException;
import assesment.incubyte.exception.BorrowLimitExceededException;
import assesment.incubyte.exception.InvalidReturnAttemptException;
import assesment.incubyte.exception.LibraryInitialisationException;

public class TestLibrary {
    public static final String libName1 = "Rollwala Library";
    public static final String libName2 = "Gujarat University Library";

    private static final Object useReflectionToMakeFieldAccessibleAndReturnTheObject(String className, String fieldName, Object obj) throws Exception{
        Class<?> cls = Class.forName(className);
        Field field = cls.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }

    @Test
    @DisplayName("Library Constructor call for null or empty command must throw an LibraryInitialisationException")
    public void testThat_ConstructorShouldThrowErrorWithNoArgument() {
        Exception e = assertThrows(LibraryInitialisationException.class, () -> new Library());
        assertEquals("Can't Create Library Without Name Parameter", e.getMessage());
    }

    @Test
    @DisplayName("Library Constructor call with library name as null must raise LibraryInitialisationException")
    public void testThat_ConstructorShouldThrowErrorWithNullNameArgument() {
        Exception e = assertThrows(LibraryInitialisationException.class, () -> new Library(null));
        assertEquals("Library name must contain atleast 4 characters", e.getMessage());
    }

    @Test
    @DisplayName("Library Constructor call with library name less than length 4 must raise LibraryInitialisationException")
    public void testThat_ConstructorShouldThrowErrorWithInvalidNameArgument() {
        Exception e = assertThrows(LibraryInitialisationException.class, () -> new Library("abc"));
        assertEquals("Library name must contain atleast 4 characters", e.getMessage());
    }

    @Test
    @DisplayName("Library Constructor call with appropriate library name should return non null object")
    public void testThat_ConstructorShouldWorkWithValidArguments() {
        Library lib = new Library("Rollwala Library");
        assertNotEquals(null, lib, "Constructor Should Not Return Null With Valid Arguments");
    }

    @Test
    @DisplayName("Library Constructor call with multiple arguments must throw an LibraryInitialisationException")
    public void testThat_ConstructorCallWithMultipleArgumentsMustThrowError() {
        Exception e = assertThrows(LibraryInitialisationException.class,
                () -> new Library("Rollwala Library", "Gujarat University Library"));
        assertEquals("Library Constructor should be called with exactly one argument(i:e Library name)",
                e.getMessage());
    }

    @Test
    @DisplayName("Testing Initialisation Of Object with getName Function")
    public void testThat_InitialisationAndGetNameWorksTogether() {
        Library lib = new Library(libName1);
        assertNotNull(lib);
        assertEquals(libName1, lib.getName());
    }

    @Test
    @DisplayName("Test That addBook Method throws InvalidBookException if we pass null argument for book ")
    public void testThat_addMethodThrowsInvalidBookExceptionWhenWePassNullBookArgument() {
        Library lib = new Library(libName2);
        User usr = DemoTestUserObjectStore.getUserObject(0);

        Exception e = assertThrows(InvalidBookException.class, () -> lib.addBook(null, usr));
        assertEquals("Book Parameter Is Either Null Or Invalid", e.getMessage());
    }

    @Test
    @DisplayName("Test That addBook Method throws InvalidUserException if we pass null argument for user ")
    public void testThat_addMethodInvalidUserExceptionIfWePassNullUserArgument() {
        Library lib = new Library(libName1);
        Book testBook = DemoTestBookObjectStore.getBookObject(2);

        Exception e = assertThrows(InvalidUserException.class, () -> lib.addBook(testBook, null));
        assertEquals("User Parameter Is Either Null Or Invalid", e.getMessage());
    }

    @Test
    @DisplayName("Test That addBook Method returns true when a new book is added to library")
    public void testThat_addMethodAcceptsABookObjectAndAnUserObject() {
        Library lib = new Library(libName2);
        Book testBook = DemoTestBookObjectStore.getBookObject(1);

        User usr = new User("Biswojit");
        assertTrue(lib.addBook(testBook, usr));
    }

    @Test
    @DisplayName("Test That addBook Method really adds that and only that book it got in order")
    public void testThat_addMethodUpdatesTheBookList() {
        Library lib = new Library(libName1);
        Book testBook = DemoTestBookObjectStore.getBookObject(2);
        User usr = DemoTestUserObjectStore.getUserObject(0);

        assertTrue(lib.addBook(testBook, usr));
        assertEquals(1, lib.getAvlBooks().size());

        List<Book> avlBookList = lib.getAvlBooks();
        assertEquals(testBook, avlBookList.get(0));
    }

    @Test
    @DisplayName("Test That addBook Method doesn't adds duplicate book again")
    public void testThat_addMethodIncrementsBookCountWithDuplicateBooks() {
        Library lib = new Library(libName1);
        Book testBook = DemoTestBookObjectStore.getBookObject(3);
        User usr = DemoTestUserObjectStore.getUserObject(4);


        assertTrue(lib.addBook(testBook, usr));
        assertTrue(lib.addBook(testBook, usr));
        assertEquals(1, lib.getAvlBooks().size());
        assertEquals(testBook, lib.getAvlBooks().get(0));
    }

    @Test
    @DisplayName("Test that addBook method registers an user and only that user if not registsred previously")
    public void testThat_addMethodRegistersAUserIfNotRegistredPrevioulsy() {
        LibraryDatabase database = new LibraryDatabase();
        Library lib = new Library(libName2,database);
        Book testBook = DemoTestBookObjectStore.getBookObject(3);
        User usr = DemoTestUserObjectStore.getUserObject(4);
        User otherUsr = DemoTestUserObjectStore.getUserObject(7);

        assertTrue(lib.addBook(testBook, usr));

        try {
            @SuppressWarnings("unchecked")
            Set<User> userCollection = (HashSet<User>) useReflectionToMakeFieldAccessibleAndReturnTheObject(
                            "assesment.incubyte.library.LibraryDatabase","userCollection",database);

            assertTrue(userCollection.contains(usr));
            assertFalse(userCollection.contains(otherUsr));

        } catch (Exception e) {
            assertNull(e);
        }
    }

    @Test
    @DisplayName("Test That showBook Method returns empty list when there is no book in the library")
    public void testThat_showBookMethodReturnsEmptyList() {
        Library lib = new Library(libName1);
        assertEquals(0, lib.getAvlBooks().size());
    }

    @Test
    @DisplayName("Borrow book method throws InvalidUserException when called with null argument for user")
    public void testThat_borrowBookThrowsInvalidUserExceptionWithNullArgument() {
        Library lib = new Library(libName2);
        Book testBook = DemoTestBookObjectStore.getBookObject(5);

        Exception e = assertThrows(InvalidUserException.class, () -> lib.borrowBook(testBook, null));
        assertEquals("User Parameter Is Either Null Or Invalid", e.getMessage());
    }

    @Test
    @DisplayName("Borrow book method throws InvalidBookException when called with null argument for book")
    public void testThat_borrowBookThrowsInvalidBookExceptionWithNullArgument2() {
        Library lib = new Library(libName1);
        User usr = DemoTestUserObjectStore.getUserObject(1);

        Exception e = assertThrows(InvalidBookException.class, () -> lib.borrowBook(null, usr));
        assertEquals("Book Parameter Is Either Null Or Invalid", e.getMessage());

    }

    @Test
    @DisplayName("Borrow book method throws sub classes of IllegalArgumentException when called with null argument for both book and user")
    public void testThat_borrowBookThrowsIllegalArgumentExceptionWithNullArgument3() {
        Library lib = new Library(libName2);

        Exception e = assertThrows(IllegalArgumentException.class, () -> lib.borrowBook(null, null));
        assertEquals("Book Parameter Is Either Null Or Invalid", e.getMessage());
    }

    @Test
    @DisplayName("Borrow book method throws BookNotAvailableException when called with empty bookContainer")
    public void testThat_boorowBookThrowsBookNotAvailableExceptionWhenCalledWithEmptyBookContainer() {
        Library lib = new Library(libName1);
        Book testBook = DemoTestBookObjectStore.getBookObject(3);
        User usr = DemoTestUserObjectStore.getUserObject(4);

        Exception e = assertThrows(BookNotAvailableException.class, () -> lib.borrowBook(testBook, usr));
        assertEquals("Sorry " + testBook.getBookTitle() + " book is currently not available to borrow", e.getMessage());
    }

    @Test
    @DisplayName("Test that borrowBook method decrements book count by 1 when called with proper parameters")
    public void testThat_borrowBookDecrementsBookCount() {
        LibraryDatabase database = new LibraryDatabase();
        Library lib = new Library(libName2,database);
        Book testBook = DemoTestBookObjectStore.getBookObject(1);
        User usr = DemoTestUserObjectStore.getUserObject(0);
        lib.addBook(testBook, usr);

        try {
            @SuppressWarnings("unchecked")
            Map<Book, Integer> bookContainer = (Map<Book, Integer>) useReflectionToMakeFieldAccessibleAndReturnTheObject(
                            "assesment.incubyte.library.LibraryDatabase","bookContainer",database);

            assertTrue(bookContainer.keySet().contains(testBook));
            assertEquals(1, bookContainer.get(testBook));

            assertDoesNotThrow(() -> lib.borrowBook(testBook, usr));
            assertEquals(0, bookContainer.get(testBook));

        } catch (Exception e) {
            assertNull(e);
        }
    }

    @Test
    @DisplayName("Test that borrowBook method doesn't decrements book count when count is already zero")
    public void testThat_borrowBookDoesNotDecrementsBookCountWhenCountIsZero() {
        LibraryDatabase database = new LibraryDatabase();
        Library lib = new Library(libName1);
        Book testBook = DemoTestBookObjectStore.getBookObject(5);
        User usr = DemoTestUserObjectStore.getUserObject(2);

        lib.addBook(testBook, usr);

        try {
            @SuppressWarnings("unchecked")
            Map<Book, Integer> bookContainer = (Map<Book, Integer>) useReflectionToMakeFieldAccessibleAndReturnTheObject(
                            "assesment.incubyte.library.LibraryDatabase","bookContainer",database);

            lib.borrowBook(testBook, usr);
            Exception e = assertThrows(BookNotAvailableException.class, () -> lib.borrowBook(testBook, usr));
            assertEquals("Sorry " + testBook.getBookTitle() + " book is currently not available to borrow",
                    e.getMessage());

            // assertEquals(0, bookContainer.get(testBook));

        } catch (Exception e) {
            assertNull(e);
        }

    }

    @Test
    @DisplayName("Test that borrowBook method registers an user and only that user if not registsred previously")
    public void testThat_borrowMethodRegistersAUserIfNotRegistredPrevioulsy() {
        LibraryDatabase database = new LibraryDatabase();
        Library lib = new Library(libName2, database);
        Book testBook = DemoTestBookObjectStore.getBookObject(3);
        User usr = DemoTestUserObjectStore.getUserObject(4);
        User borrower = DemoTestUserObjectStore.getUserObject(5);
        User otheruser = DemoTestUserObjectStore.getUserObject(6);
        assertTrue(lib.addBook(testBook, usr));
        assertTrue(lib.borrowBook(testBook, borrower));

        try {
            @SuppressWarnings("unchecked")
            Set<User> userCollection = (HashSet<User>) useReflectionToMakeFieldAccessibleAndReturnTheObject(
                            "assesment.incubyte.library.LibraryDatabase","userCollection",database);

            assertTrue(userCollection.contains(borrower));
            assertFalse(userCollection.contains(otheruser));

        } catch (Exception e) {
            assertNull(e);
        }
    }

    @Test
    @DisplayName("Test that borrowBook method keeps the log for a suucessful borrowing(logging user & book)")
    public void testThat_borrowMethodLogsSuccessfulBorrowing() {
        LibraryDatabase database = new LibraryDatabase();
        Library lib = new Library(libName1,database);
        Book testBook = DemoTestBookObjectStore.getBookObject(3);
        User usr = DemoTestUserObjectStore.getUserObject(4);

        lib.addBook(testBook, usr);
        lib.borrowBook(testBook, usr);

        try{
            @SuppressWarnings("unchecked")
            Map<User, List<Book>> borrowedBooksRecord = (Map<User, List<Book>>) useReflectionToMakeFieldAccessibleAndReturnTheObject(
                            "assesment.incubyte.library.LibraryDatabase","borrowedBooksRecord",database);

            assertTrue(borrowedBooksRecord.keySet().contains(usr));
            assertTrue(borrowedBooksRecord.get(usr).contains(testBook));

        } catch (Exception e) {
            assertNull(e);
        }
    }

    @Test
    @DisplayName("Test that borrowBook method limits the max borrowing numbers to specified value")
    public void testThat_borrowMethodLimitsNumberOfBooksToBorrow() {
        Library lib = new Library(libName1);
        User usr = DemoTestUserObjectStore.getUserObject(4);
        Book testBook1 = DemoTestBookObjectStore.getBookObject(0);
        Book testBook2 = DemoTestBookObjectStore.getBookObject(1);
        Book testBook3 = DemoTestBookObjectStore.getBookObject(2);


        assertEquals(2, lib.getMaxBooksAllowedToBorrow());// considering this as 2
        // TODO : Automate The Case
        lib.addBook(testBook1, usr);
        lib.addBook(testBook2, usr);
        lib.addBook(testBook3, usr);

        lib.borrowBook(testBook1, usr);
        lib.borrowBook(testBook2, usr);
        Exception e = assertThrows(BorrowLimitExceededException.class, () -> lib.borrowBook(testBook3, usr));
        assertEquals(
                "You have Excceded The Number Of Books Allowed To Borrow Please Return Any Of Your Previous Books To Continue",
                e.getMessage());
    }

    @Test
    @DisplayName("Test that borrowBook method allows a single copy of any book to be borrowed by a single user")
    public void testThat_borrowMethodAllowsOnlySingleCopyToBeBorrowed() {
        Library lib = new Library(libName2);
        Book testBook = DemoTestBookObjectStore.getBookObject(3);
        User usr = DemoTestUserObjectStore.getUserObject(4);
        lib.addBook(testBook, usr);
        lib.addBook(testBook, usr);
        lib.borrowBook(testBook, usr);
        Exception e = assertThrows(BorrowLimitExceededException.class, () -> lib.borrowBook(testBook, usr));
        assertEquals(
                "You have Excceded The Number Of Books Allowed To Borrow Please Return Any Of Your Previous Books To Continue",
                e.getMessage());

    }

    @Test
    @DisplayName("Test That returnBook Method Throws Expected Exceptions For Related Invalid Scenarios")
    public void testThat_returnBookThrowsExpectedException() {
        Library lib = new Library(libName2);
        Book testBook = DemoTestBookObjectStore.getBookObject(3);
        User usr = DemoTestUserObjectStore.getUserObject(4);

        Exception e = assertThrows(InvalidUserException.class, () -> lib.returnBook(testBook, null));
        assertEquals("User Parameter Is Either Null Or Invalid", e.getMessage());

        Exception f = assertThrows(InvalidBookException.class, () -> lib.returnBook(null, usr));
        assertEquals("Book Parameter Is Either Null Or Invalid", f.getMessage());

        Exception g = assertThrows(IllegalArgumentException.class, () -> lib.borrowBook(null, null));
        assertEquals("Book Parameter Is Either Null Or Invalid", g.getMessage());
    }

    @Test
    @DisplayName("Test That returnBook Method Throws InvalidReturnAttemptException with Invalid User Or Book")
    public void testThat_returnBookThrowsInvalidReturnAttemptException() {
        Library lib = new Library(libName1);
        User usr = DemoTestUserObjectStore.getUserObject(4);
        User unregUser = DemoTestUserObjectStore.getUserObject(5);
        Book testBook = DemoTestBookObjectStore.getBookObject(6);
        Book unregBook = DemoTestBookObjectStore.getBookObject(7);

        lib.addBook(testBook, usr);

        Exception e = assertThrows(InvalidReturnAttemptException.class, () -> lib.returnBook(unregBook, usr));
        assertEquals(unregBook.getBookTitle() + " book is not currently allowed to be returned by " + usr.getName(),
                e.getMessage());

        Exception f = assertThrows(InvalidReturnAttemptException.class, () -> lib.returnBook(testBook, unregUser));
        assertEquals(
                testBook.getBookTitle() + " book is not currently allowed to be returned by " + unregUser.getName(),
                f.getMessage());

        Exception g = assertThrows(InvalidReturnAttemptException.class, () -> lib.returnBook(unregBook, unregUser));
        assertEquals(
                unregBook.getBookTitle() + " book is not currently allowed to be returned by " + unregUser.getName(),
                g.getMessage());

    }

    @Test
    @DisplayName("Test That returnBook increments book counter and removes data from borrowedBooksRecord with valid User and Book")
    public void testThat_returnBookIncrementsBookCounter() {
        LibraryDatabase database = new LibraryDatabase();
        Library lib = new Library(libName1,database);
        User usr = DemoTestUserObjectStore.getUserObject(4);
        Book testBook = DemoTestBookObjectStore.getBookObject(6);

        try {
            @SuppressWarnings("unchecked")
            Map<Book, Integer> bookContainer = (Map<Book, Integer>) useReflectionToMakeFieldAccessibleAndReturnTheObject(
                "assesment.incubyte.library.LibraryDatabase","bookContainer",database);

            @SuppressWarnings("unchecked")
            Map<User, List<Book>> borrowedBooksRecord = (Map<User, List<Book>>) useReflectionToMakeFieldAccessibleAndReturnTheObject(
                            "assesment.incubyte.library.LibraryDatabase","borrowedBooksRecord",database);

            lib.addBook(testBook, usr);
            assertEquals(1, bookContainer.get(testBook));

            lib.borrowBook(testBook, usr);
            assertEquals(0, bookContainer.get(testBook));
            assertTrue(borrowedBooksRecord.get(usr).contains(testBook));

            lib.returnBook(testBook, usr);
            assertEquals(1, bookContainer.get(testBook));
            assertFalse(borrowedBooksRecord.get(usr).contains(testBook));

        } catch (Exception e) {
            assertNull(e);
        }
    }


    @Test
    @DisplayName("test to check borrow book method accepts book ISBN parameter as well as book object for borrowing books")
    public void testThat_borrowBookMethodWorksWithISBN()
    {
        Library lib = new Library(libName1);
        User usr = DemoTestUserObjectStore.getUserObject(4);
        Book testBook = DemoTestBookObjectStore.getBookObject(6);

        lib.addBook(testBook, usr);
        assertTrue(lib.borrowBook(testBook, usr));
        lib.returnBook(testBook, usr);
        assertTrue(lib.borrowBook(testBook.getISBN(), usr));
    }

    @Test
    @DisplayName("test to check borrow book method throws InvalidBookException if ISBN is not available")
    public void testThat_borrowBookMethodThrowsInvalidBookExceptionForInvalidISBN()
    {
        Library lib = new Library(libName1);
        User usr = DemoTestUserObjectStore.getUserObject(4);
        Book testBook = DemoTestBookObjectStore.getBookObject(6);

        Exception e = assertThrows(InvalidBookException.class,()->lib.borrowBook(testBook.getISBN(), usr));
        assertEquals("Book Parameter Is Either Null Or Invalid", e.getMessage());
    }

    @Test
    @DisplayName("test to check borrow book method throws InvalidBookException if parameters paased are other than ISBN or Book type")
    public void testThat_borrowBookMethodThrowsInvalidBookExceptionForInvalidISBNOrBookParameter()
    {
        Library lib = new Library(libName1);
        User usr = DemoTestUserObjectStore.getUserObject(4);
        Book testBook = DemoTestBookObjectStore.getBookObject(6);

        lib.addBook(testBook, usr);
        Exception e = assertThrows(InvalidBookException.class,()->lib.borrowBook(new Object(), usr));
        assertEquals("Invalid book parameter type", e.getMessage());
    }

}
