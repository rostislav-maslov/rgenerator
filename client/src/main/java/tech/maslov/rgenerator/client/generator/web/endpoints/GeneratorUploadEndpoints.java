package tech.maslov.rgenerator.client.generator.web.endpoints;

import com.rcore.domain.file.entity.FileEntity;
import com.rcore.domain.file.port.FileRepository;
import com.rcore.domain.file.port.FileStorage;
import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.token.exception.AuthorizationException;
import com.rcore.restapi.web.api.response.SuccessApiResponse;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.maslov.rgenerator.adapter.generator.GeneratorAdapter;
import tech.maslov.rgenerator.client.generator.application.FileWebMapper;
import tech.maslov.rgenerator.client.generator.application.GeneratorWebMapper;
import tech.maslov.rgenerator.client.generator.web.api.response.GeneratorWeb;
import tech.maslov.rgenerator.client.generator.web.routes.GeneratorApiRoutes;
import tech.maslov.rgenerator.domain.generator.entity.GeneratorEntity;
import tech.maslov.rgenerator.domain.generator.port.GeneratorRepository;
import tech.maslov.rgenerator.domain.templateResult.service.ZipService;

import java.io.IOException;
import java.util.Map;

@Secured({})
@Api(tags = "RGenerator Upload")
@RequiredArgsConstructor
@Controller
public class GeneratorUploadEndpoints {

    private final GeneratorWebMapper generatorWebMapper;
    private final FileWebMapper fileWebMapper;
    private final GeneratorAdapter generatorAdapter;
    private final FileStorage fileStorage;
    private final FileRepository fileRepository;
    private final ZipService zipService;
    private final GeneratorRepository generatorRepository;

    @ResponseBody
    @RequestMapping(value = GeneratorApiRoutes.FILE_UPLOAD, method = RequestMethod.POST)
    public SuccessApiResponse<GeneratorWeb.File> create(@RequestParam String id, @RequestParam String path, @RequestParam MultipartFile file) throws IOException, AuthenticationException, AuthorizationException {
        String filePath = fileStorage.store(file.getInputStream(), file.getName(), file.getContentType());
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(file.getName());
        fileEntity.setFilePath(filePath);
        fileRepository.save(fileEntity);

        return SuccessApiResponse.of(fileWebMapper.map(generatorAdapter.client.fileAdd(id, path, fileEntity)));
    }

    @ResponseBody
    @RequestMapping(value = GeneratorApiRoutes.GENERATE, method = RequestMethod.POST, produces = "text/plain")
    public String create(@PathVariable String id, @RequestParam String content) throws IOException {
        GeneratorEntity generatorEntity = generatorRepository.findById(id).get();
        Map data = zipService.contentObject(generatorEntity.getExample());
        String result = zipService.genTemplate(data, content);
        return result;
    }
}
