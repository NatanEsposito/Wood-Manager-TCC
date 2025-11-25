<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<title>Menu</title>
<script>
	
</script>
</head>
<body>
	<nav class="navbar navbar-expand-lg bg-body-tertiary"
		data-bs-theme="light">
		<div class="container-fluid">
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown"
				aria-controls="navbarNavDropdown" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNavDropdown">
				<div class="collapse navbar-collapse" id="navbarNavDropdown">
					<a
						class="d-flex align-items-center mb-3 mb-md-0 me-md-auto link-body-emphasis text-decoration-none"
						href="${pageContext.request.contextPath}/index"> <img
						src="${pageContext.request.contextPath}/resources/imagens/logo.png"
						style="width: 40px; height: 40px;"> <span class="ms-2 fs-4">Wood
							Manager</span> <!-- Use uma classe de estilo para tamanho de fonte -->
					</a>

					<ul class="nav nav-pills">
						<li class="nav-item dropdown"><a
							class="nav-link dropdown-toggle px-3 link-body-emphasis" href="#"
							id="navbarDropdown" role="button" data-bs-toggle="dropdown"
							aria-expanded="false"> Opções </a>
							<ul class="dropdown-menu" aria-labelledby="navbarDropdown">
								<li><a class="dropdown-item"
									href="${pageContext.request.contextPath}/cliente">Opção 1</a></li>
								<li><a class="dropdown-item"
									href="${pageContext.request.contextPath}/equipamento">Opção
										2</a></li>
								<li><a class="dropdown-item"
									href="${pageContext.request.contextPath}/fornecedor">Opção
										3</a></li>

							</ul></li>

						<li class="nav-item"><a
							class="nav-link px-3 link-body-emphasis"
							href="${pageContext.request.contextPath}/material">Material</a></li>

						<li class="nav-item"><a
							class="nav-link px-3 link-body-emphasis"
							href="${pageContext.request.contextPath}/sobramaterial">Sobra
								Material</a></li>

						<li class="nav-item"><a
							class="nav-link px-3 link-body-emphasis"
							href="${pageContext.request.contextPath}/visualizacao">Visualização</a></li>

						<li class="nav-item"><a
							class="nav-link px-3 link-body-emphasis"
							href="${pageContext.request.contextPath}/fornecedor">Fornecedor</a></li>

						<li class="nav-item"><a
							class="nav-link px-3 link-body-emphasis"
							href="${pageContext.request.contextPath}/cliente">Cliente</a></li>

						<li class="nav-item"><a
							class="nav-link px-3 link-body-emphasis"
							href="${pageContext.request.contextPath}/tipomaterial">Tipos
								de Materiais</a></li>
					</ul>

				</div>
			</div>
		</div>
	</nav>
</body>
</html>