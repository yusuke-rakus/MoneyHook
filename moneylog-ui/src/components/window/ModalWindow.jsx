import React, { useState } from "react";
import { Button } from "@mui/material";
import "../components_CSS/window_CSS/ModalWindow.css";
import { CSSTransition } from "react-transition-group";
import ModalBox from "./ModalBox";
import BlurView from "./BlurView";
import HouseholdBudgetButton from "../HouseholdBudgetButton";

const ModalWindow = () => {
  const [modalWindow, setModalWindow] = useState(false);

  return (
    <>
      <HouseholdBudgetButton
        setModalWindow={setModalWindow}
        buttonText={"追加"}
      />

      <BlurView status={modalWindow} setStatus={setModalWindow} />
      <CSSTransition
        in={modalWindow}
        timeout={200}
        unmountOnExit
        classNames="Modal-show"
      >
        <ModalBox modalWindow={modalWindow} setModalWindow={setModalWindow} />
      </CSSTransition>
    </>
  );
};
export default ModalWindow;
