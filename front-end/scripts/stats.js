document.addEventListener("DOMContentLoaded", () => {
  const limitInput = document.getElementById("limitInput");
  const statsBtn = document.getElementById("statsBtn");
  const statsMessage = document.getElementById("statsMessage");

  const positivePercent = document.getElementById("positivePercent");
  const neutralPercent = document.getElementById("neutralPercent");
  const negativePercent = document.getElementById("negativePercent");

  statsBtn.addEventListener("click", async () => {
    const limit = parseInt(limitInput.value);

    if (!limit || limit <= 0) {
      statsMessage.textContent ="Informe um número válido de comentários.";
      return;
    }

    try {
      const response = await fetch(
        `http://localhost:8080/sentiment/stats?limit=${limit}`
      );

      if (!response.ok) {
        throw new Error("Erro ao buscar estatísticas");
      }

      const data = await response.json();

      if (!data.total || data.total === 0) {
        positivePercent.textContent = "—";
        neutralPercent.textContent = "—";
        negativePercent.textContent = "—";

        statsMessage.textContent =
          "Não existem análises suficientes para a estatística ser gerada.";
        return;
      }

      statsMessage.textContent = "";

      positivePercent.textContent = data.positivePercentage.toFixed(1) + "%";

      neutralPercent.textContent = data.neutralPercentage.toFixed(1) + "%";

      negativePercent.textContent = data.negativePercentage.toFixed(1) + "%";
    } catch (error) {
      console.error(error);
      statsMessage.textContent ="Não foi possível carregar as estatísticas.";
    }
  });
});
