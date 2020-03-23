package tech.maslov.rgenerator.generator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.maslov.rgenerator.generator.services.ZipService;

import java.io.IOException;

@Controller
public class GeneratorApiController {

    @Autowired private ZipService generatorService;

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String example() throws IOException {
        generatorService.generateZip();
        return "ok";
    }

}
