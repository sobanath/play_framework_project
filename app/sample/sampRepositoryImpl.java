package sample;

import global.common.BaseRepository;
import global.configuration.db.mongodb.MongoDBConnection;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import global.common.BaseModel;
import org.bson.types.ObjectId;
import java.util.Arrays;

@Singleton
public class sampRepositoryImpl extends BaseRepository<sampModel> implements sampRepository {

    @Inject
    public sampRepositoryImpl(MongoDBConnection databaseConnection) {
        super(sampModel.class, databaseConnection);
    }

    @Override
    public void updateUser(sampModel sample) {
        update(sample);
    }


    @Override
    public sampModel createuser(sampModel newuser) {
        create(newuser);
        return newuser;
    }

    @Override
    public boolean deleteUser(ObjectId userId) {
        return delete(userId);
    }



    @Override
    public List<sampModel> getLike() {
        return query().order("-like").asList();
    }
    @Override
    public List<sampModel> getPosted() {
        return query().order("-createdAt").asList();
    }

    @Override
    public sampModel getUser(String name, String password) {
        return
                query().field(sampModel.Fields.name.name())
                        .equal(name)
                        .field(sampModel.Fields.password.name())
                        .equal(password)
                        .get();

    }

    @Override
    public sampModel getuser(String name) {
        return
                query().field(sampModel.Fields.name.name())
                        .equal(name).get();

    }


    @Override
    public sampModel getUsers(ObjectId userId) {
        return query()
                .field(BaseModel.Fields.id.name())
                .equal(userId)
                .get();
    }
    @Override
    public sampModel getuserLike(String mail) {
        return query()
                .field(sampModel.Fields.mail.name())
                .equal(mail)
                .get();
    }
    @Override
    public sampModel getComments(String mail,int like) {
        return query().field(sampModel.Fields.mail.name())
                .equal(mail)
                .field(sampModel.Fields.like.name())
                .equal(like)
                .get();
    }

    @Override
    public List<sampModel> viewComments(String s1) {
        List<String> postedIdArray = Arrays.asList(s1.split(","));
        return  query().field(sampModel.Fields.name.name())
                .in(postedIdArray)
                .asList();


    }


}


