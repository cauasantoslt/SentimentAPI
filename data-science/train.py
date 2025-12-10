import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.linear_model import LogisticRegression
from sklearn.pipeline import Pipeline
from skl2onnx import convert_sklearn
from skl2onnx.common.data_types import StringTensorType
import onnxmltools

# 1. Dados Dummy (apenas para testar o fluxo)
# Criamos alguns exemplos falsos só para o modelo aprender algo básico
data = {
    'text': ['gostei muito', 'péssimo serviço', 'ótimo atendimento', 'horrível', 'muito bom'],
    'label': [1, 0, 1, 0, 1]  # 1 = Positivo, 0 = Negativo
}
df = pd.DataFrame(data)

# 2. Pipeline Simples (Vetorização + Modelo)
# Transforma texto em números (TF-IDF) e treina uma Regressão Logística
pipeline = Pipeline([
    ('tfidf', TfidfVectorizer()),
    ('clf', LogisticRegression())
])

print("Treinando modelo dummy...")
pipeline.fit(df['text'], df['label'])

# 3. Conversão para ONNX
# Definimos que a entrada é uma string (texto)
initial_type = [('text_input', StringTensorType([None, 1]))]
onnx_model = convert_sklearn(pipeline, initial_types=initial_type)

# 4. Salvar o arquivo
output_file = "sentiment_model.onnx"
with open(output_file, "wb") as f:
    f.write(onnx_model.SerializeToString())

print(f"Sucesso! Modelo salvo como {output_file}")