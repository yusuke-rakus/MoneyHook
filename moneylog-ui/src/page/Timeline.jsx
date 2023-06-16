import React, { useEffect, useState } from "react";
/** CSS */
import "./page_CSS/Timeline.css";
import "./page_CSS/common.css";
/** 自作コンポーネント */
import { rootURI, timeoutRange } from "../env/env";
import Sidebar from "../components/Sidebar";
import { LoadFetchErrorWithSeparateBanner } from "../components/FetchError";
import { getJST } from "../components/GetJST";
import TimelineDataList from "../components/TimelineDataList";
import AddTransactionListWindow from "../components/window/AddTransactionListWindow";
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
import Alert from "@mui/material/Alert";
import IconButton from "@mui/material/IconButton";
import Collapse from "@mui/material/Collapse";
import CloseIcon from "@mui/icons-material/Close";
import { useCookies } from "react-cookie";
import { useNavigate } from "react-router-dom";
import { SpeedDial, SpeedDialAction, SpeedDialIcon } from "@mui/material";
import EditIcon from "@mui/icons-material/Edit";
import AddIcon from "@mui/icons-material/Add";
import ListIcon from "@mui/icons-material/List";

Chart.register(...registerables);

const Timeline = (props) => {
  const { themeColor } = props;
  const [isLoading, setLoading] = useState(false);
  const [cookie] = useCookies();
  const navigate = useNavigate();

  /** バナーのステータス */
  const [banner, setBanner] = useState(false);
  const [bannerMessage, setBannerMessage] = useState("");
  const [bannerType, setBannerType] = useState("success");
  useEffect(() => {
    setTimeout(() => {
      setBanner(false);
    }, timeoutRange);
  }, [banner]);

  /** 今月 */
  const [sysDate, setSysDate] = useState(getJST(new Date()));
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
        label: "合計支出",
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
    if (
      a.transactionAmount * a.transactionSign !==
      b.transactionAmount * b.transactionSign
    ) {
      return (
        a.transactionAmount * a.transactionSign -
        b.transactionAmount * b.transactionSign
      );
    }
    if (a.transactionId !== b.transactionId) {
      return a.transactionId - b.transactionId;
    }
  }
  function compareAmountReverse(a, b) {
    if (
      a.transactionAmount * a.transactionSign !==
      b.transactionAmount * b.transactionSign
    ) {
      return (
        b.transactionAmount * b.transactionSign -
        a.transactionAmount * a.transactionSign
      );
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
      default:
        setTimelineDataList(timelineDataList.sort(compareDateReverse));
    }
  };

  /** 追加ウィンドウ */
  const [modalWindow, setModalWindow] = useState(false);

  /** 追加用データ */
  const [transaction, setTransaction] = useState({});

  /** 一括入力ウィンドウ */
  const [tranList, openTranList] = useState(false);

  /** 一括入力用データ */
  const [transactionList, setTransactionList] = useState([]);

  /** 収支追加モーダル */
  const actions = [
    { icon: <AddIcon />, name: "追加", func: () => setModalWindow(true) },
    { icon: <ListIcon />, name: "一括入力", func: () => openTranList(true) },
  ];

  /** API関連 */
  const getInit = (month) => {
    setLoading(true);
    // 6ヶ月分の合計支出を取得
    fetch(`${rootURI}/transaction/getMonthlySpendingData`, {
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
          setGraphData(
            data.monthlyTotalAmountList
              .map((d) => Math.abs(d.totalAmount))
              .reverse()
          );

          setGraphMonth(
            data.monthlyTotalAmountList
              .map((d) => new Date(d.month).getMonth() + 1)
              .reverse()
              .map((d) => `${d}月`)
          );
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

    // 当月のTransactionを取得
    fetch(`${rootURI}/transaction/getTimelineData`, {
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
          setTimelineDataList(data.transactionList);
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

  /** 前月データを取得 */
  const getPastMonth = () => {
    let tempDate = new Date(sysDate);
    tempDate.setMonth(tempDate.getMonth() - 1);
    setSysDate(tempDate);
    getInit(tempDate);
  };

  /** 次月データを取得 */
  const getForwardMonth = () => {
    if (sysDate < new Date()) {
      let tempDate = new Date(sysDate);
      tempDate.setMonth(tempDate.getMonth() + 1);
      setSysDate(tempDate);
      getInit(tempDate);
    }
  };

  useEffect(() => {
    !cookie.userId && navigate("/login");
    getInit(sysDate);
  }, [setGraphMonth, setGraphData]);

  return (
    <>
      <Sidebar themeColor={themeColor} />

      <div className="homeArea">
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
          {isLoading ? (
            <div className="chartLoading loading"></div>
          ) : (
            <Bar
              data={graphDatasets}
              options={option}
              className="timelineGraph"
            />
          )}

          {/* 並べ替えプルダウン */}
          {isLoading === false && timelineDataList.length === 0 ? (
            <div className="sortButtonArea"></div>
          ) : (
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
          )}

          {isLoading === false && timelineDataList.length === 0 && (
            <div className="dataNotFound">
              <p>データが登録されていません</p>
              <p>家計簿を入力して記録を開始しましょう</p>
            </div>
          )}

          {/* タイムラインデータ */}
          {isLoading ? (
            <div className="timelineArea">
              <div className="timelineLoading loading"></div>
              <div className="timelineLoading loading"></div>
              <div className="timelineLoading loading"></div>
              <div className="timelineLoading loading"></div>
              <div className="timelineLoading loading"></div>
              <div className="timelineLoading loading"></div>
              <div className="timelineLoading loading"></div>
              <div className="timelineLoading loading"></div>
            </div>
          ) : (
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
          )}

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

          {/* 追加ボタン */}
          <div className="addTransactionArea">
            <SpeedDial
              ariaLabel="SpeedDial openIcon example"
              sx={{ position: "absolute", bottom: 16, right: 16 }}
              icon={
                <SpeedDialIcon
                  onClick={() => setModalWindow(true)}
                  openIcon={<EditIcon />}
                  sx={{ color: "white" }}
                />
              }
            >
              {actions.map((action) => (
                <SpeedDialAction
                  key={action.name}
                  icon={action.icon}
                  tooltipTitle={action.name}
                  onClick={action.func}
                />
              ))}
            </SpeedDial>
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

          {/* 一括入力画面 */}
          <BlurView
            status={tranList}
            setStatus={openTranList}
            listObject={transactionList}
            setListObject={setTransactionList}
          />
          <CSSTransition
            in={tranList}
            timeout={200}
            unmountOnExit
            classNames="Modal-show"
          >
            <AddTransactionListWindow
              transactionList={transactionList}
              setTransactionList={setTransactionList}
              openWindow={openTranList}
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
