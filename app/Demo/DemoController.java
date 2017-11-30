package Demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ImmutableMap;
import global.common.BaseController;
import global.exceptions.CustomException;
import org.bson.types.ObjectId;
import play.data.Form;
import play.mvc.BodyParser;
import play.mvc.Result;
import session.SessionService;
import javax.inject.Inject;
import java.util.List;

public class DemoController extends BaseController {

    private final DemoService demoService;
    private final SessionService sessionService;


    @Inject
    public DemoController(DemoService demoService, SessionService sessionService) {
        this.demoService = demoService;
        this.sessionService = sessionService;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result createuser() {
        if (isSessionValid()) {
            try {
                final Form<DemoRequestForm> DemoModelForm = formFactory.form(DemoRequestForm.class).bindFromRequest();
                if (DemoModelForm.hasErrors()) {
                    return failure(buildValidationErrorMessage(DemoModelForm.allErrors()));
                }
                final DemoRequestForm DemoForm = DemoModelForm.get();

                final DemoModel demo = this.demoService.createuser(DemoForm);
                return demo != null ? success("successfully created user with ID: ") : failure("failed to create user");
            } catch (CustomException e) {
                return failure(e.getMessage());

            }
        } else {
            return failure("Session Expires");
        }
    }

    public Result updateUser(String userIdStr) {
        if (isSessionValid()) {
            try {
                final Form<DemoRequestForm> DemoModelForm = formFactory.form(DemoRequestForm.class).bindFromRequest();
                if (DemoModelForm.hasErrors()) {
                    return failure(buildValidationErrorMessage(DemoModelForm.allErrors()));
                }

                if (!ObjectId.isValid(userIdStr)) {
                    return failure("Invalid User ID type");
                }

                final DemoRequestForm DemoForm = DemoModelForm.get();
                final DemoModel Demo = this.demoService.updateUser(new ObjectId(userIdStr), DemoForm);
                return Demo != null ? success("successfully updated user") : failure("failed to update user");
            } catch (CustomException e) {
                return failure(e.getMessage());
            }
        } else {
            return failure("Session Expires");
        }
    }

    public Result getUsers(String userIdStr) {
        if (isSessionValid()) {
            try {
                if (!ObjectId.isValid(userIdStr)) {
                    return failure("Invalid User ID type");
                }
                DemoModel Demo = demoService.getUsers(new ObjectId(userIdStr));
                return Demo != null ? success("Demo", Demo) : failure("Invalid User ID");
            } catch (CustomException e) {
                return failure(e.getMessage());
            }
        } else {
            return failure("Session Expires");
        }
    }

    public Result deleteUser(String userIdStr) {
        if (isSessionValid()) {
            try {
                if (!ObjectId.isValid(userIdStr)) {
                    return failure("Invalid User ID type");
                }
                final boolean status = demoService.deleteUser(new ObjectId(userIdStr));
                return status ? success("successfully deleted user") : failure("failed to delete user");
            } catch (CustomException e) {
                return failure(e.getMessage());
            }
        } else {
            return failure("Session Expires");
        }
    }

    public Result getuserLike(String mail) {
        if (isSessionValid()) {
            try {
                DemoModel Demo = demoService.getuserLike(mail);

                return Demo != null ? success(ImmutableMap.of("Demo", Demo)) : failure("Invalid User ID");
            } catch (CustomException e) {
                return failure(e.getMessage());
            }
        } else {
            return failure("Session Expires");
        }
    }

    public Result getLike() {
        if (isSessionValid()) {
            try {
                return success(ImmutableMap.of("demo", demoService.getLike()));
            } catch (CustomException e) {
                return failure(e.getMessage());
            }
        } else {
            return failure("Session Expires");
        }
    }

    public Result getComments() {
        if (isSessionValid()) {
            try {
                final Form<DemoRequestForm> DemoForm = formFactory.form(DemoRequestForm.class).bindFromRequest();

                final DemoRequestForm DemForm = DemoForm.get();
                DemoModel Demo = this.demoService.getComments(DemForm);

                return Demo != null ? success("Demo", Demo) : failure("Invalid User ID");
            } catch (CustomException e) {
                return failure(e.getMessage());
            }
        } else {
            return failure("Session Expires");
        }
    }

    public Result viewComments(String name) {
        if (isSessionValid()) {
            try {
                List<DemoModel> comments = demoService.viewComments(name);
                ObjectMapper mapper1 = new ObjectMapper();
                JsonNode childNode1;
                ArrayNode obj = mapper1.createArrayNode();
                for (DemoModel b : comments) {
                    childNode1 = mapper1.createObjectNode();
                    if (b.getComment() != null) {
                        ((ObjectNode) childNode1).put("comments", b.getComment());
                        ((ObjectNode) childNode1).put("Email", b.getEmail());
                        ((ArrayNode) obj).add(childNode1);
                    }
                }
                return comments != null ? success(obj) : failure("Failed to Fetch Comments");
            } catch (CustomException e) {
                return failure(e.getMessage());
            }
        } else {
            return failure("Sesssion Expires");
        }
    }

    public Result getPosted() {
        if (isSessionValid()) {
            try {
                return success(ImmutableMap.of("Demo", demoService.getPosted()));
            } catch (CustomException e) {
                return failure(e.getMessage());
            }
        } else {
            return failure("Session Expires");
        }

    }
}
