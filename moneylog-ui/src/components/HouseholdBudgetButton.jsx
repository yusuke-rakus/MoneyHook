import React from "react";
import Fab from "@mui/material/Fab";
import AddIcon from "@mui/icons-material/Add";
import "./components_CSS/HouseholdBudgeButton.css";

const HouseholdBudgetButton = (props) => {
  const { setModalWindow, buttonText, setData } = props;

  const openModalWindow = () => {
    setModalWindow(true);
    setData();
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
