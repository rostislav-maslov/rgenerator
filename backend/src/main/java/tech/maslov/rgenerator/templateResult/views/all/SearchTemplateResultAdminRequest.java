package tech.maslov.rgenerator.templateResult.views.all;

import com.ub.core.base.search.SearchRequest;

public class SearchTemplateResultAdminRequest extends SearchRequest {
    public SearchTemplateResultAdminRequest() {
    }

    public SearchTemplateResultAdminRequest(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public SearchTemplateResultAdminRequest(Integer currentPage, Integer pageSize) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }

}
