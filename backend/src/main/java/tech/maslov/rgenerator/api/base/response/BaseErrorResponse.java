package tech.maslov.sandbox.api.base.response;

public class BaseErrorResponse {
    private Boolean success = true;
    private Integer code = -1;
    private String message;

    public static BaseErrorResponse auth(){
        BaseErrorResponse response = new BaseErrorResponse();
        response.setSuccess(false);
        response.setCode(403);
        response.setMessage("Ошибка авторизации. Проверьте код подтверждения.");
        return response;
    }

    public static BaseErrorResponse userNotFound(){
        BaseErrorResponse response = new BaseErrorResponse();
        response.setSuccess(false);
        response.setCode(404);
        response.setMessage("Такой клиент не найден");
        return response;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
