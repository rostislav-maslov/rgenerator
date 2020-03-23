<%@ page import="tech.maslov.rgenerator.templateResult.routes.TemplateResultAdminRoutes" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="/WEB-INF/com/ub/core/admin/main/components/pageHeader.jsp"/>

<div class="row">
    <div class="col-lg-12">
        <div class="widget widget-green" id="widget_profit_chart">
            <div class="widget-title">
                <h3><i class="fa fa-tasks" aria-hidden="true"></i> Редактировать </h3>
            </div>
            <div class="widget-content">

                <form:form action="<%= TemplateResultAdminRoutes.EDIT%>" modelAttribute="doc" method="POST">

                    <c:set value="${doc}" var="doc" scope="request"/>
                    <jsp:include page="form.jsp"/>
                    <br>
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary">Сохранить</button>
                    </div>
                </form:form>

            </div>
        </div>
    </div>
</div>