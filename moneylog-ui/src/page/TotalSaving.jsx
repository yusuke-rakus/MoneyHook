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
  return (
    <div className="container">
      <div className="TotalSavingTitleArea">
        <span>貯金総額</span>
        <span>nnn,nnn</span>
      </div>
      <div className="LineChartArea">
        <Line data={data} options={option} />
      </div>
      <div className="SavingTargetCardArea">
        <SavingTargetCard />
        <SavingTargetCard />
        <AddSharpIcon fontSize="large" className="AddSavingTargetButton" />
      </div>

      <div className="UncategorizedSavingCardArea">
        <UncategorizedSavingCard className="UncategorizedSavingCard" />
      </div>
    </div>
  );
};
export default TotalSaving;
