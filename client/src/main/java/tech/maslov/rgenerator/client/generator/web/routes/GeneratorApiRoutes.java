package tech.maslov.rgenerator.client.generator.web.routes;

import com.rcore.restapi.routes.BaseApiRoutes;

public class GeneratorApiRoutes {
    public static final String ROOT = BaseApiRoutes.V1 + "/generator";
    public static final String MY_GENERATORS = BaseApiRoutes.V1 + "/generator/my";
    public static final String GH_CHECK = BaseApiRoutes.V1 + "/generator/github/check";
    public static final String GH_REPOS = BaseApiRoutes.V1 + "/generator/github/repos";
    public static final String BY_ID = ROOT + "/{id}";
    public static final String GH_SYNC = ROOT + "/{id}/sync";

    public static final String INFO = BY_ID + "/info";
    public static final String JSON = BY_ID + "/json";
    public static final String FILE = BY_ID + "/file/{fileId}";

    public static final String FILE_UPLOAD = ROOT + "/file";
    public static final String GENERATE = ROOT + "/{id}/generate";
}
