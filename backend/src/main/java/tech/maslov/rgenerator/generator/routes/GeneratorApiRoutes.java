package tech.maslov.rgenerator.generator.routes;

import tech.maslov.rgenerator.api.base.routes.BaseApiRouter;

public class GeneratorApiRoutes {
    public static final String ROOT = BaseApiRouter.V1 + "/generator";

    public static final String BY_ID = ROOT + "/{id}";
    public static final String FILE_UPLOAD = ROOT + "/file";
    public static final String GENERATE = ROOT + "/{id}/generate";

}