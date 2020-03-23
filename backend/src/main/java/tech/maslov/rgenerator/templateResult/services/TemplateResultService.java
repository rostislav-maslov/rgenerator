package tech.maslov.rgenerator.templateResult.services;

import tech.maslov.rgenerator.templateResult.models.TemplateResultDoc;
import tech.maslov.rgenerator.templateResult.events.ITemplateResultEvent;
import tech.maslov.rgenerator.templateResult.views.all.SearchTemplateResultAdminRequest;
import tech.maslov.rgenerator.templateResult.views.all.SearchTemplateResultAdminResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Component
public class TemplateResultService {
    private static Map<String, ITemplateResultEvent> events = new HashMap<String, ITemplateResultEvent>();

    @Autowired private MongoTemplate mongoTemplate;

    public static void addEvent(ITemplateResultEvent event) {
        events.put(event.getClass().getCanonicalName(), event);
    }

    public TemplateResultDoc save(TemplateResultDoc doc) {
        doc.setUpdateAt(new Date());
        mongoTemplate.save(doc);
        callAfterSave(doc);
        return doc;
    }

    public TemplateResultDoc findById(ObjectId id) {
        return mongoTemplate.findById(id, TemplateResultDoc.class);
    }

    public void remove(ObjectId id) {
        TemplateResultDoc doc = findById(id);
        if (doc == null) return;
        mongoTemplate.remove(doc);
        callAfterDelete(doc);
    }

    public Long count(Query query) {
        return mongoTemplate.count(query, TemplateResultDoc.class);
    }

    public SearchTemplateResultAdminResponse findAll(SearchTemplateResultAdminRequest request) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(
                request.getCurrentPage(),
                request.getPageSize(),
                sort);

        Criteria criteria = new Criteria();
        //Criteria.where("title").regex(request.getQuery(), "i");

        Query query = new Query(criteria);
        Long count = mongoTemplate.count(query, TemplateResultDoc.class);
        query = query.with(pageable);

        List<TemplateResultDoc> result = mongoTemplate.find(query, TemplateResultDoc.class);
        SearchTemplateResultAdminResponse response = new SearchTemplateResultAdminResponse(
                request.getCurrentPage(),
                request.getPageSize(),
                result);
        response.setAll(count);
        response.setQuery(request.getQuery());
        return response;
    }

    private void callAfterSave(TemplateResultDoc doc) {
        for (ITemplateResultEvent event : events.values()) {
            event.afterSave(doc);
        }
    }

    private void callAfterDelete(TemplateResultDoc doc) {
        for (ITemplateResultEvent event : events.values()) {
            event.afterDelete(doc);
        }
    }
}
