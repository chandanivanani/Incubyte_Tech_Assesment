package assesment.incubyte.exception;

public class InvalidUserException extends IllegalArgumentException{

    public InvalidUserException(String errMsg){
        super(errMsg);
    }     
}
