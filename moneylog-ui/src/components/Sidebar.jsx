import React, { useContext } from "react";
import { useState } from "react";
import { LightenDarkenColor } from "lighten-darken-color";
import { Link } from "react-router-dom";
/** CSS */
import "./components_CSS/Sidebar.css";
/** 自作コンポーネント */
import { isTabletOrMobile } from "../App";
import { SidebarData } from "./SidebarData";
/** 外部コンポーネント */
import Tabs from "@mui/material/Tabs";
import Tab from "@mui/material/Tab";

const Sidebar = (props) => {
  const { themeColor } = props;

  let bgcolor = "";
  if (themeColor.length > 7) {
    bgcolor = themeColor.slice(0, 7);
  } else {
    bgcolor = themeColor;
  }

  const [isHover, setHover] = useState("");

  const isOverLabtop = useContext(isTabletOrMobile);

  return (
    <>
      {isOverLabtop ? (
        <>
          {/* PC用レスポンシブ */}
          <div className="blank"></div>
          <div
            className="sidebar"
            style={{
              background:
                themeColor.length === 7
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
                          isHover === key ||
                          window.location.pathname === value.link
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
        </>
      ) : (
        <>
          {/* タブレット用レスポンシブ */}
          <div className="tabletBlank">
            <Tabs
              variant="scrollable"
              className="tableSidebar"
              scrollButtons={false}
              sx={{ zIndex: 1 }}
              textColor="#ffffff"
              TabIndicatorProps={{
                sx: {
                  backgroundColor: "#ffffff",
                },
              }}
              value={SidebarData.findIndex((d) => {
                return d.link === window.location.pathname;
              })}
              style={{
                background:
                  themeColor.length === 7
                    ? themeColor
                    : `linear-gradient(${themeColor})`,
              }}
            >
              {SidebarData.map((value, index) => {
                return (
                  <Tab
                    key={index}
                    icon={value.icon}
                    label={value.title}
                    sx={{ color: "#fafafa" }}
                    LinkComponent={Link}
                    to={value.link}
                  />
                );
              })}
            </Tabs>
          </div>
        </>
      )}
    </>
  );
};
export default Sidebar;
