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

# 🧠 Recursos de Data Science

A camada de inteligência do projeto foi desenvolvida utilizando **Python** e bibliotecas de **Machine Learning** focadas em Processamento de Linguagem Natural (NLP). Abaixo estão listados os recursos, algoritmos e técnicas empregados para garantir a precisão do modelo híbrido (Finanças + Atendimento).

## 🛠️ Tecnologias e Bibliotecas

| Recurso | Versão/Tipo | Descrição |
| :--- | :--- | :--- |
| **Linguagem** | Python 3.x | Linguagem base para desenvolvimento do modelo e scripts de automação. |
| **Scikit-learn** | Lib (Python) | Biblioteca principal para criação do Pipeline, Algoritmo de Classificação e Métricas. |
| **Pandas** | Lib (Python) | Manipulação e estruturação dos datasets, limpeza de dados e injeção da "Vacina". |
| **Numpy** | Lib (Python) | Processamento numérico de alta performance para vetores e matrizes. |
| **Skl2onnx** | Lib (Python) | Conversor responsável por transformar o modelo Python em formato **.onnx**. |
| **ONNX** | Standard | *Open Neural Network Exchange*. Formato utilizado para exportar o modelo treinado para o Backend (Java). |

## ⚙️ Arquitetura do Modelo

O modelo de Análise de Sentimentos utiliza uma arquitetura de aprendizado supervisionado composta por:

### 1. Pré-Processamento e Vetorização
* **TF-IDF Vectorizer:** Transforma texto em números baseando-se na frequência e relevância das palavras.
* **N-Grams (1, 2):** Configurado para entender palavras isoladas ("lucro") e pares de palavras ("não lucro", "lucro líquido") para captar contexto.
* **Multilanguage Support:** O modelo foi treinado para processar entradas tanto em **Português (PT-BR)** quanto em **Inglês (EN)** simultaneamente.

### 2. Algoritmo Classificador
* **Logistic Regression:** Escolhido por sua eficiência em classificação de texto e probabilidade calibrada.
    * *Solver:* `lbfgs`
    * *Max Iter:* 500
    * *Class Weight:* Ajustado via Oversampling manual.

## 🧪 Engenharia de Dados e Técnicas Avançadas

Para garantir a robustez do modelo em cenários reais, foram aplicadas as seguintes técnicas:

* **Balanceamento de Classes (Oversampling):**
    * Técnica utilizada para evitar o viés da IA em direção à classe neutra.
    * Duplicação estratégica de exemplos das classes minoritárias (Positivo e Negativo) para equilibrar o aprendizado.

* **Calibração Fina ("Vacina de Dados"):**
    * Injeção manual de dados sintéticos (Data Augmentation) para corrigir erros específicos de domínio.
    * Correção forçada para termos críticos como *"Falência"* (Negativo) e *"Incrível"* (Positivo).
    * Inclusão de gírias e vocabulário informal de Customer Service ("Lixo", "Maravilhoso").

## 📂 Artefatos Gerados

* **`sentiment_model_multilang.onnx`**: Arquivo binário final contendo o modelo treinado e o vetorizador, pronto para ser consumido pela aplicação Java sem necessidade de Python instalado no servidor.

--- 

# 💻 Recursos de Back-end

[Inserir breve descrição sobre o papel do Back-end no projeto. Ex: Responsável por gerenciar as requisições da API, realizar a comunicação com o banco de dados e executar o modelo de IA através do ONNX Runtime.]

## 🛠️ Stack Tecnológico

| Recurso | Versão/Tipo | Descrição |
| :--- | :--- | :--- |
| **[Linguagem]** | [Inserir Versão] | [Breve descrição do uso] |
| **[Framework]** | [Inserir Nome] | [Breve descrição do uso] |
| **[Banco de Dados]** | [Inserir Nome] | [Breve descrição do uso] |
| **[Build Tool]** | [Inserir Nome] | [Breve descrição do uso, ex: Maven/Gradle] |
| **[IA Runtime]** | [Inserir Nome] | [Biblioteca usada para carregar o ONNX] |

## 🏗️ Arquitetura da Aplicação

O sistema foi estruturado seguindo o padrão **[Inserir Padrão, ex: MVC / Clean Arch / Microsserviços]**, organizado da seguinte forma:

### 1. Camada de [Nome da Camada 1, ex: Controladores]
* **Função:** [Descrever responsabilidade]
* **Componentes:** [Listar principais endpoints ou classes]

### 2. Camada de [Nome da Camada 2, ex: Serviços / Business Logic]
* **Função:** [Descrever responsabilidade]
* **Integração IA:** [Descrever como essa camada chama o modelo .onnx]

### 3. Camada de [Nome da Camada 3, ex: Persistência / Dados]
* **Função:** [Descrever responsabilidade]
* **ORM/Driver:** [Citar ferramenta usada para falar com o banco]

## 🔌 Integrações e Segurança

Para garantir a robustez e a comunicação do sistema, foram implementados:

* **[Recurso de Segurança, ex: Autenticação/JWT]:**
    * [Detalhe sobre como funciona]
    * [Detalhe sobre a proteção]

* **[Integração com Modelo ONNX]:**
    * [Detalhe sobre o carregamento do arquivo]
    * [Detalhe sobre o tratamento da saída (0, 1, 2)]

* **[Outra Integração, ex: Swagger/Docker]:**
    * [Detalhe sobre o recurso]

## 📦 Artefatos de Deploy

* **`[Nome do Arquivo Principal, ex: application.jar]`**: [Descrição do executável]
* **`[Arquivo de Configuração, ex: application.properties]`**: [Descrição das variáveis de ambiente]
* **`[Documentação, ex: Swagger UI]`**: [Link ou descrição de onde acessar a doc da API]


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
| [Julio Clepf de Carvalho](https://www.linkedin.com/in/julio-clepf/) | [Carolini Rufino dos Santos](https://www.linkedin.com/in/carolinirufino) |
 [Laura Tenan](https://www.linkedin.com/in/laura-tenan/) | [Cauã Santos](https://linkedin.com/in/cauasantoslt) | 
| [Keila De Oliveira Nunes](https://www.linkedin.com/in/keilanunes-dev) | [Jefferson Franca Ferreira](link) |
| [Pedro Henrique de Assis Ferreira](https://www.linkedin.com/in/phaferreira/) | 
| [Rafael Silva Nunes](https://www.linkedin.com/in/rafaelsilvanunes) | |
| [Vinicius Silva](link) | |