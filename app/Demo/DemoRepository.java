package Demo;

import org.bson.types.ObjectId;
import java.util.List;

public interface DemoRepository {

     DemoModel createuser(DemoModel newuser);

     DemoModel checkUser(String name);

     void updateUser(final DemoModel Demo);

     DemoModel getUsers(ObjectId userId);

     boolean deleteUser(final ObjectId userId);

     DemoModel getuserLike(String email);

     List<DemoModel> getLike();

     DemoModel getComments(String email,int like);

     List<DemoModel> getPosted();

     List<DemoModel> viewComments(String name);
}
