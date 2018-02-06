package at.oberauer.frontendcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by michael on 05.01.18.
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    public String getHome(){
        return "redirect:keyword";
    }

}
