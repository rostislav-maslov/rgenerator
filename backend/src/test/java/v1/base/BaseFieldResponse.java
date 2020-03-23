package v1.base;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadDocumentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseFieldResponse {

    public static FieldDescriptor[] error() {
        FieldDescriptor[] response = {
                PayloadDocumentation.fieldWithPath("error")
                        .description("Инфомрация об ошибке запроса"),
                PayloadDocumentation.fieldWithPath("error.success")
                        .description("Флаг успешного запроса"),
                PayloadDocumentation.fieldWithPath("error.code")
                        .description("Код ошибки"),
                PayloadDocumentation.fieldWithPath("error.message")
                        .optional().type(String.class)
                        .description("Сообщение об ошибке")
        };

        return response;
    }

    public static FieldDescriptor[] okResponse() {
        List<FieldDescriptor> response = new ArrayList<>();

        response.add(PayloadDocumentation.fieldWithPath("result").description("Ответ просто строкой."));

        response.addAll(Arrays.asList(error()));

        return response.toArray(new FieldDescriptor[response.size()]);
    }

}
