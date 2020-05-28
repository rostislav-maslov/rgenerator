package tech.maslov.rgenerator.client.user.application;

import com.rcore.domain.user.entity.UserEntity;
import tech.maslov.rgenerator.adapter.developer.dto.DeveloperDTO;
import tech.maslov.rgenerator.client.user.web.api.MeResponse;

public class UserWebMapper {

    public MeResponse map(DeveloperDTO developerDTO, UserEntity userEntity) {
        return MeResponse.builder()
                .id(developerDTO.getId())
                .email(userEntity.getEmail())
                .login(userEntity.getLogin())
                .build();
    }

}
