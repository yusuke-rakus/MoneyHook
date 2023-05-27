import React from "react";

const BlurView = (props) => {
  const { status, setStatus, setObject, listObject, setListObject } = props;

  const BlurStyle = {
    position: "fixed",
    left: "0",
    top: "0",
    height: "100%",
    width: "100%",
    backgroundColor: "rgba(128, 128, 128, 0.7)",
    zIndex: 2,
  };

  const onClickEvent = () => {
    setStatus(false);
    if (Array.isArray(listObject)) {
      setListObject([]);
    } else {
      setObject({});
    }
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
