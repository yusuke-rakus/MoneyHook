import React from "react";
import "./page_CSS/SavingList.css";
import "./page_CSS/common.css";
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import ChevronRightIcon from "@mui/icons-material/ChevronRight";
import HouseholdBudgetButton from "../components/HouseholdBudgetButton";

const SavingList = () => {
  const month = 2;

  const totalSavingAmount = 1500000;

  const savingData = [
    {
      savingDate: 20,
      savingName: "タバコ",
      savingAmount: 500,
    },
    {
      savingDate: 20,
      savingName: "タバコ",
      savingAmount: 500,
    },
    {
      savingDate: 20,
      savingName: "タバコ",
      savingAmount: 500,
    },
    {
      savingDate: 20,
      savingName: "タバコ",
      savingAmount: 500,
    },
  ];

  return (
    <div className="container">
      <div className="month">
        <ArrowBackIosNewIcon fontSize="large" className="switchMonthButton" />
        <span>{month + "月"}</span>
        <ArrowForwardIosIcon fontSize="large" className="switchMonthButton" />
      </div>

      <div className="monthlyTotalSaving">
        <span>今月の貯金額</span>
        <span className="totalSavingAmount">
          {totalSavingAmount.toLocaleString()}
        </span>
      </div>

      <div className="savingList">
        <ul>
          {savingData.map((data) => {
            return (
              <li className="savingData">
                <div className="savingDate">{data.savingDate + "日"}</div>
                <div className="savingName">{data.savingName}</div>
                <div className="savingAmount">
                  {"¥" + data.savingAmount.toLocaleString()}
                </div>
                <span>
                  <ChevronRightIcon />
                </span>
              </li>
            );
          })}
        </ul>
      </div>

      <HouseholdBudgetButton />
    </div>
  );
};
export default SavingList;
