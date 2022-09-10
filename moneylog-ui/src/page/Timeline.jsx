import React, { useState } from "react";
/** CSS */
import "./page_CSS/Timeline.css";
import "./page_CSS/common.css";
/** 自作コンポーネント */
import TimelineDataList from "../components/TimelineDataList";
import HouseholdBudgetButton from "../components/HouseholdBudgetButton";
import ModalBox from "../components/window/ModalBox";
import BlurView from "../components/window/BlurView";
/** 外部コンポーネント */
import { Bar } from "react-chartjs-2";
import { Chart, registerables } from "chart.js";
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import { CSSTransition } from "react-transition-group";

Chart.register(...registerables);

const Timeline = () => {
  /** 今月 */
  let date = new Date();
  let formatday = `${date.getFullYear()}-${date.getMonth() + 1}-1`;

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
  const [timelineDataList, setTimelineDataList] = useState([
    {
      transactionId: 1,
      transactionDate: "2022-07-2",
      categoryName: "分類1",
      subCategoryName: "小類1",
      transactionName: "取引名1",
      transactionAmount: -20000,
    },
    {
      transactionId: 2,
      transactionDate: "2022-07-25",
      categoryName: "分類2",
      subCategoryName: "小類2",
      transactionName: "取引名2",
      transactionAmount: -40000,
    },
    {
      transactionId: 3,
      transactionDate: "2022-07-25",
      categoryName: "分類3",
      subCategoryName: "小類3",
      transactionName: "取引名3",
      transactionAmount: -40000,
    },
    {
      transactionId: 4,
      transactionDate: "2022-07-30",
      categoryName: "分類4",
      subCategoryName: "小類4",
      transactionName: "取引名4",
      transactionAmount: 30000,
    },
  ]);

  const [sortCd, setSortCd] = React.useState(1);

  function compareDate(a, b) {
    if (a.transactionDate !== b.transactionDate) {
      return new Date(a.transactionDate) - new Date(b.transactionDate);
    }
    if (a.transactionId !== b.transactionId) {
      return a.transactionId - b.transactionId;
    }
  }
  function compareDateReverse(a, b) {
    if (a.transactionDate !== b.transactionDate) {
      return new Date(b.transactionDate) - new Date(a.transactionDate);
    }
    if (a.transactionId !== b.transactionId) {
      return a.transactionId - b.transactionId;
    }
  }

  function compareAmount(a, b) {
    if (a.transactionAmount !== b.transactionAmount) {
      return a.transactionAmount - b.transactionAmount;
    }
    if (a.transactionId !== b.transactionId) {
      return a.transactionId - b.transactionId;
    }
  }
  function compareAmountReverse(a, b) {
    if (a.transactionAmount !== b.transactionAmount) {
      return b.transactionAmount - a.transactionAmount;
    }
    if (a.transactionId !== b.transactionId) {
      return a.transactionId - b.transactionId;
    }
  }

  const sorting = (event) => {
    setSortCd(event.target.value);
    switch (event.target.value) {
      case 1:
        // 日付昇順
        setTimelineDataList(timelineDataList.sort(compareDate));
        break;
      case 2:
        // 日付降順
        setTimelineDataList(timelineDataList.sort(compareDateReverse));
        break;
      case 3:
        // 金額昇順
        setTimelineDataList(timelineDataList.sort(compareAmount));
        break;
      case 4:
        // 金額降順
        setTimelineDataList(timelineDataList.sort(compareAmountReverse));
        break;
    }
  };

  /** モーダルウィンドウ */
  const [modalWindow, setModalWindow] = useState(false);
  /** 登録用データ */
  const [transaction, setTransaction] = useState({
    // userId: "",
    // transactionDate: "",
    // transactionAmount: "",
    // transactionName: "",
    // categoryId: "",
    // categoryName: "",
    // subCategoryId: "",
    // subCategoryName: "",
    // fixedFlg: "",
  });

  return (
    <div className="container">
      {/* 月 */}
      <div className="month">
        <ArrowBackIosNewIcon fontSize="large" className="switchMonthButton" />
        <span>{date.getMonth() + 1}月</span>
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
            onChange={sorting}
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
        {timelineDataList.map((data, i) => {
          return (
            <TimelineDataList
              key={i}
              timeline={data}
              setModalWindow={setModalWindow}
              setTransaction={setTransaction}
            />
          );
        })}
      </div>

      {/* 追加ボタン */}
      <div className="addTransactionArea">
        <HouseholdBudgetButton
          openWindow={setModalWindow}
          buttonText={"追加"}
        />
      </div>

      {/* 取引追加画面 */}
      <BlurView
        status={modalWindow}
        setStatus={setModalWindow}
        setObject={setTransaction}
      />
      <CSSTransition
        in={modalWindow}
        timeout={200}
        unmountOnExit
        classNames="Modal-show"
      >
        <ModalBox
          openWindow={setModalWindow}
          transaction={transaction}
          setTransaction={setTransaction}
        />
      </CSSTransition>
    </div>
  );
};
export default Timeline;
