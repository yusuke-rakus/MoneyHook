import React from "react";
import { SidebarData } from "./SidebarData";
import { Link } from "react-router-dom";

const Sidebar = () => {
  return (
    <>
      <div className="Sidebar">
        <ul className="SidebarList">
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
    </>
  );
};
export default Sidebar;
