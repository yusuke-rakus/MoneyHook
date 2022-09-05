import React from "react";
/** CSS */
import "./components_CSS/TimelineDataList.css";
/** 外部コンポーネント */
import ChevronRightIcon from "@mui/icons-material/ChevronRight";

const TimelineDataList = (props) => {
  const { timeline, setTransaction, setModalWindow } = props;

  const editTransactionData = () => {
    setModalWindow(true);
    setTransaction(timeline);
  };

  return (
    <ul>
      <li className="transactionData" onClick={() => editTransactionData()}>
        <div className="transactionDate">{timeline.transactionDate + "日"}</div>
        <div className="transactionCategory">{timeline.categoryName}</div>
        <div className="transactionName">{timeline.transactionName}</div>
        <div className="transactionAmount">
          {timeline.transactionAmount < 0 ? "- " : ""}
          {"¥" + Math.abs(timeline.transactionAmount).toLocaleString()}
        </div>
        <span>
          <ChevronRightIcon />
        </span>
      </li>
    </ul>
  );
};

export default TimelineDataList;
