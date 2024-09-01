package assesment.incubyte.user;

public class User {
    private static Integer userCounter=1000;
    private Integer userId;
    private String userName;

    public User(){
        throw new UnsupportedOperationException("User Can't be Created Without Name");
    }

    public User(String userName){
        if(isNameValid(userName)==true){
            this.userName=userName;
            this.userId=++userCounter;
        }else{
            throw new IllegalArgumentException("User name must contain atleast 4 chracters");
        }
    }

    private boolean isNameValid(String userName){
        if(userName == null || userName.length()<4){
           return false;
        }else{
            return true;
        }
    }

    public User(Object arg1, Object... args){
        throw new UnsupportedOperationException("Constructor should be called with only one argument");
    }

    public Integer getUserId() {
        return this.userId;
    }

    public String getName(){
        return this.userName;
    }

}
