package tech.maslov.rgenerator.generator.menu;

import tech.maslov.rgenerator.generator.routes.GeneratorAdminRoutes;
import com.ub.core.base.menu.CoreMenu;

public class GeneratorAddMenu extends CoreMenu {
    public GeneratorAddMenu() {
        this.name = "Добавить";
        this.parent = new GeneratorMenu();
        this.url = GeneratorAdminRoutes.ADD;
    }
}
