import React, { useState } from "react";
import BlurView from "./BlurView";
import { Button } from "@mui/material";
import AddTargetBox from "./AddTargetBox";
import { CSSTransition } from "react-transition-group";

const AddTargetWindow = () => {
  const [addTargetStatus, setAddTargetStatus] = useState(true);

  return (
    <>
      <Button
        onClick={() => {
          setAddTargetStatus(true);
        }}
        variant="contained"
      >
        OpenModal
      </Button>
      <BlurView status={addTargetStatus} setStatus={setAddTargetStatus} />

      <CSSTransition
        in={addTargetStatus}
        timeout={100}
        unmountOnExit
        classNames="Modal-show"
      >
        <AddTargetBox setAddTargetStatus={setAddTargetStatus} />
      </CSSTransition>
    </>
  );
};
export default AddTargetWindow;
