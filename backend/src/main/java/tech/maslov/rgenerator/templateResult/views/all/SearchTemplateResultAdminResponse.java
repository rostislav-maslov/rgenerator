package tech.maslov.rgenerator.templateResult.views.all;

import com.ub.core.base.search.SearchResponse;
import tech.maslov.rgenerator.templateResult.models.TemplateResultDoc;

import java.util.List;

public class SearchTemplateResultAdminResponse extends SearchResponse {
    private List<TemplateResultDoc> result;


    public SearchTemplateResultAdminResponse() {
    }

    public SearchTemplateResultAdminResponse(Integer currentPage, Integer pageSize, List<TemplateResultDoc> result) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.result = result;
    }

    public List<TemplateResultDoc> getResult() {
        return result;
    }

    public void setResult(List<TemplateResultDoc> result) {
        this.result = result;
    }
}
