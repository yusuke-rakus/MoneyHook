import React, { useEffect, useState } from "react";
/** CSS */
import "./page_CSS/Home.css";
import "./page_CSS/common.css";
/** 自作コンポーネント */
import { rootURI, timeoutRange } from "../env/env";
import { LoadFetchErrorWithSeparateBanner } from "../components/FetchError";
import { getJST } from "../components/GetJST";
import HomeAccodion from "../components/HomeAccodion";
import ModalBox from "../components/window/ModalBox";
import AddTransactionListWindow from "../components/window/AddTransactionListWindow";
import BlurView from "../components/window/BlurView";
/** 外部コンポーネント */
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import { Pie } from "react-chartjs-2";
import { CSSTransition } from "react-transition-group";
import Sidebar from "../components/Sidebar";
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

const Home = (props) => {
  const { themeColor } = props;
  const navigate = useNavigate();
  const [isLoading, setLoading] = useState(false);
  const [cookie] = useCookies();

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
  const [sysDate, setSysDate] = useState(getJST(new Date()));
  sysDate.setDate(1);

  const transactionTitle = "支出または収入の入力";

  /** 収支合計 */
  const [monthlyTotalAmount, setMonthlyTotalAmount] = useState(0);

  /** アコーディオンデータ */
  const [homeAccodionDataList, setHomeAccodionDataList] = useState([]);

  /** カラーリスト */
  const dataColorList = [
    "#e57373",
    "#29b6f6",
    "#81c784",
    "#5c6bc0",
    "#f9a825",
    "#0097a7",
    "#9575cd",
    "#bdbdbd",
  ];

  /** グラフデータ */
  const data = {
    labels: homeAccodionDataList.map((e) => e.categoryName),
    datasets: [
      {
        data: homeAccodionDataList.map((e) => Math.abs(e.categoryTotalAmount)),
        backgroundColor: dataColorList,
        borderWidth: 0,
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
  };

  /** 追加ウィンドウ */
  const [modalWindow, openWindow] = useState(false);

  /** 追加用データ */
  const [transaction, setTransaction] = useState({});

  /** 一括入力ウィンドウ */
  const [tranList, openTranList] = useState(false);

  /** 一括入力用データ */
  const [transactionList, setTransactionList] = useState([]);

  /** 収支追加モーダル */
  const actions = [
    { icon: <AddIcon />, name: "追加", func: () => openWindow(true) },
    { icon: <ListIcon />, name: "一括入力", func: () => openTranList(true) },
  ];

  /** API関連 */
  // アコーディオンデータを取得
  const getInit = (month) => {
    setLoading(true);
    fetch(`${rootURI}/transaction/getHome`, {
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
        setHomeAccodionDataList(data.categoryList);
        setMonthlyTotalAmount(data.balance);
        setLoading(false);
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
  }, [setHomeAccodionDataList]);

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

          {/* 収支合計 */}
          <div className="monthlyTotalAmountTitleArea">
            <span>合計支出</span>
            <span
              style={
                monthlyTotalAmount >= 0
                  ? { color: "#1B5E20" }
                  : { color: "#B71C1C" }
              }
            >
              {monthlyTotalAmount.toLocaleString()}
            </span>
          </div>

          {isLoading === false && homeAccodionDataList.length === 0 && (
            <div className="dataNotFound">
              <p>データが登録されていません</p>
              <p>家計簿を入力して記録を開始しましょう</p>
            </div>
          )}

          <div className="dataArea">
            {isLoading ? (
              <div className="accodionDataArea">
                <div className="homeLoading loading"></div>
                <div className="homeLoading loading"></div>
                <div className="homeLoading loading"></div>
                <div className="homeLoading loading"></div>
                <div className="homeLoading loading"></div>
              </div>
            ) : (
              <div className="accodionDataArea">
                {homeAccodionDataList.map((data, index) => {
                  return (
                    <HomeAccodion
                      homeAccodionData={data}
                      bgcolor={dataColorList[index]}
                      key={index}
                    />
                  );
                })}
              </div>
            )}

            {/* グラフ */}
            <div className="pieGraph">
              <Pie data={data} options={option} className="pieGraph" />
            </div>
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

          {/* 追加ボタン */}
          <div className="addTransactionArea">
            <SpeedDial
              ariaLabel="SpeedDial openIcon example"
              sx={{ position: "absolute", bottom: 16, right: 16 }}
              icon={
                <SpeedDialIcon
                  onClick={() => openWindow(true)}
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
            setStatus={openWindow}
            setObject={setTransaction}
          />
          <CSSTransition
            in={modalWindow}
            timeout={200}
            unmountOnExit
            classNames="Modal-show"
          >
            <ModalBox
              transaction={transaction}
              setTransaction={setTransaction}
              openWindow={openWindow}
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
export default Home;
