import React, { useState } from "react";
/** CSS */
import "./page_CSS/TotalSaving.css";
import "./page_CSS/common.css";
/** 自作コンポーネント */
import SavingTargetCard from "../components/SavingTargetCard";
import UncategorizedSavingCard from "../components/UncategorizedSavingCard";
import BlurView from "../components/window/BlurView";
import AddTargetBox from "../components/window/AddTargetBox";
/** 外部コンポーネント */
import AddSharpIcon from "@mui/icons-material/AddSharp";
import { Line } from "react-chartjs-2";
import { CSSTransition } from "react-transition-group";

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
  const [windowStatus, setWindowStatus] = useState(false);
  // 貯金総額
  const totalSaving = 100000;

  // 未分類の貯金額
  const uncategorizedSavingAmount = 100000;

  // 貯金目標
  const [savingTargetData, setSavingTargetData] = useState([
    {
      savingTargetId: 1,
      savingTargetName: "沖縄旅行",
      targetAmount: 100000,
      savingCount: 4,
      savingAmount: 50000,
    },
    {
      savingTargetId: 2,
      savingTargetName: "長野旅行",
      targetAmount: 10000,
      savingCount: 3,
      savingAmount: 4000,
    },
  ]);

  // 貯金目標ウィンドウのタイトル
  const [title, setTitle] = useState("貯金目標を追加");

  // 貯金目標編集ウィンドウのデータ
  const [editSavingTarget, setEditSavingTarget] = useState({});
  const resetEditSavingTarget = () => {
    setEditSavingTarget({
      savingTargetName: "",
      targetAmount: "",
      savingCount: "",
      savingAmount: "",
    });
  };

  return (
    <div className="container">
      {/* 貯金総額 */}
      <div className="totalSavingTitleArea">
        <span>貯金総額</span>
        <span>{totalSaving.toLocaleString()}</span>
      </div>

      {/* グラフ */}
      <div className="lineChartArea">
        <Line data={data} options={option} />
      </div>

      {/* 貯金目標 */}
      <div className="savingTargetCardArea">
        {savingTargetData.map((data, i) => {
          return (
            <SavingTargetCard
              key={i}
              savingTargetData={data}
              setWindowStatus={setWindowStatus}
              setTitle={setTitle}
              setEditSavingTarget={setEditSavingTarget}
            />
          );
        })}
        <AddSharpIcon
          onClick={() => {
            resetEditSavingTarget();
            setWindowStatus(true);
            setTitle("貯金目標を追加");
          }}
          fontSize="large"
          className="addSavingTargetButton"
        />
      </div>

      {/* 未分類の貯金額 */}
      <div className="uncategorizedSavingCardArea">
        <UncategorizedSavingCard
          UncategorizedSavingAmount={uncategorizedSavingAmount}
        />
      </div>

      {/* 貯金目標追加ウィンドウ */}
      <BlurView status={windowStatus} setStatus={setWindowStatus} />
      <CSSTransition
        in={windowStatus}
        timeout={100}
        unmountOnExit
        classNames="Modal-show"
      >
        <AddTargetBox
          setWindowStatus={setWindowStatus}
          title={title}
          savingTargetData={editSavingTarget}
          setSavingTargetData={setEditSavingTarget}
        />
      </CSSTransition>
    </div>
  );
};
export default TotalSaving;
