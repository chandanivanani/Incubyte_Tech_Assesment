package userTest;

import java.util.List;
import java.util.ArrayList;
import assesment.incubyte.user.User;


public class DemoTestUserObjectStore {
    private static List<User> userObjectList = new ArrayList<>();

    static {
        userObjectList.add(new User("Biswojit"));
        userObjectList.add(new User("Aarav"));
        userObjectList.add(new User("Ananya"));
        userObjectList.add(new User("Lakshmi"));
        userObjectList.add(new User("Rajesh"));
        userObjectList.add(new User("Meera"));
        userObjectList.add(new User("Vikram"));
        userObjectList.add(new User("Priya"));
        userObjectList.add(new User("Sahil"));
        userObjectList.add(new User("Nandini"));
    }

    static public User getUserObject(int index) {
        return userObjectList.get(index);
    }
}
