package org.modular.cac.endpoints.redirect;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/")
public class RedirectController {

    @Value("${springdoc.swagger-ui.path}")
    String htmlLocation;

    @GetMapping
    public RedirectView redirectToSwaggerUi(){
        return new RedirectView(htmlLocation);
    }
}
