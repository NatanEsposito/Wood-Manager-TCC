// Material.js

function consultarMaterial(codigo) {
	window.location.href = 'consulta?codigo=' + codigo;
}

function editarMaterial(codigo) {
	window.location.href = 'material?cmd=alterar&codigo=' + codigo;
}

function excluirMaterial(codigo) {
	if (confirm("Tem certeza que deseja excluir este Material?")) {
		window.location.href = 'material?cmd=excluir&codigo=' + codigo;
	}
}

function abrirModalMaterial(codigo) {

	// Validação do código
	if (codigo == 0 || codigo.trim() === "") {
		alert("O código do material não pode estar vazio.");
		return; // Interrompe a função se o código estiver vazio
	}
	// Seleciona os campos de entrada com os dados preenchidos no formulário
	var codigoInput = document.getElementById('codigo');
	var nomeInput = document.getElementById('nome');
	var precoInput = document.getElementById('preco');
	var corInput = document.getElementById('cor');
	var dataInput = document.getElementById('data');
	var fornecedorSelect = document.getElementById('fornecedor');
	var tipoMaterialSelect = document.getElementById('tipoMaterial');
	var larguraInput = document.getElementById('largura');
	var comprimentoInput = document.getElementById('comprimento');
	var espessuraInput = document.getElementById('espessura');

	// Preenche os dados no modal (substitua os valores pelos valores reais)
	document.getElementById('modalCodigo').innerText = codigoInput.value;
	document.getElementById('modalNome').innerText = nomeInput.value;
	document.getElementById('modalPreco').innerText = precoInput.value;
	document.getElementById('modalCor').innerText = corInput.value;
	document.getElementById('modalData').innerText = dataInput.value;

	// Pega o texto do fornecedor e tipoMaterial selecionados
	var fornecedorNome = fornecedorSelect.options[fornecedorSelect.selectedIndex].text;
	var tipoMaterialNome = tipoMaterialSelect.options[tipoMaterialSelect.selectedIndex].text;

	document.getElementById('modalFornecedor').innerText = fornecedorNome;
	document.getElementById('modalTipoMaterial').innerText = tipoMaterialNome;

	document.getElementById('modalLargura').innerText = larguraInput.value;
	document.getElementById('modalComprimento').innerText = comprimentoInput.value;
	document.getElementById('modalEspessura').innerText = espessuraInput.value;

	// Exibe o modal
	let materialModal = new bootstrap.Modal(document.getElementById('materialModal'));
	materialModal.show();
}


function validarBusca() {
	var codigo = document.getElementById("nome").value;
	if (codigo.trim() === "") {
		alert("Por favor, insira um Nome.");
		return false;
	}
	return true;
}

function validarDataCompra(dataCompra) {
	var hoje = new Date();
	var compra = new Date(dataCompra);

	// Zera as horas, minutos e segundos de ambas as datas para comparar apenas as datas
	hoje.setHours(0, 0, 0, 0);
	compra.setHours(0, 0, 0, 0);

	// A data de aquisição deve ser anterior ao dia de hoje
	return compra < hoje;
}

function validarFormulario(event) {
	var botao = event.submitter ? event.submitter.value : null;
	var campos = [
		{ id: "nome", nome: "Nome" },
		{ id: "preco", nome: "Preço de Compra" },
		{ id: "cor", nome: "Cor" },
		{ id: "cliente", nome: "Cliente" },
		{ id: "fornecedor", nome: "Fornecedor" },
		{ id: "tipoMaterial", nome: "Tipo de Material" },
		{ id: "largura", nome: "Largura" },
		{ id: "comprimento", nome: "Comprimento" },
		{ id: "espessura", nome: "Espessura" },
		{ id: "data", nome: "Data de Compra" }

	];

	if (botao === "Cadastrar" || botao === "Alterar") {
		for (var i = 0; i < campos.length; i++) {
			var campo = document.getElementById(campos[i].id);
			if (campo) {
				// Verifica se o campo está vazio ou se é uma seleção inválida
				if (campo.value.trim() === "" ||
					((campo.id === "fornecedor" || campo.id === "tipoMaterial" || campo.id === "cliente") && campo.value === "0")) {
					alert("Por favor, preencha o campo " + campos[i].nome + ".");
					campo.focus(); // Coloca o foco no campo vazio
					event.preventDefault();
					return false;
				}

				// Validação para preço de compra
				if (campo.id === "preco" && parseFloat(campo.value.replace(/[^\d,]/g, '').replace(",", ".")) <= 0) {
					alert("O campo " + campos[i].nome + " deve ser maior que R$ 0,00.");
					campo.focus();
					event.preventDefault();
					return false;
				}
			}
		}

		// Validação da data de compra
		var dataCompra = document.getElementById("dataCompra").value;
		if (!validarDataCompra(dataCompra)) {
			alert("Data de Compra é inválida. Por favor, insira uma data no passado.");
			document.getElementById("dataCompra").focus();
			event.preventDefault();
			return false;
		}

	} else if (botao === "Excluir") {
		var codigo = document.getElementById("codigo").value.trim();
		if (codigo === "" || isNaN(codigo) || parseInt(codigo) <= 0) {
			alert("Por favor, preencha o campo de código corretamente.");
			document.getElementById("codigo").focus();
			event.preventDefault();
			return false;
		}
		// Confirmar a exclusão
		if (!confirm('Você realmente deseja excluir este registro? Esta ação não pode ser desfeita.')) {
			event.preventDefault();
			return false;
		}
	}
	return true;
}


function formatarMoeda(campo) {
	let valor = campo.value;

	valor = valor.replace(/[^\d]/g, '');
	valor = (valor / 100).toFixed(2) + '';
	valor = valor.replace(".", ",");

	valor = valor.replace(/\B(?=(\d{3})+(?!\d))/g, ".");
	campo.value = 'R$ ' + valor;

	if (campo.value.endsWith(',0')) {
		campo.value = campo.value.slice(0, -1) + '00';
	}
}

function formatarQuantidade(input) {
	// Remove qualquer caractere que não seja número ou ponto
	input.value = input.value.replace(/[^0-9.]/g, '');

	// Garante que só exista um ponto decimal
	if ((input.value.match(/\./g) || []).length > 1) {
		input.value = input.value.replace(/\.+$/, ''); // Remove o último ponto se houver mais de um
	}

	// Limita a dois números após o ponto decimal
	const partes = input.value.split('.');
	if (partes[1] && partes[1].length > 2) {
		partes[1] = partes[1].slice(0, 2);
		input.value = partes.join('.');
	}
}

function abrirModalFornecedor() {
	// Abre o modal
	var fornecedorModal = new bootstrap.Modal(document.getElementById('fornecedorModal'));
	fornecedorModal.show();
}

document.addEventListener('DOMContentLoaded', function() {
	const campoPrecoCompra = document.getElementById('preco');
	if (campoPrecoCompra) {
		formatarMoeda(campoPrecoCompra);
		campoPrecoCompra.addEventListener('input', function() {
			formatarMoeda(this);
		});
	}

});

document.addEventListener('DOMContentLoaded', function() {
	document.getElementById('fornecedor').addEventListener('change', function() {
		if (this.value === 'novo') {
			var modal = new bootstrap.Modal(document.getElementById('modalNovoFornecedor'));
			modal.show();
			this.value = '0'; // Resetar o valor do select
		}
	});
});


