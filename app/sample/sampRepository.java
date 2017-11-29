package sample;
import java.util.List;
import org.bson.types.ObjectId;

public interface sampRepository {
    sampModel createuser(sampModel newuser);

    sampModel getUser(String name, String password);

    void updateUser(final sampModel sample);

    sampModel getUsers(ObjectId userId);

    sampModel getuserLike(String mail);

    boolean deleteUser(final ObjectId userId);

    sampModel getuser(String name);

    List<sampModel> getLike();

    sampModel getComments(String mail,int like);

    List<sampModel> getPosted();

    List<sampModel> viewComments(String name);
}
