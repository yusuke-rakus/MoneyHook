import React from "react";
import "./page_CSS/SavingList.css";
import "./page_CSS/common.css";
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import ChevronRightIcon from "@mui/icons-material/ChevronRight";
import HouseholdBudgetButton from "../components/HouseholdBudgetButton";

const SavingList = () => {
  return (
    <>
      <div className="container">
        <div className="container-box">
          <div className="month">
            <ArrowBackIosNewIcon
              fontSize="large"
              className="switch-month-button"
            />
            <span>6月</span>
            <ArrowForwardIosIcon
              fontSize="large"
              className="switch-month-button"
            />
          </div>

          <div className="monthly-total-saving">
            <span>今月の貯金額</span>
            <span className="total-saving-amount">1,200,000</span>
          </div>

          <div className="saving-list">
            <ul>
              <li className="saving-data">
                <div className="saving-date">20日</div>
                <div className="saving-name">タバコ</div>
                <div className="saving-amount">¥500</div>
                <span>
                  <ChevronRightIcon />
                </span>
              </li>
              <li className="saving-data">
                <div className="saving-date">20日</div>
                <div className="saving-name">タバコ</div>
                <div className="saving-amount">¥500</div>
                <span>
                  <ChevronRightIcon />
                </span>
              </li>
              <li className="saving-data">
                <div className="saving-date">20日</div>
                <div className="saving-name">タバコ</div>
                <div className="saving-amount">¥500</div>
                <span>
                  <ChevronRightIcon />
                </span>
              </li>
              <li className="saving-data">
                <div className="saving-date">20日</div>
                <div className="saving-name">タバコ</div>
                <div className="saving-amount">¥500</div>
                <span>
                  <ChevronRightIcon />
                </span>
              </li>
              <li className="saving-data">
                <div className="saving-date">20日</div>
                <div className="saving-name">タバコ</div>
                <div className="saving-amount">¥500</div>
                <span>
                  <ChevronRightIcon />
                </span>
              </li>
            </ul>
          </div>
        </div>
        <HouseholdBudgetButton />
      </div>
    </>
  );
};
export default SavingList;
