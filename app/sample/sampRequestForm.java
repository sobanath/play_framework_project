package sample;

import lombok.Getter;
import lombok.Setter;
import play.data.validation.Constraints;

import java.util.Date;


@Getter
@Setter
public class sampRequestForm {

    public String id;



    public String name;


    public String password;

    public String mail;
    public String comment;


   public int like;
}
