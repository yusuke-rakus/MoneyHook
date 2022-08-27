import React from "react";
import { Bar } from "react-chartjs-2";
import { Chart, registerables } from "chart.js";
import ChevronRightIcon from "@mui/icons-material/ChevronRight";
import "./page_CSS/Timeline.css";
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import HouseholdBudgetButton from "../components/HouseholdBudgetButton";

Chart.register(...registerables);

const Timeline = () => {
  /** グラフデータ */
  const data = {
    labels: ["7月", "8月", "9月", "10月", "11月", "12月"],
    datasets: [
      // 表示するデータセット
      {
        data: [220000, 170000, 240000, 190000, 200000, 180000],
        backgroundColor: "#03A9F4",
        label: "月別支出推移",
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

  return (
    <div className="container">
      {/* 月 */}
      <div className="month">
        <ArrowBackIosNewIcon fontSize="large" className="switch-month-button" />
        <span>6月</span>
        <ArrowForwardIosIcon fontSize="large" className="switch-month-button" />
      </div>

      {/* グラフ */}
      <Bar data={data} options={option} className="TimelineGraph" />

      {/* 並べ替えプルダウン */}
      <div className="sortButtonArea">
        <select name="sort" id="sort">
          <option value="">並べ替え</option>
          <option value="sample1">sample1</option>
          <option value="smaple2">sample2</option>
        </select>
      </div>

      {/* タイムラインデータ */}
      <div className="timelineArea">
        <ul>
          <li className="transactionData">
            <div className="transactionDate">n日</div>
            <div className="transactionCategory">カテゴリ</div>
            <div className="transactionName">取引名</div>
            <div className="transactionAmount">¥5,000</div>
            <span>
              <ChevronRightIcon />
            </span>
          </li>
          <li className="transactionData">
            <div className="transactionDate">n日</div>
            <div className="transactionCategory">カテゴリ</div>
            <div className="transactionName">取引名</div>
            <div className="transactionAmount">¥5,000</div>
            <span>
              <ChevronRightIcon />
            </span>
          </li>
          <li className="transactionData">
            <div className="transactionDate">n日</div>
            <div className="transactionCategory">カテゴリ</div>
            <div className="transactionName">取引名</div>
            <div className="transactionAmount">¥5,000</div>
            <span>
              <ChevronRightIcon />
            </span>
          </li>
          <li className="transactionData">
            <div className="transactionDate">n日</div>
            <div className="transactionCategory">カテゴリ</div>
            <div className="transactionName">取引名</div>
            <div className="transactionAmount">¥5,000</div>
            <span>
              <ChevronRightIcon />
            </span>
          </li>
          <li className="transactionData">
            <div className="transactionDate">n日</div>
            <div className="transactionCategory">カテゴリ</div>
            <div className="transactionName">取引名</div>
            <div className="transactionAmount">¥5,000</div>
            <span>
              <ChevronRightIcon />
            </span>
          </li>
        </ul>
      </div>
      <HouseholdBudgetButton />
    </div>
  );
};
export default Timeline;
