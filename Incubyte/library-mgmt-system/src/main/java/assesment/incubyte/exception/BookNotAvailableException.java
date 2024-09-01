package assesment.incubyte.exception;

import java.lang.RuntimeException;
import assesment.incubyte.book.Book;

public class BookNotAvailableException extends RuntimeException {

    String errMsg=null;

    public BookNotAvailableException(Book unavailableBook){
        this.errMsg="Sorry "+unavailableBook.getBookTitle()+" book is currently not available to borrow";     
    }
    
    @Override
    public String getMessage(){
        return this.errMsg;
    }
}
