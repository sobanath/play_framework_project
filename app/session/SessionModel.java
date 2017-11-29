package session;

import global.common.BaseModel;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;

@Entity(value = "session",noClassnameStored = true)
@Getter
@Setter
public class SessionModel extends BaseModel {

    private ObjectId userID;
    private String authToken;
    private String email;

    public enum Fields {authToken, userID, email}

    long epoch = System.currentTimeMillis()/1000;

}
