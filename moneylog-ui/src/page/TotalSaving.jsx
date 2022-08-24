import React from "react";
import SavingTargetCard from "../components/SavingTargetCard";
import UncategorizedSavingCard from "../components/UncategorizedSavingCard";
import "./page_CSS/TotalSaving.css";
import "./page_CSS/common.css";
import AddSharpIcon from "@mui/icons-material/AddSharp";
import { Line } from "react-chartjs-2";

const data = {
  labels: ["2月", "3月", "4月", "5月", "6月", "7月"],
  datasets: [
    {
      label: "累計額",
      borderColor: "#03A9F4",
      data: [300, 1000, 2100, 3100, 3400, 4000, 4800],
    },
  ],
};

const TotalSaving = () => {
  return (
    <>
      <div className="LineChartArea">
        <Line data={data} />
      </div>
      <div className="SavingTargetCardArea">
        <SavingTargetCard />
        <SavingTargetCard />
        <AddSharpIcon fontSize="large" className="AddSavingTargetButton" />
      </div>

      <UncategorizedSavingCard className="UncategorizedSavingCard" />
    </>
  );
};
export default TotalSaving;
