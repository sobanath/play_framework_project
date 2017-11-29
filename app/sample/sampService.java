package sample;

import global.exceptions.CustomException;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;
import static global.utils.Helper.*;

@Singleton
public class sampService {

    private final sampRepository repository;

    @Inject
    public sampService(final sampRepository repository)
    {
        this.repository=repository;
    }

    public sampModel createuser(sampRequestForm sampForm){
        final sampModel newuser=new sampModel();
        newuser.setName(sampForm.getName());
        newuser.setPassword(hash(sampForm.getPassword()));
        newuser.setMail(sampForm.getMail());
        newuser.setLike(sampForm.getLike());
        newuser.setComment(sampForm.getComment());
        return repository.createuser(newuser);
}


    List<sampModel> getLike() {
        return repository.getLike();
    }

    List<sampModel> getPosted() {
        return repository.getPosted();
    }




    public sampModel getuser(String name) {
        sampModel sample =  repository.getuser(name);
        return sample;

    }

    public sampModel getUsers(ObjectId userId) {
         return  repository.getUsers(userId);
    }

    public sampModel getuserLike(String mail) {
        sampModel sample=repository.getuserLike(mail);
        sample.setLike(sample.getLike()+1);
        repository.updateUser(sample);
        return sample;
    }

    public sampModel getComments(sampRequestForm sampForm) {
        final sampModel sample = repository.getComments(sampForm.getMail(),sampForm.getLike());
        if(sample != null) {
            sample.setComment(sampForm.getComment());
            repository.updateUser(sample);
            return sample;
        }
        return null;
    }

    public List<sampModel> viewComments(String name) {
        System.out.print(" size  " +repository.viewComments(name).size());
        return repository.viewComments(name);
    }


    public Optional<sampModel>signin(sampRequestForm sampForm){
        final sampModel samp=repository.getuser(sampForm.getName());
        boolean flag=compare(sampForm.getPassword(),samp.getPassword());
        if(flag)
            return Optional.of(samp);
        else
            return Optional.empty();
    }
    public sampModel signin(String name,String password) {

        return repository.getUser(name, password);
    }
    public sampModel updateUser(ObjectId userId, sampRequestForm sampForm) {
        final sampModel sample = repository.getUsers(userId);
        if (sample == null) {
            throw new CustomException("No user exists for given user ID");
        }

       sample.setPassword(sampForm.getPassword());
        sample.setMail(sampForm.getMail());
        sample.setName(sampForm.getName());
         sample.setLike(sampForm.getLike());
         sample.setComment(sampForm.getComment());
        repository.updateUser(sample);

        return sample;
    }

    boolean deleteUser(ObjectId userId) {
        final sampModel sample = repository.getUsers(userId);
        if (sample == null) {
            throw new CustomException("No user exists for given user ID");
        }

        return repository.deleteUser(userId);
    }
}
