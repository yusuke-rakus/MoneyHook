import React from "react";
/** CSS */
import "./components_CSS/Sidebar.css";
/** 自作コンポーネント */
/** 外部コンポーネント */
import { SidebarData } from "./SidebarData";
import { Link } from "react-router-dom";
import { useState } from "react";
import { LightenDarkenColor } from "lighten-darken-color";
import { NoEncryption } from "@mui/icons-material";

const Sidebar = (props) => {
  const { themeColor } = props;

  let bgcolor = "";
  if (themeColor.length > 7) {
    bgcolor = themeColor.slice(0, 7);
  } else {
    bgcolor = themeColor;
  }

  const [isHover, setHover] = useState("");

  return (
    <div
      className="sidebar"
      style={{
        background:
          themeColor.length == 7
            ? themeColor
            : `linear-gradient(${themeColor})`,
      }}
    >
      <ul className="sidebarList">
        {SidebarData.map((value, key) => {
          return (
            <li key={key}>
              <Link
                to={value.link}
                className={"row" + (value.heading ? " heading" : "")}
                onMouseEnter={() => setHover(key)}
                onMouseLeave={() => setHover("")}
                style={{
                  cursor: "pointer",
                  borderRadius: "10px",
                  userSelect: "none",
                  boxShadow:
                    isHover === key || window.location.pathname === value.link
                      ? `inset 5px 5px 12px 
                      ${LightenDarkenColor(bgcolor, -25)} 
                      , inset -5px -5px 12px 
                      ${LightenDarkenColor(bgcolor, 25)}`
                      : "",
                }}
              >
                <div id="icon">{value.icon}</div>
                <div id="title">{value.title}</div>
              </Link>
            </li>
          );
        })}
      </ul>
    </div>
  );
};
export default Sidebar;
