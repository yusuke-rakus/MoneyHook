import React from "react";
import "./page_CSS/SavingList.css";
import "./page_CSS/common.css";
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import ChevronRightIcon from "@mui/icons-material/ChevronRight";
import HouseholdBudgetButton from "../components/HouseholdBudgetButton";

const SavingList = () => {
  return (
    <div className="container">
      <div className="containerBox">
        <div className="month">
          <ArrowBackIosNewIcon fontSize="large" className="switchMonthButton" />
          <span>6月</span>
          <ArrowForwardIosIcon fontSize="large" className="switchMonthButton" />
        </div>

        <div className="monthlyTotalSaving">
          <span>今月の貯金額</span>
          <span className="totalSavingAmount">1,200,000</span>
        </div>

        <div className="savingList">
          <ul>
            <li className="savingData">
              <div className="savingDate">20日</div>
              <div className="savingName">タバコ</div>
              <div className="savingAmount">¥500</div>
              <span>
                <ChevronRightIcon />
              </span>
            </li>
            <li className="savingData">
              <div className="savingDate">20日</div>
              <div className="savingName">タバコ</div>
              <div className="savingAmount">¥500</div>
              <span>
                <ChevronRightIcon />
              </span>
            </li>
            <li className="savingData">
              <div className="savingDate">20日</div>
              <div className="savingName">タバコ</div>
              <div className="savingAmount">¥500</div>
              <span>
                <ChevronRightIcon />
              </span>
            </li>
            <li className="savingData">
              <div className="savingDate">20日</div>
              <div className="savingName">タバコ</div>
              <div className="savingAmount">¥500</div>
              <span>
                <ChevronRightIcon />
              </span>
            </li>
            <li className="savingData">
              <div className="savingDate">20日</div>
              <div className="savingName">タバコ</div>
              <div className="savingAmount">¥500</div>
              <span>
                <ChevronRightIcon />
              </span>
            </li>
          </ul>
        </div>
      </div>
      <HouseholdBudgetButton />
    </div>
  );
};
export default SavingList;
