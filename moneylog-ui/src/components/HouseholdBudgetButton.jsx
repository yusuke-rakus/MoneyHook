import React, { useState } from "react";
import Fab from "@mui/material/Fab";
import AddIcon from "@mui/icons-material/Add";
import { CSSTransition } from "react-transition-group";
import "./components_CSS/HouseholdBudgeButton.css";

const HouseholdBudgetButton = (props) => {
  // 変数を宣言
  const { BoxText } = props;
  const [BoxStatus, setBoxStatus] = useState(true);

  return (
    <>
      <div className="add-house-hold-budget-button">
        <CSSTransition
          in={BoxStatus}
          timeout={500}
          unmountOnExit
          classNames="HouseholdBudgetButton-show"
        >
          <div className="HouseholdBudgetButton">
            <p className="box-text-style">{BoxText}</p>
          </div>
        </CSSTransition>
        <Fab
          onMouseEnter={() => setBoxStatus(true)}
          onMouseLeave={() => setBoxStatus(false)}
          className="button-style"
          color="info"
          aria-label="add"
        >
          <AddIcon />
        </Fab>
      </div>
    </>
  );
};

export default HouseholdBudgetButton;
