import React from "react";
/** CSS */
import "./components_CSS/SavingListData.css";
/** 外部コンポーネント */
import ChevronRightIcon from "@mui/icons-material/ChevronRight";

const SavingListData = (props) => {
  const { savingData, setAddSavingStatus, setSavingData } = props;

  const openEditModal = () => {
    setAddSavingStatus(true);
    setSavingData(savingData);
  };

  return (
    <>
      <ul>
        <li className="savingData" onClick={() => openEditModal()}>
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
    </>
  );
};

export default SavingListData;
