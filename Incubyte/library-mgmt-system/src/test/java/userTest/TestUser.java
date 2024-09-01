package userTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import assesment.incubyte.user.User;

public class TestUser {

    @Test
    @DisplayName("User Constructor call for null or empty command must throw an UnsupportedOperationException")
    public void testThat_ConstructorShouldThrowErrorWithNoArgument(){
        assertThrows(UnsupportedOperationException.class, ()-> new User());
    }

    @Test
    @DisplayName("User Constructor call with User name as null must raise IllegalArgumentException")
    public void testThat_ConstructorShouldThrowErrorWithNullNameArgument(){
        assertThrows(IllegalArgumentException.class, ()->new User(null));
    }

    @Test
    @DisplayName("User Constructor call with User name less than length 4 must raise IllegalArgumentException")
    public void testThat_ConstructorShouldThrowErrorWithInvalidNameArgument(){
        assertThrows(IllegalArgumentException.class, ()->new User("abc"));
    }

    @Test
    @DisplayName("User Constructor call with appropriate User name should return non null object")
    public void testThat_ConstructorShouldWorkWithValidArguments(){
        User testUser=new User("Biswojit");
        assertNotEquals(null, testUser,"Constructor Should Not Return Null With Valid Arguments");
    }

    @Test
    @DisplayName("User Constructor call with multiple arguments must throw an UnsupportedOperationException")
    public void testThat_ConstructorCallWithMultipleArgumentsMustThrowError(){
        assertThrows(UnsupportedOperationException.class,()-> new User("Biswojit","Other User"));
    }

    @Test
    @DisplayName("Testing Initialisation Of Object with Functionalities")
    public void testThat_InitialisationAndFunctionalitiesWorksTogether(){
        String usrName="Biswojit";
        User testUser = new User(usrName);
        assertNotNull(testUser);
        assertEquals(usrName, testUser.getName());
        assertNotEquals(0, testUser.getUserId());
        assertNotNull(testUser.getUserId());
    }

    
}
