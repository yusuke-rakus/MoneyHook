import React from "react";
import SavingTargetCard from "../components/SavingTargetCard";
import UncategorizedSavingCard from "../components/UncategorizedSavingCard";
import "./page_CSS/TotalSaving.css";
import "./page_CSS/common.css";
import AddSharpIcon from "@mui/icons-material/AddSharp";
import { Line } from "react-chartjs-2";

/** グラフデータ */
const data = {
  labels: ["2月", "3月", "4月", "5月", "6月", "7月"],
  datasets: [
    {
      label: "累計額",
      borderColor: "#03A9F4",
      pointBorderColor: "#03A9F4",
      pointBackgroundColor: "#03A9F4",
      data: [300, 1000, 2100, 3100, 3400, 4000, 4800],
    },
  ],
};

/** グラフオプション */
const option = {
  plugins: {
    legend: {
      display: false,
    },
  },
  scales: {
    x: {
      ticks: {
        font: {
          size: 18,
        },
      },
      grid: {
        display: false,
        drawBorder: false,
      },
    },
    y: {
      ticks: {
        display: false,
      },
      grid: {
        display: false,
        drawBorder: false,
      },
    },
  },
};

const TotalSaving = () => {
  // 貯金総額
  const totalSaving = 100000;

  // 未分類の貯金額
  const uncategorizedSavingAmount = 100000;

  // 貯金目標
  const savingTargetData = [
    {
      savingTargetName: "沖縄旅行",
      targetAmount: 100000,
      savingCount: 4,
      savingAmount: 50000,
    },
  ];

  return (
    <div className="container">
      <div className="totalSavingTitleArea">
        <span>貯金総額</span>
        <span>{totalSaving.toLocaleString()}</span>
      </div>
      <div className="lineChartArea">
        <Line data={data} options={option} />
      </div>
      <div className="savingTargetCardArea">
        {savingTargetData.map((data) => {
          return (
            <>
              <SavingTargetCard savingTargetData={data} />
            </>
          );
        })}
        <AddSharpIcon fontSize="large" className="addSavingTargetButton" />
      </div>

      <div className="uncategorizedSavingCardArea">
        <UncategorizedSavingCard
          UncategorizedSavingAmount={uncategorizedSavingAmount}
        />
      </div>
    </div>
  );
};
export default TotalSaving;
