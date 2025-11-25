USE master
--DROP DATABASE WoodManager
CREATE DATABASE WoodManager
GO
USE WoodManager 
GO
CREATE TABLE fornecedor(
codigo			INT				NOT NULL IDENTITY(1,1),
nome			VARCHAR(100)	NOT NULL,
telefone		CHAR(12)		NOT NULL,
PRIMARY KEY (codigo)
)
GO
CREATE TABLE cliente(
codigo			INT				NOT NULL IDENTITY (1,1),
nome			VARCHAR(100)	NOT NULL,
telefone		CHAR(11)		NOT NULL,
CEP		        CHAR(09)	    NOT NULL,
logradouro      VARCHAR(150)	NOT NULL, 
bairro          VARCHAR(150)	NOT NULL,
localidade      VARCHAR(100)	NOT NULL,
UF              CHAR(02)	    NOT NULL,
complemento     VARCHAR(100)	NULL,
numero          VARCHAR (20)	NOT NULL
PRIMARY KEY (codigo)
)
GO 
CREATE TABLE tipoMaterial(
codigo			INT				NOT NULL IDENTITY(1,1),
nome			VARCHAR(100)	NOT NULL
PRIMARY KEY (codigo)
)
GO
CREATE TABLE material(
codigo			INT				NOT NULL IDENTITY(1,1),
nome			VARCHAR(200)	NOT NULL,
preco           DECIMAL (10,2)  NOT NULL,
cor             VARCHAR(30)  	NOT NULL,
data			DATE			NOT NULL,
largura	        VARCHAR(10)		NOT NULL,
comprimento  	VARCHAR(10)		NOT NULL,
espessura       VARCHAR(10)		NOT NULL,
fornecedor      INT				NOT NULL,
tipoMaterial    INT				NOT NULL,
cliente         INT				NOT NULL
PRIMARY KEY (codigo)
FOREIGN KEY (fornecedor) REFERENCES fornecedor (codigo),
FOREIGN KEY (tipoMaterial) REFERENCES tipoMaterial (codigo),
FOREIGN KEY (cliente) REFERENCES cliente (codigo)
);
GO
CREATE TABLE sobraMaterial(
codigo			INT				NOT NULL IDENTITY(1,1),
estoque			VARCHAR(200)	NOT NULL,
data			DATE			NOT NULL,
largura	        VARCHAR(10)		NOT NULL,
comprimento  	VARCHAR(10)		NOT NULL,
espessura       VARCHAR(10)		NOT NULL,
material        INT				NOT NULL
PRIMARY KEY (codigo),
FOREIGN KEY (material) REFERENCES material (codigo)
)
GO
-- Inicio dos Inserts
INSERT INTO fornecedor (nome, telefone) VALUES
('Madeira Brasmad Ltda.','1234567890'),
('Leo Madeiras', '1195678901'),
('MadriraNit', '11956789012');
GO
INSERT INTO cliente (nome, telefone, CEP, logradouro, bairro, localidade, UF, complemento, numero) VALUES
('Fabio de Lima', '11956432345', '08120300', 'Rua Nogueira Viotti', 'Itaim Paulista', 'São Paulo', 'SP', NULL, '156B'),
('Manoel Gonçalves Costa', '11983774561',  '09154900', 'Estrada de Ferro Santos Jundiaí', 'Vila Elclor', 'Santo André', 'SP', NULL, '45'),
('Astolfo Melo de Cunha', '11984823716',  '01531000', 'Rua da Glória', 'Liberdade', 'São Paulo', 'SP', NULL, '234'),
('Gabriela Bittencourt', '11965428657',  '01002000', 'Rua Boa Vista', 'Centro', 'São Paulo', 'SP', NULL, '90');
GO
INSERT INTO tipoMaterial (nome) VALUES
('MDF'),
('Aglomerado');
GO
INSERT INTO Material (nome, preco, cor, data, largura,comprimento,espessura, fornecedor, tipoMaterial,cliente) VALUES 
('Madeira Cedro - Eduardo', 150.75, 'Marrom', '2024-10-10', '2000','1500','15', 1, 1, 1),
('Madeira Mogno - Francico', 180.50, 'Vermelho Escuro', '2024-10-12', '1800','1400','18', 2, 2,2),
('Madeira Pinus - Márcia', 120.00, 'Amarelo Claro', '2024-10-15', '1850','1500','16', 3, 1,3),
('Madeira Carvalho - Eduardo', 220.30, 'Bege', '2024-10-20', '2000','1500','15', 1, 2,4),
('Madeira Eucalipto - Cleide', 140.99, 'Cinza', '2024-10-25', '2000','1500','15', 2, 1,1);
GO
INSERT INTO sobraMaterial (estoque, data, largura, comprimento, espessura, material)
VALUES 
('Estoque A', '2024-01-15', '100', '200', '15', 1),
('Estoque B', '2024-02-20', '300', '250', '16', 2),
('Estoque C', '2024-03-10', '120', '180', '18', 3),
('Estoque D', '2024-04-05', '200', '300', '15', 4),
('Estoque E', '2024-05-25', '100', '150', '18', 5);
GO
-- Fim dos Inserts

-- Inicio das Procedures
CREATE PROCEDURE sp_iud_fornecedor
    @acao CHAR(1),
    @codigo INT NULL,
    @nome VARCHAR(100),
    @telefone CHAR(12),
    @saida VARCHAR(100) OUTPUT
AS
BEGIN
    IF (@acao = 'I')
    BEGIN
        IF EXISTS (SELECT 1 FROM fornecedor WHERE codigo = @codigo)
        BEGIN
            RAISERROR('Código já existe. Não é possível inserir o fornecedor.', 16, 1)
            RETURN
        END
        INSERT INTO fornecedor (nome, telefone)
        VALUES  (@nome, @telefone)
        SET @saida = 'Fornecedor inserido com sucesso'
    END
    ELSE IF (@acao = 'U')
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM fornecedor WHERE codigo = @codigo)
        BEGIN
            RAISERROR('Código não existe. Não é possível atualizar o fornecedor.', 16, 1)
            RETURN
        END

        UPDATE fornecedor
        SET nome = @nome, telefone = @telefone
        WHERE codigo = @codigo
        SET @saida = 'Fornecedor alterado com sucesso'
    END
    ELSE IF (@acao = 'D')
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM fornecedor WHERE codigo = @codigo)
        BEGIN
            RAISERROR('Código não existe. Não é possível excluir o fornecedor.', 16, 1)
            RETURN
        END

        DELETE FROM fornecedor WHERE codigo = @codigo
        SET @saida = 'Fornecedor excluído com sucesso'
    END
    ELSE
    BEGIN
        RAISERROR('Operação inválida', 16, 1)
        RETURN
    END
END
GO
CREATE PROCEDURE sp_iud_tipoMaterial
    @acao CHAR(1),
    @codigo INT NULL,
    @nome VARCHAR(100),
    @saida VARCHAR(100) OUTPUT
AS
BEGIN
    IF (@acao = 'I')
    BEGIN
        IF EXISTS (SELECT 1 FROM tipoMaterial WHERE codigo = @codigo)
        BEGIN
            RAISERROR('Código já existe. Não é possível inserir o tipo de material.', 16, 1)
            RETURN
        END
        INSERT INTO tipoMaterial (nome)
        VALUES (@nome)
        SET @saida = 'Tipo de material inserido com sucesso'
    END
    ELSE IF (@acao = 'U')
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM tipoMaterial WHERE codigo = @codigo)
        BEGIN
            RAISERROR('Código não existe. Não é possível atualizar o tipo de material.', 16, 1)
            RETURN
        END

        UPDATE tipoMaterial
        SET nome = @nome
        WHERE codigo = @codigo
        SET @saida = 'Tipo de material alterado com sucesso'
    END
    ELSE IF (@acao = 'D')
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM tipoMaterial WHERE codigo = @codigo)
        BEGIN
            RAISERROR('Código não existe. Não é possível excluir o tipo de material.', 16, 1)
            RETURN
        END

        DELETE FROM tipoMaterial WHERE codigo = @codigo
        SET @saida = 'Tipo de material excluído com sucesso'
    END
    ELSE
    BEGIN
        RAISERROR('Operação inválida', 16, 1)
        RETURN
    END
END
GO
CREATE PROCEDURE sp_iud_material
    @acao CHAR(1),
    @codigo INT = NULL,
    @nome VARCHAR(200),
    @preco DECIMAL(10,2),
    @cor VARCHAR(30),
    @data DATE,
    @largura VARCHAR(20),
	@comprimento VARCHAR(20),
	@espessura VARCHAR(20),
    @fornecedor INT,
    @tipoMaterial INT,
	@cliente INT,
    @saida VARCHAR(100) OUTPUT
AS
BEGIN
    IF (@acao = 'I')
    BEGIN
        IF EXISTS (SELECT 1 FROM Material WHERE codigo = @codigo)
        BEGIN
            RAISERROR('Código já existe. Não é possível inserir o material.', 16, 1)
            RETURN
        END
        INSERT INTO Material (nome, preco, cor, data, largura, comprimento, espessura, fornecedor, tipoMaterial,cliente)
        VALUES (@nome, @preco, @cor, GETDATE(), @largura,@comprimento,@espessura, @fornecedor, @tipoMaterial,@cliente)
        SET @saida = 'Material inserido com sucesso'
    END
    ELSE IF (@acao = 'U')
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM Material WHERE codigo = @codigo)
        BEGIN
            RAISERROR('Código não existe. Não é possível atualizar o material.', 16, 1)
            RETURN
        END
        UPDATE Material
        SET nome = @nome, preco = @preco, cor = @cor, data = @data, largura = @largura,
            comprimento = @comprimento, espessura = @espessura, fornecedor = @fornecedor, 
			tipoMaterial = @tipoMaterial, cliente = @cliente
        WHERE codigo = @codigo
        SET @saida = 'Material atualizado com sucesso'
    END
    ELSE IF (@acao = 'D')
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM Material WHERE codigo = @codigo)
        BEGIN
            RAISERROR('Código não existe. Não é possível excluir o material.', 16, 1)
            RETURN
        END
        DELETE FROM Material WHERE codigo = @codigo
        SET @saida = 'Material excluído com sucesso'
    END
    ELSE
    BEGIN
        RAISERROR('Operação inválida', 16, 1)
        RETURN
    END
END
GO
CREATE PROCEDURE sp_iud_sobraMaterial
    @acao CHAR(1),
    @codigo INT = NULL,
    @estoque VARCHAR(200),
    @data DATE,
    @largura VARCHAR(10),
    @comprimento VARCHAR(10),
    @espessura VARCHAR(10),
    @material INT,
    @saida VARCHAR(100) OUTPUT
AS
BEGIN
    IF (@acao = 'I')
    BEGIN
        -- Verifica se o material existe antes de inserir a sobra
        IF NOT EXISTS (SELECT 1 FROM Material WHERE codigo = @material)
        BEGIN
            RAISERROR('Material especificado não existe. Não é possível inserir a sobra de material.', 16, 1)
            RETURN
        END

        INSERT INTO sobraMaterial (estoque, data, largura, comprimento, espessura, material)
        VALUES (@estoque, @data, @largura, @comprimento, @espessura, @material)
        SET @saida = 'Sobra de material inserida com sucesso'
    END
    ELSE IF (@acao = 'U')
    BEGIN
        -- Verifica se a sobra de material existe antes de atualizar
        IF NOT EXISTS (SELECT 1 FROM sobraMaterial WHERE codigo = @codigo)
        BEGIN
            RAISERROR('Código não existe. Não é possível atualizar a sobra de material.', 16, 1)
            RETURN
        END

        UPDATE sobraMaterial
        SET estoque = @estoque, data = @data, largura = @largura,
            comprimento = @comprimento, espessura = @espessura, material = @material
        WHERE codigo = @codigo
        SET @saida = 'Sobra de material atualizada com sucesso'
    END
    ELSE IF (@acao = 'D')
    BEGIN
        -- Verifica se a sobra de material existe antes de excluir
        IF NOT EXISTS (SELECT 1 FROM sobraMaterial WHERE codigo = @codigo)
        BEGIN
            RAISERROR('Código não existe. Não é possível excluir a sobra de material.', 16, 1)
            RETURN
        END

        DELETE FROM sobraMaterial WHERE codigo = @codigo
        SET @saida = 'Sobra de material excluída com sucesso'
    END
    ELSE
    BEGIN
        RAISERROR('Operação inválida', 16, 1)
        RETURN
    END
END
GO
CREATE PROCEDURE sp_iud_cliente(
    @acao CHAR(1), 
    @codigocliente INT NULL, 
    @nome VARCHAR(100), 
    @telefone CHAR(12), 
  	@CEP       CHAR(09),
	@logradouro VARCHAR(150), 
	@bairro VARCHAR(150), 
	@localidade VARCHAR(100), 
	@UF CHAR(02), 
	@complemento VARCHAR(100), 
	@numero VARCHAR(20), 
    @saida VARCHAR(200) OUTPUT
)
AS
BEGIN
    IF (@acao = 'I')
    BEGIN
        IF EXISTS (SELECT 1 FROM cliente WHERE codigo = @codigocliente)
        BEGIN
            RAISERROR('Código já existe. Não é possível inserir o cliente.', 16, 1)
            RETURN
        END

        INSERT INTO cliente (nome, telefone, CEP,logradouro,bairro,localidade,UF,complemento,numero)
        VALUES (@nome, @telefone,@CEP,@logradouro,@bairro,@localidade,@UF,@complemento,@numero)
        SET @saida = 'Cliente inserido com sucesso'
    END
    ELSE IF (@acao = 'U')
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM cliente WHERE codigo = @codigocliente)
        BEGIN
            RAISERROR('Código não existe. Não é possível atualizar o cliente.', 16, 1)
            RETURN
        END

        UPDATE cliente
        SET nome = @nome,
            telefone = @telefone,
			cep = @CEP,
			logradouro=@logradouro,
			bairro=@bairro,
			localidade=@localidade,
			UF=@UF,
			complemento=@complemento,
			numero=@numero
        WHERE codigo = @codigocliente    
        SET @saida = 'Cliente atualizado com sucesso'
    END
    ELSE IF (@acao = 'D')
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM cliente WHERE codigo = @codigocliente)
        BEGIN
            RAISERROR('Código não existe. Não é possível excluir o cliente.', 16, 1)
            RETURN
        END
        DELETE FROM cliente
        WHERE codigo = @codigocliente
        SET @saida = 'Cliente excluído com sucesso'
    END
    ELSE
    BEGIN
        RAISERROR('Operação inválida', 16, 1)
        RETURN
    END
END
GO
-- Fim das Procedures

-- Inicio das View
CREATE VIEW v_fornecedor AS
SELECT
    codigo,
    nome,
    telefone
FROM
    fornecedor;
GO
CREATE VIEW v_tipoMaterial AS
SELECT
    codigo,
    nome
FROM
    tipoMaterial;
GO
CREATE VIEW v_material AS
SELECT 
    m.codigo,
    m.nome AS nome,
    m.preco,
    m.cor,
    m.data,
    m.largura,
	m.comprimento,
	m.espessura,
	f.codigo AS codigoFornecedor,
    f.nome AS nomeFornecedor,
    t.codigo AS codigoTipoMaterial,
	t.nome AS nomeTipoMaterial,
	c.codigo AS codigoCliente,
	c.nome AS nomeCliente
FROM 
    Material m
JOIN 
    fornecedor f ON m.fornecedor = f.codigo
JOIN 
    tipoMaterial t ON m.tipoMaterial = t.codigo
JOIN 
    cliente c ON m.cliente = c.codigo;
GO
CREATE VIEW v_sobraMaterial AS
SELECT 
    s.codigo AS codigo,
    s.estoque,
    s.data,
    s.largura,
    s.comprimento,
    s.espessura,
    s.material AS codigoMaterial,
    m.nome AS nomeMaterial
FROM 
    sobraMaterial s
JOIN 
    Material m ON s.material = m.codigo;
GO
CREATE VIEW v_cliente AS
SELECT
    codigo,
    nome,
    telefone,
    CEP,
    logradouro,
    bairro,
    localidade,
    UF,
    complemento,
    numero
FROM cliente;
GO
-- Fim das View