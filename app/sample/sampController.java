package sample;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ImmutableMap;
import com.sun.net.httpserver.Authenticator;
import global.common.BaseController;
import global.exceptions.CustomException;
import org.bson.types.ObjectId;
import play.data.Form;
import play.mvc.BodyParser;
import play.mvc.Result;
import session.SessionService;

import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public final class sampController extends BaseController{

    private final sampService SampService;
    private final SessionService sessionService;
    @Inject
    public sampController(sampService SampService,SessionService sessionService){this.SampService=SampService; this.sessionService = sessionService;}



    public Result getUsers(String userIdStr) {
        try {
            if (!ObjectId.isValid(userIdStr)) {
                return failure("Invalid User ID type");
            }
            sampModel sample = SampService.getUsers(new ObjectId(userIdStr));
            return sample != null ? success(ImmutableMap.of("sample", sample)) : failure("Invalid User ID");
        } catch (CustomException e) {
            return failure(e.getMessage());
        }
    }
    public Result getuserLike(String mail) {
        try {
            sampModel sample = SampService.getuserLike(mail);

            return sample != null ? success(ImmutableMap.of("sample", sample)) : failure("Invalid User ID");
        } catch (CustomException e) {
            return failure(e.getMessage());
        }
    }
    public Result getComments() {
        try {
            final Form<sampRequestForm> sampForm = formFactory.form(sampRequestForm.class).bindFromRequest();

            final sampRequestForm samForm = sampForm.get();
            sampModel sample = this.SampService.getComments(samForm);

            return sample != null ? success(ImmutableMap.of("sample", sample)) : failure("Invalid User ID");
        } catch (CustomException e) {
            return failure(e.getMessage());
        }
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
              else
              {
                  final sampModel sample2 = this.SampService.createuser(sampForm);
                  return sample2 != null ? success("successfully created user with ID: ") : failure("failed to create user");
              }
                    } catch (CustomException e) {
            return failure(e.getMessage());
        }
    }


    public Result deleteUser(String userIdStr) {
        try {
            if (!ObjectId.isValid(userIdStr)) {
                return failure("Invalid User ID type");
            }
            final boolean status = SampService.deleteUser(new ObjectId(userIdStr));
            return status ? success("successfully deleted user") : failure("failed to delete user");
        } catch (CustomException e) {
            return failure(e.getMessage());
        }
    }


    public Result getLike() {
        try {
            return success(ImmutableMap.of("samp", SampService.getLike()));
        } catch (CustomException e) {
            return failure(e.getMessage());
        }
    }

    public Result getPosted() {
        try {
            return success(ImmutableMap.of("samp", SampService.getPosted()));
        } catch (CustomException e) {
            return failure(e.getMessage());
        }
    }


    public Result getuser(String name) {
        try {

            final Form<sampRequestForm> sampModelForm = formFactory.form(sampRequestForm.class).bindFromRequest();
            final sampRequestForm sampForm = sampModelForm.get();
            final sampModel sample = this.SampService.getuser(name);
                     return sample != null ? success("View Data ") : failure("not in list ");
        } catch (CustomException e) {
            return failure(e.getMessage());
        }
    }

    public Result viewComments(String name) {
        try {
            List<sampModel> comments = SampService.viewComments(name);
            ObjectMapper mapper1 = new ObjectMapper();
            JsonNode childNode1;
            ArrayNode obj = mapper1.createArrayNode();
            for (sampModel b : comments) {
                childNode1 = mapper1.createObjectNode();
                if (b.getComment() != null) {
                    ((ObjectNode) childNode1).put("comments", b.getComment());
                    ((ObjectNode) childNode1).put("Name", b.getName());
                    ((ArrayNode)obj).add(childNode1);
                }
            }
            return comments != null ? success(obj) : failure("Failed to Fetch Comments");
        } catch (CustomException e) {
            return failure(e.getMessage());
        }
    }
    public Result updateUser(String userIdStr) {
        try {
            final Form<sampRequestForm> sampModelForm = formFactory.form(sampRequestForm.class).bindFromRequest();
            if (sampModelForm.hasErrors()) {
                return failure(buildValidationErrorMessage(sampModelForm.allErrors()));
            }

            if (!ObjectId.isValid(userIdStr)) {
                return failure("Invalid User ID type");
            }

            final sampRequestForm sampForm = sampModelForm.get();
            final sampModel sample = this.SampService.updateUser(new ObjectId(userIdStr), sampForm);
            return sample != null ? success("successfully updated user") : failure("failed to update user");
        } catch (CustomException e) {
            return failure(e.getMessage());
        }
    }

}
