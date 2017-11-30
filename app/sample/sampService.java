package sample;


import javax.inject.Inject;
import javax.inject.Singleton;
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
        return repository.createuser(newuser);
    }
    public sampModel signin(String name,String password) {
        return repository.getUser(name, password);
    }

    public Optional<sampModel>signin(sampRequestForm sampForm){
        final sampModel samp=repository.getuser(sampForm.getName());
        boolean flag=compare(sampForm.getPassword(),samp.getPassword());
        if(flag)
            return Optional.of(samp);
        else
            return Optional.empty();
    }

    public sampModel getuser(String name) {
        sampModel sample =  repository.getuser(name);
        return sample;

    }
}

