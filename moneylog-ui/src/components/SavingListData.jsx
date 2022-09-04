import React, { useState } from "react";
import "./components_CSS/SavingListData.css";
import ChevronRightIcon from "@mui/icons-material/ChevronRight";
import { CSSTransition } from "react-transition-group";
import ModalBox from "./window/ModalBox";
import BlurView from "./window/BlurView";
import AddSavingBox from "./window/AddSavingBox";

const SavingListData = (props) => {
  const [addSavingStatus, setAddSavingStatus] = useState(false);
  const { savingData } = props;

  return (
    <>
      <ul>
        <li className="savingData" onClick={() => setAddSavingStatus(true)}>
          <div className="savingDate">{savingData.savingDate + "日"}</div>
          <div className="savingName">{savingData.savingName}</div>
          <div className="savingAmount">
            {"¥" + savingData.savingAmount.toLocaleString()}
          </div>
          <span>
            <ChevronRightIcon />
          </span>
        </li>
      </ul>

      <BlurView status={addSavingStatus} setStatus={setAddSavingStatus} />

      <CSSTransition
        in={addSavingStatus}
        timeout={100}
        unmountOnExit
        classNames="Modal-show"
      >
        <AddSavingBox
          setAddSavingStatus={setAddSavingStatus}
          savingData={savingData}
          title={"貯金を編集"}
        />
      </CSSTransition>
    </>
  );
};

export default SavingListData;
