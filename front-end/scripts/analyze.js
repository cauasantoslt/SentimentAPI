document.addEventListener("DOMContentLoaded", () => {
  // --- ELEMENTS ---
  // Toggles
  const btnModeText = document.getElementById("btnModeText");
  const btnModeFile = document.getElementById("btnModeFile");

  // Sections
  const sectionTextMode = document.getElementById("sectionTextMode");
  const sectionFileMode = document.getElementById("sectionFileMode");

  // Text Mode Inputs
  const textInput = document.getElementById("textInput");
  const analyzeBtn = document.getElementById("analyzeBtn");

  // File Mode Inputs
  const fileInput = document.getElementById("fileInput");
  const fileNameDisplay = document.getElementById("fileNameDisplay");
  const skipHeaderCheck = document.getElementById("skipHeaderCheck");
  const analyzeBatchBtn = document.getElementById("analyzeBatchBtn");

  // Results
  const resultSingle = document.getElementById("resultSingle");
  const resultBatch = document.getElementById("resultBatch");

  const sentimentText = document.getElementById("sentimentText");
  const sentimentEmoji = document.getElementById("sentimentEmoji");
  const topFeaturesContainer = document.getElementById("topFeaturesContainer");
  const topFeaturesList = document.getElementById("topFeaturesList");

  const batchStatusText = document.getElementById("batchStatusText");
  const batchOutput = document.getElementById("batchOutput");

  // --- MODE TOGGLING ---
  btnModeText.addEventListener("click", () => {
    switchMode("text");
  });

  btnModeFile.addEventListener("click", () => {
    switchMode("file");
  });

  function switchMode(mode) {
    if (mode === "text") {
      sectionTextMode.style.display = "block";
      sectionFileMode.style.display = "none";
      resultSingle.style.removeProperty("display");
      resultBatch.style.display = "none";

      btnModeText.style.backgroundColor = "var(--cor-terciaria)";
      btnModeText.style.color = "var(--cor-primaria)";
      btnModeFile.style.backgroundColor = "#ddd";
      btnModeFile.style.color = "#333";
    } else {
      sectionTextMode.style.display = "none";
      sectionFileMode.style.display = "flex";
      resultSingle.style.display = "none";
      resultBatch.style.display = "block";

      btnModeFile.style.backgroundColor = "var(--cor-terciaria)";
      btnModeFile.style.color = "var(--cor-primaria)";
      btnModeText.style.backgroundColor = "#ddd";
      btnModeText.style.color = "#333";
    }
  }

  // --- FILE INPUT HANDLING ---
  fileInput.addEventListener("change", (e) => {
    if (e.target.files.length > 0) {
      fileNameDisplay.textContent = e.target.files[0].name;
    } else {
      fileNameDisplay.textContent = "";
    }
  });

  // --- TEXT ANALYSIS ---
  analyzeBtn.addEventListener("click", performTextAnalysis);

  textInput.addEventListener("keydown", (e) => {
    // Enter without Shift triggers analysis
    if (e.key === "Enter" && !e.shiftKey) {
      e.preventDefault(); // prevent new line
      performTextAnalysis();
    }
  });

  async function performTextAnalysis() {
    const text = textInput.value.trim();

    if (!text) {
      sentimentText.textContent = "Digite um texto para análise";
      sentimentEmoji.textContent = "⚠️";
      topFeaturesContainer.style.display = "none";
      return;
    }

    if (text.length < 6) {
      sentimentText.textContent = "O texto precisa ter pelo menos 6 caracteres";
      sentimentEmoji.textContent = "⚠️";
      return;
    }

    try {
      const response = await fetch("/sentiment", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ text }),
      });

      await handleApiError(response);

      const data = await response.json();

      // Pass text for display
      displaySingleResult(data, text);

      // Clear input on success
      textInput.value = "";
    } catch (error) {
      console.error("API Error Details:", error);
      sentimentText.textContent = "Erro na API: " + error.message;
      sentimentEmoji.textContent = "❌";
    }
  }

  // --- BATCH ANALYSIS ---
  analyzeBatchBtn.addEventListener("click", async () => {
    if (fileInput.files.length === 0) {
      batchStatusText.textContent = "Selecione um arquivo CSV primeiro!";
      return;
    }

    const file = fileInput.files[0];
    const skipHeader = skipHeaderCheck.checked;

    const formData = new FormData();
    formData.append("file", file);
    // Passa param header baseado no checkbox
    formData.append("header", skipHeader);

    batchStatusText.textContent = "Processando arquivo... aguarde...";
    batchOutput.innerHTML = "";

    try {
      const response = await fetch("/sentiment/batch", {
        method: "POST",
        body: formData,
      });

      await handleApiError(response);

      const list = await response.json();
      displayBatchResult(list);
    } catch (error) {
      console.error(error);
      batchStatusText.textContent = "Erro ao processar arquivo.";
    }
  });

  // --- HELPERS ---
  async function handleApiError(response) {
    if (!response.ok) {
      let errorMsg = "Erro na API";
      try {
        const errData = await response.json();
        if (errData.message) errorMsg = errData.message;
        if (errData.errors) errorMsg = JSON.stringify(errData.errors);
      } catch (e) {
        const errText = await response.text();
        if (errText) errorMsg = errText;
      }
      throw new Error(errorMsg);
    }
  }

  function displaySingleResult(data, originalText) {
    const probabilityFormatted = (data.probability * 100).toFixed(1) + "%";

    // Show original text if provided
    let textDisplay = "";
    if (originalText) {
      textDisplay = `<p style="font-style: italic; color: #000000; font-size: 0.9em; margin-bottom: 10px;">"${originalText}"</p>`;
    }

    sentimentText.innerHTML = `${textDisplay}<strong>${capitalize(
      data.sentiment
    )}</strong> (${probabilityFormatted})`;

    // Emoji
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

    // Top Features
    if (data.topFeatures && data.topFeatures.length > 0) {
      topFeaturesContainer.style.display = "block";
      data.topFeatures.forEach((word) => {
        const span = document.createElement("span");
        span.textContent = word;
        span.classList.add("history__feature");
        topFeaturesList.appendChild(span);
      });
    } else {
      topFeaturesContainer.style.display = "none";
    }
  }

  function displayBatchResult(list) {
    batchStatusText.textContent = `Processamento concluído: ${list.length} comentários analisados.`;
    batchOutput.innerHTML = "";

    list.forEach((item) => {
      const sentiment = item.forecast || item.sentiment || "Neutro";
      const probability = (item.probability * 100).toFixed(1) + "%";

      let emoji = "😐";
      if (sentiment.toUpperCase() === "POSITIVO") emoji = "😊";
      if (sentiment.toUpperCase() === "NEGATIVO") emoji = "😞";

      const card = document.createElement("div");
      card.classList.add("history__item");

      card.innerHTML = `
  <div class="history__emoji">${emoji}</div>
  <div class="history__info">
    <p class="history__comment">"${item.analyzedText}"</p>

    ${(() => {
      let cls = "neutral";
      if (sentiment.toUpperCase() === "POSITIVO") cls = "positive";
      if (sentiment.toUpperCase() === "NEGATIVO") cls = "negative";
      return `<span class="history__sentiment sentiment--${cls}">${sentiment}</span>`;
    })()}

    <span class="history__probability">
      Probabilidade: ${probability}
    </span>
      ${
        item.topFeatures && item.topFeatures.length > 0
          ? `
    <div class="history__features">
      <span class="history__features-label">Palavras-chave:</span>
      ${item.topFeatures
        .map((word) => `<span class="history__feature">${word}</span>`)
        .join("")}
    </div>
  `
          : ""
      }
  </div>
`;

      batchOutput.appendChild(card);
    });
  }
});

function capitalize(text) {
  return text.charAt(0).toUpperCase() + text.slice(1).toLowerCase();
}
