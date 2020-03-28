package tech.maslov.rgenerator.generator.services;

import tech.maslov.rgenerator.generator.models.GeneratorDoc;
import tech.maslov.rgenerator.generator.events.IGeneratorEvent;
import tech.maslov.rgenerator.generator.views.all.SearchGeneratorAdminRequest;
import tech.maslov.rgenerator.generator.views.all.SearchGeneratorAdminResponse;
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
public class GeneratorService {
    private static Map<String, IGeneratorEvent> events = new HashMap<String, IGeneratorEvent>();

    @Autowired private MongoTemplate mongoTemplate;

    public static void addEvent(IGeneratorEvent event) {
        events.put(event.getClass().getCanonicalName(), event);
    }

    public GeneratorDoc save(GeneratorDoc doc) {
        doc.setUpdateAt(new Date());
        mongoTemplate.save(doc);
        callAfterSave(doc);
        return doc;
    }

    public GeneratorDoc findById(ObjectId id) {
        return mongoTemplate.findById(id, GeneratorDoc.class);
    }

    public void remove(ObjectId id) {
        GeneratorDoc doc = findById(id);
        if (doc == null) return;
        mongoTemplate.remove(doc);
        callAfterDelete(doc);
    }

    public Long count(Query query) {
        return mongoTemplate.count(query, GeneratorDoc.class);
    }

    public List<GeneratorDoc> findAll(){
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Query query = new Query();
        query.with(sort);
        return mongoTemplate.find(query, GeneratorDoc.class);
    }

    public SearchGeneratorAdminResponse findAll(SearchGeneratorAdminRequest request) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(
                request.getCurrentPage(),
                request.getPageSize(),
                sort);

        Criteria criteria = new Criteria();
        //Criteria.where("title").regex(request.getQuery(), "i");

        Query query = new Query(criteria);
        Long count = mongoTemplate.count(query, GeneratorDoc.class);
        query = query.with(pageable);

        List<GeneratorDoc> result = mongoTemplate.find(query, GeneratorDoc.class);
        SearchGeneratorAdminResponse response = new SearchGeneratorAdminResponse(
                request.getCurrentPage(),
                request.getPageSize(),
                result);
        response.setAll(count);
        response.setQuery(request.getQuery());
        return response;
    }

    private void callAfterSave(GeneratorDoc doc) {
        for (IGeneratorEvent event : events.values()) {
            event.afterSave(doc);
        }
    }

    private void callAfterDelete(GeneratorDoc doc) {
        for (IGeneratorEvent event : events.values()) {
            event.afterDelete(doc);
        }
    }
}
