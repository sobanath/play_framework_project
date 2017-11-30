package Demo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DemoRequestForm {
    public String email;
    public String blogname;
    public String blogdescription;
    public String comment;
    public int like;
    public String name;
}
