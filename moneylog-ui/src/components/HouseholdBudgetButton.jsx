import React from "react";
/** CSS */
import "./components_CSS/HouseholdBudgeButton.css";
/** 外部コンポーネント */
import Fab from "@mui/material/Fab";
import AddIcon from "@mui/icons-material/Add";

const HouseholdBudgetButton = (props) => {
  const { openWindow, buttonText, setSavingTitle } = props;

  const openModalWindow = () => {
    openWindow(true);
    setSavingTitle("貯金を追加");
  };

  return (
    <div className="householdBudgetButton" onClick={() => openModalWindow()}>
      <div className="textArea">
        <p className="textStyle">{buttonText}</p>
      </div>

      <div className="buttonArea">
        <Fab color="info" aria-label="add" sx={{ boxShadow: "0" }}>
          <AddIcon />
        </Fab>
      </div>
    </div>
  );
};

export default HouseholdBudgetButton;
