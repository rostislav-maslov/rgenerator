<%@ page import="tech.maslov.rgenerator.templateResult.routes.TemplateResultAdminRoutes" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="modal fade custom-width" id="modal-templateResult">
    <div class="modal-dialog" style="width: 96%">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Выбор Template Result</h4>
            </div>

            <div class="modal-body">

                <div class="row">

                    <div class="col-lg-5">
                        <div class="input-group">
                            <input type="text" class="form-control input-sm" id="modal-templateResult-query" name="query"
                                   value="" placeholder="Поиск"/>

                            <div class="input-group-btn">
                                <button type="submit" id="modal-templateResult-search" class="btn btn-sm btn-default"><i
                                        class="entypo-search">Поиск </i>
                                </button>
                            </div>
                        </div>
                    </div>

                </div>
                <section id="modal-templateResult-parent-content"></section>

            </div>
        </div>
    </div>
</div>

<script>
    function initTemplateResultModal() {
        $.get("<%= TemplateResultAdminRoutes.MODAL_PARENT%>",
                {query: $('#modal-templateResult-query').val()},
                function (data) {
                    updateTemplateResultContent(data);
                });
    }

    function updateTemplateResultContent(data) {
        $('#modal-templateResult-parent-content').html(data);
    }

    function onClickTemplateResultMTable() {
        var id = $(this).attr('data-id');
        var title = $(this).attr('data-title');
        $('#parent-templateResult-hidden').val(id);
        $('#parent-templateResult').val(title);
        $('#modal-templateResult').modal('hide');
    }

    function onClickTemplateResultPaginator() {
        var q = $(this).attr('data-query');
        var n = $(this).attr('data-number');
        $.get("<%= TemplateResultAdminRoutes.MODAL_PARENT%>",
                {query: q, currentPage: n},
                function (data) {
                    updateTemplateResultContent(data);
                });
    }

    $(function () {
        $('#btn_parent_templateResult').click(function () {
            $('#modal-templateResult').modal('show');
            initTemplateResultModal();
            return false;
        });
        $('#btn_parent_templateResult_clear').click(function () {
            $('#parent-templateResult-hidden').val('');
            $('#parent-templateResult').val('');
            return false;
        });

        $('#modal-templateResult').on('click', '.modal-templateResult-line', onClickTemplateResultMTable);
        $('#modal-templateResult').on('click', '.modal-templateResult-goto', onClickTemplateResultPaginator);
        $('#modal-templateResult-search').click(initTemplateResultModal);

    });
</script>
