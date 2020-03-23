package tech.maslov.sandbox.api.base.response;

import java.util.List;

public class ListApiResponse<T> {

    private List<T> items;
    private Long total;

    public ListApiResponse() {
    }

    public ListApiResponse(List<T> result, Long total) {
        this.items = result;
        this.total = total;
    }

    public static <T> ListApiResponse of(List<T> result, Long total) {
        return new ListApiResponse(result, total);
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}

