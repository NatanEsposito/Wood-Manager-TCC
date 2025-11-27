<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet"
        href="${pageContext.request.contextPath}/resources/css/styles.css">
    <script src="${pageContext.request.contextPath}/resources/js/scriptsBootStrap.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/SobraMaterial.js"></script>
    <title>Visualização de Sobra de Material</title>
</head>
<body>

    <div>
        <jsp:include page="menu.jsp" />
    </div>

    <div class="container py-4">
        <div class="p-5 mb-4 bg-body-tertiary rounded-3 text-center shadow">

            <h1 class="display-6 fw-bold">Visualização de Sobra de Material</h1>

            <!-- MENSAGENS -->
            <div align="center">
                <c:if test="${not empty erro}">
                    <div class="alert alert-danger fs-5" role="alert">
                        <c:out value="${erro}" />
                    </div>
                </c:if>

                <c:if test="${not empty saida}">
                    <div class="alert alert-success fs-5" role="alert">
                        <c:out value="${saida}" />
                    </div>
                </c:if>
            </div>

            <!-- FORMULÁRIO -->
            <form action="visualizacao" method="post" class="row g-3 mt-3">

                <!-- PRIMEIRA LINHA: Estoque (50%) | Material (50%) -->
                <div class="row g-3">

                    <div class="col-md-6">
                        <div class="form-floating">
                            <input class="form-control" type="text" id="estoque"
                                name="estoque" placeholder="Estoque" maxlength="100"
                                value='<c:out value="${sobramaterial.estoque}"></c:out>'>
                            <label for="estoque">Estoque</label>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="form-floating">
                            <select id="material" name="material" class="form-select">
                                <option value="0">Escolha um Material</option>
                                <c:forEach var="m" items="${materiais}">
                                    <option value="${m.codigo}"
                                        <c:if test="${m.codigo eq sobramaterial.material.codigo}">selected</c:if>>
                                        <c:out value="${m}" />
                                    </option>
                                </c:forEach>
                            </select>
                            <label for="material">Material</label>
                        </div>
                    </div>

                </div>

                <!-- SEGUNDA LINHA: Largura | Comprimento | Espessura -->
                <div class="row g-3 mt-2">
                    <div class="col-md-4">
                        <div class="form-floating">
                            <input type="text" id="largura" name="largura"
                                class="form-control" placeholder="Largura"
                                value='<c:out value="${sobramaterial.largura}"></c:out>'>
                            <label for="largura">Largura</label>
                        </div>
                    </div>

                    <div class="col-md-4">
                        <div class="form-floating">
                            <input type="text" id="comprimento" name="comprimento"
                                class="form-control" placeholder="Comprimento"
                                value='<c:out value="${sobramaterial.comprimento}"></c:out>'>
                            <label for="comprimento">Comprimento</label>
                        </div>
                    </div>

                    <div class="col-md-4">
                        <div class="form-floating">
                            <input type="text" id="espessura" name="espessura"
                                class="form-control" placeholder="Espessura"
                                value='<c:out value="${sobramaterial.espessura}"></c:out>'>
                            <label for="espessura">Espessura</label>
                        </div>
                    </div>
                </div>

                <!-- LINHA DOS BOTÕES (50% | 50%) -->
                <div class="row g-3 mt-3">

                    <!-- Pesquisar -->
                    <div class="col-md-6 d-grid">
                        <button type="submit" id="botao" name="botao" value="Buscar"
                            class="btn btn-outline-primary btn-align d-flex justify-content-center align-items-center"
                            style="height: 56px;">

                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
                                fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                                <path
                                    d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0" />
                            </svg>

                        </button>
                    </div>

                    <!-- Limpar -->
                    <div class="col-md-6 d-grid">
                        <button type="submit" id="botao" name="botao"
                            value="Limpar" class="btn btn-secondary btn-align"
                            style="height: 56px;">
                            Limpar
                        </button>
                    </div>

                </div>

            </form>
        </div>
    </div>

    <!-- TABELA -->
    <div class="container py-4 text-center d-flex justify-content-center">
        <c:if test="${not empty sobraMateriais}">
            <div class="table-responsive w-100">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th class="titulo-tabela" colspan="7"
                                style="text-align:center; font-size:35px;">
                                Lista de Sobras de Materiais
                            </th>
                        </tr>
                        <tr class="table-dark">
                            <th>Código</th>
                            <th>Estoque</th>
                            <th>Material</th>
                            <th>Data</th>
                            <th>Largura</th>
                            <th>Comprimento</th>
                            <th>Espessura</th>
                        </tr>
                    </thead>

                    <tbody class="table-group-divider">
                        <c:forEach var="sm" items="${sobraMateriais}">
                            <tr>
                                <td><c:out value="${sm.codigo}" /></td>
                                <td><c:out value="${sm.estoque}" /></td>
                                <td><c:out value="${sm.material.nome}" /></td>
                                <td><fmt:formatDate value="${sm.data}" pattern="dd/MM/yyyy" /></td>
                                <td><c:out value="${sm.largura}" /></td>
                                <td><c:out value="${sm.comprimento}" /></td>
                                <td><c:out value="${sm.espessura}" /></td>
                            </tr>
                        </c:forEach>
                    </tbody>

                </table>
            </div>
        </c:if>
    </div>

    <div>
        <jsp:include page="footer.jsp" />
    </div>

</body>
</html>
