package tech.maslov.rgenerator.generator.views.all;

import com.ub.core.base.search.SearchResponse;
import tech.maslov.rgenerator.generator.models.GeneratorDoc;

import java.util.List;

public class SearchGeneratorAdminResponse extends SearchResponse {
    private List<GeneratorDoc> result;


    public SearchGeneratorAdminResponse() {
    }

    public SearchGeneratorAdminResponse(Integer currentPage, Integer pageSize, List<GeneratorDoc> result) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.result = result;
    }

    public List<GeneratorDoc> getResult() {
        return result;
    }

    public void setResult(List<GeneratorDoc> result) {
        this.result = result;
    }
}
