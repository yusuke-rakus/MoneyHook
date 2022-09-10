import React, { useState } from "react";
/** CSS */
import "./page_CSS/SavingList.css";
import "./page_CSS/common.css";
/** 自作コンポーネント */
import SavingListData from "../components/SavingListData";
import AddSavingBox from "../components/window/AddSavingBox";
import HouseholdBudgetButton from "../components/HouseholdBudgetButton";
import BlurView from "../components/window/BlurView";
/** 外部コンポーネント */
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import { CSSTransition } from "react-transition-group";

const SavingList = () => {
  const [AddSavingStatus, setAddSavingStatus] = useState(false);

  /** 今月 */
  let date = new Date();
  let formatday = `${date.getFullYear()}-${date.getMonth() + 1}-1`;

  const totalSavingAmount = 1500000;

  const savingDataList = [
    {
      savingDate: "2022-07-25",
      savingName: "タバコ",
      savingAmount: 500,
    },
    {
      savingDate: "2022-07-25",
      savingName: "電車代",
      savingAmount: 200,
    },
    {
      savingDate: "2022-07-25",
      savingName: "ジュース",
      savingAmount: 300,
    },
  ];

  const [saving, setSaving] = useState({});

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
        {savingDataList.map((data, i) => {
          return (
            <SavingListData
              key={i}
              setAddSavingStatus={setAddSavingStatus}
              saving={data}
              setSaving={setSaving}
            />
          );
        })}
      </div>

      {/* 貯金追加ボタン */}
      <div className="addSavingArea">
        <HouseholdBudgetButton
          openWindow={setAddSavingStatus}
          buttonText={"貯金"}
          setData={setSaving}
        />
      </div>

      {/* 貯金追加ウィンドウ */}
      <BlurView
        status={AddSavingStatus}
        setStatus={setAddSavingStatus}
        setObject={setSaving}
      />
      <CSSTransition
        in={AddSavingStatus}
        timeout={100}
        unmountOnExit
        classNames="Modal-show"
      >
        <AddSavingBox
          title={"貯金を追加"}
          setAddSavingStatus={setAddSavingStatus}
          saving={saving}
          setSaving={setSaving}
        />
      </CSSTransition>
    </div>
  );
};
export default SavingList;
