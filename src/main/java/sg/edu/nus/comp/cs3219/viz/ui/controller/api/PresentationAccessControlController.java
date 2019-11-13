package sg.edu.nus.comp.cs3219.viz.ui.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.AccessLevel;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.PresentationData;
import sg.edu.nus.comp.cs3219.viz.common.entity.Presentation;
import sg.edu.nus.comp.cs3219.viz.common.entity.PresentationAccessControl;
import sg.edu.nus.comp.cs3219.viz.common.exception.PresentationAccessControlNotFoundException;
import sg.edu.nus.comp.cs3219.viz.common.exception.PresentationNotFoundException;
import sg.edu.nus.comp.cs3219.viz.logic.GateKeeper;
import sg.edu.nus.comp.cs3219.viz.logic.PresentationAccessControlLogic;
import sg.edu.nus.comp.cs3219.viz.logic.PresentationLogic;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PresentationAccessControlController extends BaseRestController {
    private final PresentationLogic presentationLogic;

    private final GateKeeper gateKeeper;

    private PresentationAccessControlLogic presentationAccessControlLogic;

    public PresentationAccessControlController(PresentationLogic presentationLogic,
                                               GateKeeper gateKeeper,
                                               PresentationAccessControlLogic presentationAccessControlLogic) {
        this.presentationAccessControlLogic = presentationAccessControlLogic;
        this.presentationLogic = presentationLogic;
        this.gateKeeper = gateKeeper;
    }

    @GetMapping("/presentations/{presentationId}/accessControl")
    public List<PresentationAccessControl> all(@PathVariable Long presentationId,  @CookieValue(value = "userEmail") String email,
                                               @CookieValue(value = "userPassword") String password) {
        Presentation presentation = presentationLogic.findById(presentationId)
                .orElseThrow(() -> new PresentationNotFoundException(presentationId));
        gateKeeper.verifyAccessForPresentation(presentation, AccessLevel.CAN_READ, email, password);

        return presentationAccessControlLogic.findAllByPresentation(presentation);
    }
/*
    @GetMapping("/presentations/shared")
    public List<Long> sharedPresentations(@CookieValue(value = "userEmail") String email, @CookieValue(value = "userPassword") String password) {
        String userIdentifier = gateKeeper.verifyLoginAccess(email, password).getUserEmail();
        List<PresentationAccessControl> accessControls = presentationAccessControlLogic
            .findSharedPresentations(userIdentifier, AccessLevel.CAN_READ);
        return accessControls.stream().map(PresentationAccessControl::getId).collect(Collectors.toList());
    }*/

    @GetMapping("/presentations/shared")
    public List<PresentationData> sharedPresentations(@CookieValue(value = "userEmail") String email, @CookieValue(value = "userPassword") String password) {
        String userIdentifier = gateKeeper.verifyLoginAccess(email, password).getUserEmail();
        List<PresentationAccessControl> accessControls = presentationAccessControlLogic
                .findSharedPresentationsRead(userIdentifier, AccessLevel.CAN_READ);
        accessControls.addAll(presentationAccessControlLogic.findSharedPresentationsEdit(userIdentifier, AccessLevel.CAN_WRITE));
        List<PresentationData> presentationList = new ArrayList<>();

        accessControls.stream()
                .map(PresentationAccessControl::getPresentation)
                .map(Presentation::getId)
                .forEach(x -> {
                    Presentation p = presentationLogic.findById(x).orElseThrow(EntityNotFoundException::new);
                    PresentationData data = presentationLogic.getPresentationData(p);
                    presentationList.add(data);
                });

        for(PresentationData presentationData : presentationList) {
            if(presentationData.getCreatorIdentifier().equals(userIdentifier)) {
                presentationList.remove(presentationData);
            }
        }
        return presentationList;
    }

    @PostMapping("/presentations/{presentationId}/accessControl")
    public ResponseEntity<?> addPermission(@RequestBody PresentationAccessControl presentationAccessControl, @PathVariable Long presentationId,
                                           @CookieValue(value = "userEmail") String email,
                                           @CookieValue(value = "userPassword") String password) throws URISyntaxException {
        Presentation presentation = presentationLogic.findById(presentationId)
                .orElseThrow(() -> new PresentationNotFoundException(presentationId));
        gateKeeper.verifyAccessForPresentation(presentation, AccessLevel.CAN_WRITE, email, password);

        PresentationAccessControl newAccessControl = presentationAccessControlLogic.saveForPresentation(presentation, presentationAccessControl);
        return ResponseEntity
                .created(new URI("/presentations/" + presentation.getId() + "/accessControl/" + newAccessControl.getId()))
                .body(newAccessControl);
    }

    @PutMapping("/presentations/{presentationId}/accessControl/{accessControlId}")
    public ResponseEntity<?> updatePermission(@RequestBody PresentationAccessControl presentationAccessControl, @PathVariable Long presentationId, @PathVariable Long accessControlId,
                                              @CookieValue(value = "userEmail") String email,
                                              @CookieValue(value = "userPassword") String password) throws URISyntaxException {
        Presentation presentation = presentationLogic.findById(presentationId)
                .orElseThrow(() -> new PresentationNotFoundException(presentationId));
        gateKeeper.verifyAccessForPresentation(presentation, AccessLevel.CAN_WRITE, email, password);

        PresentationAccessControl oldPresentationAccessControl = presentationAccessControlLogic.findById(accessControlId)
                .orElseThrow(() -> new PresentationAccessControlNotFoundException(presentationId, accessControlId));

        PresentationAccessControl updatedPresentationAccessControl =
                presentationAccessControlLogic.updatePresentationAccessControl(oldPresentationAccessControl, presentationAccessControl);

        return ResponseEntity
                .created(new URI("/presentations/" + presentationId + "/accessControl/" + accessControlId))
                .body(updatedPresentationAccessControl);
    }

    @DeleteMapping("/presentations/{presentationId}/accessControl/{accessControlId}")
    public ResponseEntity<?> removePermission(@PathVariable Long presentationId, @PathVariable Long accessControlId,
                                              @CookieValue(value = "userEmail") String email,
                                              @CookieValue(value = "userPassword") String password) {
        Presentation presentation = presentationLogic.findById(presentationId)
                .orElseThrow(() -> new PresentationNotFoundException(presentationId));
        gateKeeper.verifyAccessForPresentation(presentation, AccessLevel.CAN_WRITE, email, password);

        presentationAccessControlLogic.deleteById(accessControlId);

        return ResponseEntity.noContent().build();
    }
}
