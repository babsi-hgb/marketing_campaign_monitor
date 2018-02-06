package at.oberauer.frontendcontroller;

import at.oberauer.formdata.KeywordRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by michael on 05.01.18.
 */
@Controller
public class KeywordController {

    @GetMapping("/keyword")
    public String keywordForm(Model model) {
        model.addAttribute("keywordRequest", new KeywordRequest());
        return "keyword";
    }

    @PostMapping("/keyword")
    public String keywordSubmit(@ModelAttribute KeywordRequest keywordRequest) {
        //Generate Result -> Call Downstream Service


        return "keyword_result";
    }

}
