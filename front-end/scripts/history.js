const historyContainer = document.getElementById("historyContainer");
const prevBtn = document.getElementById("prevBtn");
const nextBtn = document.getElementById("nextBtn");
const pageInfo = document.getElementById("pageInfo");
const totalInfo = document.getElementById("totalInfo");

let currentPage = 0;
let totalPages = 1;

function getEmoji(sentiment) {
  switch (sentiment.toLowerCase()) {
    case "positivo":
      return "😊";
    case "negativo":
      return "😞";
    case "neutro":
      return "😐";
    default:
      return "❓";
  }
}

function getSentimentClass(sentiment) {
  switch (sentiment.toLowerCase()) {
    case "positivo":
      return "sentiment--positive";
    case "negativo":
      return "sentiment--negative";
    case "neutro":
      return "sentiment--neutral";
    default:
      return "";
  }
}

function capitalize(text) {
  return text.charAt(0).toUpperCase() + text.slice(1).toLowerCase();
}

async function loadHistory(page = 0) {
  try {
    const response = await fetch(
      `/sentiment/history?page=${page}`
    );
    const data = await response.json();
    totalInfo.textContent = `Total de comentários: ${data.totalElements}`;
    historyContainer.innerHTML = "";

    if (!data.content || data.content.length === 0) {
      totalInfo.textContent = "Total de comentários: 0";
      totalInfo.style.display = "none";
      historyContainer.innerHTML = `
    <div class="history__empty">
      <p class="history__empty-text">
        Nenhuma análise foi realizada ainda.
      </p>
      <p class="history__empty-subtext">
        Faça uma análise para começar a visualizar o histórico.
      </p>
    </div>
  `;

      prevBtn.style.display = "none";
      nextBtn.style.display = "none";
      pageInfo.style.display = "none";

      return;
    }

    totalInfo.style.display = "block";
    data.content.forEach((item) => {
      const card = document.createElement("div");
      card.classList.add("history__item");

      card.innerHTML = `
  <span class="history__emoji">
    ${getEmoji(item.forecast)}
  </span>

  <div class="history__info">
    <p class="history__comment">
      "${item.analyzedText}"
    </p>

    <strong class="history__sentiment ${getSentimentClass(item.forecast)}">
      ${capitalize(item.forecast)}
    </strong>

    <span class="history__probability">
      Probabilidade: ${(item.probability * 100).toFixed(1)}%
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

      historyContainer.appendChild(card);
    });

    currentPage = data.number;
    totalPages = data.totalPages;
    pageInfo.textContent = `Página ${currentPage + 1} de ${totalPages}`;

    prevBtn.disabled = currentPage === 0;
    nextBtn.disabled = currentPage + 1 === totalPages;
  } catch (error) {
    console.error("Erro ao carregar histórico:", error);
  }
}

prevBtn.addEventListener("click", () => {
  if (currentPage > 0) {
    loadHistory(currentPage - 1);
  }
});

nextBtn.addEventListener("click", () => {
  if (currentPage + 1 < totalPages) {
    loadHistory(currentPage + 1);
  }
});

loadHistory();
