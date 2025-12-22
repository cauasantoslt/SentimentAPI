<div align="left" style="position: relative;">
<img src="https://nocountry.tech/one.png" align="right" width="80" style="margin: auto;">
<img src="https://nocountry.tech/nocountry-logo.png" align="right" width="80" style="margin: 10px;">
<h1 style="padding-bottom: 2rem;" >SentimentAPI</h1>
<p align="left">
	<em>Transformando feedback bruto em inteligência de dados.</em>
</p>

</div>


## 📝 Introdução 
Análise de Sentimentos de Feedbacks a partir de textos analisados por um modelo treinado, o qual resulta na classificação imediata da opinião como Positiva ou Negativa. Utilizando técnicas de Processamento de Linguagem Natural (NLP) e um modelo otimizado, o sistema é capaz de interpretar feedbacks em Português e Inglês, garantindo alta performance e fácil integração via ONNX.

## 🎯 Objetivos
* Treinar modelo....
* Gerar arquivo Onnx....
* Desenvolver Back-End Spring-Boot
* Encapsular o modelo desenvolvido em ONNX no Back-End Java
* Criar API para consulta para analisar, das analises realizadas e erros (?)
* Persistência de dados em Banco de Dados (?)
* Front-End (?)
* Conteinerização do projeto em Docker
* Ambiente de avaliação em Cloud da OCI (?)

## 🏗️ Arquitetura
* JDK 21
* Python/Jupyter (?)
* Spring-Boot 4.0.0
* Onnx Runtime 1.23.2
* Outras Dependências: JPA / Flyway / Lombok / Swagger
* Banco de Dados: H2 (ambiente teste) / Postgres (ambiente produção)
* Docker
* Etc....

## 🛠️ Setup
* GitHub do Código Fonte: https://github.com/cauasantoslt/SentimentAPI
* Necessário utilizar o argumento para compilação (IntelliJ em Run Options, Add VM Options): --enable-native-access=ALL-UNNAMED

## ⚙️ Como Executar
Este projeto foi desenvolvido utilizando Jupyter Notebook. Para reproduzir o treinamento do modelo ou gerar novas versões do arquivo ONNX, siga os passos abaixo:

#### **Pré-requisitos**:

* Python 3.8 ou superior

* Pip (Gerenciador de pacotes)

#### **Passo a Passo**

1. Clone o repositório

```Bash

git clone https://github.com/seu-usuario/seu-repo.git
cd seu-repo/data-science
```

2. Instale as dependências

```Bash

pip install -r requirements.txt
```

3. Execute o Notebook

* Abra o terminal na pasta do projeto e digite:

```Bash

jupyter notebook
```

* Abra o arquivo AnaliseDeSentimentos.ipynb.

* Execute todas as células sequencialmente para baixar o dataset, treinar o modelo e exportar o arquivo .onnx.

*Nota sobre a Integração:* O artefato final deste processo é o arquivo sentiment_model_multilang.onnx. Este arquivo deve ser carregado pela aplicação Backend (Java) para realizar as predições em produção. O input esperado pelo modelo chama-se text_input.

## 🔗 Links para recursos
* JDK Microsoft: https://learn.microsoft.com/en-us/java/openjdk/download
* VC++ Microsoft: https://learn.microsoft.com/en-us/cpp/windows/latest-supported-vc-redist?view=msvc-170#latest-supported-redistributable-version 
* Onnx Runtime Documentação Java: https://onnxruntime.ai/docs/get-started/with-java

## 👥 Integrantes

Time responsável pelo desenvolvimento da solução:

| **Backend Developers** ☕ | **Data Scientists** 📊 |
| :--- | :--- |
| [Julio Clepf de Carvalho](https://www.linkedin.com/in/julio-clepf/) | [Bruno Bronsato](link) |
| [Keila De Oliveira Nunes](link) | [Carolini Rufino dos Santos](link) |
| [Laura Tenan](https://www.linkedin.com/in/laura-tenan/) | [Cauã Santos](https://linkedin.com/in/cauasantoslt) |
| [Pedro Henrique de Assis Ferreira](link) | [Jefferson Franca Ferreira](link) |
| [Rafael Silva Nunes](https://www.linkedin.com/in/rafaelsilvanunes) | |
| [Vinicius Silva](link) | |