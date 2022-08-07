import React, { useState } from "react";
import "./components_CSS/AddSavingWindow.css";
import AddSavingBox from "./AddSavingBox";
import { Button } from "@mui/material";
import { CSSTransition } from "react-transition-group";
import BlurView from "./BlurView";

const AddSavingWindow = () => {
  const [AddSavingStatus, setAddSavingStatus] = useState(true);

  return (
    <>
      <Button
        onClick={() => {
          setAddSavingStatus(true);
        }}
        variant="contained"
      >
        OpenModal
      </Button>

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
