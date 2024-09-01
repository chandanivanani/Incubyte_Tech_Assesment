package assesment.incubyte.exception;

public class InvalidBookException extends IllegalArgumentException{

    public InvalidBookException(String errMsg){
        super(errMsg);
    }   
}