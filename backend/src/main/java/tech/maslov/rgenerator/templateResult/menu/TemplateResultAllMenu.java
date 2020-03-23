package tech.maslov.rgenerator.templateResult.menu;

import tech.maslov.rgenerator.templateResult.routes.TemplateResultAdminRoutes;
import com.ub.core.base.menu.CoreMenu;

public class TemplateResultAllMenu extends CoreMenu {
    public TemplateResultAllMenu() {
        this.name = "Все";
        this.parent = new TemplateResultMenu();
        this.url = TemplateResultAdminRoutes.ALL;
    }
}
