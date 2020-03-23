package tech.maslov.sandbox.api.base.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OptionController {

    /**
     * Данный запрос нужен только для срабатывания OPTIONS запроса
     * и для корректной обработки  CORS Policy
     *
     * Работает только вместе с - CorsInterceptor
     */
    @ResponseBody
    @RequestMapping(value = "/api/**", method = RequestMethod.OPTIONS)
    public void option(){

    }

}
