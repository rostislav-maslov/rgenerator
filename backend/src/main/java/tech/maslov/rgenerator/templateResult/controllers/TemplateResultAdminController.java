package tech.maslov.rgenerator.templateResult.controllers;

import com.mongodb.gridfs.GridFSDBFile;
import tech.maslov.rgenerator.templateResult.models.TemplateResultDoc;
import tech.maslov.rgenerator.templateResult.routes.TemplateResultAdminRoutes;
import tech.maslov.rgenerator.templateResult.services.TemplateResultService;
import tech.maslov.rgenerator.templateResult.views.all.SearchTemplateResultAdminRequest;
import tech.maslov.rgenerator.templateResult.views.all.SearchTemplateResultAdminResponse;
import com.ub.core.base.utils.RouteUtils;
import com.ub.core.file.services.FileService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.ub.core.base.views.breadcrumbs.BreadcrumbsLink;
import com.ub.core.base.views.pageHeader.PageHeader;

@Controller
public class TemplateResultAdminController {

    @Autowired private TemplateResultService templateResultService;

    private PageHeader defaultPageHeader(String current) {
        PageHeader pageHeader = PageHeader.defaultPageHeader();
        pageHeader.setLinkAdd(TemplateResultAdminRoutes.ADD);
        pageHeader.getBreadcrumbs().getLinks().add(new BreadcrumbsLink(TemplateResultAdminRoutes.ALL, "Все Template Result"));
        pageHeader.getBreadcrumbs().setCurrentPageTitle(current);
        return pageHeader;
    }

    @RequestMapping(value = TemplateResultAdminRoutes.ADD, method = RequestMethod.GET)
    public String create(Model model) {
        TemplateResultDoc doc = new TemplateResultDoc();
        doc.setId(new ObjectId());
        model.addAttribute("doc", doc);
        model.addAttribute("pageHeader", defaultPageHeader("Добавление"));
        return "tech.maslov.rgenerator.admin.templateResult.add";
    }

    @RequestMapping(value = TemplateResultAdminRoutes.ADD, method = RequestMethod.POST)
    public String create(@ModelAttribute("doc") TemplateResultDoc doc,
                         RedirectAttributes redirectAttributes) {
        templateResultService.save(doc);
        redirectAttributes.addAttribute("id", doc.getId());
        return RouteUtils.redirectTo(TemplateResultAdminRoutes.EDIT);
    }

    @RequestMapping(value = TemplateResultAdminRoutes.EDIT, method = RequestMethod.GET)
    public String update(@RequestParam ObjectId id, Model model) {
        TemplateResultDoc doc = templateResultService.findById(id);
        model.addAttribute("doc", doc);
        model.addAttribute("pageHeader", defaultPageHeader("Редактирование"));
        return "tech.maslov.rgenerator.admin.templateResult.edit";
    }

    @RequestMapping(value = TemplateResultAdminRoutes.EDIT, method = RequestMethod.POST)
    public String update(@ModelAttribute("doc") TemplateResultDoc doc,
                         RedirectAttributes redirectAttributes) {
        templateResultService.save(doc);
        redirectAttributes.addAttribute("id", doc.getId());
        return RouteUtils.redirectTo(TemplateResultAdminRoutes.EDIT);
    }

    @RequestMapping(value = TemplateResultAdminRoutes.ALL, method = RequestMethod.GET)
    public String all(@RequestParam(required = false, defaultValue = "0") Integer currentPage,
                      @RequestParam(required = false, defaultValue = "") String query,
                      Model model) {
        SearchTemplateResultAdminRequest searchRequest = new SearchTemplateResultAdminRequest(currentPage);
        searchRequest.setQuery(query);
        model.addAttribute("search", templateResultService.findAll(searchRequest));
        model.addAttribute("pageHeader", defaultPageHeader("Все"));
        return "tech.maslov.rgenerator.admin.templateResult.all";
    }

    @RequestMapping(value = TemplateResultAdminRoutes.MODAL_PARENT, method = RequestMethod.GET)
    public String modalResponse(@RequestParam(required = false, defaultValue = "0") Integer currentPage,
                                @RequestParam(required = false, defaultValue = "") String query,
                                Model model) {
        SearchTemplateResultAdminRequest searchRequest = new SearchTemplateResultAdminRequest(currentPage);
        searchRequest.setQuery(query);
        model.addAttribute("search", templateResultService.findAll(searchRequest));
        return "tech.maslov.rgenerator.admin.templateResult.modal.parent";
    }

    @RequestMapping(value = TemplateResultAdminRoutes.DELETE, method = RequestMethod.GET)
    public String delete(@RequestParam ObjectId id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("pageHeader", defaultPageHeader("Удаление"));
        return "tech.maslov.rgenerator.admin.templateResult.delete";
    }

    @RequestMapping(value = TemplateResultAdminRoutes.DELETE, method = RequestMethod.POST)
    public String delete(@RequestParam ObjectId id) {
        templateResultService.remove(id);
        return RouteUtils.redirectTo(TemplateResultAdminRoutes.ALL);
    }
}
