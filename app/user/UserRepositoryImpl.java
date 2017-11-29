package user;

import global.common.BaseModel;
import global.common.BaseRepository;
import global.configuration.db.mongodb.MongoDBConnection;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class UserRepositoryImpl extends BaseRepository<UserModel> implements UserRepository {

    @Inject
    public UserRepositoryImpl(MongoDBConnection databaseConnection) {
        super(UserModel.class, databaseConnection);
    }

    @Override
    public UserModel getUserByAge(final int age) {
        return query()
                .field(UserModel.Fields.age.name())
                .equal(age)
                .get();
    }

    @Override
    public UserModel getUserByName(final String name) {
        return query()
                .field(UserModel.Fields.name.name())
                .equal(name)
                .get();
    }

    @Override
    public List<UserModel> getAllUsers() {
        return query().asList();
    }

    @Override
    public boolean deleteUser(ObjectId userId) {
        return delete(userId);
    }

    @Override
    public void updateUser(UserModel user) {
        update(user);
    }

    @Override
    public UserModel getUser(int age, String name) {
        return query()
                .field(UserModel.Fields.name.name())
                .equal(name)
                .field(UserModel.Fields.age.name())
                .equal(age)
                .get();
    }

    @Override
    public UserModel getUser(ObjectId userId) {
        return query()
                .field(BaseModel.Fields.id.name())
                .equal(userId)
                .get();
    }

    @Override
    public UserModel createUser(UserModel newUser) {
        create(newUser);
        return newUser;
    }

}
