import React, { useState } from "react";
import "./components_CSS/TimelineDataList.css";
import ChevronRightIcon from "@mui/icons-material/ChevronRight";

const TimelineDataList = (props) => {
  const { timelineData, setTransactionData, setModalWindow } = props;

  const editTransactionData = () => {
    setModalWindow(true);
    setTransactionData(timelineData);
  };

  return (
    <ul>
      <li className="transactionData" onClick={() => editTransactionData()}>
        <div className="transactionDate">
          {timelineData.transactionDate + "日"}
        </div>
        <div className="transactionCategory">{timelineData.categoryName}</div>
        <div className="transactionName">{timelineData.transactionName}</div>
        <div className="transactionAmount">
          {timelineData.transactionAmount < 0 ? "- " : ""}
          {"¥" + Math.abs(timelineData.transactionAmount).toLocaleString()}
        </div>
        <span>
          <ChevronRightIcon />
        </span>
      </li>
    </ul>
  );
};

export default TimelineDataList;
