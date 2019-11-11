package sg.edu.nus.comp.cs3219.viz.ui.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.AccessLevel;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.UserInfo;
import sg.edu.nus.comp.cs3219.viz.common.entity.Presentation;
import sg.edu.nus.comp.cs3219.viz.common.exception.PresentationNotFoundException;
import sg.edu.nus.comp.cs3219.viz.logic.GateKeeper;
import sg.edu.nus.comp.cs3219.viz.logic.PresentationLogic;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PresentationController extends BaseRestController {

    private final PresentationLogic presentationLogic;

    private final GateKeeper gateKeeper;

    public PresentationController(PresentationLogic presentationLogic,
                                  GateKeeper gateKeeper) {
        this.presentationLogic = presentationLogic;
        this.gateKeeper = gateKeeper;
    }

    @GetMapping("/presentations")
    public List<Presentation> all(@CookieValue(value = "userEmail") String email, @CookieValue(value = "userPassword") String password) {
        UserInfo currentUser = gateKeeper.verifyLoginAccess(email, password);

        return presentationLogic.findAllForUser(currentUser);
    }

    @PostMapping("/presentations")
    public ResponseEntity<?> newPresentation(@RequestBody Presentation presentation,
                                             @CookieValue(value = "userEmail") String email,
                                             @CookieValue(value = "userPassword") String password) throws URISyntaxException {
        UserInfo currentUser = gateKeeper.verifyLoginAccess(email, password);

        Presentation newPresentation = presentationLogic.saveForUser(presentation, currentUser);

        return ResponseEntity
                .created(new URI("/presentations/" + newPresentation.getId()))
                .body(newPresentation);
    }

    @GetMapping("/presentations/{id}")
    public Presentation one(@PathVariable Long id, @CookieValue(value = "userEmail") String email,
                            @CookieValue(value = "userPassword") String password) {
        Presentation presentation = presentationLogic.findById(id)
                .orElseThrow(() -> new PresentationNotFoundException(id));

        gateKeeper.verifyAccessForPresentation(presentation, AccessLevel.CAN_READ, email, password);

        return presentation;
    }

    @GetMapping("/presentations/created")
    public List<Long> createdPresentation(@CookieValue(value = "userEmail") String email, @CookieValue(value = "userPassword") String password) {
        UserInfo userInfo = gateKeeper.verifyLoginAccess(email, password);
        List<Presentation> presentations = presentationLogic.findAllForUser(userInfo);
        return presentations.stream().map(Presentation::getId).collect(Collectors.toList());
    }

    @PutMapping("/presentations/{id}")
    public ResponseEntity<?> updatePresentation(@RequestBody Presentation newPresentation, @PathVariable Long id,
                                                @CookieValue(value = "userEmail") String email,
                                                @CookieValue(value = "userPassword") String password) throws URISyntaxException {

        Presentation oldPresentation = presentationLogic.findById(id)
                .orElseThrow(() -> new PresentationNotFoundException(id));
        gateKeeper.verifyAccessForPresentation(oldPresentation, AccessLevel.CAN_WRITE, email, password);

        Presentation updatedPresentation = presentationLogic.updatePresentation(oldPresentation, newPresentation);
        return ResponseEntity
                .created(new URI("/presentations/" + newPresentation.getId()))
                .body(updatedPresentation);
    }

    @DeleteMapping("/presentations/{id}")
    public ResponseEntity<?> deletePresentation(@PathVariable Long id,
                                                @CookieValue(value = "userEmail") String email,
                                                @CookieValue(value = "userPassword") String password) {
        Presentation oldPresentation = presentationLogic.findById(id)
                .orElseThrow(() -> new PresentationNotFoundException(id));
        gateKeeper.verifyDeletionAccessForPresentation(oldPresentation, email, password);

        presentationLogic.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
