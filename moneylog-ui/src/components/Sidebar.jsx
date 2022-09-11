import React from "react";
/** CSS */
import "./components_CSS/Sidebar.css";
/** 自作コンポーネント */
/** 外部コンポーネント */
import { SidebarData } from "./SidebarData";
import { Link } from "react-router-dom";
import { useState } from "react";
import { LightenDarkenColor } from "lighten-darken-color";

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
            : "linear-gradient(" + themeColor + ")",
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
                boxShadow:
                  isHover === key
                    ? "inset 5px 5px 12px " +
                      LightenDarkenColor(bgcolor, -20) +
                      ", inset -5px -5px 12px " +
                      LightenDarkenColor(bgcolor, 20)
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
