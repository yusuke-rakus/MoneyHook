import React from "react";
import { Bar } from "react-chartjs-2";
import { Chart, registerables } from "chart.js";

Chart.register(...registerables);

const Timeline = () => {
  const data = {
    labels: ["7月", "8月", "9月", "10月", "11月", "12月"],
    datasets: [
      // 表示するデータセット
      {
        data: [5.6, 7.2, 10.6, 13.6, 20, 21.8],
        backgroundColor: "rgba(30, 144, 255, 1)",
        label: "月別合計降水量(mm)",
      },
    ],
  };

  /** グラフオプション */
  const option = {};

  return (
    <>
      <Bar data={data} options={option} />
    </>
  );
};
export default Timeline;
