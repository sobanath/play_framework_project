package sample;

import global.common.BaseModel;
import lombok.Getter;
import lombok.Setter;
import org.mongodb.morphia.annotations.Entity;

import java.util.Date;

@Entity(value = "samp",noClassnameStored = true)
@Getter
@Setter
public class sampModel extends BaseModel {
private String name,password,mail,comment;
private int like;
public enum Fields{name,password,mail,like,comment,date}
}
