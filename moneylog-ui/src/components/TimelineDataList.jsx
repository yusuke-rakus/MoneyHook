import React from "react";
/** CSS */
import "./components_CSS/TimelineDataList.css";
/** 外部コンポーネント */
import ChevronRightIcon from "@mui/icons-material/ChevronRight";

const TimelineDataList = (props) => {
  const { timeline, setTransaction, setModalWindow, setTransactionTitle } =
    props;

  const editTransactionData = () => {
    setModalWindow(true);
    setTransaction(timeline);
    setTransactionTitle("収支の編集");
  };

  return (
    <ul>
      <li className="transactionData" onClick={() => editTransactionData()}>
        <div className="transactionDate">
          {`${new Date(timeline.transactionDate).getDate()}日`}
        </div>
        <div className="transactionCategory">{timeline.categoryName}</div>
        <div className="transactionName">{timeline.transactionName}</div>
        <div className="transactionAmount">
          {timeline.transactionAmount < 0 ? "-" : ""}
          {`${Math.abs(timeline.transactionAmount).toLocaleString()}円`}
        </div>
        <span>
          <ChevronRightIcon />
        </span>
      </li>
    </ul>
  );
};

export default TimelineDataList;
