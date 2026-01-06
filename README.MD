<div align="left" style="position: relative;">
<img src="https://nocountry.tech/one.png" align="right" width="80" style="margin: auto;">
<img src="https://nocountry.tech/nocountry-logo.png" align="right" width="80" style="margin: 10px;">
<h1 style="padding-bottom: 2rem;" >SentimentAPI</h1>
<p align="left">
	<em>Transformando feedback bruto em inteligência de dados.</em>
</p>

</div>


## 📝 Introdução 
A SentimentAPI é uma API REST desenvolvida em Java com Spring Boot que realiza análise de sentimentos em textos, classificando-os como Positivo, Negativo ou Neutro.

O sistema integra um modelo de Ciência de Dados treinado com técnicas de Processamento de Linguagem Natural (NLP), permitindo que aplicações consumam previsões de sentimento de forma automática por meio de requisições HTTP, com retorno em formato JSON.

O projeto foi desenvolvido como um MVP funcional, com foco na integração entre Back-end e Ciência de Dados, demonstrando uma solução aplicável a cenários reais de negócio.


## 🎯 Problema de Negócio

Empresas recebem diariamente um grande volume de comentários, avaliações e feedbacks de clientes em canais como redes sociais, pesquisas de satisfação e plataformas de atendimento.

* A análise manual dessas informações se torna inviável com o aumento do volume de dados.
* A SentimentAPI oferece uma solução automática para:
* Identificar rapidamente elogios e reclamações;
* Priorizar respostas a comentários negativos;
* Acompanhar a percepção da marca ao longo do tempo;
* Apoiar decisões nas áreas de atendimento, marketing e operações.

## ⚙️ Funcionalidades (MVP)
* Classificação de sentimento de textos (Positivo / Negativo / Neutro);
* API REST para consumo por aplicações externas;
* Retorno da previsão acompanhada de probabilidade;
* Validação de entrada e tratamento de erros;
* Suporte a textos em Português e Inglês.

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

O Back-end é responsável por gerenciar as requisições da API, validar os dados de entrada, integrar o modelo de Inteligência Artificial e retornar as previsões em formato JSON.

Ele atua como a camada intermediária entre as aplicações clientes e o modelo de Ciência de Dados, garantindo comunicação consistente, tratamento de erros e clareza nas respostas.

## 🛠️ Stack Tecnológico

| Recurso | Versão/Tipo | Descrição |
| :--- | :--- | :--- |
| **Java** | 17.x | Linguagem principal da aplicação |
| **Spring Boot** | 3.x | Framework para construção da API REST |
| **Maven** |  | Gerenciamento de dependências e build |
| **ONNX Runtime** | 1.23.2 | Execução do modelo de Machine Learning |
| **H2 / PostgreSQL** | Opicional | Persistência de dados (quando habilitada) |

## 🏗️ Arquitetura da Aplicação

O sistema foi estruturado seguindo o padrão **MVC**, organizado da seguinte forma:

### 1. Camada de Controladores
* **Função:** Receber requisições HTTP e retornar respostas JSON.
* **Componentes:** Componentes: Endpoints REST, como /sentiment.

### 2. Camada de Serviços
* **Função:**  Implementar a lógica de negócio.
* **Integração IA:** Responsável por carregar o modelo .onnx, preparar os dados de entrada e interpretar a saída do modelo (rótulos e probabilidades).

### 3. Camada de Dados
* **Função:** Persistência das requisições e previsões (quando habilitada).
* **ORM/Driver:** Spring Data JPA / JDBC.

## 🔌 Integrações e Segurança

Para garantir a robustez e a comunicação do sistema, foram implementados:

* **Integração com Modelo ONNX:**
    * Carregamento do modelo serializado;
    * Conversão da saída numérica do modelo para rótulos compreensíveis (Positivo, Neutro ou Negativo).

* **Validação de Entrada:**
    * Verificação da existência do texto e tamanho mínimo;
    * Retorno de mensagens de erro claras e amigáveis.

* **Consistência de Respostas:**
    * Padronização das respostas JSON, tanto para sucesso quanto para erro.

## 📡 Endpoints da API

* POST /sentiment

Classifica o sentimento de um texto enviado.



* Request: 


``` json
{

  "text": "O atendimento foi excelente e muito rápido."

}

```

* Response: 

``` json
{

  "previsao": "Positivo",

  "probabilidade": 0.87

}
```

## ⚙️ Como Executar
Este projeto foi desenvolvido utilizando Jupyter Notebook. Para reproduzir o treinamento do modelo ou gerar novas versões do arquivo ONNX, siga os passos abaixo:

#### **Pré-requisitos**:

* Python 3.8 ou superior

* Pip (Gerenciador de pacotes)

#### **Passo a Passo**

1. Clone o repositório

```Bash
git clone https://github.com/cauasantoslt/SentimentAPI.git

cd SentimentAPI
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

### Compilar o projeto

```Bash
./mvnw clean install
```

### Executar a aplicação
```Bash
./mvnw spring-boot:run
```

A aplicação ficará disponível em:http://localhost:8080

*Nota sobre a Integração:* O artefato final deste processo é o arquivo sentiment_model_multilang.onnx. Este arquivo deve ser carregado pela aplicação Backend (Java) para realizar as predições em produção. O input esperado pelo modelo chama-se text_input.

---

## 📦 Artefatos de Deploy

* application.jar: artefato executável da aplicação.
* application.properties: arquivo de configuração com variáveis de ambiente.
* Postman / cURL: ferramentas recomendadas para teste dos endpoints da API.

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
| [Rafael Silva Nunes](https://www.linkedin.com/in/rafaelsilvanunes) | 