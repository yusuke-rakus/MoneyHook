import React, { useEffect, useState } from "react";
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
import Sidebar from "../components/Sidebar";
import Alert from "@mui/material/Alert";
import IconButton from "@mui/material/IconButton";
import Collapse from "@mui/material/Collapse";
import CloseIcon from "@mui/icons-material/Close";

Chart.register(...registerables);

const Timeline = (props) => {
  const { themeColor } = props;

  /** バナーのステータス */
  const [banner, setBanner] = useState(false);
  const [bannerMessage, setBannerMessage] = useState("");
  const [bannerType, setBannerType] = useState("success");

  /** 今月 */
  const [sysDate, setSysDate] = useState(new Date("2022-06-01"));
  sysDate.setDate(1);

  const [transactionTitle, setTransactionTitle] =
    useState("支出または収入の入力");

  /** グラフデータ */
  const [graphData, setGraphData] = useState([]);
  const [graphMonth, setGraphMonth] = useState([]);
  const graphDatasets = {
    labels: graphMonth,
    datasets: [
      // 表示するデータセット
      {
        data: graphData,
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
  const [timelineDataList, setTimelineDataList] = useState([]);

  const [sortCd, setSortCd] = useState(2);

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
  const [transaction, setTransaction] = useState({});

  /** API関連 */
  const rootURI = "http://localhost:8080";

  const getInit = (month) => {
    // 6ヶ月分の合計支出を取得
    fetch(`${rootURI}/transaction/getMonthlySpendingData`, {
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
          setGraphData(
            data.monthlyTotalAmountList
              .map((d) => Math.abs(d.totalAmount))
              .reverse()
          );

          setGraphMonth(
            data.monthlyTotalAmountList
              .map((d) => `${Number(d.month)}月`)
              .reverse()
          );
        }
      });

    // 当月のTransactionを取得
    fetch(`${rootURI}/transaction/getTimelineData`, {
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
          setTimelineDataList(data.transactionList);
        }
      });
  };

  /** 前月データを取得 */
  const getPastMonth = () => {
    let tempDate = new Date(sysDate);
    tempDate.setMonth(tempDate.getMonth() - 1);
    setSysDate(tempDate);
    getInit(tempDate);
  };

  /** 次月データを取得 */
  const getForwardMonth = () => {
    let tempDate = new Date(sysDate);
    tempDate.setMonth(tempDate.getMonth() + 1);
    setSysDate(tempDate);
    getInit(tempDate);
  };

  useEffect(() => {
    getInit(sysDate);
  }, [setGraphMonth, setGraphData]);

  return (
    <>
      <Sidebar themeColor={themeColor} />

      <div className="homeArea">
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
              sx={{ mb: 2 }}
            >
              {bannerMessage}
            </Alert>
          </Collapse>
        </div>

        <div className="container">
          {/* 月 */}
          <div className="month">
            <span onClick={getPastMonth}>
              <ArrowBackIosNewIcon
                fontSize="large"
                className="switchMonthButton"
              />
            </span>
            <span>{sysDate.getMonth() + 1}月</span>
            <span onClick={getForwardMonth}>
              <ArrowForwardIosIcon
                fontSize="large"
                className="switchMonthButton"
              />
            </span>
          </div>

          {/* グラフ */}
          <Bar
            data={graphDatasets}
            options={option}
            className="timelineGraph"
          />

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
                  setTransactionTitle={setTransactionTitle}
                />
              );
            })}
          </div>

          {/* 追加ボタン */}
          <div className="addTransactionArea">
            <HouseholdBudgetButton
              openWindow={setModalWindow}
              buttonText={"追加"}
              setTransactionTitle={setTransactionTitle}
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
              title={transactionTitle}
              getInit={getInit}
              month={sysDate}
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
export default Timeline;
