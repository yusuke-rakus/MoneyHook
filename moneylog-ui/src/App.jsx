import React, { useEffect, useState } from "react";
/** CSS */
import "./App.css";
/** 自作コンポーネント */
import Home from "./page/Home";
import Timeline from "./page/Timeline";
import MonthlyVariable from "./page/MonthlyVariable";
import MonthlyFixed from "./page/MonthlyFixed";
import SavingList from "./page/SavingList";
import TotalSaving from "./page/TotalSaving";
import SettingsPage from "./page/Settings";
/** 外部コンポーネント */
import { Routes, Route, BrowserRouter, Navigate } from "react-router-dom";
import { useCookies } from "react-cookie";
import NotFound from "./page/NotFound";
import Login from "./page/Login";

const App = () => {
  /** 背景色のリスト */
  const [colorList, setColorList] = useState({
    themeColorCodeList: [],
    themeColorGradientCodeList: [],
  });

  const [cookie, setCookie] = useCookies();

  const [themeColor, setThemeColor] = useState(
    cookie.themeColor === void 0 ? "#76d5ff" : cookie.themeColor
  );

  return (
    <BrowserRouter>
      <Routes>
        <Route path="login" element={<Login setCookie={setCookie} />}></Route>
        <Route
          path="/"
          element={
            !cookie.userId ? (
              <Navigate to="/login" />
            ) : (
              <Home themeColor={themeColor} />
            )
          }
        ></Route>
        <Route
          path="/home"
          element={
            !cookie.userId ? (
              <Navigate to="/login" />
            ) : (
              <Home themeColor={themeColor} />
            )
          }
        ></Route>
        <Route
          path="/timeline"
          element={
            !cookie.userId ? (
              <Navigate to="/login" />
            ) : (
              <Timeline themeColor={themeColor} />
            )
          }
        ></Route>
        <Route
          path="/monthlyVariable"
          element={
            !cookie.userId ? (
              <Navigate to="/login" />
            ) : (
              <MonthlyVariable themeColor={themeColor} />
            )
          }
        ></Route>
        <Route
          path="/monthlyFixed"
          element={
            !cookie.userId ? (
              <Navigate to="/login" />
            ) : (
              <MonthlyFixed themeColor={themeColor} />
            )
          }
        ></Route>
        <Route
          path="/savingList"
          element={
            !cookie.userId ? (
              <Navigate to="/login" />
            ) : (
              <SavingList themeColor={themeColor} />
            )
          }
        ></Route>
        <Route
          path="/totalSaving"
          element={
            !cookie.userId ? (
              <Navigate to="/login" />
            ) : (
              <TotalSaving themeColor={themeColor} />
            )
          }
        ></Route>
        <Route
          path="/settingsPage"
          element={
            !cookie.userId ? (
              <Navigate to="/login" />
            ) : (
              <SettingsPage
                colorList={colorList}
                setColorList={setColorList}
                themeColor={themeColor}
                setThemeColor={setThemeColor}
              />
            )
          }
        ></Route>
        <Route path="*" element={<NotFound />} />
      </Routes>
    </BrowserRouter>
  );
};

export default App;
