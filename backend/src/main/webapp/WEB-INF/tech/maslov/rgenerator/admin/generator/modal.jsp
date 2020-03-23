<%@ page import="tech.maslov.rgenerator.generator.routes.GeneratorAdminRoutes" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="modal fade custom-width" id="modal-generator">
    <div class="modal-dialog" style="width: 96%">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Выбор Generator</h4>
            </div>

            <div class="modal-body">

                <div class="row">

                    <div class="col-lg-5">
                        <div class="input-group">
                            <input type="text" class="form-control input-sm" id="modal-generator-query" name="query"
                                   value="" placeholder="Поиск"/>

                            <div class="input-group-btn">
                                <button type="submit" id="modal-generator-search" class="btn btn-sm btn-default"><i
                                        class="entypo-search">Поиск </i>
                                </button>
                            </div>
                        </div>
                    </div>

                </div>
                <section id="modal-generator-parent-content"></section>

            </div>
        </div>
    </div>
</div>

<script>
    function initGeneratorModal() {
        $.get("<%= GeneratorAdminRoutes.MODAL_PARENT%>",
                {query: $('#modal-generator-query').val()},
                function (data) {
                    updateGeneratorContent(data);
                });
    }

    function updateGeneratorContent(data) {
        $('#modal-generator-parent-content').html(data);
    }

    function onClickGeneratorMTable() {
        var id = $(this).attr('data-id');
        var title = $(this).attr('data-title');
        $('#parent-generator-hidden').val(id);
        $('#parent-generator').val(title);
        $('#modal-generator').modal('hide');
    }

    function onClickGeneratorPaginator() {
        var q = $(this).attr('data-query');
        var n = $(this).attr('data-number');
        $.get("<%= GeneratorAdminRoutes.MODAL_PARENT%>",
                {query: q, currentPage: n},
                function (data) {
                    updateGeneratorContent(data);
                });
    }

    $(function () {
        $('#btn_parent_generator').click(function () {
            $('#modal-generator').modal('show');
            initGeneratorModal();
            return false;
        });
        $('#btn_parent_generator_clear').click(function () {
            $('#parent-generator-hidden').val('');
            $('#parent-generator').val('');
            return false;
        });

        $('#modal-generator').on('click', '.modal-generator-line', onClickGeneratorMTable);
        $('#modal-generator').on('click', '.modal-generator-goto', onClickGeneratorPaginator);
        $('#modal-generator-search').click(initGeneratorModal);

    });
</script>
