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
<script
	src="${pageContext.request.contextPath}/resources/js/scriptsBootStrap.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/material.js"></script>
<title>Material</title>

</head>
<body>
	<div>
		<jsp:include page="menu.jsp" />
	</div>
	<div class="container py-4">
		<div class="p-5 mb-4 bg-body-tertiary rounded-3 text-center shadow">
			<div class="container-fluid py-1">
				<h1 class="display-6 fw-bold">Manutenção de Material</h1>

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
				
				<form action="material" method="post" class="row g-3 mt-3"
					onsubmit="return validarFormulario(event);">
					<!-- Primeira Linha: Código, Nome, Preço de Custo -->
					<div class="row g-3">
						<div class="col-md-4">
							<div class="form-floating">
								<input class="form-control" type="number" min="0" step="1"
									id="codigo" name="codigo" placeholder="Código"
									value='<c:out value="${material.codigo}"></c:out>'> <label
									for="codigo">Código</label>
							</div>
						</div>
						<div class="col-md-3">
							<div class="form-floating">
								<input class="form-control" type="text" id="nome" name="nome"
									placeholder="Nome" maxlength="100"
									value='<c:out value="${material.nome}"></c:out>'> <label
									for="nome">Nome</label>
							</div>
						</div>

						<div class="col-md-1">
							<button type="submit" id="botao" name="botao" value="Buscar"
								class="btn btn-outline-primary w-100 d-flex justify-content-center align-items-center"
								onclick="return validarBusca()" style="height: 56px;">
								<!-- Ícone SVG dentro do botão -->
								<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
									fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
            <path
										d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0" />
        </svg>
							</button>
						</div>

						<div class="col-md-4">
							<div class="form-floating">
								<input class="form-control" type="text" id="preco" name="preco"
									placeholder="Preço de Custo"
									value='<fmt:formatNumber value="${material.preco}" type="currency" currencySymbol="R$" />'>
								<label for="preco">Preço de Custo:</label>
							</div>
						</div>
					</div>

					<!-- Segunda Linha: Preço de Venda, Quantidade, Unidade -->
					<div class="row g-3 mt-2">
						<div class="col-md-4">
							<div class="form-floating">
								<input type="text" id="cor" name="cor" class="form-control"
									placeholder="Cor"
									value='<c:out value="${material.cor}"></c:out>'> <label
									for="cor">Cor</label>
							</div>
						</div>


						<div class="col-md-4">
							<div class="form-floating">
								<select id="cliente" name="cliente" class="form-select">
									<option value="0">Escolha um Cliente</option>
									<c:forEach var="c" items="${clientes}">
										<option value="${c.codigo}"
											<c:if test="${c.codigo eq material.cliente.codigo}">selected</c:if>>
											<c:out value="${c}" />
										</option>
									</c:forEach>
								</select> <label for="cliente">Cliente</label>
							</div>
						</div>

						<div class="col-md-4">
							<div class="form-floating">
								<select id="fornecedor" name="fornecedor" class="form-select">
									<option value="0">Escolha um Fornecedor</option>
									<c:forEach var="f" items="${fornecedores}">
										<option value="${f.codigo}"
											<c:if test="${f.codigo eq material.fornecedor.codigo}">selected</c:if>>
											<c:out value="${f}" />
										</option>
									</c:forEach>
									<option value="novo" class="option-novo-fornecedor">
										Novo Fornecedor</option>
								</select> <label for="fornecedor">Fornecedor</label>
							</div>
						</div>
					</div>


					<!-- Terceira Linha: Fornecedor, Data de Compra -->
					<div class="row g-3 mt-2">
						<div class="col-md-4">
							<div class="form-floating">
								<select id="tipoMaterial" name="tipoMaterial"
									class="form-select">
									<option value="0">Escolha um Material</option>
									<c:forEach var="tm" items="${tiposMateriais}">
										<option value="${tm.codigo}"
											<c:if test="${tm.codigo eq material.tipoMaterial.codigo}">selected</c:if>>
											<c:out value="${tm}" />
										</option>
									</c:forEach>
								</select> <label for="tipoMaterial">Tipos Materiais</label>
							</div>
						</div>

						<div class="col-md-4">
							<div class="form-floating">
								<input type="text" id="largura" name="largura"
									class="form-control" placeholder="Largura"
									value='<c:out value="${material.largura}"></c:out>'> <label
									for="largura">Largura</label>
							</div>
						</div>

						<div class="col-md-4">
							<div class="form-floating">
								<input type="text" id="comprimento" name="comprimento"
									class="form-control" placeholder="Comprimento"
									value='<c:out value="${material.comprimento}"></c:out>'>
								<label for="comprimento">Comprimento</label>
							</div>
						</div>
					</div>

					<div class="row g-3 mt-2">
						<div class="col-md-4">
							<div class="form-floating">
								<input type="text" id="espessura" name="espessura"
									class="form-control" placeholder="Espessura"
									value='<c:out value="${material.espessura}"></c:out>'>
								<label for="espessura">Espessura</label>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-floating">
								<input type="date" id="data" name="data" class="form-control"
									placeholder="Data de Compra"
									value='<c:out value="${material.data}"></c:out>'> <label
									for="data">Data de Compra</label>
							</div>
						</div>
					</div>

					<div class="modal fade" id="materialModal" tabindex="-1"
						aria-labelledby="materialModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="materialModalLabel">Detalhes
										do Material</h5>
									<button type="button" class="btn-close" data-bs-dismiss="modal"
										aria-label="Close"></button>
								</div>
								<div class="modal-body">
									<p>
										<strong>Código:</strong> <span id="modalCodigo"></span>
									</p>
									<p>
										<strong>Nome:</strong> <span id="modalNome"></span>
									</p>
									<p>
										<strong>Preço de Custo:</strong> <span id="modalPreco"></span>
									</p>
									<p>
										<strong>Cor:</strong> <span id="modalCor"></span>
									</p>
									<p>
										<strong>Data de Compra:</strong> <span id="modalData"></span>
									</p>
									<p>
										<strong>Fornecedor:</strong> <span id="modalFornecedor"></span>
									</p>
									<p>
										<strong>Tipo de Material:</strong> <span
											id="modalTipoMaterial"></span>
									</p>
									<p>
										<strong>Largura:</strong> <span id="modalLargura"></span>
									</p>
									<p>
										<strong>Comprimento:</strong> <span id="modalComprimento"></span>
									</p>
									<p>
										<strong>Espessura:</strong> <span id="modalEspessura"></span>
									</p>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-secondary"
										data-bs-dismiss="modal">Fechar</button>
								</div>
							</div>
						</div>
					</div>


					<!-- Linha dos Botões -->
					<div class="row g-3 mt-3">
						<div class="col-md-2 d-grid text-center">
							<input type="submit" id="botao" name="botao" value="Cadastrar"
								class="btn btn-success btn-align">
						</div>
						<div class="col-md-2 d-grid text-center">
							<input type="submit" id="botao" name="botao" value="Alterar"
								class="btn btn-warning btn-align">
						</div>
						<div class="col-md-2 d-grid text-center">
							<input type="submit" id="botao" name="botao" value="Excluir"
								class="btn btn-danger btn-align">
						</div>

						<div class="col-md-2 d-grid text-center">
							<input type="submit" id="botao" name="botao" value="Listar"
								class="btn btn-dark btn-align">
						</div>
						<div class="col-md-2 d-grid text-center">
							<input type="submit" id="botao" name="botao" value="Limpar"
								class="btn btn-secondary btn-align">
						</div>

						<div class="col-md-2 d-grid text-center">
							<!-- Botão para abrir o modal de visualização de material -->
							<button type="button" class="btn btn-info btn-align"
								onclick="abrirModalMaterial('${material.codigo}')">Ver
								Detalhes</button>
						</div>
					</div>
				</form>

				<!-- Modal para cadastrar novo fornecedor -->
				<div class="modal fade" id="modalNovoFornecedor" tabindex="-1"
					aria-labelledby="modalNovoFornecedorLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="modalNovoFornecedorLabel">Cadastrar
									Novo Fornecedor</h5>
								<button type="button" class="btn-close" data-bs-dismiss="modal"
									aria-label="Close"></button>
							</div>
							<div class="modal-body">
								<form id="formNovoFornecedor"
									action="${pageContext.request.contextPath}/cadastrarFornecedor"
									method="post">
									<div class="form-floating mb-3">
										<input type="text" class="form-control" id="nomeFornecedor"
											name="nomeFornecedor" placeholder="Nome do Fornecedor"
											required> <label for="nomeFornecedor">Nome do
											Fornecedor</label>
									</div>
									<div class="form-floating mb-3">
										<input type="text" class="form-control"
											id="telefoneFornecedor" name="telefoneFornecedor"
											placeholder="Telefone" required> <label
											for="telefoneFornecedor">Telefone</label>
									</div>
									<button type="submit" class="btn btn-primary">Salvar</button>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="container py-4 text-center d-flex justify-content-center"
		align="center">
		<c:if test="${not empty materiais }">
			<div class="table-responsive w-100">
				<table class="table table-striped">
					<thead>
						<tr>
							<th class="titulo-tabela" colspan="9"
								style="text-align: center; font-size: 35px;">Lista de
								Materiais</th>
						</tr>
						<tr class="table-dark">
							<th></th>
							<th>Código</th>
							<th>Nome</th>
							<th>Preço</th>
							<th>Cor</th>
							<th>Data</th>
							<th>Fornecedor</th>
							<th>Tipos Materiais</th>
							<th>Excluir</th>
						</tr>
					</thead>
					<tbody class="table-group-divider">
						<c:forEach var="m" items="${materiais}">
							<tr>
								<td style="text-align: center;">
									<button class="btn btn-outline-dark" name="opcao"
										value="${m.codigo}" onclick="editarMaterial(this.value)"
										${m.codigo eq codigoEdicao ? 'checked' : ''}>
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
								<td><c:out value="${m.codigo}" /></td>
								<td><c:out value="${m.nome}" /></td>
								<td><fmt:formatNumber value="${m.preco}" type="currency"
										currencySymbol="R$" /></td>
								<td><c:out value="${m.cor}" /></td>
								<td><fmt:formatDate value="${m.data}" pattern="dd/MM/yyyy" /></td>
								<td><c:out value="${m.fornecedor.nome}" /></td>
								<td><c:out value="${m.tipoMaterial.nome}" /></td>
								<td style="text-align: center;">
									<button class="btn btn-danger"
										onclick="excluirMaterial('${m.codigo}')">Excluir</button>
								</td>
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
