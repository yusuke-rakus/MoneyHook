import React, { useEffect, useState } from "react";
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
import Sidebar from "../components/Sidebar";

const TotalSaving = (props) => {
  const { themeColor } = props;
  /** 今月 */
  const [sysDate, setSysDate] = useState(new Date("2022-06-01"));
  sysDate.setDate(1);

  /** 貯金総額 */
  const [totalSaving, setTotalSaving] = useState(0);

  /** グラフデータ */
  const [graphData, setGraphData] = useState([]);
  const [graphMonth, setGraphMonth] = useState([]);

  /** 未分類の貯金額 */
  const [uncategorizedSavingAmount, setUncategorizedSavingAmount] = useState(0);

  /** 貯金目標 */
  const [savingTargetData, setSavingTargetData] = useState([]);

  const data = {
    labels: graphMonth,
    datasets: [
      {
        label: "累計額",
        borderColor: "#03A9F4",
        pointBorderColor: "#03A9F4",
        pointBackgroundColor: "#03A9F4",
        data: graphData,
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
  const [windowStatus, setWindowStatus] = useState(false);

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

  /** API関連 */
  const rootURI = "http://localhost:8080";

  const getInit = (month) => {
    // 貯金目標リストの取得
    fetch(`${rootURI}/saving/getSavingAmountForTarget`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: "a77a6e94-6aa2-47ea-87dd-129f580fb669",
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status == "success") {
          setUncategorizedSavingAmount(
            data.uncategorizedAmount == void 0 ? 0 : data.uncategorizedAmount
          );
          setSavingTargetData(data.savingTargetList);
        }
      });

    // 貯金総額の取得
    fetch(`${rootURI}/saving/getTotalSaving`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: "a77a6e94-6aa2-47ea-87dd-129f580fb669",
        month: month,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status == "success") {
          // グラフを設定
          setGraphData(
            data.savingDataList.map((d) => d.monthlyTotalSavingAmount)
          );
          // グラフの月を設定
          setGraphMonth(
            data.savingDataList.map(
              (d) => new Date(d.savingMonth).getMonth() + 1
            )
          );
          // 貯金総額を設定
          setTotalSaving(data.totalSavingAmount);
        }
      });
  };

  useEffect(() => {
    getInit(sysDate);
  }, [setGraphData, setSavingTargetData]);

  return (
    <>
      <Sidebar themeColor={themeColor} />

      <div className="homeArea">
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
      </div>
    </>
  );
};
export default TotalSaving;
