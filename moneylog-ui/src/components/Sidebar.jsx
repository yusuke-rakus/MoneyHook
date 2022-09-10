import React from "react";
/** CSS */
import "./components_CSS/Sidebar.css";
/** 外部コンポーネント */
import { SidebarData } from "./SidebarData";
import { Link } from "react-router-dom";
import { useState } from "react";
import { LightenDarkenColor } from "lighten-darken-color";
import { style } from "@mui/system";

const Sidebar = () => {
  const themeColorCodeList = [
    "#76d5ff",
    "#607d8b",
    "#212121",
    "#43a047",
    "#00acc1",
    "#e53935",
    "#8e24aa",
  ];

  const themeColorGradientCodeList = [
    "#355C7D, #C06C84",
    "#11998e, #38ef7d",
    "#108dc7, #ef8e38",
    "#FC5C7D, #6A82FB",
    "#74ebd5, #ACB6E5",
    "#36D1DC, #5B86E5",
  ];

  const user = {
    // themeColorCode: themeColorCodeList[0],
    // themeColorGradientCode: "",
    themeColorCode: "",
    themeColorGradientCode: themeColorGradientCodeList[2],
  };

  if (!user.themeColorCode) {
    user.themeColorCode += user.themeColorGradientCode.slice(0, 7);
  }

  const [isHover, setHover] = useState("");

  return (
    <div
      className="sidebar"
      style={{
        background: user.themeColorGradientCode
          ? "linear-gradient(" + user.themeColorGradientCode + ")"
          : user.themeColorCode,
      }}
    >
      <ul className="sidebarList">
        {SidebarData.map((value, key) => {
          return (
            <Link
              to={value.link}
              key={key}
              id={window.location.pathname === value.link ? "active" : ""}
              className={"row" + (value.heading ? " heading" : "")}
              onMouseEnter={() => setHover(key)}
              onMouseLeave={() => setHover("")}
              style={{
                background:
                  isHover === key
                    ? LightenDarkenColor(user.themeColorCode, 0)
                    : "",
                boxShadow:
                  isHover === key
                    ? "inset 5px 5px 12px " +
                      LightenDarkenColor(user.themeColorCode, -20) +
                      ", inset -5px -5px 12px " +
                      LightenDarkenColor(user.themeColorCode, 20)
                    : "",
              }}
            >
              <div id="icon">{value.icon}</div>
              <div id="title">{value.title}</div>
            </Link>
          );
        })}
      </ul>
    </div>
  );
};
export default Sidebar;
