import React, { useEffect, useState } from "react";
/** CSS */
import "./page_CSS/Home.css";
import "./page_CSS/common.css";
/** 自作コンポーネント */
import HomeAccodion from "../components/HomeAccodion";
import HouseholdBudgetButton from "../components/HouseholdBudgetButton";
import ModalBox from "../components/window/ModalBox";
import BlurView from "../components/window/BlurView";
/** 外部コンポーネント */
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import { Pie } from "react-chartjs-2";
import { CSSTransition } from "react-transition-group";

const Home = () => {
  /** 今月 */
  const [date, setDate] = useState(new Date("2022-06-01"));
  date.setDate(1);

  const [transactionTitle, setTransactionTitle] =
    useState("支出または収入の入力");

  /** 収支合計 */
  const [monthlyTotalAmount, setMonthlyTotalAmount] = useState(0);

  /** アコーディオンデータ */
  const [homeAccodionDataList, setHomeAccodionDataList] = useState([]);

  /** カラーリスト */
  const dataColorList = [
    "#e57373",
    "#29b6f6",
    "#81c784",
    "#f9a825",
    "#9575cd",
    "#bdbdbd",
    "#0097a7",
    "#9e9d24",
    "#ab47bc",
    "#3f51b5",
    "#78909c",
    "#8d6e63",
    "#ef6c00",
    "#26a69a",
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

  /** モーダルウィンドウ */
  const [modalWindow, openWindow] = useState(false);

  /** 登録用データ */
  const [transaction, setTransaction] = useState({});

  /** API関連 */
  const rootURI = "http://localhost:8080";

  useEffect(() => {
    fetch(`${rootURI}/transaction/getHome`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: "a77a6e94-6aa2-47ea-87dd-129f580fb669",
        month: date,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        setHomeAccodionDataList(data.categoryList);
        setMonthlyTotalAmount(data.balance);
      });
  }, [setHomeAccodionDataList]);

  return (
    <div className="container">
      {/* 月 */}
      <div className="month">
        <ArrowBackIosNewIcon fontSize="large" className="switchMonthButton" />
        <span>{date.getMonth() + 1}月</span>
        <ArrowForwardIosIcon fontSize="large" className="switchMonthButton" />
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
      <div className="dataArea">
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

        {/* グラフ */}
        <div className="pieGraph">
          <Pie data={data} options={option} className="pieGraph" />
        </div>
      </div>

      {/* 追加ボタン */}
      <div className="addTransactionArea">
        <HouseholdBudgetButton
          openWindow={openWindow}
          buttonText={"追加"}
          setTransactionTitle={setTransactionTitle}
        />
      </div>

      {/* 取引追加画面 */}
      <BlurView
        status={modalWindow}
        setStatus={openWindow}
        setTransaction={setTransaction}
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
        />
      </CSSTransition>
    </div>
  );
};
export default Home;
