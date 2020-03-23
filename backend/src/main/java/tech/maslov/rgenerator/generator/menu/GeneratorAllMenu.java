package tech.maslov.rgenerator.generator.menu;

import tech.maslov.rgenerator.generator.routes.GeneratorAdminRoutes;
import com.ub.core.base.menu.CoreMenu;

public class GeneratorAllMenu extends CoreMenu {
    public GeneratorAllMenu() {
        this.name = "Все";
        this.parent = new GeneratorMenu();
        this.url = GeneratorAdminRoutes.ALL;
    }
}
