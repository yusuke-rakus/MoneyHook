import React, { useState } from "react";
/** CSS */
import "./App.css";
/** 自作コンポーネント */
import Sidebar from "./components/Sidebar";
import Home from "./page/Home";
import Timeline from "./page/Timeline";
import MonthlyVariable from "./page/MonthlyVariable";
import MonthlyFixed from "./page/MonthlyFixed";
import SavingList from "./page/SavingList";
import TotalSaving from "./page/TotalSaving";
import SettingsPage from "./page/Settings";
/** 外部コンポーネント */
import { Routes, Route, BrowserRouter } from "react-router-dom";
import { useCookies } from "react-cookie";
import NotFound from "./page/NotFound";

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
        <Route path="/" element={<Home themeColor={themeColor} />}></Route>
        <Route path="/home" element={<Home themeColor={themeColor} />}></Route>
        <Route
          path="/timeline"
          element={<Timeline themeColor={themeColor} />}
        ></Route>
        <Route
          path="/monthlyVariable"
          element={<MonthlyVariable themeColor={themeColor} />}
        ></Route>
        <Route
          path="/monthlyFixed"
          element={<MonthlyFixed themeColor={themeColor} />}
        ></Route>
        <Route
          path="/savingList"
          element={<SavingList themeColor={themeColor} />}
        ></Route>
        <Route
          path="/totalSaving"
          element={<TotalSaving themeColor={themeColor} />}
        ></Route>
        <Route
          path="/settingsPage"
          element={
            <SettingsPage
              colorList={colorList}
              setColorList={setColorList}
              themeColor={themeColor}
              setThemeColor={setThemeColor}
            />
          }
        ></Route>
        <Route path="*" element={<NotFound />} />
      </Routes>
    </BrowserRouter>
  );
};

export default App;
