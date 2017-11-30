package sample;

import global.common.BaseModel;
import lombok.Getter;
import lombok.Setter;
import org.mongodb.morphia.annotations.Entity;



@Entity(value = "samp",noClassnameStored = true)
@Getter
@Setter
 public class sampModel extends BaseModel {
     private String name,password;
     public enum Fields{name,password}
 }
