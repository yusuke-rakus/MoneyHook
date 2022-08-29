import React from "react";
import "./components_CSS/TimelineDataList.css";
import ChevronRightIcon from "@mui/icons-material/ChevronRight";

const TimelineDataList = (props) => {
  const { timelineDataList } = props;

  return (
    <>
      <ul>
        {timelineDataList.map((data) => {
          return (
            <li className="transactionData">
              <div className="transactionDate">
                {data.transactionDate + "日"}
              </div>
              <div className="transactionCategory">
                {data.transactionCategory}
              </div>
              <div className="transactionName">{data.transactionName}</div>
              <div className="transactionAmount">
                {"¥" + data.transactionAmount.toLocaleString()}
              </div>
              <span>
                <ChevronRightIcon />
              </span>
            </li>
          );
        })}
      </ul>
    </>
  );
};

export default TimelineDataList;
