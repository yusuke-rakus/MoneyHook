import React from "react";

const BlurView = (props) => {
  const { status, setStatus } = props;

  const BlurStyle = {
    position: "fixed",
    left: "0",
    height: "100%",
    width: "100%",
    backgroundColor: "rgba(128, 128, 128, 0.7)",
  };

  const onClickEvent = () => {
    setStatus(false);
  };

  return (
    <>
      {status && (
        <div onClick={onClickEvent} className="blur" style={BlurStyle}></div>
      )}
    </>
  );
};
export default BlurView;
