import React, { useState } from "react";
import "./components_CSS/TimelineDataList.css";
import ChevronRightIcon from "@mui/icons-material/ChevronRight";
import { CSSTransition } from "react-transition-group";
import ModalBox from "./window/ModalBox";
import BlurView from "./window/BlurView";

const TimelineDataList = (props) => {
  const [modalWindow, setModalWindow] = useState(false);
  const { timelineData } = props;

  const editTransactionData = () => {
    setModalWindow(true);
  };

  return (
    <>
      <ul>
        <li className="transactionData" onClick={() => editTransactionData()}>
          <div className="transactionDate">
            {timelineData.transactionDate + "日"}
          </div>
          <div className="transactionCategory">{timelineData.categoryName}</div>
          <div className="transactionName">{timelineData.transactionName}</div>
          <div className="transactionAmount">
            {"¥" + Math.abs(timelineData.transactionAmount).toLocaleString()}
          </div>
          <span>
            <ChevronRightIcon />
          </span>
        </li>
      </ul>

      <BlurView status={modalWindow} setStatus={setModalWindow} />
      <CSSTransition
        in={modalWindow}
        timeout={200}
        unmountOnExit
        classNames="Modal-show"
      >
        <ModalBox
          modalWindow={modalWindow}
          setModalWindow={setModalWindow}
          transactionData={timelineData}
        />
      </CSSTransition>
    </>
  );
};

export default TimelineDataList;
