import React from "react";
import { Bar } from "react-chartjs-2";
import { Chart, registerables } from "chart.js";
import "./page_CSS/Timeline.css";
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import HouseholdBudgetButton from "../components/HouseholdBudgetButton";
import TimelineDataList from "../components/TimelineDataList";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import ModalWindow from "../components/window/ModalWindow";

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

  /** タイムラインデータ */
  const timelineDataList = [
    {
      transactionDate: "20",
      transactionCategory: "住宅",
      transactionName: "家賃",
      transactionAmount: 70000,
    },
    {
      transactionDate: "20",
      transactionCategory: "住宅",
      transactionName: "家賃",
      transactionAmount: 7000,
    },
    {
      transactionDate: "20",
      transactionCategory: "住宅",
      transactionName: "家賃",
      transactionAmount: 7000,
    },
  ];

  const [sortCd, setSortCd] = React.useState("");

  const handleChange = (event) => {
    // ソート処理
    setSortCd(event.target.value);
  };

  return (
    <div className="container">
      {/* 月 */}
      <div className="month">
        <ArrowBackIosNewIcon fontSize="large" className="switchMonthButton" />
        <span>6月</span>
        <ArrowForwardIosIcon fontSize="large" className="switchMonthButton" />
      </div>

      {/* グラフ */}
      <Bar data={data} options={option} className="timelineGraph" />

      {/* 並べ替えプルダウン */}
      <div className="sortButtonArea">
        <FormControl sx={{ m: 1, minWidth: 120 }} size="small">
          <InputLabel id="demo-select-small">並べ替え</InputLabel>
          <Select
            labelId="demo-select-small"
            id="demo-select-small"
            value={sortCd}
            onChange={handleChange}
            label="並べ替え"
          >
            <MenuItem value={1}>日付昇順</MenuItem>
            <MenuItem value={2}>日付降順</MenuItem>
            <MenuItem value={3}>金額昇順</MenuItem>
            <MenuItem value={4}>金額降順</MenuItem>
          </Select>
        </FormControl>
      </div>

      {/* タイムラインデータ */}
      <div className="timelineArea">
        <TimelineDataList timelineDataList={timelineDataList} />
      </div>

      <div className="addTransactionArea">
        <ModalWindow />
      </div>
    </div>
  );
};
export default Timeline;
