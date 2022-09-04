import React, { useState } from "react";
import "./page_CSS/SavingList.css";
import "./page_CSS/common.css";
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import ChevronRightIcon from "@mui/icons-material/ChevronRight";
import AddSavingWindow from "../components/window/AddSavingWindow";
import { CSSTransition } from "react-transition-group";
import BlurView from "../components/window/BlurView";
import AddSavingBox from "../components/window/AddSavingBox";
import SavingListData from "../components/SavingListData";

const SavingList = () => {
  const [AddSavingStatus, setAddSavingStatus] = useState(false);

  /** 今月 */
  let date = new Date();
  let formatday = `${date.getFullYear()}-${date.getMonth() + 1}-1`;

  const totalSavingAmount = 1500000;

  const savingData = [
    {
      savingDate: 20,
      savingName: "タバコ",
      savingAmount: 500,
    },
    {
      savingDate: 20,
      savingName: "電車代",
      savingAmount: 200,
    },
    {
      savingDate: 20,
      savingName: "ジュース",
      savingAmount: 300,
    },
  ];

  return (
    <div className="container">
      {/* 月 */}
      <div className="month">
        <ArrowBackIosNewIcon fontSize="large" className="switchMonthButton" />
        <span>{date.getMonth() + 1}月</span>
        <ArrowForwardIosIcon fontSize="large" className="switchMonthButton" />
      </div>

      {/* 今月の貯金額 */}
      <div className="monthlyTotalSaving">
        <span>今月の貯金額</span>
        <span className="totalSavingAmount">
          {totalSavingAmount.toLocaleString()}
        </span>
      </div>

      {/* 貯金データ */}
      <div className="savingList">
        {savingData.map((data) => {
          return <SavingListData savingData={data} />;
        })}
      </div>

      {/* 貯金追加ボタン */}
      <div className="addSavingArea">
        <AddSavingWindow />
      </div>
    </div>
  );
};
export default SavingList;
