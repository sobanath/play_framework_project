package Demo;
import global.common.BaseRepository;
import global.configuration.db.mongodb.MongoDBConnection;
import javax.inject.Inject;

import java.util.List;
import global.common.BaseModel;
import org.bson.types.ObjectId;
import sample.sampModel;
import java.util.Arrays;


public class DemoRepositoryImpl extends BaseRepository<DemoModel> implements DemoRepository {
    @Inject
    public DemoRepositoryImpl(MongoDBConnection databaseConnection) {
        super(DemoModel.class, databaseConnection);
    }

    @Override
    public DemoModel createuser(DemoModel newuser) {
        create(newuser);
        return newuser;
    }

    @Override
    public void updateUser(DemoModel Demo) {
        update(Demo);
    }

    @Override
    public DemoModel getUsers(ObjectId userId) {
        return query()
                .field(BaseModel.Fields.id.name())
                .equal(userId)
                .get();
    }

    @Override
    public DemoModel checkUser(String name) {
        return query()
                .field(DemoModel.Fields.name.name())
                .equal(name)
                .field(sampModel.Fields.name.name())
                .equal(name)
                .get();

    }

    @Override
    public boolean deleteUser(ObjectId userId) {
        return delete(userId);
    }

    @Override
    public DemoModel getuserLike(String email) {
        return query()
                .field(DemoModel.Fields.email.name())
                .equal(email)
                .get();
    }

    @Override
    public List<DemoModel> getLike() {
        return query().order("-like").asList();
    }


    @Override
    public DemoModel getComments(String email,int like) {
        return query().field(DemoModel.Fields.email.name())
                .equal(email)
                .field(DemoModel.Fields.like.name())
                .equal(like)
                .get();
    }

    @Override
    public List<DemoModel> viewComments(String s1) {
        List<String> postedIdArray = Arrays.asList(s1.split(","));
        return  query().field(sampModel.Fields.name.name())
                .in(postedIdArray)
                .asList();
    }

    @Override
    public List<DemoModel> getPosted() {
        return query().order("-createdAt").asList();
    }

}
