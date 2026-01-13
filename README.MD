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
* **Classificação de Sentimento:** Identifica se um texto é Positivo, Negativo ou Neutro.
* **Top Features:** Destaca as palavras-chave que mais influenciaram na decisão do modelo.
* **Análise em Lote (Batch):** Processamento de múltiplos comentários via upload de arquivo CSV.
* **Histórico de Análises:** Registro persistente de todas as análises realizadas.
* **Dashboard de Estatísticas:** Visão geral da distribuição dos sentimentos processados.
* **Interface Web:** Front-end amigável para interação direta com a API.
* **API REST:** Endpoints documentados para integração com outros sistemas.
* **Suporte Trilingue:** Processamento de textos em Português, Inglês e Espanhol.

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
| **Java** | 21.x | Linguagem principal da aplicação |
| **Spring Boot** | 4.x | Framework para construção da API REST |
| **Maven** | 3.9 | Gerenciamento de dependências e build |
| **ONNX Runtime** | 1.19.2 | Execução do modelo de Machine Learning |
| **Nginx** | 1.25+ | Proxy Reverso para servir o Frontend e rotear requisições |
| **Docker Compose** | 3.x | Orquestração dos containers (DB, API, Frontend) |
| **PostgreSQL** | 15-Alpine | Banco de dados para persistência de dados |
| **Flyway** | Latest | Gerenciamento de migrações de banco de dados |
| **HTML/CSS/JS** | Vanilla | Interface Web leve e responsiva |

## 🏗️ Arquitetura da Aplicação

O sistema foi estruturado seguindo o padrão **MVC**, organizado da seguinte forma:

### 1. Camada de Controladores (API)
* **Função:** Receber requisições HTTP e retornar respostas JSON.
* **Componentes:** Endpoints REST como `/sentiment`, `/sentiment/batch`.

### 2. Camada de Serviços
* **Função:**  Implementar a lógica de negócio.
* **Integração IA:** Responsável por carregar o modelo .onnx, preparar os dados de entrada e interpretar a saída do modelo (rótulos, probabilidades e top features).

### 3. Camada de Dados
* **Função:** Persistência das requisições e previsões.
* **Tecnologia:** PostgreSQL via Spring Data JPA.

### 4. Camada de Frontend
* **Função:** Interface para usuário final.
* **Hospedagem:** Servidor Nginx em container dedicado.

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
    * Validação de arquivos CSV para processamento em lote.

## 📡 Endpoints da API

Abaixo os principais endpoints da aplicação:

### 1. Analisar Texto Único
* **URL:** `/sentiment`
* **Método:** `POST`
* **Corpo (JSON):**
```json
{
  "text": "O atendimento foi excelente e muito rápido."
}
```
* **Resposta (JSON):**
```json
{
  "label": "Positivo",
  "probability": 0.87,
  "topFeatures": ["excelente", "rápido"]
}
```

### 2. Analisar Lote (CSV)
* **URL:** `/sentiment/batch`
* **Método:** `POST`
* **Consumes:** `multipart/form-data`
* **Parâmetro:** `file` (Arquivo .csv - uma frase por linha)
* **Resposta:** Lista JSON com as análises de cada linha.

### 3. Consultar Histórico
* **URL:** `/sentiment/history`
* **Método:** `GET`
* **Parâmetros:** `page`, `size` (Paginação)
* **Resposta:** Objeto Pageable com histórico de análises.

### 4. Obter Estatísticas
* **URL:** `/sentiment/stats`
* **Método:** `GET`
* **Resposta:** Contagem total, percentuais de positivos/negativos/neutros.

## ⚙️ Como Executar
Este projeto foi desenvolvido utilizando Jupyter Notebook. Para reproduzir o treinamento do modelo ou gerar novas versões do arquivo ONNX, siga os passos abaixo:

#### **Pré-requisitos**:
* Python 3.8+ (para treinar modelo, opcional se já tiver o .onnx)
* JDK 21
* Maven
* PostgreSQL rodando localmente

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
A API ficará disponível em: http://localhost

---

## 📦 Artefatos de Deploy

* application.jar: artefato executável da aplicação.
* application.properties: arquivo de configuração com variáveis de ambiente.
* docker-compose.yml: orquestração dos serviços.

## 🔗 Links para recursos
* JDK Microsoft: https://learn.microsoft.com/en-us/java/openjdk/download
* VC++ Microsoft: https://learn.microsoft.com/en-us/cpp/windows/latest-supported-vc-redist?view=msvc-170#latest-supported-redistributable-version 
* Onnx Runtime Documentação Java: https://onnxruntime.ai/docs/get-started/with-java

## 👥 Integrantes

Time responsável pelo desenvolvimento da solução:

| **Backend Developers** ☕ | **Data Scientists** 📊 |
| :--- | :--- |
| [Julio Clepf de Carvalho](https://www.linkedin.com/in/julio-clepf/) | [Carolini Rufino dos Santos](https://www.linkedin.com/in/carolinirufino) |
| [Laura Tenan](https://www.linkedin.com/in/laura-tenan/) | [Cauã Santos](https://linkedin.com/in/cauasantoslt) | 
| [Keila De Oliveira Nunes](https://www.linkedin.com/in/keilanunes-dev) | [Jefferson Franca Ferreira](https://www.linkedin.com/in/jefferson-ferreira-ds/) |
| [Pedro Henrique de Assis Ferreira](https://www.linkedin.com/in/phaferreira/) | 
| [Rafael Silva Nunes](https://www.linkedin.com/in/rafaelsilvanunes) | 
