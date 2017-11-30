package Demo;
import global.common.BaseModel;
import lombok.Getter;
import lombok.Setter;
import org.mongodb.morphia.annotations.Entity;

@Entity(value = "demo",noClassnameStored = true)
@Getter
@Setter
public class DemoModel extends BaseModel {
    private String email,blogname,comment,blogdescription,name;
    private int like;
    long epoch = System.currentTimeMillis()/1000;
    public enum Fields{email,blogname,comment,blogdescription,name,like};
}
