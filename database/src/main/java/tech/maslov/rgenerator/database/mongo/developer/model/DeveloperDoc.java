package tech.maslov.rgenerator.database.mongo.developer.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import tech.maslov.rgenerator.domain.developer.entity.DeveloperEntity;

@Document
@CompoundIndexes({
        @CompoundIndex(def = "{'_id': 1}", name = "_id_")
})
@SuperBuilder
@Getter
@NoArgsConstructor
public class DeveloperDoc extends DeveloperEntity {
    public DeveloperDoc toEntity(){
        return this;
    }


}
