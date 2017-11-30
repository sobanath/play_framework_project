package Demo;

import global.exceptions.CustomException;
import org.bson.types.ObjectId;


import javax.inject.Inject;
import java.util.List;
import global.utils.Helper;

public class DemoService {
    private final DemoRepository repository;

    @Inject
    public DemoService(final DemoRepository repository){
        this.repository=repository;
    }

    public DemoModel createuser(DemoRequestForm DemoForm){
        final DemoModel newuser=new DemoModel();
        newuser.setEmail(DemoForm.getEmail());
        newuser.setBlogdescription(DemoForm.getBlogdescription());
        newuser.setBlogname(DemoForm.getBlogname());
        newuser.setLike(DemoForm.getLike());
        newuser.setName(DemoForm.getName());
        newuser.setComment(DemoForm.getComment());
        newuser.setCreatedAt(Helper.currentEpoch());
        System.out.print("time " + Helper.currentEpoch( ));
        return repository.createuser(newuser);
    }

    public DemoModel updateUser(ObjectId userId, DemoRequestForm DemoForm) {
        final DemoModel Demo = repository.getUsers(userId);
        if (Demo == null) {
            throw new CustomException("No user exists for given user ID");
        }
        Demo.setBlogname(DemoForm.getBlogname());
        Demo.setBlogdescription(DemoForm.getBlogdescription());
        Demo.setEmail(DemoForm.getEmail());
        Demo.setLike(DemoForm.getLike());
        Demo.setComment(DemoForm.getComment());
        repository.updateUser(Demo);

        return Demo;
    }

    public DemoModel getUsers(ObjectId userId) {
        return  repository.getUsers(userId);
    }

    boolean deleteUser(ObjectId userId) {
        final DemoModel Demo = repository.getUsers(userId);
        if (Demo == null) {
            throw new CustomException("No user exists for given user ID");
        }
        return repository.deleteUser(userId);
    }

    public DemoModel getuserLike(String email) {
        DemoModel Demo=repository.getuserLike(email);
        Demo.setLike(Demo.getLike()+1);
        repository.updateUser(Demo);
        return Demo;
    }

    List<DemoModel> getLike() {
        return repository.getLike();
    }

    public DemoModel getComments(DemoRequestForm DemoForm) {
        final DemoModel Demo = repository.getComments(DemoForm.getEmail(),DemoForm.getLike());
        if(Demo != null) {
            Demo.setComment(DemoForm.getComment());
            repository.updateUser(Demo);
            return Demo;
        }
        return null;
    }

    public List<DemoModel> viewComments(String name) {
        System.out.print(" size  " +repository.viewComments(name).size());
        return repository.viewComments(name);
    }

    List<DemoModel> getPosted() {
        return repository.getPosted();
    }

}
