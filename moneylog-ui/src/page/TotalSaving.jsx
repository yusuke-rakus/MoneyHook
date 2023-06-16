import React, { useEffect, useState } from "react";
/** CSS */
import "./page_CSS/TotalSaving.css";
import "./page_CSS/common.css";
/** 自作コンポーネント */
import { rootURI, timeoutRange } from "../env/env";
import UncategorizedSavingWindow from "../components/window/UncategorizedSavingWindow";
import { LoadFetchErrorWithSeparateBanner } from "../components/FetchError";
import { getJST } from "../components/GetJST";
import SavingTargetCard from "../components/SavingTargetCard";
import UncategorizedSavingCard from "../components/UncategorizedSavingCard";
import BlurView from "../components/window/BlurView";
import AddTargetBox from "../components/window/AddTargetBox";
/** 外部コンポーネント */
import AddSharpIcon from "@mui/icons-material/AddSharp";
import { Line } from "react-chartjs-2";
import { CSSTransition } from "react-transition-group";
import Sidebar from "../components/Sidebar";
import Alert from "@mui/material/Alert";
import IconButton from "@mui/material/IconButton";
import Collapse from "@mui/material/Collapse";
import CloseIcon from "@mui/icons-material/Close";
import { useCookies } from "react-cookie";
import { useNavigate } from "react-router-dom";

const TotalSaving = (props) => {
  const { themeColor } = props;
  const [isLoading, setLoading] = useState(false);
  const [cookie] = useCookies();
  const navigate = useNavigate();

  /** バナーのステータス */
  const [banner, setBanner] = useState(false);
  const [bannerMessage, setBannerMessage] = useState("");
  const [bannerType, setBannerType] = useState("");
  useEffect(() => {
    setTimeout(() => {
      setBanner(false);
    }, timeoutRange);
  }, [banner]);

  /** 今月 */
  const [sysDate] = useState(getJST(new Date()));
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
    setEditSavingTarget({});
  };

  /** 未分類の貯金額 */
  const [uncategorizedWindow, setUncategorizedWindow] = useState(false);
  const openUncategorizedWindow = () => {
    if (uncategorizedSavingAmount !== 0) {
      setUncategorizedWindow(true);
    }
  };

  /** API関連 */
  const getInit = (month) => {
    setLoading(true);
    getSavingAmountForTarget(month);
    getTotalSaving(month);
  };

  /** 貯金総額の取得 */
  const getTotalSaving = (month) => {
    fetch(`${rootURI}/saving/getTotalSaving`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: cookie.userId,
        month: month,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status === "success") {
          // グラフを設定
          setGraphData(
            data.savingDataList.map((d) => d.monthlyTotalSavingAmount).reverse()
          );
          // グラフの月を設定
          setGraphMonth(
            data.savingDataList
              .map((d) => new Date(d.savingMonth).getMonth() + 1)
              .reverse()
              .map((d) => `${d}月`)
          );
          // 貯金総額を設定
          setTotalSaving(data.totalSavingAmount);
          setLoading(false);
        }
      })
      .catch(() => {
        // サーバーエラーが発生した場合
        LoadFetchErrorWithSeparateBanner(
          setLoading,
          setBanner,
          setBannerMessage,
          setBannerType
        );
      });
  };

  /** 貯金目標リストの取得 */
  const getSavingAmountForTarget = () => {
    fetch(`${rootURI}/saving/getSavingAmountForTarget`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: cookie.userId,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status === "success") {
          setUncategorizedSavingAmount(
            !data.uncategorizedAmount ? 0 : data.uncategorizedAmount
          );
          setSavingTargetData(data.savingTargetList);
          setLoading(false);
        }
      })
      .catch(() => {
        // サーバーエラーが発生した場合
        LoadFetchErrorWithSeparateBanner(
          setLoading,
          setBanner,
          setBannerMessage,
          setBannerType
        );
      });
  };

  useEffect(() => {
    !cookie.userId && navigate("/login");
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
            <span>{totalSaving && totalSaving.toLocaleString()}</span>
          </div>

          {/* グラフ */}
          <div className="lineChartArea">
            {isLoading ? (
              <div className="loading chartLoading"></div>
            ) : (
              <Line data={data} options={option} />
            )}
          </div>

          {isLoading === false && savingTargetData.length === 0 && (
            <div className="dataNotFound">
              <p>貯金目標が存在しません</p>
              <p>貯金データを振り分けて目標に向けて貯金しましょう</p>
            </div>
          )}

          {/* 貯金目標 */}
          {isLoading ? (
            <div className="savingTargetCardArea">
              <div className="savingTargetLoading loading"></div>
              <div className="savingTargetLoading loading"></div>
              <div className="savingTargetLoading loading"></div>
            </div>
          ) : (
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
          )}

          {/* 未分類の貯金額 */}
          <div className="uncategorizedSavingCardArea">
            <UncategorizedSavingCard
              UncategorizedSavingAmount={uncategorizedSavingAmount}
              openUncategorizedWindow={openUncategorizedWindow}
            />
          </div>

          {/* バーナー */}
          <div className="bannerArea">
            <Collapse in={banner}>
              <Alert
                severity={bannerType}
                action={
                  <IconButton
                    aria-label="close"
                    color="inherit"
                    size="small"
                    onClick={() => {
                      setBanner(false);
                    }}
                  >
                    <CloseIcon fontSize="inherit" />
                  </IconButton>
                }
                sx={{ mb: 1 }}
              >
                {bannerMessage}
              </Alert>
            </Collapse>
          </div>

          {/* 貯金目標追加ウィンドウ */}
          <BlurView
            status={windowStatus}
            setStatus={setWindowStatus}
            setObject={setEditSavingTarget}
          />
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
              getInit={getInit}
              setBanner={setBanner}
              setBannerMessage={setBannerMessage}
              setBannerType={setBannerType}
            />
          </CSSTransition>

          {/* 未分類の貯金ウィンドウ */}
          <BlurView
            status={uncategorizedWindow}
            setStatus={setUncategorizedWindow}
            setObject={setEditSavingTarget}
          />
          <CSSTransition
            in={uncategorizedWindow}
            timeout={100}
            unmountOnExit
            classNames="Modal-show"
          >
            <UncategorizedSavingWindow
              setUncategorizedWindow={setUncategorizedWindow}
              uncategorizedAmount={uncategorizedSavingAmount}
              getSavingAmountForTarget={getSavingAmountForTarget}
              setBanner={setBanner}
              setBannerMessage={setBannerMessage}
              setBannerType={setBannerType}
            />
          </CSSTransition>
        </div>
      </div>
    </>
  );
};
export default TotalSaving;
