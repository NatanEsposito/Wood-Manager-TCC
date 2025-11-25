// Cliente.js

function buscarEndereco() {
	var cep = document.getElementById("CEP").value.replace(/\D/g, '');

	if (cep != "") {
		var validacep = /^[0-9]{8}$/;

		if (validacep.test(cep)) {
			// Exibe "carregando" enquanto busca o endereço
			document.getElementById("logradouro").value = "...";
			document.getElementById("bairro").value = "...";
			document.getElementById("localidade").value = "...";
			document.getElementById("UF").value = "...";
			// Faz a requisição AJAX para buscar o endereço
			$.ajax({
				url: 'https://viacep.com.br/ws/' + cep + '/json/',
				dataType: 'json',
				success: function(conteudo) {
					if (!("erro" in conteudo)) {
						preencherEndereco(conteudo);
					} else {
						alert("CEP não encontrado.");
						limparCamposEndereco();
					}
				},
				error: function() {
					alert("Erro ao buscar o CEP.");
				}
			});
		} else {
			alert("Formato de CEP inválido.");
		}
	} else {
		alert("CEP não informado.");
	}
}

function preencherEndereco(conteudo) {
	if (!("erro" in conteudo)) {
		document.getElementById("logradouro").value = conteudo.logradouro;
		document.getElementById("bairro").value = conteudo.bairro;
		document.getElementById("localidade").value = conteudo.localidade;
		document.getElementById("UF").value = conteudo.uf;
	} else {
		alert("CEP não encontrado.");
		limparCamposEndereco();
	}
}

function limparCamposEndereco() {
	document.getElementById("logradouro").value = "";
	document.getElementById("bairro").value = "";
	document.getElementById("localidade").value = "";
	document.getElementById("UF").value = "";
}

function editarCliente(codigo) {
	window.location.href = 'cliente?cmd=alterar&codigo=' + codigo;
}

function excluirCliente(codigo) {
	if (confirm("Tem certeza que deseja excluir este Cliente?")) {
		window.location.href = 'cliente?cmd=excluir&codigo=' + codigo;
	}
}

function abrirModalCliente(codigo) {
	// Validação do código
	if (codigo == 0 || codigo.trim() === "") {
		alert("O código do cliente não pode estar vazio.");
		return; // Interrompe a função se o código estiver vazio
	}

	// Seleciona os campos de entrada com os dados preenchidos no formulário
	var codigoInput = document.getElementById('codigo');
	var nomeInput = document.getElementById('nome');
	var telefoneInput = document.getElementById('telefone');
	var cepInput = document.getElementById('CEP');
	var logradouroInput = document.getElementById('logradouro');
	var bairroInput = document.getElementById('bairro');
	var ufInput = document.getElementById('UF');
	var localidadeInput = document.getElementById('localidade');
	var numeroInput = document.getElementById('numero');
	var complementoInput = document.getElementById('complemento');




	// Preenche os dados no modal
	document.getElementById('modalCodigo').innerText = codigoInput.value;
	document.getElementById('modalNome').innerText = nomeInput.value;
	document.getElementById('modalTelefone').innerText = telefoneInput.value;
	document.getElementById('modalCEP').innerText = cepInput.value;
	document.getElementById('modalLogradouro').innerText = logradouroInput.value;
	document.getElementById('modalBairro').innerText = bairroInput.value;
	document.getElementById('modalUF').innerText = ufInput.value;
	document.getElementById('modalLocalidade').innerText = localidadeInput.value;
	document.getElementById('modalNumero').innerText = numeroInput.value;
	document.getElementById('modalComplemento').innerText = complementoInput.value;


	// Exibe o modal
	let clienteModal = new bootstrap.Modal(document.getElementById('clienteModal'));
	clienteModal.show();
}


function exibirSpinner() {
	const spinner = document.getElementById("spinner");
	const botao = document.getElementById("botao");

	// Exibe o spinner e desativa o botão para evitar múltiplos envios
	spinner.classList.remove("d-none");
	botao.setAttribute("disabled", true);
}


function validarBusca() {
	var codigo = document.getElementById("nome").value;
	if (codigo.trim() === "") {
		alert("Por favor, insira um nome.");
		return false;
	}
	return true;
}

function validarFormulario(event) {
	var botao = event.submitter.value;
	var campos = [
		{ id: "nome", nome: "Nome" },
		{ id: "telefone", nome: "Telefone" },
		{ id: "numero", nome: "Número" }
	];

	if (botao === "Cadastrar" || botao === "Alterar") {
		for (var i = 0; i < campos.length; i++) {
			var campo = document.getElementById(campos[i].id);
			if (campo.value.trim() === "") {
				alert("Por favor, preencha o campo " + campos[i].nome + ".");
				campo.focus(); // Coloca o foco no campo vazio
				event.preventDefault();
				return false;
			}
		}

	} else if (botao === "Excluir") {
		var codigo = document.getElementById("codigo").value.trim();
		if (codigo === "" || isNaN(codigo) || parseInt(codigo) <= 0) {
			alert("Por favor, preencha o campo de código corretamente.");
			document.getElementById("codigo").focus(); // Coloca o foco no campo código
			event.preventDefault();
			return false;
		}
		// Confirmar a exclusão
		if (!confirm('Você realmente deseja excluir este registro? Esta ação não pode ser desfeita.')) {
			event.preventDefault(); // Cancela o envio do formulário se o usuário cancelar a exclusão
			return false;
		}
	}
	return true;
}

function validarTelefone(input) {
    // Remove todos os caracteres que não são dígitos
    let valor = input.value.replace(/\D/g, '');
    // Limita o valor a 11 dígitos
    if (valor.length > 11) {
        valor = valor.slice(0, 11);
    }
    // Atualiza o valor do input
    input.value = valor;
}

function formatarTelefone(telefone) {
    telefone = telefone.replace(/\D/g, ''); // Remove caracteres não numéricos
    telefone = telefone.slice(0, 11); // Limita a 11 dígitos

    if (telefone.length <= 2) {
        return telefone;
    } else if (telefone.length <= 6) {
        return '(' + telefone.slice(0, 2) + ') ' + telefone.slice(2);
    } else if (telefone.length <= 10) {
        return '(' + telefone.slice(0, 2) + ') ' + telefone.slice(2, 6) + '-' + telefone.slice(6);
    } else {
        return '(' + telefone.slice(0, 2) + ') ' + telefone.slice(2, 7) + '-' + telefone.slice(7);
    }
}

function aplicarMascaraTelefone() {
    var telefoneInput = document.getElementById('telefone');
    telefoneInput.addEventListener('input', function () {
        this.value = formatarTelefone(this.value);
    });

    // Formata o valor inicial se o campo já tiver um valor
    if (telefoneInput.value) {
        telefoneInput.value = formatarTelefone(telefoneInput.value);
    }
}

function redirectToWhatsApp() {
    // Obtém o valor do campo de telefone
    var phoneNumber = document.getElementById('telefone').value;

    if (phoneNumber) {
        // Construa a URL do WhatsApp
        var url = "https://web.whatsapp.com/send?phone=" + encodeURIComponent(phoneNumber);
        window.open(url, "_blank");
    } else {
        alert("Número de telefone não encontrado.");
    }
}

document.addEventListener('DOMContentLoaded', function() {
     aplicarMascaraTelefone();
});