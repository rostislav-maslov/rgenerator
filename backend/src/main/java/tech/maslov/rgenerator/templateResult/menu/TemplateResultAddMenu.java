package tech.maslov.rgenerator.templateResult.menu;

import tech.maslov.rgenerator.templateResult.routes.TemplateResultAdminRoutes;
import com.ub.core.base.menu.CoreMenu;

public class TemplateResultAddMenu extends CoreMenu {
    public TemplateResultAddMenu() {
        this.name = "Добавить";
        this.parent = new TemplateResultMenu();
        this.url = TemplateResultAdminRoutes.ADD;
    }
}
