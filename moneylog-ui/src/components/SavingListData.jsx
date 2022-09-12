import React from "react";
/** CSS */
import "./components_CSS/SavingListData.css";
/** 外部コンポーネント */
import ChevronRightIcon from "@mui/icons-material/ChevronRight";

const SavingListData = (props) => {
  const { saving, setAddSavingStatus, setSaving, setSavingTitle } = props;

  const openEditModal = () => {
    setAddSavingStatus(true);
    setSaving(saving);
    setSavingTitle("貯金を編集");
  };

  return (
    <ul>
      <li className="savingData" onClick={() => openEditModal()}>
        <div className="savingDate">
          {`${new Date(saving.savingDate).getDate()}日`}
        </div>
        <div className="savingName">{saving.savingName}</div>
        <div className="savingAmount">
          {`¥${saving.savingAmount.toLocaleString()}`}
        </div>
        <span>
          <ChevronRightIcon />
        </span>
      </li>
    </ul>
  );
};

export default SavingListData;
