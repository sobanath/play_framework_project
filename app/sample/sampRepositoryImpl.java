package sample;

import global.common.BaseRepository;
import global.configuration.db.mongodb.MongoDBConnection;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
 public class  sampRepositoryImpl extends BaseRepository<sampModel> implements sampRepository {

    @Inject
    public sampRepositoryImpl(MongoDBConnection databaseConnection) {
        super(sampModel.class, databaseConnection);
    }

    @Override
    public sampModel createuser(sampModel newuser) {
        create(newuser);
        return newuser;
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
}


