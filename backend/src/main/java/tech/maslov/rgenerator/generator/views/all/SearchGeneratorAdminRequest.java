package tech.maslov.rgenerator.generator.views.all;

import com.ub.core.base.search.SearchRequest;

public class SearchGeneratorAdminRequest extends SearchRequest {
    public SearchGeneratorAdminRequest() {
    }

    public SearchGeneratorAdminRequest(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public SearchGeneratorAdminRequest(Integer currentPage, Integer pageSize) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }

}
