package assesment.incubyte.exception;

public class BorrowLimitExceededException extends IllegalStateException{
    public BorrowLimitExceededException(String errMsg){
        super(errMsg);
    }
}
