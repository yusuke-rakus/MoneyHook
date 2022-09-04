import React from "react";
/** CSS */
import "./components_CSS/Sidebar.css";
/** 外部コンポーネント */
import { SidebarData } from "./SidebarData";
import { Link } from "react-router-dom";

const Sidebar = () => {
  return (
    <div className="sidebar">
      <ul className="sidebarList">
        {SidebarData.map((value, key) => {
          return (
            <Link
              to={value.link}
              key={key}
              id={window.location.pathname === value.link ? "active" : ""}
              className={"row" + (value.heading ? " heading" : "")}
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
