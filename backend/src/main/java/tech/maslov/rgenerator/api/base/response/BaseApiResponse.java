package tech.maslov.rgenerator.api.base.response;

public class BaseApiResponse<T> {

    private BaseErrorResponse error = new BaseErrorResponse();
    private T result;

    public BaseApiResponse() {
    }

    public BaseApiResponse(T result) {
        this.result = result;
    }

    public static BaseApiResponse error(BaseErrorResponse error){
        BaseApiResponse response = new BaseApiResponse(null);
        response.setError(error);
        return response;
    }

    public static<T> BaseApiResponse of(T result){
        return new BaseApiResponse(result);
    }

    public BaseErrorResponse getError() {
        return error;
    }

    public void setError(BaseErrorResponse error) {
        this.error = error;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}

