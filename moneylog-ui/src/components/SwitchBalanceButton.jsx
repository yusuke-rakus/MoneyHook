import React, { useState } from "react";
import AddIcon from "@mui/icons-material/Add";
import RemoveIcon from "@mui/icons-material/Remove";
import "./components_CSS/SwitchBalanceButton.css";
import { CSSTransition } from "react-transition-group";

const SwitchBalanceButton = () => {
  const [switchBalance, setSwitchBalance] = useState({
    text: "支出",
    value: -1,
    backgroundColor: "#e31826",
    translateX: "0",
  });

  const handleChange = (e) => {
    if (switchBalance.value === -1) {
      setSwitchBalance({
        text: "収入",
        value: 1,
        backgroundColor: "#2b9900",
        circleTranslateX: "translateX(55px)",
        labelTraslateX: "translateX(-35px)",
      });
    } else {
      setSwitchBalance({
        text: "支出",
        value: -1,
        backgroundColor: "#e31826",
        circleTranslateX: "translateX(0)",
        labelTraslateX: "translateX(0)",
      });
    }
  };

  return (
    <>
      <div className="switch">
        <input
          onChange={handleChange}
          type="checkbox"
          id="switch-box"
          value={switchBalance.value}
        />
        <div
          onClick={handleChange}
          style={{
            backgroundColor: switchBalance.backgroundColor,
            transform: switchBalance.circleTranslateX,
          }}
          className="switch-circle"
        >
          {switchBalance.value === -1 && (
            <RemoveIcon fontSize="large" className="icon" />
          )}
          {switchBalance.value === 1 && (
            <AddIcon fontSize="large" className="icon" />
          )}
        </div>
        <label
          style={{ transform: switchBalance.labelTraslateX }}
          htmlFor="switch-box"
        >
          {switchBalance.text}
        </label>
      </div>

      <div className="box1">
        <div className="box2"></div>
      </div>
    </>
  );
};

export default SwitchBalanceButton;
