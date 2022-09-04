import React, { useState } from "react";
import "../components_CSS/window_CSS/AddSavingWindow.css";
import AddSavingBox from "./AddSavingBox";
import { Button } from "@mui/material";
import { CSSTransition } from "react-transition-group";
import BlurView from "./BlurView";
import HouseholdBudgetButton from "../HouseholdBudgetButton";

const AddSavingWindow = () => {
  const [AddSavingStatus, setAddSavingStatus] = useState(false);

  return (
    <>
      <HouseholdBudgetButton
        setModalWindow={setAddSavingStatus}
        buttonText={"貯金"}
      />

      <BlurView status={AddSavingStatus} setStatus={setAddSavingStatus} />

      <CSSTransition
        in={AddSavingStatus}
        timeout={100}
        unmountOnExit
        classNames="Modal-show"
      >
        <AddSavingBox setAddSavingStatus={setAddSavingStatus} />
      </CSSTransition>
    </>
  );
};

export default AddSavingWindow;
