document.addEventListener("DOMContentLoaded", () => {
  const limitInput = document.getElementById("limitInput");
  const statsBtn = document.getElementById("statsBtn");
  const statsMessage = document.getElementById("statsMessage");
  const statsTotal = document.getElementById("statsTotal");
  const statsUsedInfo = document.getElementById("statsUsedInfo");

  const positivePercent = document.getElementById("positivePercent");
  const neutralPercent = document.getElementById("neutralPercent");
  const negativePercent = document.getElementById("negativePercent");

  async function loadTotal() {
    try {
      const response = await fetch("/sentiment/stats");
      const data = await response.json();

      statsTotal.innerHTML = `Total de comentários disponíveis: ${data.total}`;
    } catch (error) {
      statsTotal.textContent = "Não foi possível carregar o total de comentários.";
    }
  }

  statsBtn.addEventListener("click", async () => {
    const limit = parseInt(limitInput.value);

    if (!limit || limit <= 0) {
      statsMessage.textContent ="Informe um número válido de comentários.";
      return;
    }

    try {
      const response = await fetch(
        `/sentiment/stats?limit=${limit}`
      );

      if (!response.ok) {
        throw new Error("Erro ao buscar estatísticas");
      }

      const data = await response.json();

      if (!data.total || data.total === 0) {
        positivePercent.textContent = "—";
        neutralPercent.textContent = "—";
        negativePercent.textContent = "—";

        statsMessage.textContent = "Não existem análises suficientes para a estatística ser gerada.";
        statsUsedInfo.textContent = "";
        return;
      }

      statsMessage.textContent = "";

      positivePercent.textContent = data.positivePercentage.toFixed(1) + "%";

      neutralPercent.textContent = data.neutralPercentage.toFixed(1) + "%";

      negativePercent.textContent = data.negativePercentage.toFixed(1) + "%";

      statsUsedInfo.textContent = `Mostrando estatísticas de ${data.used} de ${data.total} comentários disponíveis.`;
    } catch (error) {
      console.error(error);
      statsMessage.textContent ="Não foi possível carregar as estatísticas.";
      statsUsedInfo.textContent = "";
    }
  });
  loadTotal();
});
