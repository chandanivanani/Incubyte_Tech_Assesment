package bookTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Year;
import assesment.incubyte.book.Book;

public class TestBook {
    
    @Test
    @DisplayName("Book Constructor call for null or empty command must throw an UnsupportedOperationException")
    public void testThat_ConstructorShouldThrowErrorWithNoArgument(){
        assertThrows(UnsupportedOperationException.class, ()-> new Book());
    }

    @Test
    @DisplayName("Book Constructor call with appropriate parameters should return non null object")
    public void testThat_ConstructorShouldWorkWithValidArguments(){
        Book testBook=DemoTestBookObjectStore.getBookObject(0);
        assertNotEquals(null, testBook,"Constructor Should Not Return Null With Valid Arguments");
    }


    @Test
    @DisplayName("Book Constructor call with more than enough arguments must throw an UnsupportedOperationException")
    public void testThat_ConstructorCallWithMultipleArgumentsMustThrowError(){
        String ISBN = "1234567890";
        Year publicationYear = Year.of(2000);
        assertThrows(UnsupportedOperationException.class,()-> new Book(ISBN,"Deep Learning","lan Goodfellow",publicationYear,"Extra Arg"));
    }

    @Test
    @DisplayName("Book Constructor call with Book ISBN as null must raise IllegalArgumentException")
    public void testThat_ConstructorShouldThrowErrorWithNullArgumentForISBN(){
        Year publicationYear = Year.of(2000);
        assertThrows(IllegalArgumentException.class, ()->new Book(null,"Deep Learning","lan Goodfellow",publicationYear));
    }

    @Test
    @DisplayName("Book Constructor call with Book ISBN length less than 10 must raise IllegalArgumentException")
    public void testThat_ConstructorShouldThrowErrorWithInvalidArgumentForISBN(){
        String ISBN = "1234567";
        Year publicationYear = Year.of(2000);
        assertThrows(IllegalArgumentException.class, ()->new Book(ISBN,"Deep Learning","lan Goodfellow",publicationYear));
    }

    @Test
    @DisplayName("Book Constructor call with Book ISBN length more than 13 must raise IllegalArgumentException")
    public void testThat_ConstructorShouldThrowErrorWithInvalidArgumentForISBN2(){
        String ISBN = "12345678901234";
        Year publicationYear = Year.of(2000);
        assertThrows(IllegalArgumentException.class, ()->new Book(ISBN,"Deep Learning","lan Goodfellow",publicationYear));
    }

    @Test
    @DisplayName("Book Constructor call with Book title null must raise IllegalArgumentException")
    public void testThat_ConstructorShouldThrowErrorWithNullArgumentForTitle(){
        String ISBN = "1234567890";
        Year publicationYear = Year.of(2000);
        assertThrows(IllegalArgumentException.class, ()->new Book(ISBN,null,"lan Goodfellow",publicationYear));
    }

    @Test
    @DisplayName("Book Constructor call with Book title less than length 4 must raise IllegalArgumentException")
    public void testThat_ConstructorShouldThrowErrorWithInvalidArgumentForTitle(){
        String ISBN = "1234567890";
        Year publicationYear = Year.of(2000);
        assertThrows(IllegalArgumentException.class, ()->new Book(ISBN,"Dee","lan Goodfellow",publicationYear));
    }

    @Test
    @DisplayName("Book Constructor call with Book author name as null must raise IllegalArgumentException")
    public void testThat_ConstructorShouldThrowErrorWithNullArgumentForAuthorName(){
        String ISBN = "1234567890";
        Year publicationYear = Year.of(2000);
        assertThrows(IllegalArgumentException.class, ()->new Book(ISBN,"Deep Learning",null,publicationYear));
    }

    @Test
    @DisplayName("Book Constructor call with Book author name less than length 4 must raise IllegalArgumentException")
    public void testThat_ConstructorShouldThrowErrorWithInvalidArgumentForAuthorName(){
        String ISBN = "1234567890";
        Year publicationYear = Year.of(2000);
        assertThrows(IllegalArgumentException.class, ()->new Book(ISBN,"Deep Learning","lan",publicationYear));
    }

    @Test
    @DisplayName("Book Constructor call with Book publication Year as null must raise IllegalArgumentException")
    public void testThat_ConstructorShouldThrowErrorWithNullArgumentForPublicationYear(){
        String ISBN = "1234567890";
        assertThrows(IllegalArgumentException.class, ()->new Book(ISBN,"Deep Learning","lan Goodfellow",null));
    }

    @Test
    @DisplayName("Book Constructor call with Book publication Year greater than current year or 0000 must raise IllegalArgumentException")
    public void testThat_ConstructorShouldThrowErrorWithInvalidArgumentForPublicationYear(){
        String ISBN = "1234567890";
        Year publicationYear1 = Year.of(0000);
        assertThrows(IllegalArgumentException.class, ()->new Book(ISBN,"Deep Learning","lan Goodfellow",publicationYear1));

        Year publicationYear2 = Year.of((Year.now().getValue()+1));
        assertThrows(IllegalArgumentException.class, ()->new Book(ISBN,"Deep Learning","lan Goodfellow",publicationYear2));
    }
    

    @Test
    @DisplayName("Testing get methods of perfectly created Book Object")
    public void testThat_GetMethodsWorkingFine(){
        String ISBN = "1234567890";
        Year publicationYear = Year.of(2000);
        String bookTitle="Deep Learning";
        String authorName="lan Goodfellow";
        Book testBook = new Book(ISBN,bookTitle,authorName,publicationYear);
        assertEquals(ISBN, testBook.getISBN());
        assertEquals(bookTitle, testBook.getBookTitle());
        assertEquals(authorName, testBook.getAuthorName());
        assertEquals(publicationYear, testBook.getPublicationYear());
    }

    @Test
    @DisplayName("Testing hashcode and equals has the contract with custom hashCode and toString function")
    public void testThat_equalsAndHashCodeHasTheContract(){
        String ISBN = "1234567890";
        Year publicationYear = Year.of(2000);
        String bookTitle="Deep Learning";
        String authorName="lan Goodfellow";
        Book testBook1 = new Book(ISBN,bookTitle,authorName,publicationYear);
        Book testBook2 = new Book(ISBN,bookTitle,authorName,publicationYear);

        assertFalse(testBook1==testBook2); //reference is not same
        assertTrue(testBook1.equals(testBook2));
        assertEquals(testBook1, testBook2);
    }

    @Test
    @DisplayName("Testing toString method must not return null for a perfectly created object")
    public void testThat_toStringReturnsNonNull(){
    
        Book testBook = DemoTestBookObjectStore.getBookObject(1);
        String expectedString = "Book [ISBN=" + testBook.getISBN() + ", bookTitle=" + testBook.getBookTitle() + ", authorName=" + testBook.getAuthorName()
                + ", publicationYear=" + testBook.getPublicationYear() + "]";
        assertNotNull(testBook.toString());
        assertEquals(expectedString, testBook.toString());
    }

    @Test
    @DisplayName("Test case for checking hashCode functionalities")
    public void testThat_hashCodeImplementationStaysConsistent(){
        String ISBN = "1234567890";
        Year publicationYear = Year.of(2000);
        String bookTitle="Deep Learning";
        String authorName="lan Goodfellow";
        Book testBook1 = new Book(ISBN,bookTitle,authorName,publicationYear);
        Book testBook2 = new Book(ISBN,bookTitle,authorName,publicationYear);
        assertEquals(testBook1.hashCode(), testBook2.hashCode());
    }

    @Test
    @DisplayName("Additional Testing of equals Method")
    public void testAdditionalFunctionalitiesOfEqualsMethod(){
        String ISBN = "1234567890";
        String ISBN2 = "12345678901";
        Year publicationYear = Year.of(2000);
        Year publicationYear2 = Year.of(2002);
        String bookTitle="Deep Learning";
        String authorName="lan Goodfellow";
        Book testBook1 = new Book(ISBN,bookTitle,authorName,publicationYear);
        Book testBook2 = new Book(ISBN,bookTitle,authorName,publicationYear);
        Book testBook3 = new Book(ISBN2,"other book", "demo name", publicationYear2);

        assertFalse(testBook1.equals(null));
        assertFalse(testBook1.equals(testBook3));
        assertFalse(testBook1.equals(new Object()));
        assertTrue(testBook1.equals(testBook2));
        assertTrue(testBook1.equals(testBook1));
    }
}
