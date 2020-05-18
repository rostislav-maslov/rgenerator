package tech.maslov.rgenerator.infrastructure.microservice;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rcore.restapi.exceptions.BadRequestApiException;
import com.rcore.restapi.exceptions.ExceptionDTO;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * Приобразовываем ошыбку в ApiException
 */
@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {

        try {
            HashMap<String, HashMap<String, Object>> error = new ObjectMapper().readValue(response.body().toString(), HashMap.class);
            ExceptionDTO exceptionDTO = new ObjectMapper().convertValue(error.get("error"), ExceptionDTO.class);
            return new BadRequestApiException(exceptionDTO);
        } catch (JsonProcessingException e) { }
        return new Exception();
    }
}
