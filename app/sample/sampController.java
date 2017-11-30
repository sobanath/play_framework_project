package sample;


import com.google.common.collect.ImmutableMap;
import global.common.BaseController;
import global.exceptions.CustomException;
import play.data.Form;
import play.mvc.BodyParser;
import play.mvc.Result;
import session.SessionService;

import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class sampController extends BaseController{

    private final sampService SampService;
    private final SessionService sessionService;

    @Inject
    public sampController(sampService SampService,SessionService sessionService)
     {
         this.SampService=SampService;
         this.sessionService = sessionService;
     }


    public Result signin() {
        try {
              final Form<sampRequestForm> sampModelForm = formFactory.form(sampRequestForm.class).bindFromRequest();
              if (sampModelForm.hasErrors()) {
                 return failure(buildValidationErrorMessage(sampModelForm.allErrors()));
              }
            final sampRequestForm sampForm = sampModelForm.get();
            Optional<sampModel> samp = this.SampService.signin(sampForm);
             if (samp != null) {
                sampModel SampModel = samp.get();
                String session = sessionService.generateSession();
                boolean status = sessionService.assignSessionToUser(SampModel.getId(), session, sampForm);
                return status ? success("successfully login", ImmutableMap.of("session", session)) : failure("failed to login");
             }
             else {
                return failure("Invalid Login credentials");
             }

        }
        catch (CustomException e) {
            return failure(e.getMessage());
        }
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result createuser() {
        try {
             final Form<sampRequestForm> sampModelForm = formFactory.form(sampRequestForm.class).bindFromRequest();
             if (sampModelForm.hasErrors()) {
                 return failure(buildValidationErrorMessage(sampModelForm.allErrors()));
             }
             final sampRequestForm sampForm = sampModelForm.get();
             final sampModel sample = this.SampService.getuser(sampForm.getName());
              if(sample!=null) {
                 return success("exits");
              }
              else {
                  final sampModel sample2 = this.SampService.createuser(sampForm);
                  return sample2 != null ? success("successfully created user with ID: ") : failure("failed to create user");
              }
             } catch (CustomException e) {
            return failure(e.getMessage());
            }
    }

    public Result logoutUser(String sessionToken) {
        boolean status = this.sessionService.deleteSession(sessionToken);
        return status ? success("You've been successfully logged out") : failure("not logout");
    }
}
