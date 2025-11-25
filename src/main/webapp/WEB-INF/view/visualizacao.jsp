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
<title>Visualização</title>
</head>
<body>
	<script
		src="${pageContext.request.contextPath}/resources/js/visualizacao.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/scriptsBootStrap.js"></script>
	<div>
		<jsp:include page="menu.jsp" />
	</div>
	<div class="container py-4">
		<div class="p-5 mb-4 bg-body-tertiary rounded-3 text-center shadow">
			<div class="container-fluid py-1">
				<h1 class="display-6 fw-bold">Visualização</h1>

				<div align="center">
					<!-- Mensagem de Erro -->
					<c:if test="${not empty erro}">
						<div class="alert alert-danger fs-5" role="alert">
							<c:out value="${erro}" />
						</div>
					</c:if>

					<!-- Mensagem de Sucesso -->
					<c:if test="${not empty saida}">
						<div class="alert alert-success fs-5" role="alert">
							<c:out value="${saida}" />
						</div>
					</c:if>
				</div>

				<form action="visualizacao" method="post" class="row g-3 mt-3"
					onsubmit="return validarFormulario(event);"></form>
			</div>
		</div>
	</div>

	<div class="container py-4 text-center d-flex justify-content-center"
		align="center">
		<c:if test="${not empty fornecedores }">
			<div class="table-responsive w-100">
				<table class="table table-striped">
					<thead>
						<tr>
							<th class="titulo-tabela" colspan="14"
								style="text-align: center; font-size: 35px;">Lista de
								Fornecedores</th>
						</tr>
						<tr class="table-dark">
							<th></th>
							<th>Código</th>
							<th>Nome</th>
							<th>Telefone</th>
							<th>Excluir</th>
						</tr>
					</thead>
					<tbody class="table-group-divider">
						<c:forEach var="f" items="${fornecedores}">
							<tr>
								<td style="text-align: center;">
									<button class="btn btn-outline-dark" name="opcao"
										value="${f.codigo}" onclick="editarFornecedor(this.value)"
										${f.codigo eq codigoEdicao ? 'checked' : ''}>
										<svg xmlns="http://www.w3.org/2000/svg" width="26" height="26"
											fill="currentColor" class="bi bi-pencil-square"
											viewBox="0 0 16 16">
						<path
												d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z" />
						<path fill-rule="evenodd"
												d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5z" />
					</svg>
									</button>
								</td>
								<td><c:out value="${f.codigo}" /></td>
								<td><c:out value="${f.nome}" /></td>
								<td><c:out value="${f.telefone}" /></td>

								<td style="text-align: center;">
									<button class="btn btn-danger"
										onclick="excluirFornecedor('${f.codigo}')">Excluir</button>
								</td>
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