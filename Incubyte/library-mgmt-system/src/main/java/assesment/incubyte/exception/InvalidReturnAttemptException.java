package assesment.incubyte.exception;

import assesment.incubyte.book.Book;
import assesment.incubyte.user.User;

public class InvalidReturnAttemptException extends IllegalStateException{

    public InvalidReturnAttemptException(Book book, User usr){
        super(book.getBookTitle() +" book is not currently allowed to be returned by "+usr.getName());
    }
}
