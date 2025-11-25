<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/styles.css">
<title>Cliente</title>
</head>
<body>
	<script
		src="${pageContext.request.contextPath}/resources/js/Cliente.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/scriptsBootStrap.js"></script>
	<div>
		<jsp:include page="menu.jsp" />
	</div>
	<div class="container py-4">
		<div class="p-5 mb-4 bg-body-tertiary rounded-3 text-center shadow">
			<div class="container-fluid py-1">
				<h1 class="display-6 fw-bold">Manutenção de Cliente</h1>

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


				<form action="cliente" method="post" class="row g-3 mt-3"
					onsubmit="return validarFormulario(event);">
					<!-- Primeira Linha: Código, Nome, Telefone -->
					<div class="row g-3">
						<div class="col-md-4">
							<div class="form-floating">
								<input type="number" min="0" step="1" id="codigo" name="codigo"
									class="form-control" placeholder="Código"
									value='<c:out value="${cliente.codigo }"></c:out>' readonly>
								<label for="codigo">Código</label>
							</div>
						</div>
						<div class="col-md-3">
							<div class="form-floating">
								<input type="text" id="nome" name="nome" class="form-control"
									placeholder="Nome" maxlength="100"
									value='<c:out value="${cliente.nome }"></c:out>'> <label
									for="nome">Nome</label>
							</div>
						</div>
						<div class="col-md-1">
							<button type="submit" id="botao" name="botao" value="Buscar"
								class="btn btn-outline-primary btn-align w-100 d-flex justify-content-center align-items-center"
								onclick="return validarBusca()">
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
								<input type="text" id="telefone" name="telefone"
									class="form-control" placeholder="Telefone" maxlength="15"
									value='<c:out value="${cliente.telefone }"></c:out>'> <label
									for="telefone">Telefone</label>
							</div>
						</div>
					</div>

					<!-- Segunda Linha: CEP, Logradouro, Bairro -->
					<div class="row g-3 mt-2">
						<div class="col-md-4">
							<div class="form-floating">
								<input type="text" id="CEP" name="CEP" class="form-control"
									placeholder="CEP" maxlength="9"
									value='<c:out value="${cliente.CEP }"></c:out>'
									onblur="buscarEndereco()"> <label for="cep">CEP</label>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-floating">
								<input type="text" id="logradouro" name="logradouro"
									class="form-control" placeholder="Logradouro" maxlength="100"
									value='<c:out value="${cliente.logradouro }"></c:out>' readonly>
								<label for="logradouro">Logradouro</label>
							</div>
						</div>

						<div class="col-md-4">
							<div class="form-floating">
								<input type="text" id="bairro" name="bairro"
									class="form-control" placeholder="Bairro" maxlength="100"
									value='<c:out value="${cliente.bairro }"></c:out>' readonly>
								<label for="bairro">Bairro</label>
							</div>
						</div>
					</div>
					<!-- Quarta Linha: UF, Localidade, Numero -->
					<div class="row g-3 mt-2">
						<div class="col-md-4">
							<div class="form-floating">
								<input type="text" id="UF" name="UF" class="form-control"
									placeholder="UF" maxlength="2"
									value='<c:out value="${cliente.UF }"></c:out>' readonly>
								<label for="uf">UF</label>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-floating">
								<input type="text" id="localidade" name="localidade"
									class="form-control" placeholder="Localidade" maxlength="100"
									value='<c:out value="${cliente.localidade }"></c:out>' readonly>
								<label for="localidade">Localidade</label>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-floating">
								<input type="text" id="numero" name="numero"
									class="form-control" placeholder="Número" maxlength="20"
									value='<c:out value="${cliente.numero }"></c:out>'> <label
									for="numero">Número</label>
							</div>
						</div>
					</div>

					<!-- Quinta Linha: Complemento, Data Nascimento e Botão Contato por WhatsApp -->
					<div class="row g-3 mt-2">
						<div class="col-md-4">
							<div class="form-floating">
								<input type="text" id="complemento" name="complemento"
									class="form-control" placeholder="Complemento" maxlength="100"
									value='<c:out value="${cliente.complemento }"></c:out>'>
								<label for="complemento">Complemento</label>
							</div>
						</div>

						<div class="col-md-4 d-flex align-items-center">
							<button type="button"
								class="btn btn-outline-success btn-align d-flex align-items-center w-100"
								onclick="redirectToWhatsApp()">
								<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
									fill="currentColor" class="bi bi-whatsapp" viewBox="0 0 16 16"
									style="margin-right: 5px;">
                <path
										d="M13.601 2.326A7.85 7.85 0 0 0 7.994 0C3.627 0 .068 3.558.064 7.926c0 1.399.366 2.76 1.057 3.965L0 16l4.204-1.102a7.9 7.9 0 0 0 3.79.965h.004c4.368 0 7.926-3.558 7.93-7.93A7.9 7.9 0 0 0 13.6 2.326zM7.994 14.521a6.6 6.6 0 0 1-3.356-.92l-.24-.144-2.494.654.666-2.433-.156-.251a6.56 6.56 0 0 1-1.007-3.505c0-3.626 2.957-6.584 6.591-6.584a6.56 6.56 0 0 1 4.66 1.931 6.56 6.56 0 0 1 1.928 4.66c-.004 3.639-2.961 6.592-6.592 6.592m3.615-4.934c-.197-.099-1.17-.578-1.353-.646-.182-.065-.315-.099-.445.099-.133.197-.513.646-.627.775-.114.133-.232.148-.43.05-.197-.1-.836-.308-1.592-.985-.59-.525-.985-1.175-1.103-1.372-.114-.198-.011-.304.088-.403.087-.088.197-.232.296-.346.1-.114.133-.198.198-.33.065-.134.034-.248-.015-.347-.05-.099-.445-1.076-.612-1.47-.16-.389-.323-.335-.445-.34-.114-.007-.247-.007-.38-.007a.73.73 0 0 0-.529.247c-.182.198-.691.677-.691 1.654s.71 1.916.81 2.049c.098.133 1.394 2.132 3.383 2.992.47.205.84.326 1.129.418.475.152.904.129 1.246.08.38-.058 1.171-.48 1.338-.943.164-.464.164-.86.114-.943-.049-.084-.182-.133-.38-.232" />
            </svg>
								Contato WhatsApp
							</button>
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
								onclick="abrirModalCliente('${cliente.codigo}')">Ver
								Detalhes</button>
						</div>
					</div>
				</form>

				<div class="modal fade" id="clienteModal" tabindex="-1"
					aria-labelledby="clienteModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="clienteModalLabel">Detalhes do
									Cliente</h5>
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
									<strong>Telefone:</strong> <span id="modalTelefone"></span>
								</p>
								<p>
									<strong>CEP:</strong> <span id="modalCEP"></span>
								</p>
								<p>
									<strong>Logradouro:</strong> <span id="modalLogradouro"></span>
								</p>
								<p>
									<strong>Bairro:</strong> <span id="modalBairro"></span>
								</p>
								<p>
									<strong>UF:</strong> <span id="modalUF"></span>
								</p>
								<p>
									<strong>Localidade:</strong> <span id="modalLocalidade"></span>
								</p>
								<p>
									<strong>Número:</strong> <span id="modalNumero"></span>
								</p>
								<p>
									<strong>Complemento:</strong> <span id="modalComplemento"></span>
								</p>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-secondary"
									data-bs-dismiss="modal">Fechar</button>
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>

	<div class="container py-4 text-center d-flex justify-content-center"
		align="center">
		<c:if test="${not empty clientes}">
			<div class="table-responsive w-100">
				<table class="table table-striped">
					<thead>
						<tr>
							<th class="titulo-tabela" colspan="8"
								style="text-align: center; font-size: 35px;">Lista de
								Clientes</th>
						</tr>
						<tr class="table-dark">
							<td></td>
							<td>Código</td>
							<td>Nome</td>
							<td>Telefone</td>
							<td>Excluir</td>
						</tr>
					</thead>
					<tbody class="table-group-divider">
						<c:forEach var="c" items="${clientes }">
							<tr>
								<td style="text-align: center;">
									<button class="btn btn-outline-dark" name="opcao"
										value="${c.codigo}" onclick="editarCliente(this.value)"
										${c.codigo eq codigoEdicao ? 'checked' : ''}>
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
								<td><c:out value="${c.codigo }" /></td>
								<td><c:out value="${c.nome }" /></td>
								<td class="telefone"><c:out value="${c.telefone }" /></td>
								<td style="text-align: center;">
									<button class="btn btn-danger"
										onclick="excluirCliente('${c.codigo}')">Excluir</button>
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
