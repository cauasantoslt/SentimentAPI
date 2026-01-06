document.addEventListener("DOMContentLoaded", () => {
  const textInput = document.getElementById("textInput");
  const analyzeBtn = document.getElementById("analyzeBtn");
  const sentimentText = document.getElementById("sentimentText");
  const sentimentEmoji = document.getElementById("sentimentEmoji");

  analyzeBtn.addEventListener("click", async () => {
    const text = textInput.value.trim();

    if (!text) {
  sentimentText.textContent = "Digite um texto para análise";
  sentimentEmoji.textContent = "⚠️";
  return;
}

if (text.length < 10) {
  sentimentText.textContent =
    "O texto precisa ter pelo menos 6 caracteres";
  sentimentEmoji.textContent = "⚠️";
  return;
}


    try {
      const response = await fetch("/sentiment", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ text }),
      });

      if (!response.ok) {
        throw new Error("Erro ao analisar sentimento");
      }

      const data = await response.json();

      const probabilityFormatted = data.probability.toFixed(2);
      sentimentText.textContent = `${capitalize(data.sentiment)} (${probabilityFormatted})`;

      switch (data.sentiment.toUpperCase()) {
        case "POSITIVO":
          sentimentEmoji.textContent = "😊";
          break;
        case "NEGATIVO":
          sentimentEmoji.textContent = "😠";
          break;
        case "NEUTRO":
          sentimentEmoji.textContent = "😐";
          break;
        default:
          sentimentEmoji.textContent = "🤔";
      }
    } catch (error) {
      console.error(error);
      sentimentText.textContent = "Erro ao conectar com a API";
      sentimentEmoji.textContent = "❌";
    }
  });
});

function capitalize(text) {
  return text.charAt(0).toUpperCase() + text.slice(1).toLowerCase();
}
