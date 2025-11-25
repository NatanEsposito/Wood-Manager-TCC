<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pagina Inicial</title>
</head>
<body>
	<script
		src="${pageContext.request.contextPath}/resources/js/scriptsBootStrap.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/index.js"></script>

	<div>
		<jsp:include page="menu.jsp" />
	</div>
	<div class="container py-4">
		<div class="p-5 mb-4 bg-body-tertiary rounded-3 text-center shadow">
			<div class="container-fluid py-1">
				<h1 class="display-6 fw-bold">Bem-Vindo Wood Manager</h1>
				<form id="index" action="index" method="post" class="row g-3 mt-3">
					<!-- Campo oculto para armazenar a escolha -->
					<input type="hidden" id="escolhaInput" name="escolha">
					<!-- Container para centralizar a imagem -->
					<div class="col-12 d-flex justify-content-center">
						<img
							src="${pageContext.request.contextPath}/resources/imagens/logo.png"
							alt="Imagem centralizada" class="img-fluid"
							style="max-width: 25%; height: auto;">
					</div>
				</form>
			</div>
		</div>

		<div class="text-center">
			<c:if test="${not empty erro}">
				<h2 class="text-danger">
					<b><c:out value="${erro}" /></b>
				</h2>
			</c:if>
		</div>

		<div class="text-center">
			<c:if test="${not empty saida }">
				<h2 class="text-success">
					<b><c:out value="${saida}" /></b>
				</h2>
			</c:if>
		</div>
	</div>
	<div>
		<jsp:include page="footer.jsp" />
	</div>

</body>
</html>