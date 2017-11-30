package sample;

 public interface sampRepository {
    sampModel createuser(sampModel newuser);
    sampModel getUser(String name, String password);
    sampModel getuser(String name);
 }
