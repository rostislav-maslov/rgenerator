package tech.maslov.rgenerator.generator.controllers;

import com.mongodb.gridfs.GridFSDBFile;
import tech.maslov.rgenerator.generator.models.GeneratorDoc;
import tech.maslov.rgenerator.generator.routes.GeneratorAdminRoutes;
import tech.maslov.rgenerator.generator.services.GeneratorService;
import tech.maslov.rgenerator.generator.views.all.SearchGeneratorAdminRequest;
import tech.maslov.rgenerator.generator.views.all.SearchGeneratorAdminResponse;
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
public class GeneratorAdminController {

    @Autowired private GeneratorService generatorService;

    private PageHeader defaultPageHeader(String current) {
        PageHeader pageHeader = PageHeader.defaultPageHeader();
        pageHeader.setLinkAdd(GeneratorAdminRoutes.ADD);
        pageHeader.getBreadcrumbs().getLinks().add(new BreadcrumbsLink(GeneratorAdminRoutes.ALL, "Все Generator"));
        pageHeader.getBreadcrumbs().setCurrentPageTitle(current);
        return pageHeader;
    }

    @RequestMapping(value = GeneratorAdminRoutes.ADD, method = RequestMethod.GET)
    public String create(Model model) {
        GeneratorDoc doc = new GeneratorDoc();
        doc.setId(new ObjectId());
        model.addAttribute("doc", doc);
        model.addAttribute("pageHeader", defaultPageHeader("Добавление"));
        return "tech.maslov.rgenerator.admin.generator.add";
    }

    @RequestMapping(value = GeneratorAdminRoutes.ADD, method = RequestMethod.POST)
    public String create(@ModelAttribute("doc") GeneratorDoc doc,
                         RedirectAttributes redirectAttributes) {
        generatorService.save(doc);
        redirectAttributes.addAttribute("id", doc.getId());
        return RouteUtils.redirectTo(GeneratorAdminRoutes.EDIT);
    }

    @RequestMapping(value = GeneratorAdminRoutes.EDIT, method = RequestMethod.GET)
    public String update(@RequestParam ObjectId id, Model model) {
        GeneratorDoc doc = generatorService.findById(id);
        model.addAttribute("doc", doc);
        model.addAttribute("pageHeader", defaultPageHeader("Редактирование"));
        return "tech.maslov.rgenerator.admin.generator.edit";
    }

    @RequestMapping(value = GeneratorAdminRoutes.EDIT, method = RequestMethod.POST)
    public String update(@ModelAttribute("doc") GeneratorDoc doc,
                         RedirectAttributes redirectAttributes) {
        generatorService.save(doc);
        redirectAttributes.addAttribute("id", doc.getId());
        return RouteUtils.redirectTo(GeneratorAdminRoutes.EDIT);
    }

    @RequestMapping(value = GeneratorAdminRoutes.ALL, method = RequestMethod.GET)
    public String all(@RequestParam(required = false, defaultValue = "0") Integer currentPage,
                      @RequestParam(required = false, defaultValue = "") String query,
                      Model model) {
        SearchGeneratorAdminRequest searchRequest = new SearchGeneratorAdminRequest(currentPage);
        searchRequest.setQuery(query);
        model.addAttribute("search", generatorService.findAll(searchRequest));
        model.addAttribute("pageHeader", defaultPageHeader("Все"));
        return "tech.maslov.rgenerator.admin.generator.all";
    }

    @RequestMapping(value = GeneratorAdminRoutes.MODAL_PARENT, method = RequestMethod.GET)
    public String modalResponse(@RequestParam(required = false, defaultValue = "0") Integer currentPage,
                                @RequestParam(required = false, defaultValue = "") String query,
                                Model model) {
        SearchGeneratorAdminRequest searchRequest = new SearchGeneratorAdminRequest(currentPage);
        searchRequest.setQuery(query);
        model.addAttribute("search", generatorService.findAll(searchRequest));
        return "tech.maslov.rgenerator.admin.generator.modal.parent";
    }

    @RequestMapping(value = GeneratorAdminRoutes.DELETE, method = RequestMethod.GET)
    public String delete(@RequestParam ObjectId id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("pageHeader", defaultPageHeader("Удаление"));
        return "tech.maslov.rgenerator.admin.generator.delete";
    }

    @RequestMapping(value = GeneratorAdminRoutes.DELETE, method = RequestMethod.POST)
    public String delete(@RequestParam ObjectId id) {
        generatorService.remove(id);
        return RouteUtils.redirectTo(GeneratorAdminRoutes.ALL);
    }
}
