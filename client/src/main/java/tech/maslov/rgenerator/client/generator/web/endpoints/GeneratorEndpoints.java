package tech.maslov.rgenerator.client.generator.web.endpoints;

import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.token.exception.AuthorizationException;
import com.rcore.restapi.web.api.response.SearchApiResponse;
import com.rcore.restapi.web.api.response.SuccessApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import retrofit2.http.Path;
import tech.maslov.rgenerator.adapter.generator.GeneratorAdapter;
import tech.maslov.rgenerator.adapter.generator.dto.GeneratorDTO;
import tech.maslov.rgenerator.client.generator.application.FileWebMapper;
import tech.maslov.rgenerator.client.generator.application.GeneratorWebMapper;
import tech.maslov.rgenerator.client.generator.web.api.request.*;
import tech.maslov.rgenerator.client.generator.web.api.response.GeneratorWeb;
import tech.maslov.rgenerator.client.generator.web.routes.GeneratorApiRoutes;
import tech.maslov.rgenerator.domain.developer.service.github.dto.RepoResponse;
import tech.maslov.rgenerator.domain.generator.dto.CheckRepoDTO;
import tech.maslov.rgenerator.domain.generator.dto.FileContentDTO;

import java.io.IOException;
import java.util.List;

@Api(tags = "RGenerator API")
@RequiredArgsConstructor
@RestController
public class GeneratorEndpoints {

    private final GeneratorWebMapper generatorWebMapper;
    private final FileWebMapper fileWebMapper;
    private final GeneratorAdapter generatorAdapter;

    @ApiOperation("Create")
    @PostMapping(value = GeneratorApiRoutes.ROOT, produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessApiResponse<GeneratorWeb> create(@RequestBody GeneratorAddRequest request) throws AuthenticationException, AuthorizationException {
        GeneratorDTO generatorDTO = generatorAdapter.client.create(request.getTitle(), request.getDescription(), request.getExample());
        return SuccessApiResponse.of(generatorWebMapper.map(generatorDTO));
    }

    @ApiOperation("Get by id")
    @GetMapping(value = GeneratorApiRoutes.BY_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessApiResponse<GeneratorWeb> byId(@PathVariable String id) throws AuthenticationException, AuthorizationException {
        GeneratorDTO generatorDTO = generatorAdapter.client.byId(id);
        return SuccessApiResponse.of(generatorWebMapper.map(generatorDTO));
    }

    @ApiOperation("Delete by id")
    @DeleteMapping(value = GeneratorApiRoutes.BY_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessApiResponse<String> deleteById(@PathVariable String id) throws AuthenticationException, AuthorizationException {
        generatorAdapter.client.delete(id);
        return SuccessApiResponse.of("OK");
    }

    @ApiOperation("Edit info")
    @PostMapping(value = GeneratorApiRoutes.INFO, produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessApiResponse<GeneratorWeb> editInfo(@PathVariable String id, @RequestBody GeneratorInfoRequest request) throws AuthenticationException, AuthorizationException {
        GeneratorDTO generatorDTO = generatorAdapter.client.editInfo(id, request.getTitle(), request.getDescription());
        return SuccessApiResponse.of(generatorWebMapper.map(generatorDTO));
    }

    @ApiOperation("Edit Json")
    @PostMapping(value = GeneratorApiRoutes.JSON, produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessApiResponse<GeneratorWeb> editJson(@PathVariable String id, @RequestBody GeneratorJsonRequest request) throws AuthenticationException, AuthorizationException {
        GeneratorDTO generatorDTO = generatorAdapter.client.editJson(id, request.getExample());
        return SuccessApiResponse.of(generatorWebMapper.map(generatorDTO));
    }

    @ApiOperation("Edit files")
    @PostMapping(value = GeneratorApiRoutes.FILE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessApiResponse<GeneratorWeb> editFile(@PathVariable String id, @RequestBody GeneratorFileEditRequest request) throws AuthenticationException, AuthorizationException {
        GeneratorDTO generatorDTO = generatorAdapter.client.editFile(id, request.getOldFileId(), request.getNewFileId());
        return SuccessApiResponse.of(generatorWebMapper.map(generatorDTO));
    }

    @ApiOperation("Delete files")
    @DeleteMapping(value = GeneratorApiRoutes.FILE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessApiResponse<GeneratorWeb> deleteFile(@PathVariable String id, @PathVariable String fileId) throws AuthenticationException, AuthorizationException {
        GeneratorDTO generatorDTO = generatorAdapter.client.fileDelete(id, fileId);
        return SuccessApiResponse.of(generatorWebMapper.map(generatorDTO));
    }

    @ApiOperation("View file content")
    @GetMapping(value = GeneratorApiRoutes.FILE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessApiResponse<GeneratorWeb.File> fileView(@PathVariable String id, @PathVariable String fileId) throws IOException, AuthenticationException, AuthorizationException {
        FileContentDTO fileDTO = generatorAdapter.client.file(id, fileId);
        return SuccessApiResponse.of(fileWebMapper.map(fileDTO));
    }

    @ApiOperation("Explore list")
    @GetMapping(value = GeneratorApiRoutes.ROOT, produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessApiResponse<SearchApiResponse<GeneratorWeb>> list() {
        List<GeneratorDTO> generatorDTO = generatorAdapter.client.list();

        return SuccessApiResponse.of(
                SearchApiResponse.withItemsAndCount(
                        generatorWebMapper.mapAll(generatorDTO),
                        (long) generatorDTO.size()
                )
        );
    }

    @ApiOperation("My RGenerators list")
    @GetMapping(value = GeneratorApiRoutes.MY_GENERATORS, produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessApiResponse<SearchApiResponse<GeneratorWeb>> myList() throws AuthenticationException {
        List<GeneratorDTO> generatorDTO = generatorAdapter.client.myGeneratorsList();

        return SuccessApiResponse.of(
                SearchApiResponse.withItemsAndCount(
                        generatorWebMapper.mapAll(generatorDTO),
                        (long) generatorDTO.size()
                )
        );
    }

    @ApiOperation("Check github repo before sync")
    @GetMapping(value = GeneratorApiRoutes.GH_CHECK, produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessApiResponse<CheckRepoDTO> ghCheck(@RequestParam String repo) throws AuthenticationException, IOException {
        CheckRepoDTO checkRepoDTO = generatorAdapter.client.checkRepo(repo);

        return SuccessApiResponse.of(checkRepoDTO);
    }

    @ApiOperation("Sync github repo content")
    @PostMapping(value = GeneratorApiRoutes.GH_SYNC, produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessApiResponse<String> ghSync(@PathVariable String id, @RequestBody GeneratorSyncRequest request) throws AuthenticationException, IOException, AuthorizationException, InterruptedException {
        generatorAdapter.client.syncRepo(id, request.getRepo());

        return SuccessApiResponse.of(HttpStatus.OK.toString());
    }

    @ApiOperation("GitHub repos")
    @GetMapping(value = GeneratorApiRoutes.GH_REPOS, produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessApiResponse<List<RepoResponse>> ghRepos() throws AuthenticationException, IOException, AuthorizationException, InterruptedException {
        return SuccessApiResponse.of(generatorAdapter.client.repos());
    }

}
