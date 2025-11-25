// tipoMaterial.js

function editarTipoMaterial(codigo) {
    window.location.href = 'tipomaterial?cmd=alterar&codigo=' + codigo;
}

function excluirTipoMaterial(codigo) {
    if (confirm("Tem certeza que deseja excluir este Tipo de Material?")) {
        window.location.href = 'tipomaterial?cmd=excluir&codigo=' + codigo;
    }
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
        { id: "nome", nome: "Nome" }
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


